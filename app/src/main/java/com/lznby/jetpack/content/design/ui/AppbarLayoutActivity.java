package com.lznby.jetpack.content.design.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.lznby.jetpack.R;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * AppbarLayout 布局演示
 *
 * @author Lznby
 */
public class AppbarLayoutActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindColor(R.color.colorRed)
    int titleColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appbar_layout);
        ButterKnife.bind(this);
        toolbar.setTitleTextColor(titleColor);
        BarUtils.setStatusBarAlpha(this,0);
    }
}
