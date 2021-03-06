package com.lznby.jetpack.content.design.ui;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseFragment;
import com.lznby.jetpack.base.callback.SimpleCallback;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.configure.RouterConfigure;
import com.lznby.jetpack.content.design.entity.ArticleSubscribeRouterEntity;
import com.lznby.jetpack.content.design.entity.FollowerRouterEntity;
import com.lznby.jetpack.content.design.entity.HomePageRouterEntity;
import com.lznby.jetpack.content.design.entity.LoginEntity;
import com.lznby.jetpack.content.design.entity.ThemeRouterEntity;
import com.lznby.jetpack.content.design.entity.UserFollowerSizeEntity;
import com.lznby.jetpack.content.design.vm.MineViewModel;
import com.lznby.jetpack.utils.LoaderImageUtils;
import com.lznby.jetpack.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @author Lznby
 */
public class MainMineFragment extends BaseFragment<MineViewModel, CenterActivity, LoginEntity> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.civ_header)
    CircleImageView mIvHeader;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_dynamic)
    TextView mTvDynamic;
    @BindView(R.id.tv_following)
    TextView mTvFollowing;
    @BindView(R.id.tv_follower)
    TextView mTvFollower;
    @BindView(R.id.tv_motto)
    TextView mTvMotto;

    @Override
    public int setLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void bindView(LoginEntity entity) {
        mTvNickname.setText((entity != null) ? (entity.getUserBaseInfoEntity().getUserNickName()) : "点击登录账号");
        LoaderImageUtils.loaderUrlCenterImage(viewModel.getActivityContent(), (entity != null) ? (entity.getUserBaseInfoEntity().getUserHeaderUrl()) : Configure.DefaultValue.DEFAULT_IMAGE_URL, mIvHeader);
        mTvMotto.setText(entity != null ? entity.getUserBaseInfoEntity().getUserMotto() : "");
    }

    @Override
    protected void doOnCreateView() {
        final Observer<UserFollowerSizeEntity> followerSize = this::initSize;
        viewModel.getFollowerSizeLiveData().observe(this, followerSize);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        viewModel.getMineUserInfo(CacheConfigure.getToken(viewModel.getActivityContent()), CacheConfigure.getUserId(getActivity()));
    }

    private void initSize(UserFollowerSizeEntity entity) {
        mTvDynamic.setText(entity != null ? String.valueOf(entity.getFollowThemeSize()) : "0");
        mTvFollowing.setText(entity != null ? String.valueOf(entity.getFollowSize()) : "0");
        mTvFollower.setText(entity != null ? String.valueOf(entity.getFollowerSize()) : "0");
    }

    @OnClick({R.id.iv_setting, R.id.cl_header, R.id.ll_dynamic, R.id.ll_following, R.id.ll_follower,
            R.id.itv_mine_love, R.id.itv_mine_message, R.id.itv_mine_theme})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_setting:
                mineSettingRouter();
                break;
            case R.id.cl_header:
                mineHomeRouter();
                break;
            case R.id.ll_dynamic:
                mineDynamicRouter();
                break;
            case R.id.ll_following:
                mineFollowingRouter();
                break;
            case R.id.ll_follower:
                mineFollowerRouter();
                break;
            case R.id.itv_mine_love:
                mineLoveRouter();
                break;
            case R.id.itv_mine_message:
                mineMessageRouter();
                break;
            case R.id.itv_mine_theme:
                mineThemeRouter();
                break;
            default:
                break;
        }
    }

    void mineSettingRouter() {
        // 跳转到设置
        RouterConfigure.normalRouterUtils(viewModel.activity.getActivity(), SettingActivity.class);
    }

    void mineHomeRouter() {
        RouterConfigure.isLoginRouter(viewModel.activity.getActivity(), new SimpleCallback() {
            @Override
            public void doSomething() {
                // 跳转到个人主页
                Intent intent = new Intent(getActivity(),HomePageActivity.class);
                intent.putExtra(HomePageRouterEntity.KEY,new HomePageRouterEntity(CacheConfigure.getUserId(viewModel.getActivityContent())));
                startActivity(intent);
            }
        });
    }

    void mineDynamicRouter() {
        RouterConfigure.isLoginRouter(viewModel.activity.getActivity(), new SimpleCallback() {
            @Override
            public void doSomething() {
                // 跳转到主题
                Intent intent = new Intent(getActivity(),ThemeActivity.class);
                intent.putExtra(ThemeRouterEntity.KEY,new ThemeRouterEntity(CacheConfigure.getUserId(viewModel.getActivityContent())));
                startActivity(intent);
            }
        });
    }

    void mineFollowingRouter() {
        RouterConfigure.isLoginRouter(viewModel.activity.getActivity(), new SimpleCallback() {
            @Override
            public void doSomething() {
                //跳转到我的关注
                Intent intent = new Intent(getActivity(),FollowerActivity.class);
                intent.putExtra(FollowerRouterEntity.KEY,new FollowerRouterEntity(Configure.RouterType.MINE,CacheConfigure.getUserId(getActivity()),FollowerRouterEntity.FOLLOW));
                startActivity(intent);
            }
        });
    }

    void mineFollowerRouter() {
        RouterConfigure.isLoginRouter(viewModel.activity.getActivity(), new SimpleCallback() {
            @Override
            public void doSomething() {
                //跳转到我的粉丝
                Intent intent = new Intent(getActivity(),FollowerActivity.class);
                intent.putExtra(FollowerRouterEntity.KEY,new FollowerRouterEntity(Configure.RouterType.MINE,CacheConfigure.getUserId(getActivity()),FollowerRouterEntity.FOLLOWER));
                startActivity(intent);
            }
        });
    }

    void mineLoveRouter() {
        RouterConfigure.isLoginRouter(viewModel.activity.getActivity(), new SimpleCallback() {
            @Override
            public void doSomething() {
                //跳转到我的收藏
                Intent intent = new Intent(getActivity(),ArticleSubscribeActivity.class);
                intent.putExtra(ArticleSubscribeRouterEntity.KEY,new ArticleSubscribeRouterEntity(CacheConfigure.getUserId(getActivity())));
                startActivity(intent);
            }
        });
    }

    void mineMessageRouter() {
        RouterConfigure.isLoginRouter(viewModel.activity.getActivity(), new SimpleCallback() {
            @Override
            public void doSomething() {
                //跳转到我的消息
                toastUtils("我的消息");
            }
        });
    }

    void mineThemeRouter() {
        RouterConfigure.checkLoginRouterUtils(viewModel.activity.getActivity(), CreateThemeActivity.class);
    }

    void toastUtils(String message) {
        ToastUtils.shortToast(viewModel.activity.getActivity(), message);
    }
}
