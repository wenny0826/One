package com.wenny.wennylibrary.util;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Ken on 2016/10/12.14:29
 */
public interface ApiService {

    String BASE_URL = "http://ikft.house.qq.com/";

    @GET
    Observable<String> getJSON(@Url String url);
}
