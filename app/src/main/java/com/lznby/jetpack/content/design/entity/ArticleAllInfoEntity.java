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
    UserBaseInfoEntity userBaseInfoEntity;
    /**
     * 资讯基本信息
     */
    ArticleEntity articleEntity;
    /**
     * 资讯文件基本信息
     */
    List<FilePathEntity> filePathEntities;
    /**
     * 主题基本信息
     */
    List<ThemeEntity> themeEntities;

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
}
