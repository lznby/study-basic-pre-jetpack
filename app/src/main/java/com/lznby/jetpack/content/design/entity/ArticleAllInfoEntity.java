package com.lznby.jetpack.content.design.entity;

import java.util.List;

/**
 * 资讯所有相关信息实体类
 *
 * @author Lznby
 */
public class ArticleAllInfoEntity {
    /**
     * 创建资讯者基本信息
     */
    private UserBaseInfoEntity userBaseInfoEntity;
    /**
     * 资讯基本信息
     */
    private ArticleEntity articleEntity;
    /**
     * 资讯文件基本信息
     */
    private List<FilePathEntity> filePathEntities;
    /**
     * 主题基本信息
     */
    private List<ThemeEntity> themeEntities;
    /**
     * 是否收藏
     */
    private boolean love;
    /**
     * 评论(查询详情时才返回)
     */
    private List<CommentEntity> commentEntities;

    public UserBaseInfoEntity getUserBaseInfoEntity() {
        return userBaseInfoEntity;
    }

    public void setUserBaseInfoEntity(UserBaseInfoEntity userBaseInfoEntity) {
        this.userBaseInfoEntity = userBaseInfoEntity;
    }

    public ArticleEntity getArticleEntity() {
        return articleEntity;
    }

    public void setArticleEntity(ArticleEntity articleEntity) {
        this.articleEntity = articleEntity;
    }

    public List<FilePathEntity> getFilePathEntities() {
        return filePathEntities;
    }

    public void setFilePathEntities(List<FilePathEntity> filePathEntities) {
        this.filePathEntities = filePathEntities;
    }

    public List<ThemeEntity> getThemeEntities() {
        return themeEntities;
    }

    public void setThemeEntities(List<ThemeEntity> themeEntities) {
        this.themeEntities = themeEntities;
    }

    public boolean isLove() {
        return love;
    }

    public void setLove(boolean love) {
        this.love = love;
    }


    public List<CommentEntity> getCommentEntities() {
        return commentEntities;
    }

    public void setCommentEntities(List<CommentEntity> commentEntities) {
        this.commentEntities = commentEntities;
    }
}
