package com.jh.testlazyfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jinhui on 2018/9/17.
 * email: 1004260403@qq.com
 *
 * onInvisible: 切换到当前fragment时会执行左右两边fragment的onInvisible()方法
 *
 * isFirst 这里将其注释掉，保证每次都加载数据
 */

public abstract class BaseFragment extends Fragment {

    private String TAG = BaseFragment.class.getSimpleName();
    protected View mRootView;
    protected boolean isVisible = false; //是否可见
    private boolean isPrepared = false;//是否初始化完成
    private boolean isFirst = true;//是否第一次加载
    public Context mContext;

    /**
     * 在这里实现Fragment数据的缓加载, 当可见的时候执行操作
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "setUserVisibleHint = " + isVisibleToUser);  // 优先执行， E/BaseFragment: setUserVisibleHint = false
        if (mRootView == null) {
            return;
        }
        if (getUserVisibleHint()) {
            Log.e(TAG, "getUserVisibleHint 懒加载时调用= " + getUserVisibleHint());
            isVisible = true;
            lazyLoad();
        } else {
            Log.e(TAG, "getUserVisibleHint 不可见时调用 = " + getUserVisibleHint());
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        Log.e(TAG, "onAttach");
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = initView(inflater, container, savedInstanceState);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Log.e(TAG, "onActivityCreated");
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mRootView.getParent()).removeView(mRootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isVisible = false;
        isPrepared = false;
        isFirst = true;
        mRootView = null;
    }

    /**
     * 懒加载
     */
    private void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        initData();
//        isFirst = false;   // isFirst 这里将其注释掉，保证每次都加载数据
    }

    /**
     * 不可见时调用
     */
    protected abstract void onInvisible();

    /**
     * 获取数据
     * 可见时才去加载数据
     */
    protected abstract void initData();

    /**
     * 初始化布局
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

}
