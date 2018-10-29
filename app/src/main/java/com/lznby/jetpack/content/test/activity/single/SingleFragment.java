package com.lznby.jetpack.content.test.activity.single;


import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.ui.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Lznby
 */
public class SingleFragment extends BaseFragment<StringViewModel,String> {

    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.et_show)
    EditText etShow;
    @BindView(R.id.ll_animation)
    LinearLayout llAnimation;

    @Override
    public int setLayout() {
        return R.layout.fragment_single;
    }

    @Override
    protected void bindView(String entity) {
        tvShow.setText(entity);
    }


    @Override
    protected void doOnCreateView() {

    }

    @OnClick(R.id.bt_show)
    public void onViewClicked() {
        viewModel.getLiveData().postValue(etShow.getText().toString().trim());
    }
}
