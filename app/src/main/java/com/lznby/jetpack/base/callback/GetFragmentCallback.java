package com.lznby.jetpack.base.callback;

import com.lznby.jetpack.base.ui.BaseFragment;

/**
 * @author Lznby
 */
public interface GetFragmentCallback<T extends BaseFragment> {
    /**
     * 回调获取Fragment
     * @return
     */
    T getFragment();
}
