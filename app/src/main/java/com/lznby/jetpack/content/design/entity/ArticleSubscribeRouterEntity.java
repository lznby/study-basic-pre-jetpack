package com.lznby.jetpack.content.design.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 跳转到用户所有资讯订阅页面
 *
 * @author Lznby
 */
public class ArticleSubscribeRouterEntity implements Parcelable {
    /**
     * Intent key
     */
    public static final String KEY = "ArticleSubscribeRouterEntity";
    /**
     * 被查询收藏用户的Id
     */
    private String userId;

    protected ArticleSubscribeRouterEntity(Parcel in) {
        userId = in.readString();
    }

    public static final Creator<ArticleSubscribeRouterEntity> CREATOR = new Creator<ArticleSubscribeRouterEntity>() {
        @Override
        public ArticleSubscribeRouterEntity createFromParcel(Parcel in) {
            return new ArticleSubscribeRouterEntity(in);
        }

        @Override
        public ArticleSubscribeRouterEntity[] newArray(int size) {
            return new ArticleSubscribeRouterEntity[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArticleSubscribeRouterEntity() {
    }

    public ArticleSubscribeRouterEntity(String userId) {
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
