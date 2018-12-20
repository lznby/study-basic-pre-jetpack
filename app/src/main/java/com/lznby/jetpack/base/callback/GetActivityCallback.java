package com.lznby.jetpack.base.callback;

import com.lznby.jetpack.base.BaseActivity;

/**
 * ViewModel中获取Activity实例
 * @author Lznby
 */
public interface GetActivityCallback<T extends BaseActivity> {
    /**
     * 回调获取Activity
     * @return
     */
    T getActivity();
}
