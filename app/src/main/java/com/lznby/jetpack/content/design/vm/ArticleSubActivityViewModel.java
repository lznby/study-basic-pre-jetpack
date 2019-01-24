package com.lznby.jetpack.content.design.vm;

import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.entity.ArticleSubscribeRouterEntity;
import com.lznby.jetpack.content.design.ui.ArticleSubscribeActivity;
import com.lznby.jetpack.net.transform.RestfulTransformer;
import com.lznby.jetpack.utils.ToastUtils;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 资讯收藏-ViewModel
 *
 * @author Lznby
 */
public class ArticleSubActivityViewModel extends BaseActivityViewModel<ArticleSubscribeActivity,List<ArticleAllInfoEntity>> {

    /**
     * 取决于查询哪个用户的收藏
     */
    private ArticleSubscribeRouterEntity params;

    @Override
    public void onCreate() {
        super.onCreate();
        getSubArticle(params.getUserId());
    }

    public void getSubArticle(String userId) {
        addDisposable(
                IApplication.api.getAllSubArticle(CacheConfigure.getToken(activity.getActivity()),userId)
                .compose(new RestfulTransformer<>())
                .observeOn(Schedulers.io())
                .subscribe(this::doOnNext,Throwable::printStackTrace)
        );
    }

    private void doOnNext(List<ArticleAllInfoEntity> entity) {
        getLiveData().postValue(entity);
    }

    /**
     * 收藏资讯
     *
     * @param fileAttribution       资讯Id
     * @param position              资讯位于RecyclerView中位置
     */
    public void articleSub(String fileAttribution, int position) {
        addDisposable(
                IApplication.api.articleSub(CacheConfigure.getToken(activity.getActivity()), fileAttribution)
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
                IApplication.api.articleUnSub(CacheConfigure.getToken(activity.getActivity()), fileAttribution)
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
        if (Objects.requireNonNull(getLiveData().getValue()).size()>position && entity.getCode()== Configure.ResponseCode.SUCCESS) {
            if (isLove) {
                getLiveData().getValue().get(position).setLove(true);
                getLiveData().getValue().get(position).getArticleEntity().setLoveCount(getLiveData().getValue().get(position).getArticleEntity().getLoveCount()+1);
            } else {
                getLiveData().getValue().get(position).setLove(false);
                getLiveData().getValue().get(position).getArticleEntity().setLoveCount(getLiveData().getValue().get(position).getArticleEntity().getLoveCount()-1);
            }
            getLiveData().postValue(getLiveData().getValue());
        } else {
            ToastUtils.shortToast(activity.getActivity(),entity.getMessage());
        }
    }

    public ArticleSubscribeRouterEntity getParams() {
        return params;
    }

    public void setParams(ArticleSubscribeRouterEntity params) {
        this.params = params;
    }
}
