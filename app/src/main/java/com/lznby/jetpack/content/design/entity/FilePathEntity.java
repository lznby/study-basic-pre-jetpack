package com.lznby.jetpack.content.design.entity;

/**
 * 文件路径表实体类
 * app_file_path
 */
public class FilePathEntity {
    /**
     * 是文件名称
     */
    String fileId;
    /**
     * 文件存储路径
     */
    String filePath;
    /**
     * 文件类型
     */
    int fileType;
    /**
     * 文件是否私有
     * 1:私有;
     * 2:公开(默认);
     */
    int filePrivate;

    /**
     * 归属于哪个帖子
     */
    String fileAttribution;

    /**
     * 上传用户id
     */
    String userId;

    /**
     * 上传时间
     */
    String time;

    public FilePathEntity() {
    }

    public FilePathEntity(String fileId, String filePath, int fileType, int filePrivate, String fileAttribution, String userId, String time) {
        this.fileId = fileId;
        this.filePath = filePath;
        this.fileType = fileType;
        this.filePrivate = filePrivate;
        this.fileAttribution = fileAttribution;
        this.userId = userId;
        this.time = time;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public int getFilePrivate() {
        return filePrivate;
    }

    public void setFilePrivate(int filePrivate) {
        this.filePrivate = filePrivate;
    }

    public String getFileAttribution() {
        return fileAttribution;
    }

    public void setFileAttribution(String fileAttribution) {
        this.fileAttribution = fileAttribution;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
