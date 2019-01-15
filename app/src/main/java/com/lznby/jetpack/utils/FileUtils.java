package com.lznby.jetpack.utils;

/**
 * 文件工具类
 *
 * @author Lznby
 */
public class FileUtils {

    public static final String IMAGE = "image";
    public static final String VIDEO = "video";
    public static final String AUDIO = "audio";

    public static final int IMAGE_TYPE = 1;
    public static final int VIDEO_TYPE = 2;
    public static final int AUDIO_TYPE = 3;

    public static String getFileContentType(String fileName) {
        String subType = getSubType(fileName);
        String type = getType(fileName);
        return type + "/" + subType;
    }

    /**
     * Returns the high-level media type, such as "text", "image", "audio", "video", or "application".
     * @return
     */
    public static String getType(String fileName) {
        String subType = getSubType(fileName);
        if ("png".equals(subType) || "gif".equals(subType) || "jpeg".equals(subType) || "bmp".equals(subType) || "jpg".equals(subType)) {
            return "image";
        } else if ("mp4".equals(subType) || "avi".equals(subType) || "flv".equals(subType) || "3gp".equals(subType) || "wmv".equals(subType) || "mov".equals(subType)) {
            return "video";
        } else if ("mp3".equals(subType) || "wma".equals(subType) || "flac".equals(subType) || "wav".equals(subType) || "ape".equals(subType)) {
            return "audio";
        } else {
            return "multipart";
        }
    }

    /**
     * Returns a specific media subtype, such as "plain" or "png", "mpeg", "mp4" or "xml".
     *
     * @return
     */
    public static String getSubType(String fileName) {
        return (fileName.substring(fileName.lastIndexOf("."),fileName.length())).replaceFirst(".","");
    }
}
