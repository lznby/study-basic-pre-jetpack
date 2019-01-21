package com.lznby.jetpack.content.design.vm;

import com.google.gson.Gson;
import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.alibaba.oss.model.StsModel;
import com.lznby.jetpack.content.design.alibaba.oss.utils.OssUtils;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.entity.FileEntity;
import com.lznby.jetpack.content.design.ui.CreateActivity;
import com.lznby.jetpack.utils.FileUtils;
import com.lznby.jetpack.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

/**
 * 创建资讯界面ViewModel
 *
 * @author Lznby
 */
public class CreateViewModel extends BaseActivityViewModel<CreateActivity, List<FileEntity>> {

    private StsModel sts = new StsModel();

    /**
     * 上传资讯老的接口
     *
     * @param parts
     */
    public void uploadImages(MultipartBody.Part[] parts) {
        addDisposable(
                IApplication.api.uploadImage(parts)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::doOnNext, Throwable::printStackTrace)
        );
    }

    private void doOnNext(BaseEntity entity) {
        ToastUtils.shortToast(activity.getActivity(), entity.getMessage());
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

    /**
     * 上传资讯
     *
     * @param urls
     */
    public void uploadImage(List<String> urls, String title, String content, String themeId) {
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
                        .doOnNext(
                                // 上传压缩后的图片到到Oss服务器
                                o -> ossUrls.add(OssUtils.uploadImage(activity.getActivity(), getSts(), o.getPath()))
                        )
                        .doOnComplete(() -> {
                            Gson gson = new Gson();
                            String ossJson = gson.toJson(ossUrls);
                            String type = ossUrls.size() > 0 ? FileUtils.getType(ossUrls.get(0)) : FileUtils.IMAGE;
                            // 执行资讯创建步骤
                            createArticle(ossJson, title, content, themeId, type);
                        }).subscribe()
        );
    }

    /**
     * 创建资讯
     *
     * @param files
     * @param title
     * @param content
     * @param themeId
     */
    private void createArticle(String files, String title, String content, String themeId, String type) {
        addDisposable(
                IApplication.api.createArticle(CacheConfigure.getToken(activity.getActivity()), files, title, content, themeId, type)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o -> ToastUtils.shortToast(activity.getActivity(), o.getMessage()), Throwable::printStackTrace)
        );
    }

    private StsModel getSts() {
        return sts;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 获取OSS动态的口令
        getStsFromService();
        // 获自己已经关注了的主题

    }
}
