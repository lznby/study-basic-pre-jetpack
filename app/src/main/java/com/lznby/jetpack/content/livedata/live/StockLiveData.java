package com.lznby.jetpack.content.livedata.live;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.math.BigDecimal;

/**
 * @author Lznby
 */
public class StockLiveData extends LiveData<BigDecimal> {

    /**
     * 从onActivity方法开始观察数据更新
     */
    @Override
    protected void onActive() {
        super.onActive();
    }

    /**
     * 无活跃的观察者调用onInactive停止对数据源数据变化的监听.
     */
    @Override
    protected void onInactive() {
        super.onInactive();
    }

    /**
     * 将观察者添加到LiveData的观察者列表当中,到LiveData设置数据后会通知这些观察者.(自动管理)
     * @param owner
     * @param observer
     */
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<BigDecimal> observer) {
        super.observe(owner, observer);
    }

    /**
     * 将观察者添加到LiveData的观察者列表当中,到LiveData设置数据后会通知这些观察者.且观察者一直订阅LivaData变化,不会被回收,除非手动调用回收方法.
     * @param observer
     */
    @Override
    public void observeForever(@NonNull Observer<BigDecimal> observer) {
        super.observeForever(observer);
    }


    /**
     * 在主线程中使用setValue()方法更新LiveData对象
     * @param value
     */
    @Override
    protected void setValue(BigDecimal value) {
        super.setValue(value);
    }

    /**
     * 在子线程中使用postValue()方法更新LiveData对象
     * @param value
     */
    @Override
    protected void postValue(BigDecimal value) {
        super.postValue(value);
    }

    /**
     * 返回当前LiveData值,当在后台时不能保证能及时获得值.
     * @return
     */
    @Nullable
    @Override
    public BigDecimal getValue() {
        return super.getValue();
    }


    /**
     * 从LivaData的观察者队列中删除指定观察者
     * @param observer
     */
    @Override
    public void removeObserver(@NonNull Observer<BigDecimal> observer) {
        super.removeObserver(observer);
    }

    /**
     * 从LiveData的观察者队列中删除指定LifecycleOwner中所有的观察者.
     * @param owner
     */
    @Override
    public void removeObservers(@NonNull LifecycleOwner owner) {
        super.removeObservers(owner);
    }

    /**
     * 判断当前LiveData的观察者队列中是否有观察者,有则返回true,反则返回false.
     * @return
     */
    @Override
    public boolean hasObservers() {
        return super.hasObservers();
    }

    /**
     * 判断当前LiveData的观察者队列中是否有活跃的观察者,有则返回true,反则返回false.
     * @return
     */
    @Override
    public boolean hasActiveObservers() {
        return super.hasActiveObservers();
    }

    public StockLiveData() {
        super();
    }
}
