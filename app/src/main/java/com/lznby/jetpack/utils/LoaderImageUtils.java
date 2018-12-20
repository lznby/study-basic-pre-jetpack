package com.lznby.jetpack.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lznby.jetpack.configure.GlideApp;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Lznby
 */
public class LoaderImageUtils {

    /**
     * 加载圆形图片
     * @param context
     * @param url
     * @param res
     * @param view
     */
    public static void loaderCircleImageView(Context context, String url, @DrawableRes int res, CircleImageView view) {
        //设置图片圆角角度
        RequestOptions requestOptions = RequestOptions.centerCropTransform()
                //不做磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                //不做内存缓存
                .skipMemoryCache(true);

        //加载网络图片
        GlideApp.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(url)
                .placeholder(res)
                .into(view);
    }
}
