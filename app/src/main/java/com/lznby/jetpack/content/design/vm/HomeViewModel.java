package com.lznby.jetpack.content.design.vm;

import android.arch.lifecycle.MutableLiveData;

import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.base.BaseFragmentViewModel;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.ui.CenterActivity;
import com.lznby.jetpack.content.design.ui.MainHomeFragment;
import com.lznby.jetpack.net.transform.RestfulTransformer;
import com.lznby.jetpack.utils.ToastUtils;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Lznby
 */
public class HomeViewModel extends BaseFragmentViewModel<MainHomeFragment,CenterActivity,List<ArticleAllInfoEntity>> {

    private MutableLiveData<List<ArticleAllInfoEntity>> articleLiveData;

    @Override
    public void onCreate() {
        super.onCreate();
        getBannerInfo(5);
        getArticleInfo();
    }

    public void getBannerInfo(int size) {
        addDisposable(
                IApplication.api.getBannerArticle(CacheConfigure.getToken(getActivityContent()),size)
                .compose(new RestfulTransformer<>())
                .observeOn(Schedulers.io())
                .subscribe(this::doOnNext,Throwable::printStackTrace)
        );
    }

    private void doOnNext(List<ArticleAllInfoEntity> entities) {
        getLiveData().postValue(entities);
    }

    public void getArticleInfo() {
        addDisposable(
                IApplication.api.getAllArticle(CacheConfigure.getToken(getActivityContent()))
                        .compose(new RestfulTransformer<>())
                        .observeOn(Schedulers.io())
                        .subscribe(this::postArticle,Throwable::printStackTrace)
        );
    }

    private void postArticle(List<ArticleAllInfoEntity> articleAllInfoEntities) {
        getArticleLiveData().postValue(articleAllInfoEntities);
    }

    public MutableLiveData<List<ArticleAllInfoEntity>> getArticleLiveData() {
        if (articleLiveData == null) {
            articleLiveData = new MutableLiveData<>();
        }
        return articleLiveData;
    }


    /**
     * 收藏资讯
     *
     * @param fileAttribution       资讯Id
     * @param position              资讯位于RecyclerView中位置
     */
    public void articleSub(String fileAttribution, int position) {
        addDisposable(
                IApplication.api.articleSub(CacheConfigure.getToken(getActivityContent()), fileAttribution)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o->updateUI(o,true,position),Throwable::printStackTrace)
        );
    }

    /**
     * 取消资讯收藏
     *
     * @param fileAttribution       资讯Id
     * @param position              资讯位于RecyclerView中位置
     */
    public void articleUnSub(String fileAttribution, int position) {
        addDisposable(
                IApplication.api.articleUnSub(CacheConfigure.getToken(getActivityContent()), fileAttribution)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o->updateUI(o,false,position),Throwable::printStackTrace)
        );
    }

    /**
     * 收藏与取消更新IU
     *
     * @param entity
     * @param isLove
     * @param position
     */
    private void updateUI(BaseEntity entity, boolean isLove, int position) {
        if (Objects.requireNonNull(getArticleLiveData().getValue()).size()>position && entity.getCode()== Configure.ResponseCode.SUCCESS) {
            if (isLove) {
                getArticleLiveData().getValue().get(position).setLove(true);
                getArticleLiveData().getValue().get(position).getArticleEntity().setLoveCount(getArticleLiveData().getValue().get(position).getArticleEntity().getLoveCount()+1);
            } else {
                getArticleLiveData().getValue().get(position).setLove(false);
                getArticleLiveData().getValue().get(position).getArticleEntity().setLoveCount(getArticleLiveData().getValue().get(position).getArticleEntity().getLoveCount()-1);
            }
            getLiveData().postValue(getLiveData().getValue());
        } else {
            ToastUtils.shortToast(getActivityContent(),entity.getMessage());
        }
    }

//    // OSS对象存储
//    private void getOssToken() {
//        getFragment()
//                .addDisposable(
//                        IApplication.api.getOssSts()
//                        .doOnNext(this::getUrl)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(this::doOnNext,Throwable::printStackTrace)
//                );
//    }
//
//    private void doOnNext(BaseEntity<StsModel> data) {
//        if (data.getData()!=null) {
//            Toast.makeText(getFragment().getContext(),data.getData().getSecurityToken(),Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    /**
//     * 获取Oss动态生成的图片Url
//     * @param data
//     */
//    private void getUrl(BaseEntity<StsModel> data) {
//        OSSCredentialProvider ossCredentialProvider = new StsGetter(new OSSFederationToken(
//                data.getData().getAccessKeyId(),
//                data.getData().getAccessKeySecret(),
//                data.getData().getSecurityToken(),
//                TimeUtils.date2TimeStamp(data.getData().getExpiration())
//        ));
//
//        ClientConfiguration conf = new ClientConfiguration();
//        // 连接超时，默认15秒
//        conf.setConnectionTimeout(15 * 1000);
//        // socket超时，默认15秒
//        conf.setSocketTimeout(15 * 1000);
//        // 最大并发请求书，默认5个
//        conf.setMaxConcurrentRequest(5);
//        // 失败后最大重试次数，默认2次
//        conf.setMaxErrorRetry(2);
//
//        OSS oss = new OSSClient(getActivityContent(),Config.endpoint,ossCredentialProvider,conf);
//        long timeEx = 60*1;
//        try {
//            String url = oss.presignConstrainedObjectURL(Config.bucket,"cheess.png",timeEx);
//            System.out.println("转换后地址："+url);
//        } catch (ClientException e) {
//            e.printStackTrace();
//            System.out.println("转化URL失败");
//        }
//    }



}
