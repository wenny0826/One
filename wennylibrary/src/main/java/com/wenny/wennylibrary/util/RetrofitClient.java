package com.wenny.wennylibrary.util;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Ken on 2016/10/12.14:31
 */
public class RetrofitClient {

    private static Context context;

    private static RetrofitClient INSTANCE = new RetrofitClient();
    private Retrofit retrofit;
    private ApiService apiService;

    public static RetrofitClient getInstance(Context context) {
        if (RetrofitClient.context == null) {
            RetrofitClient.context = context;
        }

        return INSTANCE;
    }

    /**
     * 构造方法
     */
    private RetrofitClient() {

        retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    /**
     * 创建Retrofit对象
     */
    public RetrofitClient create() {
        apiService = retrofit.create(ApiService.class);
        return this;
    }

    /**
     * 使用自定义的接口
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> tClass) {
        T apiService = retrofit.create(tClass);
        return apiService;
    }
}
