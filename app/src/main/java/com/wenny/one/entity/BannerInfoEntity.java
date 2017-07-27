package com.wenny.one.entity;

/**
 * 阅读模块的专题
 */

public class BannerInfoEntity {


    /**
     * item_id : 1528
     * title : 所有人问所有人·陈坤答一个App工作室
     * author : @陈坤 答@一个App工作室 ：
     * introduction : 问：你自己的哪个特点让你最痛恨？
     答：如果非得有一个话，懒惰吧。

     问：你认为自己最伟大的成就是什
     * web_url :
     * number : 0
     * type : 3
     */

    private String item_id;
    private String title;
    private String author;
    private String introduction;
    private String web_url;
    private int number;
    private String type;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
