package com.wenny.one.util;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by wenny on 2016/10/22.
 */

public interface Constants {


    String HOME = "hp";
    String MUSIC = "music";
    String MOVIE = "movie";
    String READING = "reading";

    String ESSAY = "essay";
    String QUESTION = "question";
    String SREIALCONTENT = "serialcontent";


    //阅读--banner
    String READ_BANNER= "http://v3.wufazhuce.com:8000/api/reading/carousel/?platform=android&version=3.5.0";

    //阅读--banner详情 http://v3.wufazhuce.com:8000/api/reading/carousel/105?platform=android&version=3.5.0
    String READ_BANNER_INFO = "http://v3.wufazhuce.com:8000/api/reading/carousel/%s?platform=android&version=3.5.0";

    //阅读--文章
    String READ_CONTENT = "http://v3.wufazhuce.com:8000/api/reading/index/?platform=android&version=3.5.0";

    String READ_INFO = "http://v3.wufazhuce.com:8000/api/%s/%s?platform=android&version=3.5.0";

    //音乐,首页 idlist
    String IDLIST = "http://v3.wufazhuce.com:8000/api/%s/idlist/0?platform=android&version=3.5.0";

    //音乐,首页 内容
    String CONTENT = "http://v3.wufazhuce.com:8000/api/%s/detail/%s?platform=android&version=3.5.0";

    //评论
    String COMMENT = "http://v3.wufazhuce.com:8000/api/comment/praiseandtime/%s/%s/0?platform=android&version=3.5.0";
    //电影列表
    String MOVIE_LIST = "http://v3.wufazhuce.com:8000/api/movie/list/0?platform=android&version=3.5.0";
    //电影详情1
    String MOVIE_STORY = "http://v3.wufazhuce.com:8000/api/movie/%s/story/1/0?platform=android&version=3.5.0";
    //电影详情
    String MOVIE_DETAIL = "http://v3.wufazhuce.com:8000/api/movie/detail/%s?platform=android&version=3.5.0";

    String SERIAL_LIST ="http://v3.wufazhuce.com:8000/api/serial/list/%s?version=3.5.0&platform=android";

    //每月内容  http://v3.wufazhuce.com:8000/api/essay/bymonth/2016-09-01%2000:00:00?platform=android&version=3.5.0
    String MOTH = "http://v3.wufazhuce.com:8000/api/%s/bymonth/%s";

}

