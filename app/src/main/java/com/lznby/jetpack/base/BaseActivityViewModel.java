package com.lznby.jetpack.base;

import android.arch.lifecycle.MutableLiveData;

import com.lznby.jetpack.base.callback.GetActivityCallback;

/**
 * @author Lznby
 */
public class BaseActivityViewModel<K extends BaseActivity,E>  extends BaseModel{

    /**
     * 获取Activity的回调
     */
    public GetActivityCallback<K> activity;

    /**
     * LiveData数据
     */
    private MutableLiveData<E> liveData;


    /**
     * 设置数据
     *
     * @return
     */
    public MutableLiveData<E> getLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }

    /**
     * 设置获取Activity的回调接口
     * 1.必须在第一时间被设置
     * @param getActivityCallback
     */
    public void setGetActivityCallback(GetActivityCallback<K> getActivityCallback) {
        if (this.activity == null) {
            this.activity = getActivityCallback;
            //bindLifecycle
            getActivityCallback.getActivity().getLifecycle().addObserver(this);
        }
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        // Do others code

        // recycle rxJava thread.
        list.dispose();
        // Do on finally
        if (activity!=null) {
            //unBinding lifecycle
            activity.getActivity().getLifecycle().removeObserver(this);
        }
    }
}
