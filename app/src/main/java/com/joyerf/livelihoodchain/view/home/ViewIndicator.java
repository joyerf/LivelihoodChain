package com.joyerf.livelihoodchain.view.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.joyerf.livelihoodchain.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiezongchang on 2018/2/6.
 */

public class ViewIndicator extends LinearLayout {
    private int mMarginLeft = 20;//px
    private int mNum;
    private List<ImageView> imageViews = new ArrayList<>();

    public ViewIndicator(Context context) {
        this(context, null);
    }

    public ViewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    /**
     * 设置指示点的间距
     *
     * @param marginLeft
     */
    public void setMarginLeft(int marginLeft) {
        mMarginLeft = marginLeft;
    }


    /**
     * 设置indicator指示点的个数以及初始化
     *
     * @param num
     */
    public void setIndicatorViewNum(int num) {
        removeAllViews();
        imageViews.clear();
        mNum = num;
        for (int i = 0; i < num; i++) {
            ImageView imageView = new ImageView(this.getContext());
            LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i == 0) {
                imageView.setImageResource(R.drawable.page_indicator_focused);
            } else {
                params.leftMargin = mMarginLeft;
                imageView.setImageResource(R.drawable.page_indicator_unfocused);
            }
            imageViews.add(imageView);
            addView(imageView, params);
        }
    }

    /**
     * 设置当前index的位置
     *
     * @param index
     */
    public void setCurrentIndex(int index) {
        for (int i = 0; i < mNum; i++) {
            if (i == index) {
                imageViews.get(i).setImageResource(R.drawable.page_indicator_focused);
            } else {
                imageViews.get(i).setImageResource(R.drawable.page_indicator_unfocused);
            }
        }
    }
}
