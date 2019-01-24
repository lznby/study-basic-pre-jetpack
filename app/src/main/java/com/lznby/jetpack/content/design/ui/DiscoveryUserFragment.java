package com.lznby.jetpack.content.design.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseFragment;
import com.lznby.jetpack.base.callback.SimpleCallback;
import com.lznby.jetpack.content.design.adapter.DiscoveryUserAdapter;
import com.lznby.jetpack.content.design.configure.EmptyRvPage;
import com.lznby.jetpack.content.design.entity.HomePageRouterEntity;
import com.lznby.jetpack.content.design.entity.UserBaseInfoEntity;
import com.lznby.jetpack.content.design.vm.DiscoveryUserViewModel;

import java.util.List;

import butterknife.BindView;

/**
 * 发现-用户界面
 *
 * @author Lznby
 */
public class DiscoveryUserFragment extends BaseFragment<DiscoveryUserViewModel,CenterActivity,List<UserBaseInfoEntity>> {

    @BindView(R.id.rv_user)
    RecyclerView rvUser;

    private DiscoveryUserAdapter adapter;

    @Override
    public int setLayout() {
        return R.layout.fragment_discovery_user;
    }

    @Override
    protected void bindView(List<UserBaseInfoEntity> entity) {
        adapter.setNewData(entity);
    }

    @Override
    protected void doOnCreateView() {
        adapter = new DiscoveryUserAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(viewModel.getActivityContent());
        rvUser.setLayoutManager(layoutManager);
        rvUser.setAdapter(adapter);
        // 设置空布局
        adapter.setEmptyView(EmptyRvPage.getEmptyView(rvUser, new SimpleCallback() {
            @Override
            public void doSomething() {
                // 点击刷新
                viewModel.getUserInfo();
            }
        }));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.itv_love_count:
                        if (viewModel.getLiveData().getValue().get(position).isFollow()) {
                            viewModel.unFollower(Integer.valueOf(viewModel.getLiveData().getValue().get(position).getUserId()),position);
                        } else {
                            viewModel.follow(Integer.valueOf(viewModel.getLiveData().getValue().get(position).getUserId()),position);
                        }
                        break;

                    default:
                        break;
                }
            }
        });
        // 设置adapter的Item的点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                UserBaseInfoEntity entity = (UserBaseInfoEntity)adapter.getData().get(position);
                Intent intent = new Intent(getContext(),HomePageActivity.class);
                intent.putExtra(HomePageRouterEntity.KEY,new HomePageRouterEntity(entity.getUserId()));
                startActivity(intent);
            }
        });
    }
}
