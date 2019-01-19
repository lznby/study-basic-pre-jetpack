package com.lznby.jetpack.content.design.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片浏览控件点击事件
 *
 * @author Lznby
 */
public class ImageWatcherItemEntity implements Parcelable {

    public static final String KEY = "ImageWatcherItemEntity";

    /**
     * 图片路径地址
     */
    private String url;
    /**
     * 点击图片所在位置
     */
    private int position;

    /**
     * 总数
     */
    private int size;

    public ImageWatcherItemEntity() {
    }

    public ImageWatcherItemEntity(String url, int position, int size) {
        this.url = url;
        this.position = position;
        this.size = size;
    }


    protected ImageWatcherItemEntity(Parcel in) {
        url = in.readString();
        position = in.readInt();
        size = in.readInt();
    }

    public static final Creator<ImageWatcherItemEntity> CREATOR = new Creator<ImageWatcherItemEntity>() {
        @Override
        public ImageWatcherItemEntity createFromParcel(Parcel in) {
            return new ImageWatcherItemEntity(in);
        }

        @Override
        public ImageWatcherItemEntity[] newArray(int size) {
            return new ImageWatcherItemEntity[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(position);
        dest.writeInt(size);
    }
}
