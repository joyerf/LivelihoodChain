package com.joyerf.livelihoodchain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by jiezongchang on 2018/1/27.
 */

public class AccountFragment extends Fragment {
    ImageView mAddImageView;

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
        return view;
    }
}
