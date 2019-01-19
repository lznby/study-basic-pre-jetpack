package com.lznby.jetpack.content.design.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lznby.jetpack.base.BaseFragment;
import com.lznby.jetpack.content.design.ui.DiscoveryArticleFragment;
import com.lznby.jetpack.content.design.ui.DiscoveryThemeFragment;
import com.lznby.jetpack.content.design.ui.DiscoveryUserFragment;

/**
 * 发现-FragmentPagerAdapter.
 *
 * @author Lznby
 */
public class DiscoveryFragmentAdapter extends FragmentStatePagerAdapter {

    private DiscoveryArticleFragment article;
    private DiscoveryThemeFragment theme;
    private DiscoveryUserFragment user;

    public DiscoveryFragmentAdapter(FragmentManager manager) {
        super(manager);
        article = new DiscoveryArticleFragment();
        theme = new DiscoveryThemeFragment();
        user = new DiscoveryUserFragment();
    }

    private String[] titles = {"资讯","主题","用户"};

    @Override
    public Fragment getItem(int i) {
        BaseFragment fragment = null;
        switch (i) {
            case 0:
                fragment = article;
                break;
            case 1:
                fragment = theme;
                break;
            case 2:
                fragment = user;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
