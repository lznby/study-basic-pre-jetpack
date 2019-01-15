package com.lznby.jetpack.content.design.entity;
import java.util.List;

/**
 * 个人主页信息
 *
 * @author Lznby
 */
public class PersonalHomePageEntity {
    /**
     * 是否为本人
     */
    boolean isSelf;
    /**
     * 个人信息
     */
    UserBaseInfoEntity userBaseInfoEntity;
    /**
     * 资讯列表
     */
    List<ArticleAllInfoEntity> articleAllInfoEntities;
    /**
     * 用户关注及订阅(还要新增一个关注主题)
     */
    UserFollowerSizeEntity userFollowerSizeEntity;

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

    public UserBaseInfoEntity getUserBaseInfoEntity() {
        return userBaseInfoEntity;
    }

    public void setUserBaseInfoEntity(UserBaseInfoEntity userBaseInfoEntity) {
        this.userBaseInfoEntity = userBaseInfoEntity;
    }

    public List<ArticleAllInfoEntity> getArticleAllInfoEntities() {
        return articleAllInfoEntities;
    }

    public void setArticleAllInfoEntities(List<ArticleAllInfoEntity> articleAllInfoEntities) {
        this.articleAllInfoEntities = articleAllInfoEntities;
    }

    public UserFollowerSizeEntity getUserFollowerSizeEntity() {
        return userFollowerSizeEntity;
    }

    public void setUserFollowerSizeEntity(UserFollowerSizeEntity userFollowerSizeEntity) {
        this.userFollowerSizeEntity = userFollowerSizeEntity;
    }
}
