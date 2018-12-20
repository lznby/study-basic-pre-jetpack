package com.lznby.jetpack.content.livedata.vm;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.content.livedata.entity.UserEntity;

/**
 * @author Lznby
 */
public class StringViewModel extends BaseActivityViewModel {

    // 使用String创建LiveData
    // Create a LiveData with a String

    private MutableLiveData<String> currentString;

    public  MutableLiveData<String> getCurrentString() {
        if (currentString == null) {
            currentString = new MutableLiveData<>();
        }
        return currentString;
    }

    // ViewModel 的其余部分
    // Rest of the ViewModel...

    //注意LiveData应放在ViewModel中进行,而不是在Fragment或Activity中.
    //1.防止Activity和Fragment中代码过多.且UI中不保存数据状态.
    //2.将Activity和Fragment的实例与LiveData实例分开,允许LiveData对象在配置更改后继续存在.


    // LiveData transform
    // LiveData 间的转换

    private LiveData<UserEntity> currentUserEntity = new MutableLiveData<>();

    // 1.通过Transformations.map转换

    public void transformations() {
        LiveData<String> currentTransform = Transformations.map(currentUserEntity,user->
            user.getAge() + "" + user.getName()
        );
    }


    // 2.通过Transformations.switchMap()转换

    private LiveData<UserEntity> getUser(String name) {
        return currentUserEntity;
    }
    public void swithMap() {
        LiveData<String> currentMap = new MutableLiveData<>();
        LiveData<UserEntity> user = Transformations.switchMap(currentMap,id -> getUser(id));
    }



}
