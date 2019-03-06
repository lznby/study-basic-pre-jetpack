package com.lznby.jetpack.content.design.ui;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.base.BaseFragment;
import com.lznby.jetpack.base.callback.SimpleCallback;
import com.lznby.jetpack.content.design.adapter.SubScribeArticleAdapter;
import com.lznby.jetpack.content.design.configure.EmptyRvPage;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.entity.ArticleDetailsRouterEntity;
import com.lznby.jetpack.content.design.vm.SubscribeViewModel;
import com.lznby.jetpack.utils.RefreshLayoutUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 订阅-关注的人以及主题下所有的资讯
 *
 * @author Lznby
 */
public class MainSubscribeFragment extends BaseFragment<SubscribeViewModel,CenterActivity,BaseEntity<List<ArticleAllInfoEntity>>> {

    @BindView(R.id.rv_subscribe)
    RecyclerView rvSubscribe;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private SubScribeArticleAdapter  adapter;

    @Override
    public int setLayout() {
        return R.layout.fragment_subscribe;
    }

    @Override
    protected void bindView(BaseEntity<List<ArticleAllInfoEntity>> entity) {
        adapter.setNewData(entity.getData());
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void doOnCreateView() {
        initRecyclerView();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        adapter = new SubScribeArticleAdapter();
        rvSubscribe.setLayoutManager (new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));
        adapter.setEmptyView(EmptyRvPage.getEmptyView(rvSubscribe, new SimpleCallback() {
            @Override
            public void doSomething() {
                viewModel.getSubInfo();
            }
        }));
        rvSubscribe.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.itv_love_count:
                        // 收藏
                        if (viewModel.getLiveData().getValue().getData().get(position).isLove()) {
                            viewModel.articleUnSub(((ArticleAllInfoEntity)adapter.getData().get(position)).getArticleEntity().getFileAttribution(),position);
                        } else {
                            viewModel.articleSub(((ArticleAllInfoEntity)adapter.getData().get(position)).getArticleEntity().getFileAttribution(),position);
                        }
                        break;

                    default:
                        break;
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // 跳转到资讯详情页
                ArticleAllInfoEntity entity = (ArticleAllInfoEntity)adapter.getData().get(position);
                Intent intent = new Intent(getContext(),ArticleDetailsActivity.class);
                intent.putExtra(ArticleDetailsRouterEntity.KEY,new ArticleDetailsRouterEntity(entity.getArticleEntity().getType(),entity.getArticleEntity().getFileAttribution()));
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化refreshLayout
     */
    private void initRefreshLayout() {
        RefreshLayoutUtils.initRefreshLayout(refreshLayout, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getSubInfo();
            }
        });
    }


}
