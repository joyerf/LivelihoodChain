package com.joyerf.livelihoodchain.view.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joyerf.livelihoodchain.R;
import com.joyerf.livelihoodchain.datastruct.MarketPrice;
import com.orhanobut.logger.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiezongchang on 2018/2/8.
 */

public class MarketAdapter extends RecyclerView.Adapter {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;

    private List<MarketPrice> mMarketList = new ArrayList<>();

    public void setMarketList(List<MarketPrice> list){
        mMarketList.clear();
        mMarketList.addAll(list);
        notifyDataSetChanged();
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            //对于Header，我们应该返回填充有Header对应布局文件的ViewHolder
            Context context = parent.getContext();
            TextView textView = new TextView(context);
            textView.setTextColor(context.getResources().getColor(R.color.market_header_text_color));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.market_header_text_size));
            textView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setHeight((int)context.getResources().getDimension(R.dimen.market_header_height));
            textView.setGravity(Gravity.CENTER);
            return new HeaderViewHolder(textView);
        } else {
            //对于Body中的item，我们也返回所对应的ViewHolder
            return new BodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_price, null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeader(position)) {
            ((HeaderViewHolder)holder).setText(R.string.market_announcement);
        }else {
            ((BodyViewHolder) holder).setMarketPrice(mMarketList.get(position - 1));
        }
    }

    @Override
    public int getItemCount() {
        return mMarketList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        HeaderViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }

        public void setText(int resId){
            mTextView.setText(resId);
        }
    }

    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    public static class BodyViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mNewPriceTextView;
        private TextView mAmountTextView;

        BodyViewHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.market_name_tv);
            mNewPriceTextView = (TextView) itemView.findViewById(R.id.market_new_price_tv);
            mAmountTextView = (TextView) itemView.findViewById(R.id.market_amount_tv);
        }

        public void setMarketPrice(MarketPrice price){
            mNameTextView.setText(price.market);
            try {
                DecimalFormat df = new DecimalFormat("#0.00");
                double amount = Double.parseDouble(price.amount);
                double newPrice = Double.parseDouble(price.new_price);
//                Logger.d("price.amount=%s, price.new_price=%s", price.amount, price.new_price);
                mNewPriceTextView.setText(df.format(newPrice));
                mAmountTextView.setText(mAmountTextView.getContext().getResources().getString(R.string.market_amount, df.format(amount)));
            } catch (Exception e){
                Logger.e(e, "amount parseDouble fail");
            }
        }
    }
}
