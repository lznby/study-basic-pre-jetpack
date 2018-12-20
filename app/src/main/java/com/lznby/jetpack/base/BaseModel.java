package com.lznby.jetpack.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;

/**
 * @author Lznby
 */
public abstract class BaseModel extends ViewModel implements LifecycleObserver {
    /**
     * recycle rxJava thread.
     */
    public ListCompositeDisposable list = new ListCompositeDisposable();

    /**
     * add rxJava thread object to recycle list.
     * @param disposable a object that rxJava return.
     */
    public void addDisposable(Disposable disposable) {
        list.add(disposable);
    }
    /**
     * do something when lifecycle was onCreate.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public abstract void onCreate();

    /**
     * do something when lifecycle was onStart.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public abstract void onStart();

    /**
     * do something when lifecycle was onResume.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public abstract void onResume();

    /**
     * do something when lifecycle was onPause.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public abstract void onPause();

    /**
     * do something when lifecycle was onStop.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public abstract void onStop();

    /**
     * do something when lifecycle was onDestroy.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public abstract void onDestroy();
}
