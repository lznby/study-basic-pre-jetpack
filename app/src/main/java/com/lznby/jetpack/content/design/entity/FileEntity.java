package com.lznby.jetpack.content.design.entity;

/**
 * 文件实体类
 *
 * @author Lznby
 */
public class FileEntity {

    public static final String VIDEO = "video";
    public static final String AUDIO = "audio";
    public static final String IMAGE = "image";
    public static final String GIF = "gif";

    /**
     * 文件路径
     */
    private String url;
    /**
     * 文件类型
     */
    private String type;

    public FileEntity() {
    }

    public FileEntity(String url, String type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
