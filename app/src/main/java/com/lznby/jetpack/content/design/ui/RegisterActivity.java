package com.lznby.jetpack.content.design.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.widget.EditText;
import android.widget.Toast;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.content.design.params.RegisterParams;
import com.lznby.jetpack.content.design.vm.RegisterViewModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Lznby
 */
public class RegisterActivity extends BaseActivity<RegisterViewModel,BaseEntity<Object>> {
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_re_password)
    EditText mEtRePassword;

    @Override
    protected int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void doOnCreate() {

    }

    @Override
    protected void bindView(BaseEntity<Object> entity) {
        Toast.makeText(RegisterActivity.this, entity.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.bt_register)
    protected void register() {
        RegisterParams params = new RegisterParams();
        params.setUserNickName(mEtUsername.getText().toString().trim());
        params.setUserPassword(mEtPassword.getText().toString().trim());
        viewModel.register(params);
    }

    /**
     * 启动RegisterActivity界面
     * @param context
     */
    public static void startActivity(Activity context) {
        Intent intent = new Intent(context,RegisterActivity.class);
        ActivityCompat.startActivity(context,intent,null);
    }

}
