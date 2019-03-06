package com.lznby.jetpack.content.design.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.content.design.adapter.ArticleItemAdapter;
import com.lznby.jetpack.content.design.configure.EmptyRvPage;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.entity.ArticleDetailsRouterEntity;
import com.lznby.jetpack.content.design.entity.ThemeHomePageEntity;
import com.lznby.jetpack.content.design.entity.ThemePageRouterEntity;
import com.lznby.jetpack.content.design.view.AppBarLayoutStateChangeListener;
import com.lznby.jetpack.content.design.vm.ThemePageViewModel;
import com.lznby.jetpack.utils.LoaderImageUtils;
import com.lznby.jetpack.utils.ToastUtils;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 主题首页
 *
 * @author Lznby
 */
public class ThemePageActivity extends BaseActivity<ThemePageViewModel,ThemeHomePageEntity> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.ctl_layout)
    CollapsingToolbarLayout ctlLayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.civ_header)
    CircleImageView civHeader;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_follow_size)
    TextView tvFollowSize;
    @BindView(R.id.tv_follower_size)
    TextView tvFollowerSize;
    @BindView(R.id.tv_motto)
    TextView tvMotto;
    @BindView(R.id.rv_article)
    RecyclerView rvArticle;

    private ArticleItemAdapter adapter;
    /**
     * 从上一个界面传递过来,决定加载哪个主题
     */
    private ThemePageRouterEntity params;

    @Override
    protected int setLayout() {
        return R.layout.activity_theme_page;
    }

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {

        params = getIntent().getParcelableExtra(ThemePageRouterEntity.KEY);
        viewModel.setParams(params);

        setSupportActionBar(toolbar);
        // 设置状态栏透明
//        BarUtils.setStatusBarAlpha(this,0);
        BarUtils.setStatusBarColor(this,0);
        toolbar.setNavigationIcon(R.mipmap.icon_left_arrows);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 动态控制Toolbar标题的显示与隐藏
        appbar.addOnOffsetChangedListener(new AppBarLayoutStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarLayoutStateChangeListener.State state) {
                switch (state) {
                    case EXPANDED:
                        toolbar.setTitleTextColor(Color.TRANSPARENT);
                        break;
                    case COLLAPSED:
                        toolbar.setTitleTextColor(Color.WHITE);
                        break;
                    case INTERMEDIATE:
                        toolbar.setTitleTextColor(Color.TRANSPARENT);
                        break;
                    default:
                        break;
                }
            }
        });
        //
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvArticle.setLayoutManager(manager);
        adapter = new ArticleItemAdapter();
        // 设置adapter的Item的点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ThemePageActivity.this,ArticleDetailsActivity.class);
                ArticleDetailsRouterEntity params = new ArticleDetailsRouterEntity();
                if (viewModel.getLiveData().getValue()!=null) {
                    params.setFileAttribution(viewModel.getLiveData().getValue().getArticleAllInfoEntities().get(position).getArticleEntity().getFileAttribution());
                    params.setType(viewModel.getLiveData().getValue().getArticleAllInfoEntities().get(position).getArticleEntity().getType());
                }
                intent.putExtra(ArticleDetailsRouterEntity.KEY,params);
                startActivity(intent);
            }
        });
        rvArticle.setAdapter(adapter);
        // 设置空布局样式
        adapter.setEmptyView(EmptyRvPage.getEmptyView(rvArticle));

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.itv_comment_count:
                        // todo 评论
                        ToastUtils.shortToast(ThemePageActivity.this,"评论");
                        break;
                    case R.id.itv_love_count:
                        // 收藏
                        if (viewModel.getLiveData().getValue().getArticleAllInfoEntities().get(position).isLove()) {
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
    protected void bindView(ThemeHomePageEntity entity) {
        LoaderImageUtils.loaderUrlCenterImage(this,entity.getThemeEntity().getThemeImage(),ivTitle);
        ctlLayout.setTitle(entity.getThemeEntity().getThemeName());
        toolbar.setTitle(entity.getThemeEntity().getThemeName());
        LoaderImageUtils.loaderUrlCenterImage(this,entity.getThemeEntity().getThemeHeaderImage(),civHeader);
        tvNickname.setText(entity.getThemeEntity().getThemeName());
        tvFollowSize.setText(String.valueOf(entity.getThemeEntity().getSubCount()));
        tvFollowerSize.setText(String.valueOf(entity.getArticleAllInfoEntities().size()));
        tvMotto.setText("简介:"+entity.getThemeEntity().getThemeNote());
        adapter.setNewData(entity.getArticleAllInfoEntities());
    }

}
