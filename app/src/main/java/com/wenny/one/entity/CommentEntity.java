package com.wenny.one.entity;

import java.io.Serializable;

/**
 * 评论
 */

public class CommentEntity implements Serializable{

    /**
     * id : 27280
     * quote :
     * content : 纵使你再也不能飞天遁地，纵使你的抬头纹、眼角纹愈发浓烈，纵使从侧面看还有些许小肚腩，但是没办法，只要你出现，我还是会一如既往的去捧你的场，谁叫你是阿汤哥
     * praisenum : 38
     * device_token :
     * del_flag : 0
     * reviewed : 1
     * user_info_id : 10089
     * input_date : 2016-10-23 23:11:49
     * created_at : 2016-10-23 23:11:49
     * updated_at : 2016-10-24 13:46:02
     * user : {"user_id":"72875","user_name":"Tony-Pan","web_url":"http://tp1.sinaimg.cn/1149838004/180/5635072927/1"}
     * touser : null
     * score : 85
     * type : 0
     */

    private String id;
    private String quote;
    private String content;
    private int praisenum;
    private String device_token;
    private String del_flag;
    private String reviewed;
    private String user_info_id;
    private String input_date;
    private String created_at;
    private String updated_at;
    /**
     * user_id : 72875
     * user_name : Tony-Pan
     * web_url : http://tp1.sinaimg.cn/1149838004/180/5635072927/1
     */

    private UserBean user;
    private Object touser;
    private String score;
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(int praisenum) {
        this.praisenum = praisenum;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    public String getReviewed() {
        return reviewed;
    }

    public void setReviewed(String reviewed) {
        this.reviewed = reviewed;
    }

    public String getUser_info_id() {
        return user_info_id;
    }

    public void setUser_info_id(String user_info_id) {
        this.user_info_id = user_info_id;
    }

    public String getInput_date() {
        return input_date;
    }

    public void setInput_date(String input_date) {
        this.input_date = input_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public Object getTouser() {
        return touser;
    }

    public void setTouser(Object touser) {
        this.touser = touser;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class UserBean implements Serializable{
        private String user_id;
        private String user_name;
        private String web_url;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }
    }
}
