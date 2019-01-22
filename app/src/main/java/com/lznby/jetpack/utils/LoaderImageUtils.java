package com.lznby.jetpack.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.lznby.jetpack.configure.GlideApp;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

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
        RequestOptions requestOptions = RequestOptions.centerCropTransform();

        //加载网络图片
        GlideApp.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(url)
                .placeholder(res)
                .skipMemoryCache(false)
                .dontAnimate()
                .into(view);
    }

    /**
     * 高斯模糊图片加载
     * @param context
     * @param url
     * @param res
     * @param view
     */
    public static void loaderBlurImageView(Context context, String url, @DrawableRes int res, ImageView view) {
        //高斯图片加载
        MultiTransformation<Bitmap> multiTransformation = new MultiTransformation<>(
                new BlurTransformation(50),
                new CenterCrop()
        );

        GlideApp.with(context)
                .load(res)
                .apply(RequestOptions.bitmapTransform(multiTransformation))
                .skipMemoryCache(false)
                .dontAnimate()
                .into(view);
    }

    /**
     * 加载本地图片
     * @param context
     * @param res
     * @param view
     */
    public static void loaderLocalImageView(Context context, @DrawableRes int res ,ImageView view) {
        GlideApp
                .with(context)
                .load(res)
                .skipMemoryCache(false)
                .dontAnimate()
                .into(view);
    }


    /**
     * 加载网络图片
     * @param context
     * @param url
     * @param res
     * @param view
     */
    public static void loaderImageView(Context context, String url, @DrawableRes int res, ImageView view) {
        //设置图片圆角角度
        RequestOptions requestOptions = RequestOptions.centerCropTransform();

        //加载网络图片
        GlideApp.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(url)
                .placeholder(res)
                .skipMemoryCache(false)
                .dontAnimate()
                .into(view);
    }

    /**
     * 加载网络图片
     *
     * @param context
     * @param url
     * @param view
     */
    public static void loaderUrlImage(Context context, String url, ImageView view) {
        GlideApp.with(context)
                .load(url)
                .skipMemoryCache(false)
                .dontAnimate()
                .into(view);
    }

    /**
     * 加载中心地图
     *
     * @param context
     * @param url
     * @param view
     */
    public static void loaderUrlCenterImage(Context context, String url, ImageView view) {
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .skipMemoryCache(false)
                .dontAnimate()
                .into(view);
    }

}
