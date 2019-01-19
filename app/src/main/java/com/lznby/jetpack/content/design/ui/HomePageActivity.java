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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.content.design.adapter.ArticleItemAdapter;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.configure.EmptyRvPage;
import com.lznby.jetpack.content.design.configure.RouterConfigure;
import com.lznby.jetpack.content.design.entity.ArticleDetailsRouterEntity;
import com.lznby.jetpack.content.design.entity.PersonalHomePageEntity;
import com.lznby.jetpack.content.design.view.AppBarLayoutStateChangeListener;
import com.lznby.jetpack.content.design.vm.HomePageViewModel;
import com.lznby.jetpack.utils.LoaderImageUtils;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人主页(自己和别人主页都行)
 *
 * @author Lznby
 */
public class HomePageActivity extends BaseActivity<HomePageViewModel,PersonalHomePageEntity> implements Toolbar.OnMenuItemClickListener {

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


    @Override
    protected int setLayout() {
        return R.layout.activity_home_page;
    }

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        // 设置状态栏透明
        BarUtils.setStatusBarAlpha(this,0);
        toolbar.setNavigationIcon(R.mipmap.icon_left_arrows);
        toolbar.setOnMenuItemClickListener(this);
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
        // 初次加载个人主页信息
        viewModel.getHomePageViewModel(CacheConfigure.getToken(this),CacheConfigure.getUserId(this));
        //
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvArticle.setLayoutManager(manager);
        adapter = new ArticleItemAdapter();
        // 设置adapter的Item的点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(HomePageActivity.this,ArticleDetailsActivity.class);
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

    }

    @Override
    protected void bindView(PersonalHomePageEntity entity) {
        Glide.with(this)
                .load(entity.getUserBaseInfoEntity().getUserHeaderUrl())
                .into(ivTitle);
        ctlLayout.setTitle(entity.getUserBaseInfoEntity().getUserNickName());
        toolbar.setTitle(entity.getUserBaseInfoEntity().getUserNickName());
        LoaderImageUtils.loaderCircleImageView(this, (entity.getUserBaseInfoEntity() != null) ? (entity.getUserBaseInfoEntity().getUserHeaderUrl()) : Configure.DefaultValue.DEFAULT_IMAGE_URL, Configure.DefaultValue.DEFAULT_IAMGE_RES, civHeader);
        tvNickname.setText(entity.getUserBaseInfoEntity().getUserNickName());
        tvFollowSize.setText(String.valueOf(entity.getUserFollowerSizeEntity().getFollowSize()));
        tvFollowerSize.setText(String.valueOf(entity.getUserFollowerSizeEntity().getFollowerSize()));
        tvMotto.setText("简介:"+entity.getUserBaseInfoEntity().getUserMotto());
        adapter.setNewData(entity.getArticleAllInfoEntities());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_page,menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_create:
                RouterConfigure.checkLoginRouterUtils(this, CreateActivity.class);
                break;
            default:
                break;
        }
        return true;
    }
}
