package com.joyerf.livelihoodchain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyerf.livelihoodchain.datastruct.ChainAccount;
import com.joyerf.livelihoodchain.datastruct.ChainMoney;
import com.joyerf.livelihoodchain.db.GreenDaoManager;
import com.joyerf.livelihoodchain.gen.ChainAccountDao;
import com.joyerf.livelihoodchain.model.GetMoneyModel;
import com.joyerf.livelihoodchain.utils.ThreadUtils;

import java.util.List;

/**
 * Created by jiezongchang on 2018/1/27.
 */

public class HomeFragment extends Fragment implements GetMoneyModel.OnGetMoneyListener{
    ImageView mAddImageView;
    TextView mMoneyTextView;
    GetMoneyModel mGetMoneyModel = new GetMoneyModel();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mAddImageView = (ImageView)view.findViewById(R.id.home_add_iv);
        mMoneyTextView = (TextView)view.findViewById(R.id.account_money);
        mAddImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), CreateAccountActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<ChainAccount> accounts = GreenDaoManager.getInstance().getDaoSession().getChainAccountDao().loadAll();
        if(accounts != null && accounts.size() > 0){
            mGetMoneyModel.getMoney(accounts.get(0).getAccount(), this);
        }
    }

    @Override
    public void onGetMoney(final ChainMoney money) {
        if(money != null){
            if(ThreadUtils.isMainThread()){
                mMoneyTextView.setText(money.balance);
            } else {
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMoneyTextView.setText(money.balance);
                    }
                });
            }

        }
    }
}
