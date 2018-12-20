package com.lznby.jetpack.content.design.configure;

import android.content.Context;
import android.content.Intent;

import com.lznby.jetpack.base.callback.SimpleCallback;
import com.lznby.jetpack.content.design.ui.LoginActivity;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * @author Lznby
 */
public class RouterConfigure {

    /**
     * router to login activity when user unLogin.
     *
     * @param context
     * @param callback
     */
    public static void isLoginRouter(Context context, SimpleCallback callback) {
        if (CacheConfigure.isLogin(context)) {
            //router to activity.
            callback.doSomething();
        } else {
            //router to activity.
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        }
    }

    /**
     * router to login activity when user unLogin.
     *
     * @param context
     * @param clazz
     */
    public static void checkLoginRouterUtils(Context context, Class clazz) {
        if (CacheConfigure.isLogin(context)) {
            //router to activity.
            Intent intent = new Intent(context, clazz);
            startActivity(intent);
        } else {
            //router to user login activity.
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        }
    }

    /**
     * no params and uncheck isLogin router utils.
     *
     * @param context
     * @param clazz
     */
    public static void normalRouterUtils(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
    }


}
