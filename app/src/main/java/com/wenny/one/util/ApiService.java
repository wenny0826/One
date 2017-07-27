package com.wenny.one.util;

import com.wenny.one.entity.HpEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by wenny on 2016/10/23.
 */

public interface ApiService{

//    @GET
//    Call<List<String>> getIdList(@Url String url);

    @GET
    Observable<String> getDatas(@Url String url);
}
