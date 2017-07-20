package com.bftv.fui.downloadlib.entity;

/**
 * Created by MaZhihua on 2017/7/20.
 * 记载下载器详细信息
 */
public class LoadEntity {

    public int fileSize;//文件大小
    private int complete;//完成度
    private String urlstring;//下载器标识

    public LoadEntity(int fileSize, int complete, String urlstring) {
        this.fileSize = fileSize;
        this.complete = complete;
        this.urlstring = urlstring;
    }

    public LoadEntity() {
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public String getUrlstring() {
        return urlstring;
    }

    public void setUrlstring(String urlstring) {
        this.urlstring = urlstring;
    }

    @Override
    public String toString() {
        return "LoadEntity [fileSize=" + fileSize + ", complete=" + complete
                + ", urlstring=" + urlstring + "]";
    }
}