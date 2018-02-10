package com.joyerf.livelihoodchain.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.joyerf.livelihoodchain.R;
import com.joyerf.livelihoodchain.datastruct.ChainAccount;
import com.joyerf.livelihoodchain.datastruct.CreateAccountResult;
import com.joyerf.livelihoodchain.db.GreenDaoManager;
import com.joyerf.livelihoodchain.model.CreateAccountModel;
import com.joyerf.livelihoodchain.utils.ToastUtils;

/**
 * Created by jiezongchang on 2018/1/28.
 */

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener, CreateAccountModel.OnCreateAccountListener {
    View mBackView;
    TextView mTitleView;
    EditText mFirstEditText;
    EditText mTwiceEditText;
    View mCreateView;
    View mImportView;
    AlertDialog mAlertDialog;
    CreateAccountModel mCreateAccountModel = new CreateAccountModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        mBackView = findViewById(R.id.title_bar_back);
        mBackView.setOnClickListener(this);
        mTitleView = (TextView) findViewById(R.id.title_bar_content);
        mTitleView.setText(R.string.create_account_title);
        mFirstEditText = (EditText) findViewById(R.id.set_first_pwd);
        mTwiceEditText = (EditText) findViewById(R.id.set_twice_pwd);
        mCreateView = findViewById(R.id.tv_create);
        mCreateView.setOnClickListener(this);
        mImportView = findViewById(R.id.tv_import_account);
        mImportView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (R.id.title_bar_back == view.getId()) {
            finish();
        } else if (R.id.tv_create == view.getId()) {
            if (mFirstEditText.getText().length() < 8) {
                ToastUtils.showToast(this, R.string.set_first_pwd_error);
                return;
            }
            String firstPwd = mFirstEditText.getText().toString();
            if (!firstPwd.equals(mTwiceEditText.getText().toString())) {
                ToastUtils.showToast(this, R.string.set_twice_pwd_error);
                return;
            }
            showAlertDialog(firstPwd);
        } else if (R.id.tv_import_account == view.getId()) {
            finish();
        }
    }

    @Override
    public void onCreateAccount(CreateAccountResult account) {
        if (account == null) {
            ToastUtils.showToast(this, R.string.create_account_error);
        } else if ("100".equals(account.code)) {
            ToastUtils.showToast(this, R.string.create_account_success);
            GreenDaoManager.getInstance().getDaoSession().insertOrReplace(new ChainAccount(account));
            finish();
            Intent intent = new Intent(this, BackupAccountActivity.class);
            intent.putExtra(BackupAccountActivity.NEW_ACCOUNT, account);
            startActivity(intent);
        } else {
            ToastUtils.showToast(this, account.msg);
            finish();
        }
    }

    private void showAlertDialog(final String password) {
        if (mAlertDialog == null) {
            final AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(this);
            normalDialog.setIcon(null);
            normalDialog.setTitle(R.string.keep_password_attention);
            normalDialog.setMessage(getString(R.string.keep_password_attention_content));
            normalDialog.setPositiveButton(getString(R.string.sure),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mCreateAccountModel.createAccount(password, CreateAccountActivity.this);
                        }
                    });
            normalDialog.setNegativeButton(R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            mAlertDialog = normalDialog.create();
        }
        mAlertDialog.show();
    }
}
