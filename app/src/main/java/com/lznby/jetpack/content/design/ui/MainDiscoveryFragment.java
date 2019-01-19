package com.lznby.jetpack.content.design.ui;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseFragment;
import com.lznby.jetpack.content.design.adapter.DiscoveryFragmentAdapter;
import com.lznby.jetpack.content.design.vm.DiscoveryViewModel;

import butterknife.BindView;

/**
 * @author Lznby
 */
public class MainDiscoveryFragment extends BaseFragment<DiscoveryViewModel,CenterActivity,Object> {


    // 1.创建 主题、用户、资讯 的发现碎片

    // 2.填充到发现页面

    // 3.将TabLayout与ViewPager绑定

    // TabLayout 样式属性设置
    // https://www.jianshu.com/p/2b2bb6be83a8

    @BindView(R.id.tl_discovery)
    TabLayout tlDiscovery;
    @BindView(R.id.vp_container)
    ViewPager vpContainer;

    private DiscoveryFragmentAdapter adapter;

    @Override
    public int setLayout() {
        return R.layout.fragment_discovery;
    }

    @Override
    protected void bindView(Object entity) {

    }

    @Override
    protected void doOnCreateView() {

        // 设置Viewpager的Adapter
        adapter = new DiscoveryFragmentAdapter(getFragmentManager());
        vpContainer.setAdapter(adapter);

        // 设置Viewpager与TabLayout联动.
        tlDiscovery.setupWithViewPager(vpContainer);


    }
}
