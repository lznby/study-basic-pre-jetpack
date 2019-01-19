package com.lznby.jetpack.content.design.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.BarUtils;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.content.design.entity.LoginEntity;
import com.lznby.jetpack.content.design.params.LoginParams;
import com.lznby.jetpack.content.design.vm.LoginViewModel;
import com.lznby.jetpack.utils.LoaderImageUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Lznby
 */
public class LoginActivity extends BaseActivity<LoginViewModel, BaseEntity<LoginEntity>> {

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.cl_body)
    ConstraintLayout clBody;
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    AppCompatEditText mEtPassword;
    @BindView(R.id.civ_header)
    CircleImageView civHeader;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        // 设置状态栏透明
        BarUtils.setStatusBarAlpha(this,0);
        // 为view增加MarginTop为状态栏高度
        BarUtils.addMarginTopEqualStatusBarHeight(clBody);
        // 加载高斯模糊背景
        LoaderImageUtils.loaderBlurImageView(this,null,R.mipmap.bg_login,ivBg);
        // 加载默认头像
        LoaderImageUtils.loaderLocalImageView(this,R.mipmap.background,civHeader);

    }

    @Override
    protected void bindView(BaseEntity<LoginEntity> entity) {

    }

    @OnClick({R.id.bt_login, R.id.bt_register, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                LoginParams params = new LoginParams();
                params.setUserNickName(mEtUsername.getText().toString().trim());
                params.setUserPassword(mEtPassword.getText().toString().trim());
                viewModel.login(params);
                break;
            case R.id.bt_register:
                RegisterActivity.startActivity(this);
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

}
