package com.lznby.jetpack.content.test.activity.single;


import com.lznby.jetpack.R;
import com.lznby.jetpack.base.entity.BaseEntity;
import com.lznby.jetpack.base.ui.BaseSingleFragmentActivity;
import com.lznby.jetpack.base.vm.BaseViewModel;

/**
 * @author Lznby
 */
public class SingleFragmentActivity extends BaseSingleFragmentActivity<BaseViewModel,BaseEntity<String>,SingleFragment> {

    @Override
    protected SingleFragment createFragment() {
        return new SingleFragment();
    }

    @Override
    public int setLayout() {
        return R.layout.activity_singel_fragment;
    }

    @Override
    protected void doOnCreate() {

    }

    @Override
    protected void bindView(BaseEntity<String> entity) {

    }
    
    @Override
    public int setContainer() {
        return R.id.activity_container;
    }
}
