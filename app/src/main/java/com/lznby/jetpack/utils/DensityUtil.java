package com.lznby.jetpack.utils;

import android.content.Context;

/**
 * @author Lznby
 * @time 2018/5/29 13:25
 * Class Note: 单位转化工具，px和dip互相转换，px与sp相互转换
 */
public class DensityUtil {
    /**
     * 根据手机的分辨率从dip的单位转化为px（像素）
     */
    public static int dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从px（像素）的单位转化为dip
     */
    public static int px2dip(Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机分辨率从sp转换为px(像素)
     */
    public static int sp2px(Context context, int spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 根据手机分辨率从px(像素)转换为sp
     */
    public static int px2sp(Context context,float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
