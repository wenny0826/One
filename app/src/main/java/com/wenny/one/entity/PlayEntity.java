package com.wenny.one.entity;

import java.io.Serializable;

/**
 * 播放的音乐
 */

public class PlayEntity implements Serializable{

    private String palyid;
    private String playTitle;
    private String playAuthor;
    private String palyUrl;
    private String type;

    public PlayEntity() {
    }

    public PlayEntity(String palyid, String playTitle, String playAuthor, String palyUrl, String type) {
        this.palyid = palyid;
        this.playTitle = playTitle;
        this.playAuthor = playAuthor;
        this.palyUrl = palyUrl;
        this.type = type;
    }

    public String getPalyid() {
        return palyid;
    }

    public void setPalyid(String palyid) {
        this.palyid = palyid;
    }

    public String getPlayTitle() {
        return playTitle;
    }

    public void setPlayTitle(String playTitle) {
        this.playTitle = playTitle;
    }

    public String getPlayAuthor() {
        return playAuthor;
    }

    public void setPlayAuthor(String playAuthor) {
        this.playAuthor = playAuthor;
    }

    public String getPalyUrl() {
        return palyUrl;
    }

    public void setPalyUrl(String palyUrl) {
        this.palyUrl = palyUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PlayEntity{" +
                "palyid='" + palyid + '\'' +
                ", playTitle='" + playTitle + '\'' +
                ", playAuthor='" + playAuthor + '\'' +
                ", palyUrl='" + palyUrl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
