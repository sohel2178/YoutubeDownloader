package com.example.youtubedownloader;

import java.io.Serializable;

public class VideoRequest implements Serializable {
    private String url;

    public VideoRequest() {
    }

    public VideoRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
