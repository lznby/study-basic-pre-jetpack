package com.lznby.jetpack.content.design.ui;


import android.widget.ImageView;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseFragment;
import com.lznby.jetpack.content.design.vm.HomeViewModel;

import butterknife.BindView;

/**
 * @author Lznby
 */
public class MainHomeFragment extends BaseFragment<HomeViewModel,CenterActivity,Object> {

    @BindView(R.id.iv_home)
    ImageView ivHome;

    @Override
    public int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void bindView(Object entity) {

    }

    @Override
    protected void doOnCreateView() {
    }
}
