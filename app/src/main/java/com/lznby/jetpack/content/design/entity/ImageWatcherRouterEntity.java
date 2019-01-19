package com.lznby.jetpack.content.design.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 图片浏览控件点击事件
 *
 * @author Lznby
 */
public class ImageWatcherRouterEntity implements Parcelable {

    public static final String KEY = "ImageWatcherRouterEntity";

    /**
     * 图片路径地址
     */
    private List<String> urls;
    /**
     * 点击图片所在位置
     */
    private int position;

    public ImageWatcherRouterEntity() {
    }

    public ImageWatcherRouterEntity(List<String> urls, int posistion) {
        this.urls = urls;
        this.position = posistion;
    }

    private ImageWatcherRouterEntity(Parcel in) {
        urls = in.createStringArrayList();
        position = in.readInt();
    }

    public static final Creator<ImageWatcherRouterEntity> CREATOR = new Creator<ImageWatcherRouterEntity>() {
        @Override
        public ImageWatcherRouterEntity createFromParcel(Parcel in) {
            return new ImageWatcherRouterEntity(in);
        }

        @Override
        public ImageWatcherRouterEntity[] newArray(int size) {
            return new ImageWatcherRouterEntity[size];
        }
    };

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(urls);
        dest.writeInt(position);
    }
}
