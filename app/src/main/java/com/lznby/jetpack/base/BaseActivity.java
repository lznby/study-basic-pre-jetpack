package com.lznby.jetpack.base;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;

/**
 * @author Lznby
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Manage rxJava lifecycle be use to release when activity onDestroy.
     */
    public ListCompositeDisposable list = new ListCompositeDisposable();


    /**
     * Request permission by rxPermissions.
     */
    final RxPermissions rxPermissions = new RxPermissions(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // The code was used to set activity content view.
        setContentView(setLayout());
        // Bind ButterKnife
        ButterKnife.bind(this);
        // Do something when activity lifecycle was 'OnCreate'.
        doOnCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Destroy rxJava thread when view was be destroy.
        list.dispose();
    }

    /**
     * Collect rxJava'disposable.
     *
     * @param disposable from rxJava'Observable return result.
     */
    public void addDisposable(Disposable disposable) {
        list.add(disposable);
    }


    /**
     * This method was used to set fragment'layoutRes that like R.layout.xxx.
     *
     * @return And this layout.xml have a FrameLayout view was used to inflate fragment.
     */
    public abstract @LayoutRes
    int setLayout();

    /**
     * This method be used to do something when activity lifecycle was 'OnCreate'.
     */
    protected abstract void doOnCreate();

}
