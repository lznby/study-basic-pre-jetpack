package com.lznby.jetpack.content.design.vm;

import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.entity.PersonalHomePageEntity;
import com.lznby.jetpack.content.design.ui.HomePageActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 个人主页ViewModel
 *
 * @author Lznby
 */
public class HomePageViewModel extends BaseActivityViewModel<HomePageActivity, PersonalHomePageEntity> {

    public void getHomePageViewModel(String userCookies, String userId) {
        addDisposable(
                IApplication.api.getPersonalHomePage(userCookies, userId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::doOnNext, Throwable::printStackTrace)
        );
    }

    private void doOnNext(BaseEntity<PersonalHomePageEntity> entity) {
        getLiveData().postValue(entity.getData());
    }
}
