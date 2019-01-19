package com.lznby.jetpack.content.design.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 跳转到主题列表界面
 *
 * @author Lznby
 */
public class ThemeRouterEntity implements Parcelable {
    /**
     * Intent key
     */
    public static final String KEY = "THEME_ROUTER_ENTITY";

    public ThemeRouterEntity() {
    }

    public ThemeRouterEntity(String userId) {
        this.userId = userId;
    }

    /**
     * userId(查看谁的)
     */
    private String userId;

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
        dest.writeString(this.userId);
    }

    protected ThemeRouterEntity(Parcel in) {
        this.userId = in.readString();
    }

    public static final Parcelable.Creator<ThemeRouterEntity> CREATOR = new Parcelable.Creator<ThemeRouterEntity>() {
        @Override
        public ThemeRouterEntity createFromParcel(Parcel source) {
            return new ThemeRouterEntity(source);
        }

        @Override
        public ThemeRouterEntity[] newArray(int size) {
            return new ThemeRouterEntity[size];
        }
    };
}
