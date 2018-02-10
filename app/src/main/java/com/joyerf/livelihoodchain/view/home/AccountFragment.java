package com.joyerf.livelihoodchain.view.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyerf.livelihoodchain.R;
import com.joyerf.livelihoodchain.datastruct.ChainAccount;
import com.joyerf.livelihoodchain.db.GreenDaoManager;
import com.joyerf.livelihoodchain.gen.ChainAccountDao;
import com.joyerf.livelihoodchain.utils.ToastUtils;
import com.joyerf.livelihoodchain.view.CreateAccountActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by jiezongchang on 2018/1/27.
 */

public class AccountFragment extends Fragment implements ViewPager.OnPageChangeListener {
    ImageView mAddImageView;
    ViewIndicator mPageIndicator;
    ViewPager mViewPager;
    AccountAdapter mAccountAdapter = new AccountAdapter();
    static class SettingsItem{
        private int layoutId;
        private int imgId;
        private String title;

        SettingsItem(int layoutId, int resId, String title){
            this.layoutId = layoutId;
            imgId = resId;
            this.title = title;
        }
    }
    List<SettingsItem> mSettingsItems = Collections.unmodifiableList(Arrays.asList(
            new SettingsItem(R.id.notification_center_layout, 0, "消息通知"),
            new SettingsItem(R.id.help_center_layout,0, "帮助中心"),
            new SettingsItem(R.id.contact_us_layout,0, "联系我们"),
            new SettingsItem(R.id.more_layout,0, "更多设置")
    ));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        mAddImageView = (ImageView)view.findViewById(R.id.home_add_iv);
        mAddImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), CreateAccountActivity.class));
            }
        });
        mViewPager =(ViewPager)view.findViewById(R.id.viewPager);
        mPageIndicator = (ViewIndicator)view.findViewById(R.id.page_indicator);
        mViewPager.setAdapter(mAccountAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
        for (final SettingsItem item : mSettingsItems){
            View viewItem = view.findViewById(item.layoutId);
            TextView titleView = (TextView)viewItem.findViewById(R.id.tip_tv);
            titleView.setText(item.title);
            viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showToast(getContext(), item.title);
                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewPager.removeOnPageChangeListener(this);
    }

    private void initData(){
        List<ChainAccount> accounts = getChainAccounts();
        if(accounts != null) {
            mAccountAdapter.setChainAccountList(accounts);
            mPageIndicator.setIndicatorViewNum(accounts.size());
        }
    }

    private List<ChainAccount> getChainAccounts(){
        ChainAccountDao chainAccountDao = GreenDaoManager.getInstance().getDaoSession().getChainAccountDao();
        return chainAccountDao.queryBuilder().listLazy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPageIndicator.setCurrentIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class AccountAdapter extends PagerAdapter {
        private  List<ChainAccount> mAccountList = new ArrayList<>();

        public AccountAdapter(){

        }

        public void setChainAccountList(List<ChainAccount> accounts){
            mAccountList.clear();
            mAccountList.addAll(accounts);
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if(mAccountList == null) return 0;
            return mAccountList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Logger.d("====instantiateItem=== %d", position);
            Context context = container.getContext();
            ChainAccount chainAccount = mAccountList.get(position);
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_account, null);
            TextView nameTextView = (TextView)itemView.findViewById(R.id.name_tv);
            TextView moneyTextView = (TextView)itemView.findViewById(R.id.money_tv);
            TextView accountTextView = (TextView)itemView.findViewById(R.id.key_name_tv);
            nameTextView.setText(context.getString(R.string.account_name, chainAccount.getId()));
            moneyTextView.setText(context.getString(R.string.account_money, chainAccount.getMoney()));
            accountTextView.setText(chainAccount.getAccount());
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
//            marginLayoutParams.setMargins(context.getResources().getDimensionPixelSize(R.dimen.account_card_left_right),
//                    context.getResources().getDimensionPixelSize(R.dimen.account_card_top_bottom),
//                    context.getResources().getDimensionPixelSize(R.dimen.account_card_left_right),
//                    context.getResources().getDimensionPixelSize(R.dimen.account_card_top_bottom));
            container.addView(itemView, marginLayoutParams);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Logger.t(TAG).d("====destroyItem=== %d", position);
            container.removeView((View) object);
        }
    }
}
