package com.joyerf.livelihoodchain.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joyerf.livelihoodchain.R;
import com.joyerf.livelihoodchain.datastruct.CreateAccountResult;
import com.joyerf.livelihoodchain.utils.SDCardUtils;
import com.joyerf.livelihoodchain.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jiezongchang on 2018/2/8.
 */

public class BackupAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private View mBackup;
    private TextView mAddressTextView;
    public final static String NEW_ACCOUNT = "NEW_ACCOUNT";
    private CreateAccountResult mAccountResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);
        mBackup = findViewById(R.id.backup_tv);
        mBackup.setOnClickListener(this);
        mAddressTextView = (TextView)findViewById(R.id.address_tv);
        findViewById(R.id.backup_skip_tv).setOnClickListener(this);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backup_skip_tv:
                finish();
                break;
            case R.id.backup_tv:
                showStartBackupDialog();
                break;
        }
    }

    private void handleIntent(Intent intent) {
        try {
            mAccountResult = intent.getParcelableExtra(NEW_ACCOUNT);
            if(mAccountResult != null) {
                mAddressTextView.setText(mAccountResult.account);
            }
        } catch (Exception e) {
            Logger.e(e, "handleIntent");
        }
    }

    private String storeAccount(CreateAccountResult accountResult) {
        if (accountResult == null) return null;
        String filePath = SDCardUtils.getSDcardPath() + accountResult.account + ".dat";
        File file = new File(filePath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            Gson gson = new GsonBuilder().create();
            String info = gson.toJson(accountResult);
            fos.write(info.getBytes());
            Logger.d("storeAccount %s %s", filePath, info);
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    Logger.e(e, "close fail");
                }
            }
        }
        return null;
    }

    private void showBackupDialog(String filePath) {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setIcon(null);
        normalDialog.setTitle(R.string.backup_success_title);
        normalDialog.setMessage(getString(R.string.backup_success_content, filePath));
        normalDialog.setPositiveButton(getString(R.string.sure),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        normalDialog.show();
    }

    private void showStartBackupDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setIcon(null);
        normalDialog.setTitle(R.string.keep_password_attention);
        normalDialog.setMessage(getString(R.string.keep_password_attention_content));
        normalDialog.setPositiveButton(getString(R.string.sure),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(mAccountResult != null){
                            String srorePath = storeAccount(mAccountResult);
                            if(TextUtils.isEmpty(srorePath)){
                                showBackupDialog(srorePath);
                            } else {
                                ToastUtils.showToast(BackupAccountActivity.this, "备份失败");
                            }
                        }
                    }
                });
        normalDialog.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        normalDialog.show();
    }

}
