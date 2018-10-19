package com.lznby.jetpack.content.livedata.vm;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * @author Lznby
 */
public class StringViewModel extends ViewModel {

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

    //注意LiveData的更新操作在ViewModel中进行,而不是在Fragment或Activity中进行.
    //1.防止Activity和Fragment中代码过多.且UI中不保存数据状态.
    //2.将Activity和Fragment的实例与LiveData实例分开,允许LiveData对象在配置更改后继续存在.

}
