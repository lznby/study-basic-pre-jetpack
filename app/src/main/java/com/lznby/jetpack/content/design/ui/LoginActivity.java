package com.lznby.jetpack.content.design.ui;

import android.view.View;
import android.widget.EditText;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.content.design.entity.LoginEntity;
import com.lznby.jetpack.content.design.params.LoginParams;
import com.lznby.jetpack.content.design.vm.LoginViewModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Lznby
 */
public class LoginActivity extends BaseActivity<LoginViewModel, BaseEntity<LoginEntity>> {

    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void doOnCreate() {

    }

    @Override
    protected void bindView(BaseEntity<LoginEntity> entity) {

    }

    @OnClick({R.id.bt_login, R.id.bt_register})
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
            default:
                break;
        }
    }

}
