package com.joyerf.livelihoodchain.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyerf.livelihoodchain.R;
import com.orhanobut.logger.Logger;

/**
 * Created by jiezongchang on 2018/1/27.
 */

public class MainActivity extends AppCompatActivity {
    private Fragment[] fragments = new Fragment[]{new HomeFragment(), new AccountFragment()};

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);

        viewPager.setAdapter(new MainPageAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setCustomView(R.layout.view_main_tab);
        tabLayout.getTabAt(1).setCustomView(R.layout.view_main_tab);

        selectTab(tabLayout.getTabAt(0));
        unSelectTab(tabLayout.getTabAt(1));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectTab(tab);
                Logger.d("onTabSelected");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                unSelectTab(tab);
                Logger.d("onTabUnselected");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                selectTab(tab);
            }
        });
        viewPager.setCurrentItem(0);
    }

    private void selectTab(TabLayout.Tab tab) {
        Logger.d("selectTab " + tab.getPosition());
        ImageView icon = (ImageView)tab.getCustomView().findViewById(R.id.tab_icon_iv);
        TextView title = (TextView)tab.getCustomView().findViewById(R.id.tab_title_tv);
        if (tab.getPosition() == 0) {
            icon.setImageResource(R.mipmap.ic_home_focus);
            title.setText(R.string.tab_home);
        } else {
            icon.setImageResource(R.mipmap.ic_wallet_focus);
            title.setText(R.string.tab_wallet);
        }
        title.setTextColor(ContextCompat.getColor(this, R.color.selected_color));
    }

    private void unSelectTab(TabLayout.Tab tab) {
        Logger.d("unSelectTab " + tab.getPosition());
        ImageView icon = (ImageView)tab.getCustomView().findViewById(R.id.tab_icon_iv);
        TextView title = (TextView)tab.getCustomView().findViewById(R.id.tab_title_tv);
        if (tab.getPosition() == 0) {
            icon.setImageResource(R.mipmap.ic_home);
            title.setText(R.string.tab_home);
        } else {
            icon.setImageResource(R.mipmap.ic_wallet);
            title.setText(R.string.tab_wallet);
        }
        title.setTextColor(ContextCompat.getColor(this, R.color.un_selected_color));
    }

    private class MainPageAdapter extends FragmentPagerAdapter {

        public MainPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}
