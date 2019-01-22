package com.lznby.jetpack.content.design.vm;

import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.alibaba.oss.model.StsModel;
import com.lznby.jetpack.content.design.alibaba.oss.utils.OssUtils;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.entity.FileEntity;
import com.lznby.jetpack.content.design.entity.ThemeEntity;
import com.lznby.jetpack.content.design.ui.CreateThemeActivity;
import com.lznby.jetpack.utils.FileUtils;
import com.lznby.jetpack.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 创建主题
 *
 * @author Lznby
 */
public class CreateThemeViewModel extends BaseActivityViewModel<CreateThemeActivity, List<FileEntity>> {

    private StsModel sts = new StsModel();


    /**
     * 创建主题
     *
     * @param params
     */
    private void createTheme(ThemeEntity params) {
        addDisposable(
                IApplication.api.createTheme(CacheConfigure.getToken(activity.getActivity()), params.getThemeName(), params.getThemeNote(), params.getThemeImage(), params.getThemeHeaderImage())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o -> ToastUtils.shortToast(activity.getActivity(), o.getMessage()), Throwable::printStackTrace)
        );
    }

    /**
     * 上传图片
     *
     * @param urls
     */
    public void uploadImage(List<String> urls, ThemeEntity params) {
        List<String> ossUrls = new ArrayList<>();
        addDisposable(
                Flowable.fromIterable(urls)
                        .map(File::new)
                        .map(o -> {
                            //根据上传文件类型确定处理过程
                            switch (FileUtils.getType(o.getPath())) {
                                case FileUtils.IMAGE:
                                    return new Compressor(activity.getActivity()).compressToFile(o);
                                case FileUtils.VIDEO:
                                    return o;
                                case FileUtils.AUDIO:
                                    return o;
                                default:
                                    return o;
                            }
                        })
                        .observeOn(Schedulers.io())
                        .doOnNext(
                                // 上传压缩后的图片到到Oss服务器
                                o -> ossUrls.add(OssUtils.uploadImage(activity.getActivity(), getSts(), o.getPath()))
                        )
                        .doOnComplete(() -> {
                            params.setThemeHeaderImage(ossUrls.size() > 0 ? ossUrls.get(0) : "");
                            params.setThemeImage(ossUrls.size() > 0 ? ossUrls.get(0) : "");
                            // 执行资讯创建步骤
                            createTheme(params);
                        }).subscribe()
        );
    }


    /**
     * 从Sts服务器获取OSS动态口令
     */
    private void getStsFromService() {
        addDisposable(
                IApplication.api.getOssSts()
                        .observeOn(Schedulers.io())
                        .subscribe(o -> sts = o.getData(), Throwable::printStackTrace)
        );
    }

    private StsModel getSts() {
        return sts;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getStsFromService();
    }
}
