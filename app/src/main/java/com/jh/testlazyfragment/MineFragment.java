package com.jh.testlazyfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by jinhui on 2018/9/17.
 * email: 1004260403@qq.com
 */

public class MineFragment extends BaseFragment {

    private static final String TAG = MineFragment.class.getSimpleName();

    @Override
    protected void onInvisible() {
//        Toast.makeText(mContext, "MineFragment onInvisible", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initData() {
        Toast.makeText(mContext, "MineFragment initData", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "setUserVisibleHint = " + isVisibleToUser);
    }

    public static Fragment getInstance() {
        MineFragment mineFragment = new MineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("11", "11");
        return mineFragment;
    }
}
