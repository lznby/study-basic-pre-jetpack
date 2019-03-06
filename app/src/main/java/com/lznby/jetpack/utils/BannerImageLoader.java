package com.lznby.jetpack.utils;

import android.content.Context;
import android.widget.ImageView;

import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.youth.banner.loader.ImageLoader;

/**
 * 轮播图图片加载
 *
 * @author Lznby
 */
public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        String url = "https://lznby-image.oss-cn-shenzhen.aliyuncs.com/25e68919031d1ec70342b795ce377d42.png";
        ArticleAllInfoEntity entity = (ArticleAllInfoEntity)path;
        if (entity.getFilePathEntities()!=null) {
            if (entity.getFilePathEntities().size()>0) {
                url = entity.getFilePathEntities().get(0).getFilePath();
            }
        }
        LoaderImageUtils.loaderUrlImage(context,url,imageView);
    }
}
