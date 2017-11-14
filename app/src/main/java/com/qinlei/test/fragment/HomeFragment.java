package com.qinlei.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qinlei.test.R;
import com.qinlei.test.base.BaseFragment;
import com.qinlei.test.base.BaseLazyLoadFragment;

/**
 * Created by qinlei
 * Created on 2017/11/14
 * Created description :
 */

public class HomeFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setText(getString(R.string.title_home));
        return textView;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
}
