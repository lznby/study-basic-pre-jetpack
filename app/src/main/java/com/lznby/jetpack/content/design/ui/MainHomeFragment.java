package com.lznby.jetpack.content.design.ui;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseFragment;
import com.lznby.jetpack.base.callback.SimpleCallback;
import com.lznby.jetpack.content.design.adapter.SubScribeArticleAdapter;
import com.lznby.jetpack.content.design.configure.EmptyRvPage;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.entity.ArticleDetailsRouterEntity;
import com.lznby.jetpack.content.design.view.ScrollRecyclerView;
import com.lznby.jetpack.content.design.vm.HomeViewModel;
import com.lznby.jetpack.utils.BannerImageLoader;
import com.lznby.jetpack.utils.RefreshLayoutUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Lznby
 */
public class MainHomeFragment extends BaseFragment<HomeViewModel, CenterActivity, List<ArticleAllInfoEntity>> {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rv_article)
    ScrollRecyclerView rvArticle;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private SubScribeArticleAdapter adapter;

    @Override
    public int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void bindView(List<ArticleAllInfoEntity> entity) {
        updateBannerData(entity);
    }

    @Override
    protected void doOnCreateView() {
        initBanner();
        bindArticleLiveData();
        initArticleAdapter();
        initRefreshLayout();
    }

    /**
     * 初始化RecyclerViewAdapter
     */
    private void initArticleAdapter() {
        adapter = new SubScribeArticleAdapter();
        rvArticle.setLayoutManager (new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));
        adapter.setEmptyView(EmptyRvPage.getEmptyView(rvArticle, new SimpleCallback() {
            @Override
            public void doSomething() {
                viewModel.getBannerInfo(5);
            }
        }));
        rvArticle.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.itv_love_count:
                        // 收藏
                        if (viewModel.getArticleLiveData().getValue().get(position).isLove()) {
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
                routerArticleDetails(entity);
            }
        });
    }

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        banner.setImageLoader(new BannerImageLoader());
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                // 跳转到详情页
                // 跳转到资讯详情页
                ArticleAllInfoEntity entity = viewModel.getLiveData().getValue().get(position);
                routerArticleDetails(entity);
            }
        });
    }

    /**
     * 跳转到详情页
     * @param entity
     */
    private void routerArticleDetails(ArticleAllInfoEntity entity) {
        Intent intent = new Intent(getContext(),ArticleDetailsActivity.class);
        intent.putExtra(ArticleDetailsRouterEntity.KEY,new ArticleDetailsRouterEntity(entity.getArticleEntity().getType(),entity.getArticleEntity().getFileAttribution()));
        startActivity(intent);
    }

    /**
     * 初始化进度条
     */
    private void initRefreshLayout() {

//        // 设置进度条的颜色,不定长参数可以设置多种颜色
//        // 对于RefreshLayout,网上说最多设置4中颜色,不要使用android.R.color, 否则会卡死
//        refreshLayout.setColorSchemeColors(
//                Color.RED,
//                Color.YELLOW,
//                Color.GREEN
//        );
//
//        // 设置进度条背景颜色
//        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
//
//        // 设置手指划过多少像素开始触犯刷新
//        refreshLayout.setDistanceToTriggerSync(100);
//
//        // 设置刷新时监听
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // 刷新数据
//                viewModel.getArticleInfo();
//            }
//        });

        RefreshLayoutUtils.initRefreshLayout(refreshLayout, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getArticleInfo();
            }
        });
    }

    /**
     * 资讯LiveData绑定
     */
    private void bindArticleLiveData() {
        final Observer<List<ArticleAllInfoEntity>> articleEntity = this::initArticle;
        viewModel.getArticleLiveData().observe(this,articleEntity);
    }

    /**
     * 绑定RecycleView视图UI绑定
     *
     * @param entities
     */
    private void initArticle(List<ArticleAllInfoEntity> entities){
        adapter.setNewData(entities);
        // 刷新完成,关闭下拉刷新组件
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        // recycler 回到最上面
        //        rvArticle.scrollToPosition(0);
    }

    /**
     * 更新Banner数据
     *
     * @param entity
     */
    private void updateBannerData(List<ArticleAllInfoEntity> entity) {
        viewModel.addDisposable(
                Flowable.fromIterable(entity)
                        .map(o -> {
                            String note = "无标题！";
                            if (!StringUtils.isEmpty(o.getArticleEntity().getContent())) {
                                note = o.getArticleEntity().getContent();
                            }
                            return note;
                        })
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o -> {
                            banner.update(entity, o);
                            banner.start();
                        })
        );
    }
}
