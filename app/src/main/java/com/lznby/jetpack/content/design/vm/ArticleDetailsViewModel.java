package com.lznby.jetpack.content.design.vm;

import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.entity.ArticleDetailsRouterEntity;
import com.lznby.jetpack.content.design.ui.ArticleDetailsActivity;

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
    private void getArticleByFileAttribution() {
        addDisposable(
                IApplication.api.getArticleByFileAttribution(CacheConfigure.getToken(activity.getActivity()), params.getFileAttribution())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::doOnNext, Throwable::printStackTrace)
        );
    }

    private void doOnNext(BaseEntity<List<ArticleAllInfoEntity>> entity) {
        getLiveData().setValue(entity.getData().get(0));
    }
}
