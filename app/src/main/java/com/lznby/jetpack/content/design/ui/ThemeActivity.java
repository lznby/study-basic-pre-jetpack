package com.lznby.jetpack.content.design.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.content.design.adapter.ThemeAdapter;
import com.lznby.jetpack.content.design.configure.EmptyRvPage;
import com.lznby.jetpack.content.design.entity.ThemeEntity;
import com.lznby.jetpack.content.design.entity.ThemeRouterEntity;
import com.lznby.jetpack.content.design.vm.ThemeViewModel;
import com.lznby.jetpack.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 主题列表界面
 *
 * @author Lznby
 */
public class ThemeActivity extends BaseActivity<ThemeViewModel,List<ThemeEntity>> {

    @BindView(R.id.rv_theme)
    RecyclerView rvTheme;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    /**
     * 从上一个界面传递过来,决定取谁的主题关注列表
     */
    ThemeRouterEntity params;

    ThemeAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_theme;
    }

    @Override
    protected void doOnCreate() {
        params = getIntent().getParcelableExtra(ThemeRouterEntity.KEY);
        viewModel.setParams(params);

        toolbar.setNavigationIcon(R.mipmap.icon_left_arrows);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new ThemeAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTheme.setLayoutManager(layoutManager);
        rvTheme.setAdapter(adapter);

        // 设置空布局
        adapter.setEmptyView(EmptyRvPage.getEmptyView(rvTheme));

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.bt_follow:
                        // 点击事件
                        ToastUtils.shortToast(ThemeActivity.this,"订阅事件");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void bindView(List<ThemeEntity> entity) {
        adapter.setNewData(entity);
    }
}