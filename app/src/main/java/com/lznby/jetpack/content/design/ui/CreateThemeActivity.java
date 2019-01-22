package com.lznby.jetpack.content.design.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.content.design.adapter.SelectorAdapter;
import com.lznby.jetpack.content.design.entity.FileEntity;
import com.lznby.jetpack.content.design.entity.ThemeEntity;
import com.lznby.jetpack.content.design.view.GridSpacingItemDecoration;
import com.lznby.jetpack.content.design.vm.CreateThemeViewModel;
import com.lznby.jetpack.utils.Glide4Engine;
import com.lznby.jetpack.utils.RxPermissionUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 创建主题
 *
 * @author Lznby
 */
public class CreateThemeActivity extends BaseActivity<CreateThemeViewModel, List<FileEntity>> implements Toolbar.OnMenuItemClickListener {

    /**
     * 知乎选择库意图
     */
    private static final int ZHIHU_CHOOSE_IMAGE = 24;

    /**
     * 预览图adapter
     */
    SelectorAdapter adapter;
    /**
     * 上传文件路径集合
     */
    List<FileEntity> files = new ArrayList<>();
    /**
     * 选择文件路径集合
     */
    List<String> urls = new ArrayList<>();


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_image)
    RecyclerView rvImage;
    @BindView(R.id.et_theme_title)
    EditText etThemeTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.iv_add)
    ImageView ivAdd;

    @Override
    protected int setLayout() {
        return R.layout.activity_create_theme;
    }

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_left_arrows);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 权限请求
        RxPermissionUtils.requestPermissions(this);

        // 设置布局管理器
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        rvImage.setLayoutManager(manager);
        adapter = new SelectorAdapter();
        rvImage.setAdapter(adapter);
        // 设置高宽相等(addItemDecoration这一条得好好看看)
        rvImage.addItemDecoration(new GridSpacingItemDecoration(3, 18, false));

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_del:
                        adapter.remove(position);
                        viewModel.getLiveData().postValue(viewModel.getLiveData().getValue());
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void bindView(List<FileEntity> entity) {
        adapter.setNewData(entity);
        if (entity.size() >= 1) {
            ivAdd.setVisibility(View.GONE);
        } else {
            ivAdd.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_theme, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_create_theme:
                // 发起创建主题请求
                ThemeEntity params = new ThemeEntity();
                params.setThemeName(etThemeTitle.getText().toString());
                params.setThemeNote(etContent.getText().toString());
                viewModel.uploadImage(urls,params);
                break;

            default:
                break;
        }
        return true;
    }

    @OnClick({R.id.iv_add})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                chooseImage();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // 知乎图片选择
            case ZHIHU_CHOOSE_IMAGE: {
                if (resultCode == RESULT_OK) {
                    urls = Matisse.obtainPathResult(data);
                }
                for (String single : urls) {
                    files.add(new FileEntity(single, FileEntity.IMAGE));
                }
                viewModel.getLiveData().postValue(files);
            }
            break;

            default:
                break;
        }
    }

    /**
     * 选择图片
     */
    private void chooseImage() {
        // 选择图片
        Matisse.from(CreateThemeActivity.this)
                .choose(MimeType.ofAll(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.lznby.jetpack.android.provider", "image"))
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Timber.e("onSelected: pathList=%s", pathList);

                    }
                })
                // 是否显示使用原图
                .originalEnable(false)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        // 这里是用于预留压缩图片的
                        Timber.e("onCheck: isChecked=%s", isChecked);
                    }
                })
                .forResult(ZHIHU_CHOOSE_IMAGE);
        clearChoose();
    }

    /**
     * 清空数据
     */
    private void clearChoose() {
        viewModel.getLiveData().postValue(new ArrayList<>());
        files.clear();
        urls.clear();
    }
}
