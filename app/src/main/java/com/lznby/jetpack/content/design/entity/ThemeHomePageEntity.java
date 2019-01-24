package com.lznby.jetpack.content.design.entity;

import java.util.List;

/**
 * 主题首页
 *
 * @author Lznby
 */
public class ThemeHomePageEntity {

    /**
     * 所有资讯信息
     */
    private List<ArticleAllInfoEntity> articleAllInfoEntities;
    /**
     * 主题基本信息
     */
    private ThemeEntity themeEntity;

    public ThemeHomePageEntity() {
    }

    public ThemeHomePageEntity(List<ArticleAllInfoEntity> articleAllInfoEntities, ThemeEntity themeEntity) {
        this.articleAllInfoEntities = articleAllInfoEntities;
        this.themeEntity = themeEntity;
    }

    public List<ArticleAllInfoEntity> getArticleAllInfoEntities() {
        return articleAllInfoEntities;
    }

    public void setArticleAllInfoEntities(List<ArticleAllInfoEntity> articleAllInfoEntities) {
        this.articleAllInfoEntities = articleAllInfoEntities;
    }

    public ThemeEntity getThemeEntity() {
        return themeEntity;
    }

    public void setThemeEntity(ThemeEntity themeEntity) {
        this.themeEntity = themeEntity;
    }
}

