package com.lznby.jetpack.base.vm;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import com.lznby.jetpack.base.callback.GetFragmentCallback;
import com.lznby.jetpack.base.ui.BaseFragment;

/**
 * @author Lznby
 */
public class BaseFragmentViewModel<T extends BaseFragment,K> extends ViewModel implements LifecycleObserver {

    /**
     * 获取Activity的回调
     */
    protected GetFragmentCallback<T> getFragmentCallback;

    /**
     * LiveData数据
     */
    private MutableLiveData<K> liveData;

    /**
     * 设置数据
     *
     * @return
     */
    public MutableLiveData<K> getLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }


    /**
     * 设置获取Activity的回调接口
     * 1.必须在第一时间被设置
     * @param getFragmentCallback
     */
    public void setGetFragmentCallback(GetFragmentCallback<T> getFragmentCallback) {
        if (this.getFragmentCallback == null) {
            this.getFragmentCallback = getFragmentCallback;
            //bindLifecycle
            getFragmentCallback.getFragment().getLifecycle().addObserver(this);
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        // Do others code

        // Do on finally
        if (getFragmentCallback!=null) {
            //unBinding lifecycle
            getFragmentCallback.getFragment().getLifecycle().removeObserver(this);
        }
    }
}
