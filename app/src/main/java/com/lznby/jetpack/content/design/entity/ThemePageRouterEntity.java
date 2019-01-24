package com.lznby.jetpack.content.design.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 跳转到主题首页
 *
 * @author Lznby
 */
public class ThemePageRouterEntity implements Parcelable {
    /**
     * Intent key
     */
    public static final String KEY = "ThemePageRouterEntity";
    /**
     * 查看的主题Id
     */
    private String themeId;

    public ThemePageRouterEntity() {
    }

    public ThemePageRouterEntity(String themeId) {
        this.themeId = themeId;
    }

    protected ThemePageRouterEntity(Parcel in) {
        themeId = in.readString();
    }

    public static final Creator<ThemePageRouterEntity> CREATOR = new Creator<ThemePageRouterEntity>() {
        @Override
        public ThemePageRouterEntity createFromParcel(Parcel in) {
            return new ThemePageRouterEntity(in);
        }

        @Override
        public ThemePageRouterEntity[] newArray(int size) {
            return new ThemePageRouterEntity[size];
        }
    };

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(themeId);
    }
}
