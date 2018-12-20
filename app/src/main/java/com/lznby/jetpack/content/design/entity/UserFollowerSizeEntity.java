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

    public UserFollowerSizeEntity() {
    }

    public UserFollowerSizeEntity(int followSize, int followerSize) {
        this.followSize = followSize;
        this.followerSize = followerSize;
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
}