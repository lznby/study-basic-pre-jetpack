package com.lznby.jetpack.content.design.entity;

/**
 * 资讯实体类
 *
 * @author Lznby
 */
public class ArticleEntity {
    /**
     * 关联资讯文件信息
     */
    String fileAttribution;
    /**
     * 创建资讯时间
     */
    String time;
    /**
     * 资讯标题
     */
    String title;
    /**
     * 资讯正文内容
     */
    String content;
    /**
     * 创建资讯者Id
     */
    String userId;

    /**
     * themeId 暂时没什么用处
     */
    String themeId;

    /**
     * 阅读数记录
     */
    int readCount;
    /**
     * 喜欢数记录
     */
    int loveCount;
    /**
     * 评论数记录
     */
    int commentCount;
    /**
     * 资讯文件类型
     */
    String type;

    public String getFileAttribution() {
        return fileAttribution;
    }

    public void setFileAttribution(String fileAttribution) {
        this.fileAttribution = fileAttribution;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(int loveCount) {
        this.loveCount = loveCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

