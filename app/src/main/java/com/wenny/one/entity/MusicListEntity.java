package com.wenny.one.entity;

/**
 * 音乐列表
 */

public class MusicListEntity {


    /**
     * id : 1090
     * title : 夜访吸血鬼
     * cover : http://image.wufazhuce.com/FvF0OGybnVc9Gk411PHgK3JdCYx6
     * platform : 1
     * music_id : 3381909
     * author : {"user_id":"6091706","user_name":"五月天","web_url":"http://image.wufazhuce.com/Fnvu6LkgBXoxQ1X80bg0anjuhb_C","desc":"台湾摇滚乐团"}
     */

    private String id;
    private String title;
    private String cover;
    private String platform;
    private String music_id;
    /**
     * user_id : 6091706
     * user_name : 五月天
     * web_url : http://image.wufazhuce.com/Fnvu6LkgBXoxQ1X80bg0anjuhb_C
     * desc : 台湾摇滚乐团
     */

    private AuthorBean author;

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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getMusic_id() {
        return music_id;
    }

    public void setMusic_id(String music_id) {
        this.music_id = music_id;
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public static class AuthorBean {
        private String user_id;
        private String user_name;
        private String web_url;
        private String desc;

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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
