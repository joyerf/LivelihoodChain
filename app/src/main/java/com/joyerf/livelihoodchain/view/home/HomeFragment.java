package com.joyerf.livelihoodchain.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joyerf.livelihoodchain.R;
import com.joyerf.livelihoodchain.datastruct.Announcement;
import com.joyerf.livelihoodchain.datastruct.ChainAccount;
import com.joyerf.livelihoodchain.datastruct.ChainMoney;
import com.joyerf.livelihoodchain.datastruct.MarketPrice;
import com.joyerf.livelihoodchain.db.GreenDaoManager;
import com.joyerf.livelihoodchain.model.GetAnnouncementModel;
import com.joyerf.livelihoodchain.model.GetMarketModel;
import com.joyerf.livelihoodchain.model.GetMoneyModel;
import com.joyerf.livelihoodchain.utils.BackGroundHandler;
import com.joyerf.livelihoodchain.utils.PreferenceUtils;
import com.joyerf.livelihoodchain.utils.ThreadUtils;
import com.joyerf.livelihoodchain.view.CreateAccountActivity;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by jiezongchang on 2018/1/27.
 */

public class HomeFragment extends Fragment implements GetMoneyModel.OnGetMoneyListener, GetAnnouncementModel.OnGetAnnouncementListener, GetMarketModel.OnGetMarketListener , View.OnClickListener{
    private TextView mMoneyTextView;
    private TextView mAnnouncementTextView;
    private GetMoneyModel mGetMoneyModel = new GetMoneyModel();
    private GetMarketModel mGetMarketModel = new GetMarketModel();
    private GetAnnouncementModel mGetAnnouncementModel = new GetAnnouncementModel();
    private RecyclerView mMarketView;
    private MarketAdapter mMarketAdapter = new MarketAdapter();
    private BackGroundHandler mBgHandler = new BackGroundHandler("LocalRequire");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        View addImageView = view.findViewById(R.id.home_add_iv);
        mMoneyTextView = (TextView) view.findViewById(R.id.account_money);
        addImageView.setOnClickListener(this);
        mAnnouncementTextView = (TextView) view.findViewById(R.id.notification_content_tv);
        view.findViewById(R.id.home_notification_rl).setOnClickListener(this);
        mMarketView = (RecyclerView) view.findViewById(R.id.rv_list);
        mMarketView.setAdapter(mMarketAdapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //Header这个条目将占用SpanCount()列数
                return mMarketAdapter.isHeader(position) ? layoutManager.getSpanCount() : 1;
            }
        });
        mMarketView.setLayoutManager(layoutManager);
        mMarketView.addItemDecoration(new MarketItemDecoration());
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<ChainAccount> accounts = GreenDaoManager.getInstance().getDaoSession().getChainAccountDao().loadAll();
        for (ChainAccount account : accounts) {
            if (account != null) {
                mGetMoneyModel.getMoney(account.getAccount(), this);
            }
        }
        mGetMarketModel.getMarket(this);
        mGetAnnouncementModel.getAnnouncement(this);
    }

    @Override
    public void onGetMoney(final String accountName, final ChainMoney money) {
        if (money != null) {
            if (ThreadUtils.isMainThread()) {
                mMoneyTextView.setText(money.getBalance());
            } else {
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMoneyTextView.setText(money.getBalance());
                    }
                });
            }

        }
    }

    @Override
    public void onGetAnnouncement(final Announcement announcement) {
        if (announcement != null) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showAnnouncement(announcement);
                }
            });
        }
    }

    @Override
    public void onGetMarket(final List<MarketPrice> prices) {
        if (prices != null) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showMarket(prices);
                }
            });
        }
    }

    private void initView() {
        mBgHandler.post(new Runnable() {
            @Override
            public void run() {
                //本地数据
                Announcement announcement = PreferenceUtils.getInstance().getAnnouncement();
                showAnnouncement(announcement);
                List<MarketPrice> prices = PreferenceUtils.getInstance().queryMarket();
                showMarket(prices);
            }
        });
    }

    private void showAnnouncement(final Announcement announcement) {
        Logger.d("showAnnouncement " + announcement);
        if (announcement == null) return;
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAnnouncementTextView.setText(announcement.activityTitle);
            }
        });
    }

    private void showMarket(final List<MarketPrice> prices) {
        if(prices == null) return;
        Logger.d("showMarket " + prices.size());
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMarketAdapter.setMarketList(prices);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_notification_rl:
                break;
            case R.id.home_add_iv:
                getActivity().startActivity(new Intent(getActivity(), CreateAccountActivity.class));
                break;
        }
    }
}
