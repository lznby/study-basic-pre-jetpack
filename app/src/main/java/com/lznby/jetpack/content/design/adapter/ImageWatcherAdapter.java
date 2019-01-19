package com.lznby.jetpack.content.design.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lznby.jetpack.content.design.entity.ImageWatcherItemEntity;
import com.lznby.jetpack.content.design.entity.ImageWatcherRouterEntity;
import com.lznby.jetpack.content.design.ui.ImageWatcherFragment;

/**
 * 图片浏览展示Viewpager
 *
 * @author Lznby
 */
public class ImageWatcherAdapter extends FragmentPagerAdapter {

    /**
     * 图片路径
     */
    private ImageWatcherRouterEntity params = new ImageWatcherRouterEntity();

    public ImageWatcherAdapter(FragmentManager fm) {
        super(fm);
    }

    public ImageWatcherRouterEntity getParams() {
        return params;
    }

    public void setParams(ImageWatcherRouterEntity params) {
        this.params = params;
    }

    @Override
    public Fragment getItem(int i) {
        ImageWatcherItemEntity item = new ImageWatcherItemEntity(params.getUrls().get(i),i,params.getUrls().size());
        return ImageWatcherFragment.newInstance(item);
    }

    @Override
    public int getCount() {
        return params.getUrls().size();
    }
}
