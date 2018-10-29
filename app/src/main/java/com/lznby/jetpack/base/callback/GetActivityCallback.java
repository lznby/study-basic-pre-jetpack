package com.lznby.jetpack.base.callback;

import com.lznby.jetpack.base.ui.BaseActivity;

/**
 * @author Lznby
 */
public interface GetActivityCallback<T extends BaseActivity> {
    /**
     * 回调获取Activity
     * @return
     */
    T getActivity();
}

