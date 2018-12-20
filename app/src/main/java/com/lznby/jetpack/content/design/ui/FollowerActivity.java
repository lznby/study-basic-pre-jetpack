package com.lznby.jetpack.content.design.ui;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.content.design.adapter.FollowerAdapter;
import com.lznby.jetpack.content.design.configure.EmptyRvPage;
import com.lznby.jetpack.content.design.entity.FollowerRouterEntity;
import com.lznby.jetpack.content.design.entity.UserFollowerInfoEntity;
import com.lznby.jetpack.content.design.vm.FollowerViewModel;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * Follower and Follow.
 *
 * @author Lznby
 */
public class FollowerActivity extends BaseActivity<FollowerViewModel, List<UserFollowerInfoEntity>> {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv_follow)
    RecyclerView mRvFollow;

    /**
     * 从上一个界面传递过来的参数,决定谁的关注或粉丝数据
     */
    FollowerRouterEntity params;

    FollowerAdapter followerAdapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_follower;
    }

    @Override
    protected void doOnCreate() {
        params = getIntent().getParcelableExtra(FollowerRouterEntity.KEY);
        mTvTitle.setText(params.getWho()+params.getType());
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        mToolbar.setNavigationIcon(R.mipmap.icon_left_arrows);
        // get page data.
        viewModel.getData(params);
        // set RecyclerView adapter.
        followerAdapter = new FollowerAdapter(R.layout.item_adapter_follower,viewModel, params);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvFollow.setLayoutManager(layoutManager);
        mRvFollow.setAdapter(followerAdapter);

        // set empty view.
        followerAdapter.setEmptyView(EmptyRvPage.getEmptyView(mRvFollow));
    }

    @Override
    protected void bindView(List<UserFollowerInfoEntity> entity) {
        followerAdapter.setNewData(entity);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
