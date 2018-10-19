package com.lznby.jetpack.content.livedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.content.livedata.vm.StringViewModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Lznby
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.et_show)
    EditText etShow;

    /**
     * 持有LiveData的ViewModel对象
     */
    private StringViewModel stringViewModel;

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void doOnCreate() {

        // Activity中其他代码片段
        // Other code to setup the activity...

        // 获得ViewModel
        // Get the ViewModel.
        stringViewModel = ViewModelProviders.of(this).get(StringViewModel.class);

        // 创建更新UI的观察者
        // Create the observer which updates the UI.
        final Observer<String> stringObserver = new Observer<String>() {

            @Override
            public void onChanged(@Nullable String s) {
                // 此处更新UI, 此处以TextView为例.
                // Update the UI, in this case, a TextView.
                tvShow.setText(s);
            }
        };
        // 观察(订阅)LiveData, 将此活动作为LifecycleOwner和Observer(观察者)传递
        // Observe the LiveData,passing in this activity as the LifecycleOwner and the observer.
        stringViewModel.getCurrentString().observe(this,stringObserver);
    }

    @OnClick(R.id.bt_show)
    public void onClick() {
        stringViewModel.getCurrentString().setValue(etShow.getText().toString().trim());
    }
}
