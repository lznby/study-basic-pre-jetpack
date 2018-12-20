package com.lznby.jetpack.content.design.ui;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.base.callback.SimpleCallback;
import com.lznby.jetpack.content.design.configure.RouterConfigure;
import com.lznby.jetpack.content.design.vm.SettingViewModel;
import com.lznby.jetpack.utils.ToastUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Lznby
 */
public class SettingActivity extends BaseActivity<SettingViewModel, Object> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_login_out)
    TextView mTvLoginOut;

    @Override
    protected int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void doOnCreate() {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        mToolbar.setNavigationIcon(R.mipmap.icon_left_arrows);
    }

    @Override
    protected void bindView(Object entity) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.itv_account_manager, R.id.itv_personal_info,R.id.tv_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.itv_account_manager:
                settingAccountInfoRouter();
                break;
            case R.id.itv_personal_info:
                settingPersonalInfoRouter();
                break;
            case R.id.tv_login_out:
                viewModel.loginOut();
                break;
            default:
                break;
        }
    }

    void settingAccountInfoRouter() {
        RouterConfigure.isLoginRouter(viewModel.activity.getActivity(), new SimpleCallback() {
            @Override
            public void doSomething() {
                //账号管理
                RouterConfigure.normalRouterUtils(viewModel.activity.getActivity(),AccountManagerActivity.class);
            }
        });
    }

    void settingPersonalInfoRouter() {
        RouterConfigure.isLoginRouter(viewModel.activity.getActivity(), new SimpleCallback() {
            @Override
            public void doSomething() {
                //个人信息
                RouterConfigure.normalRouterUtils(viewModel.activity.getActivity(),UserInfoActivity.class);
            }
        });
    }

    void toastUtils(String message) {
        ToastUtils.shortToast(viewModel.activity.getActivity(), message);
    }
}
