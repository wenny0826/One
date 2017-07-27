package com.wenny.wennylibrary.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by Ziyu on 2016/10/24.
 */

public class RetrofitUtil {

    private DownListener downListener;
    private Context context;
    private Handler handler = new Handler();


    public RetrofitUtil(Context context){
        this.context = context;
    }
    public RetrofitUtil setDownListener(DownListener downListener){
        this.downListener = downListener;
        return this;
    }

    public RetrofitUtil downJson(String url, final int requestCode) {
        RetrofitClient.getInstance(context)
                .create(ApiService.class)
                .getJSON(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (downListener != null) {
                            final Object object = downListener.paresJson(s, requestCode);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    downListener.downSucc(object, requestCode);
                                }
                            });
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(context,"网络异常，请连接网络",Toast.LENGTH_SHORT).show();
                        if(downListener != null){
                            downListener.downFail(requestCode);
                        }
                    }
                });

        return this;
    }

    public interface DownListener {

        //解析JSON时回调
        Object paresJson(String json, int requestCode);

        //解析完成后回调
        void downSucc(Object object, int requestCode);

        void downFail(int requestCode);
    }
}
