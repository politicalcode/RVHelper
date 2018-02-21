package cn.langwazi.rvhelper.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.langwazi.rvhelper.R;

/**
 * Created by langwa on 2017/12/11.
 * 默认加载更多布局.
 */

public class DefaultLoadView extends AbstractLoadView {

    @NonNull
    @Override
    public View createLoadView(ViewGroup parent) {
        return LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.helper_default_load_more, parent, false);
    }

    @Override
    public View findLoadFailView(View rootView) {
        return rootView.findViewById(R.id.helper_default_load_fail);
    }

    @Override
    public View findCompleteView(View rootView) {
        return rootView.findViewById(R.id.helper_default_load_complete);
    }

    @Override
    public View findLoadingView(View rootView) {
        return rootView.findViewById(R.id.helper_default__loading);
    }

}
