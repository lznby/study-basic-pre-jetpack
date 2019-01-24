package com.lznby.jetpack.content.design.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 跳转到用户主页界面
 *
 * @author Lznby
 */
public class HomePageRouterEntity implements Parcelable {
    /**
     * Intent key
     */
    public static final String KEY = "HomePageRouterEntity";
    /**
     * 被查看主页者的用户Id
     */
    private String userId;

    public HomePageRouterEntity() {
    }

    public HomePageRouterEntity(String userId) {
        this.userId = userId;
    }

    protected HomePageRouterEntity(Parcel in) {
        userId = in.readString();
    }

    public static final Creator<HomePageRouterEntity> CREATOR = new Creator<HomePageRouterEntity>() {
        @Override
        public HomePageRouterEntity createFromParcel(Parcel in) {
            return new HomePageRouterEntity(in);
        }

        @Override
        public HomePageRouterEntity[] newArray(int size) {
            return new HomePageRouterEntity[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
    }
}
