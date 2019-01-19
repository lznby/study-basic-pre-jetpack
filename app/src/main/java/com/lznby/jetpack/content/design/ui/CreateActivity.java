package com.lznby.jetpack.content.design.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.content.design.adapter.SelectorAdapter;
import com.lznby.jetpack.content.design.entity.FileEntity;
import com.lznby.jetpack.content.design.view.GridSpacingItemDecoration;
import com.lznby.jetpack.content.design.vm.CreateViewModel;
import com.lznby.jetpack.utils.Glide4Engine;
import com.lznby.jetpack.utils.RxPermissionUtils;
import com.lznby.jetpack.utils.ToastUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 创建资讯界面
 *
 * @author Lznby
 */
public class CreateActivity extends BaseActivity<CreateViewModel, List<FileEntity>> implements Toolbar.OnMenuItemClickListener {

    /**
     * 知乎选择库意图
     */
    private static final int ZHIHU_CHOOSE_VIDEO = 23;
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
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.rv_image)
    RecyclerView rvImage;
    @BindView(R.id.sp_theme)
    AppCompatSpinner spTheme;
    @BindView(R.id.et_content)
    EditText etContent;

    @Override
    protected int setLayout() {
        return R.layout.activity_create;
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
                        break;
                    default:
                        break;
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.shortToast(CreateActivity.this, String.valueOf(position));
            }
        });

    }

    @Override
    protected void bindView(List<FileEntity> entity) {
        adapter.setNewData(entity);
        adapter.notifyDataSetChanged();
        Timber.e("文件数量:%d", adapter.getData().size());
        for (FileEntity single : adapter.getData()) {
            Timber.e("文件路径:%s", single.getUrl());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_publish:
                // 上传资讯
                List<String> themes = new ArrayList<>();
                themes.add("a182c5c9-203c-4394-964b-a59bec6d34e1");
                Gson gson = new Gson();
                viewModel.uploadImage(urls,"",etContent.getText().toString(),gson.toJson(themes));
                break;
            default:
                break;
        }
        return true;
    }

    @OnClick({R.id.iv_image, R.id.iv_video})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_image:
                chooseImage();
                break;
            case R.id.iv_video:
                chooseVideo();
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
                    files.add(new FileEntity(single, FileEntity.VIDEO));
                }
                viewModel.getLiveData().postValue(files);
            }
            break;
            // 知乎视频选择
            case ZHIHU_CHOOSE_VIDEO:
                if (resultCode == RESULT_OK) {
                    urls = Matisse.obtainPathResult(data);
                }
                for (String single : urls) {
                    files.add(new FileEntity(single, FileEntity.VIDEO));
                }
                viewModel.getLiveData().postValue(files);
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
        Matisse.from(CreateActivity.this)
                .choose(MimeType.ofAll(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.lznby.jetpack.android.provider", "video"))
                .maxSelectable(9)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

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
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(ZHIHU_CHOOSE_IMAGE);
        clearChoose();
    }

    /**
     * 选择视屏
     */
    private void chooseVideo() {
        // 选择视屏
        Matisse.from(CreateActivity.this)
                .choose(MimeType.ofVideo())
                .showSingleMediaType(true)
                .countable(false)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.lznby.jetpack.android.provider", "video"))
                .maxSelectable(9)
                .originalEnable(true)
                .maxOriginalSize(10)
                .imageEngine(new PicassoEngine())
                .forResult(ZHIHU_CHOOSE_VIDEO);
        clearChoose();
    }

    /**
     * 清除
     */
    private void clearChoose() {
        //清空数据
        viewModel.getLiveData().postValue(new ArrayList<>());
        files.clear();
        urls.clear();
    }


    /**
     * 压缩并上传图片 应该要设置上传类型
     */
    private void uploadZipImage() {
        //        // 上传到到服务器的图片过多会有一些问题(OOM)
        //        list.add(
        //                Flowable.fromIterable(urls)
        //                        .map(File::new)
        //                        .doOnNext(
        //                                o -> zipFiles.add(new Compressor(this).compressToFile(o))
        //                        )
        //                        .doOnComplete(() -> {
        //                            MultipartBody.Part[] file = new MultipartBody.Part[urls.size()];
        //                            for (int i = 0; i < zipFiles.size(); i++) {
        //                                RequestBody requestFile = RequestBody.create(MediaType.parse(FileUtils.getFileContentType(urls.get(i))), zipFiles.get(i));
        //                                file[i] = MultipartBody.Part.createFormData("files", zipFiles.get(i).getName(), requestFile);
        //                            }
        //                            viewModel.uploadImages(file);
        //                        }).subscribe()
        //        );

        //        一波的写法
        //        list.add(
        //                Observable.fromIterable(urls)
        //                        .map(File::new)
        //                        .doOnNext(o -> zipFiles.add(new Compressor(this).compressToFile(o)))
        //                        .toList().toObservable()
        //                        .subscribeOn(Schedulers.newThread())
        //                        .map(files -> {
        //                            MultipartBody.Part[] file = new MultipartBody.Part[urls.size()];
        //                            for (int i = 0; i < zipFiles.size(); i++) {
        //                                RequestBody requestFile = RequestBody.create(MediaType.parse(FileUtils.getFileContentType(urls.get(i))), zipFiles.get(i));
        //                                file[i] = MultipartBody.Part.createFormData("files", zipFiles.get(i).getName(), requestFile);
        //                            }
        //                            return file;
        //                        }).subscribe(files -> viewModel.uploadImages(files))
        //        );
    }
}
