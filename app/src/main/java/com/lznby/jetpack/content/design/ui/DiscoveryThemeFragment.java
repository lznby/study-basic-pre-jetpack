package com.lznby.jetpack.content.design.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseFragment;
import com.lznby.jetpack.content.design.adapter.DiscoveryThemeAdapter;
import com.lznby.jetpack.content.design.configure.EmptyRvPage;
import com.lznby.jetpack.content.design.entity.ThemeEntity;
import com.lznby.jetpack.content.design.vm.DiscoveryThemeViewModel;

import java.util.List;

import butterknife.BindView;

/**
 * 发现-主题碎片
 *
 * @author Lznby
 */
public class DiscoveryThemeFragment extends BaseFragment<DiscoveryThemeViewModel,CenterActivity,List<ThemeEntity>> {

    @BindView(R.id.rv_theme)
    RecyclerView rvTheme;

    private DiscoveryThemeAdapter adapter;

    @Override
    public int setLayout() {
        return R.layout.fragment_discovery_theme;
    }

    @Override
    protected void bindView(List<ThemeEntity> entity) {
        adapter.setNewData(entity);
    }

    @Override
    protected void doOnCreateView() {
        adapter = new DiscoveryThemeAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(viewModel.getActivityContent());
        rvTheme.setLayoutManager(layoutManager);
        rvTheme.setAdapter(adapter);
        // 设置空布局
        adapter.setEmptyView(EmptyRvPage.getEmptyView(rvTheme));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.itv_love_count:
                        if (viewModel.getLiveData().getValue().get(position).isFollowed()) {
                            viewModel.unFollowTheme(((ThemeEntity)adapter.getData().get(position)).getThemeId(),position);
                        } else {
                            viewModel.followTheme(((ThemeEntity)adapter.getData().get(position)).getThemeId(),position);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
