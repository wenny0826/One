package com.wenny.one.entity;

import java.io.Serializable;

/**
 * 阅读的专题
 */

public class ReadBannerEntity implements Serializable{

    /**
     * id : 106
     * title : 不正常人类症候群
     * cover : http://image.wufazhuce.com/FkkHaZO6iMT1tF9gtIv4_l7sr8dQ
     * bottom_text :  就算时空倒转重新选择，也想要过「不正常」的人生。
     * bgcolor : #000000
     * pv_url : http://v3.wufazhuce.com:8000/api/reading/carousel/pv/106
     */

    private String id;
    private String title;
    private String cover;
    private String bottom_text;
    private String bgcolor;
    private String pv_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBottom_text() {
        return bottom_text;
    }

    public void setBottom_text(String bottom_text) {
        this.bottom_text = bottom_text;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getPv_url() {
        return pv_url;
    }

    public void setPv_url(String pv_url) {
        this.pv_url = pv_url;
    }
}
