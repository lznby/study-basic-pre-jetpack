package com.lznby.jetpack.content.design.entity;

/**
 * 主题信息表
 *
 * app_theme
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
    String fileAttribution;
    /**
     * 关注该主题的人数
     */
    int subCount;

    public ThemeEntity() {
    }

    public ThemeEntity(String themeId, String time, String themeName, String themeNote, String themeImage, String fileAttribution, int subCount) {
        this.themeId = themeId;
        this.time = time;
        this.themeName = themeName;
        this.themeNote = themeNote;
        this.themeImage = themeImage;
        this.fileAttribution = fileAttribution;
        this.subCount = subCount;
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

    public String getFileAttribution() {
        return fileAttribution;
    }

    public void setFileAttribution(String fileAttribution) {
        this.fileAttribution = fileAttribution;
    }

    public int getSubCount() {
        return subCount;
    }

    public void setSubCount(int subCount) {
        this.subCount = subCount;
    }
}
