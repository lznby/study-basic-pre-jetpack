package com.lznby.jetpack.content.design.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 跳转到资讯详情页
 *
 * @author Lznby
 */
public class ArticleDetailsRouterEntity implements Parcelable {
    /**
     * Intent key
     */
    public static final String KEY = "ARTICLE_DETAILS_ROUTER_ENTITY";

    /**
     * 资讯媒体类型
     */
    private String type;
    /**
     * 资讯Id
     */
    private String fileAttribution;

    public ArticleDetailsRouterEntity() {
    }

    public ArticleDetailsRouterEntity(String type, String fileAttribution) {
        this.type = type;
        this.fileAttribution = fileAttribution;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileAttribution() {
        return fileAttribution;
    }

    public void setFileAttribution(String fileAttribution) {
        this.fileAttribution = fileAttribution;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.fileAttribution);
    }

    protected ArticleDetailsRouterEntity(Parcel in) {
        this.type = in.readString();
        this.fileAttribution = in.readString();
    }

    public static final Parcelable.Creator<ArticleDetailsRouterEntity> CREATOR = new Parcelable.Creator<ArticleDetailsRouterEntity>() {
        @Override
        public ArticleDetailsRouterEntity createFromParcel(Parcel source) {
            return new ArticleDetailsRouterEntity(source);
        }

        @Override
        public ArticleDetailsRouterEntity[] newArray(int size) {
            return new ArticleDetailsRouterEntity[size];
        }
    };
}
