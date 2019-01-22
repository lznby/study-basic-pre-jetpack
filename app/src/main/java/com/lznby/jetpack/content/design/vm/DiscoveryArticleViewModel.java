package com.lznby.jetpack.content.design.vm;

import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.base.BaseFragmentViewModel;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.ui.CenterActivity;
import com.lznby.jetpack.content.design.ui.DiscoveryArticleFragment;
import com.lznby.jetpack.net.transform.RestfulTransformer;
import com.lznby.jetpack.utils.ToastUtils;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 发现-资讯-ViewModel
 *
 * @author Lznby
 */
public class DiscoveryArticleViewModel extends BaseFragmentViewModel<DiscoveryArticleFragment, CenterActivity, List<ArticleAllInfoEntity>> {
    @Override
    public void onResume() {
        super.onResume();
        getArticleInfo();
    }

    private void getArticleInfo() {
        addDisposable(
                IApplication.api.getAllArticle(CacheConfigure.getToken(getActivityContent()))
                .compose(new RestfulTransformer<>())
                .observeOn(Schedulers.io())
                .subscribe(this::doOnNext,Throwable::printStackTrace)
        );
    }

    private void doOnNext(List<ArticleAllInfoEntity> articleAllInfoEntities) {
        getLiveData().postValue(articleAllInfoEntities);
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
            ToastUtils.shortToast(getActivityContent(),entity.getMessage());
        }
    }
}
