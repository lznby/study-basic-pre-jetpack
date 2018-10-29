package com.lznby.jetpack.content.test.activity.base;

import android.widget.EditText;
import android.widget.TextView;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Lznby
 */
public class MainActivity extends BaseActivity<StringViewModel,String> {

    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.et_show)
    EditText etShow;

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView(String entity) {
        tvShow.setText(entity);
    }

    @Override
    protected void doOnCreate() {

    }

    @OnClick(R.id.bt_show)
    public void onClick() {
        viewModel.getLiveData().postValue(etShow.getText().toString().trim());
    }

}
