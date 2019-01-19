package com.lznby.jetpack.content.design.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.content.design.adapter.ImageWatcherAdapter;
import com.lznby.jetpack.content.design.entity.ImageWatcherRouterEntity;

import butterknife.BindView;

/**
 * 图片浏览
 *
 * @author Lznby
 */
public class ImageWatcherActivity extends BaseActivity<BaseActivityViewModel,Object> {

    @BindView(R.id.vp_image)
    ViewPager vpImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /**
     * 接收到的参数
     */
    private ImageWatcherRouterEntity params;

    private ImageWatcherAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_image_watcher;
    }

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        BarUtils.setStatusBarColor(this, Color.BLACK);

        params = getIntent().getParcelableExtra(ImageWatcherRouterEntity.KEY);

        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_left_arrows);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new ImageWatcherAdapter(getSupportFragmentManager());
        adapter.setParams(params);
        vpImage.setAdapter(adapter);
        vpImage.setCurrentItem(params.getPosition());
    }

    @Override
    protected void bindView(Object entity) {

    }
}
