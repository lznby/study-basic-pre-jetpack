package com.lznby.jetpack.content.test;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.base.BaseSingleFragmentActivity;

/**
 * @author Lznby
 */
public class SingleFragmentActivity extends BaseSingleFragmentActivity<BaseActivityViewModel,BaseEntity<String>,SingleFragment> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singel_fragment);
    }

    @Override
    protected SingleFragment createFragment() {
        return new SingleFragment();
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_singel_fragment;
    }

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {

    }


    @Override
    protected void bindView(BaseEntity<String> entity) {

    }

    @Override
    public int setContainer() {
        return R.id.activity_container;
    }
}
