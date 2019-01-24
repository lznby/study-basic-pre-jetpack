package com.lznby.jetpack.content.design.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.base.callback.SimpleCallback;
import com.lznby.jetpack.content.design.adapter.ArticleItemAdapter;
import com.lznby.jetpack.content.design.configure.EmptyRvPage;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.entity.ArticleDetailsRouterEntity;
import com.lznby.jetpack.content.design.entity.ArticleSubscribeRouterEntity;
import com.lznby.jetpack.content.design.vm.ArticleSubActivityViewModel;
import com.lznby.jetpack.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 资讯收藏界面
 *
 * @author Lznby
 */
public class ArticleSubscribeActivity extends BaseActivity<ArticleSubActivityViewModel,List<ArticleAllInfoEntity>> {

    @BindView(R.id.rv_article)
    RecyclerView rvArticle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ArticleItemAdapter adapter;

    /**
     * 从上一个界面传递过来的参数,决定查询哪个用户的id
     */
    ArticleSubscribeRouterEntity params;

    @Override
    protected int setLayout() {
        return R.layout.activity_article_subscribe;
    }

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {

        params = getIntent().getParcelableExtra(ArticleSubscribeRouterEntity.KEY);
        viewModel.setParams(params);

        setSupportActionBar(toolbar);
        // toolbar导航是指
        toolbar.setNavigationIcon(R.mipmap.icon_left_arrows);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 设置RecyclerView
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvArticle.setLayoutManager(manager);
        adapter = new ArticleItemAdapter();
        // 设置adapter的Item的点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ArticleSubscribeActivity.this,ArticleDetailsActivity.class);
                ArticleDetailsRouterEntity params = new ArticleDetailsRouterEntity();
                if (viewModel.getLiveData().getValue()!=null) {
                    params.setFileAttribution(viewModel.getLiveData().getValue().get(position).getArticleEntity().getFileAttribution());
                    params.setType(viewModel.getLiveData().getValue().get(position).getArticleEntity().getType());
                }
                intent.putExtra(ArticleDetailsRouterEntity.KEY,params);
                startActivity(intent);
            }
        });
        rvArticle.setAdapter(adapter);
        // 设置空布局样式
        adapter.setEmptyView(EmptyRvPage.getEmptyView(rvArticle, new SimpleCallback() {
            @Override
            public void doSomething() {
                // 刷新数据
                viewModel.getSubArticle(params.getUserId());
            }
        }));

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.itv_comment_count:
                        // todo 评论
                        ToastUtils.shortToast(ArticleSubscribeActivity.this,"评论");
                        break;
                    case R.id.itv_love_count:
                        // 收藏
                        if (viewModel.getLiveData().getValue().get(position).isLove()) {
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
    }

    @Override
    protected void bindView(List<ArticleAllInfoEntity> entity) {
        adapter.setNewData(entity);
    }
}
