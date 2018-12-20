package com.lznby.jetpack.content.design.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Router to FollowerActivity intent params.
 *
 * @author Lznby
 */
public class FollowerRouterEntity implements Parcelable {
    /**
     * Intent key
     */
    public static final String KEY = "FOLLOWER_ROUTER_ENTITY";
    /**
     * intent to loader follower data.
     */
    public static final String FOLLOWER = "粉丝";
    /**
     * intent to loader follow data.
     */
    public static final String FOLLOW = "关注";

    private String who;
    private String userId;
    private String type;

    public FollowerRouterEntity() {
    }

    public FollowerRouterEntity(String who, String userId, String type) {
        this.who = who;
        this.userId = userId;
        this.type = type;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.who);
        dest.writeString(this.userId);
        dest.writeString(this.type);
    }

    protected FollowerRouterEntity(Parcel in) {
        this.who = in.readString();
        this.userId = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<FollowerRouterEntity> CREATOR = new Parcelable.Creator<FollowerRouterEntity>() {
        @Override
        public FollowerRouterEntity createFromParcel(Parcel source) {
            return new FollowerRouterEntity(source);
        }

        @Override
        public FollowerRouterEntity[] newArray(int size) {
            return new FollowerRouterEntity[size];
        }
    };
}
