package com.lznby.jetpack.content.design.vm;

import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.entity.ArticleDetailsRouterEntity;
import com.lznby.jetpack.content.design.ui.ArticleDetailsActivity;
import com.lznby.jetpack.utils.ToastUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 资讯详情页ViewModel
 *
 * @author Lznby
 */
public class ArticleDetailsViewModel extends BaseActivityViewModel<ArticleDetailsActivity, ArticleAllInfoEntity> {
    /**
     * 资讯Intent传参
     */
    private ArticleDetailsRouterEntity params;

    public void setParams(ArticleDetailsRouterEntity params) {
        this.params = params;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        getArticleByFileAttribution();
    }

    /**
     * 获取资讯详情
     */
    public void getArticleByFileAttribution() {
        addDisposable(
                IApplication.api.getArticleByFileAttribution(CacheConfigure.getToken(activity.getActivity()), params.getFileAttribution())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::doOnNext, Throwable::printStackTrace)
        );
    }

    private void doOnNext(BaseEntity<List<ArticleAllInfoEntity>> entity) {
        getLiveData().setValue(entity.getData().get(0));
    }

    /**
     * 收藏资讯
     *
     * @param fileAttribution       资讯Id
     */
    public void articleSub(String fileAttribution) {
        addDisposable(
                IApplication.api.articleSub(CacheConfigure.getToken(activity.getActivity()), fileAttribution)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o->updateUI(o,true),Throwable::printStackTrace)
        );
    }

    /**
     * 取消资讯收藏
     *
     * @param fileAttribution       资讯Id
     */
    public void articleUnSub(String fileAttribution) {
        addDisposable(
                IApplication.api.articleUnSub(CacheConfigure.getToken(activity.getActivity()), fileAttribution)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o->updateUI(o,false),Throwable::printStackTrace)
        );
    }

    /**
     * 收藏与取消更新IU
     *
     * @param entity
     * @param isLove
     */
    private void updateUI(BaseEntity entity, boolean isLove) {
        if (getLiveData().getValue()!=null && entity.getCode()== Configure.ResponseCode.SUCCESS) {
            if (isLove) {
                getLiveData().getValue().setLove(true);
                getLiveData().getValue().getArticleEntity().setLoveCount(getLiveData().getValue().getArticleEntity().getLoveCount()+1);
            } else {
                getLiveData().getValue().setLove(false);
                getLiveData().getValue().getArticleEntity().setLoveCount(getLiveData().getValue().getArticleEntity().getLoveCount()-1);
            }
            getLiveData().postValue(getLiveData().getValue());
        } else {
            ToastUtils.shortToast(activity.getActivity(),entity.getMessage());
        }
    }
}
