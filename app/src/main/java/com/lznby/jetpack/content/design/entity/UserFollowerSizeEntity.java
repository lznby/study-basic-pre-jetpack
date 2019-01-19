package com.lznby.jetpack.content.design.entity;

/**
 * @author Lznby
 */
public class UserFollowerSizeEntity {
    /**
     * 关注数
     */
    private int followSize;
    /**
     * 粉丝数
     */
    private int followerSize;
    /**
     * 关注主题数
     */
    private int followThemeSize;

    public UserFollowerSizeEntity() {
    }

    public UserFollowerSizeEntity(int followSize, int followerSize, int followThemeSize) {
        this.followSize = followSize;
        this.followerSize = followerSize;
        this.followThemeSize = followThemeSize;
    }

    public int getFollowSize() {
        return followSize;
    }

    public void setFollowSize(int followSize) {
        this.followSize = followSize;
    }

    public int getFollowerSize() {
        return followerSize;
    }

    public void setFollowerSize(int followerSize) {
        this.followerSize = followerSize;
    }

    public int getFollowThemeSize() {
        return followThemeSize;
    }

    public void setFollowThemeSize(int followThemeSize) {
        this.followThemeSize = followThemeSize;
    }
}