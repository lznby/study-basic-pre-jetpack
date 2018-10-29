package com.lznby.jetpack.base.configure;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lznby.jetpack.base.net.Api;
import com.lznby.jetpack.base.net.ApiUtils;
import com.lznby.jetpack.base.utils.TimberUtil;

/**
 * @author Lznby
 */
public class IApplication extends Application {

    /**
     * 标识是否为调试
     */
    private boolean isDebug = true;

    /**
     * Context
     */
    private static Context context;

    /**
     * 网络请求Api
     */
    public static Api api;

    /**
     * 设置是否为调试阶段
     * @param debug
     */
    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 初始化Application
         */
        if (isDebug) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init( this);

        //获取Application的Context
        context = getApplicationContext();

        //设置log自动在apk为debug版本时打开，在release版本时关闭
        TimberUtil.setLogAuto();

        //全局调用网络请求
        api = ApiUtils.INSTANCE.getApi(this);
    }

    /**
     * 全局获取Context
     * @return
     */
    public static Context getContext() {
        return context;
    }

}
