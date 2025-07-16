package com.ey.xray.cloud.infrastructure.dao;

public class Attachment {
    private String filePath;
    private String id;

    public String getFilePath() {
        return filePath;
    }

    public Attachment setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getId() {
        return id;
    }

    public Attachment setId(String id) {
        this.id = id;
        return this;
    }
}
