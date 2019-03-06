package com.lznby.jetpack.content.design.entity;

/**
 * 评论资讯Entity
 */
public class CommentEntity {
    /**
     * 评论Id
     */
    private int commentId;
    /**
     * 评论内容
     */
    private String comment;
    /**
     * 评论者id
     */
    private int userId;
    /**
     * 被评论者id,无则视为评论资讯
     */
    private int toUserId;
    /**
     * 资讯id
     */
    private String fileAttribution;
    /**
     * 被评论资讯Id
     */
    private int toCommentId;
    /**
     * 创建时间
     */
    private String time;
    /**
     * 点赞数
     */
    private int loveCount;
    /**
     * 用户昵称(评论者|非数据库字段)
     */
    private String userNickname;

    /**
     * 用户头像(评论者|非数据库字段)
     */
    private String userHeaderUrl;

    /**
     * 用户昵称(被评论者|非数据库字段)
     */
    private String toUserNickname;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getFileAttribution() {
        return fileAttribution;
    }

    public void setFileAttribution(String fileAttribution) {
        this.fileAttribution = fileAttribution;
    }

    public int getToCommentId() {
        return toCommentId;
    }

    public void setToCommentId(int toCommentId) {
        this.toCommentId = toCommentId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(int loveCount) {
        this.loveCount = loveCount;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserHeaderUrl() {
        return userHeaderUrl;
    }

    public void setUserHeaderUrl(String userHeaderUrl) {
        this.userHeaderUrl = userHeaderUrl;
    }

    public String getToUserNickname() {
        return toUserNickname;
    }

    public void setToUserNickname(String toUserNickname) {
        this.toUserNickname = toUserNickname;
    }
}
