package com.lznby.jetpack.content.design.entity;

/**
 * 主题信息表
 *
 * app_theme
 *
 * @author Lznby
 */
public class ThemeEntity {
    /**
     * 主题ID,系统生成UUID
     */
    String themeId;
    /**
     * 主题创建时间,系统生成
     */
    String time;
    /**
     * 主题名称
     */
    String themeName;
    /**
     * 主题简介
     */
    String themeNote;
    /**
     * 主题背景(暂时没什么用处)
     */
    String themeImage;
    /**
     * 主题背景所在 app_theme_path 中编号
     */
    String themeHeaderImage;
    /**
     * 关注该主题的人数
     */
    int subCount;

    /**
     * 判断发起查询者是否关注了该主题
     *
     * 非数据库字段
     */
    boolean isFollow;

    public ThemeEntity() {
    }

    public ThemeEntity(String themeId, String time, String themeName, String themeNote, String themeImage, String themeHeaderImage, int subCount, boolean isFollow) {
        this.themeId = themeId;
        this.time = time;
        this.themeName = themeName;
        this.themeNote = themeNote;
        this.themeImage = themeImage;
        this.themeHeaderImage = themeHeaderImage;
        this.subCount = subCount;
        this.isFollow = isFollow;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeNote() {
        return themeNote;
    }

    public void setThemeNote(String themeNote) {
        this.themeNote = themeNote;
    }

    public String getThemeImage() {
        return themeImage;
    }

    public void setThemeImage(String themeImage) {
        this.themeImage = themeImage;
    }

    public String getThemeHeaderImage() {
        return themeHeaderImage;
    }

    public void setThemeHeaderImage(String themeHeaderImage) {
        this.themeHeaderImage = themeHeaderImage;
    }

    public int getSubCount() {
        return subCount;
    }

    public void setSubCount(int subCount) {
        this.subCount = subCount;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}
