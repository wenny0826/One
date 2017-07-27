package com.wenny.one.base;

import android.app.AlertDialog;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;

import com.wenny.one.R;
import com.wenny.one.entity.PlayEntity;
import com.wenny.one.player.MusicService;
import com.wenny.wennylibrary.util.SharedUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

/**
 * Created by wenny on 2016/10/22.
 */

public class AppContext extends Application {

    public boolean isFirst;//是否第一次进入app
    public boolean isFirstHp;//是否第一次进入主页
    public boolean isFirstMusic;//是否第一次进入音乐
    public boolean isFirstRead;
    public boolean isFirstMovie;


    public static Retrofit retrofit;
    public static String baseUrl = "http://v3.wufazhuce.com:8000/api/";
    public static MusicService musicService;
    public static List<PlayEntity> playMusicList1;
    public static List<PlayEntity> playMusicList;
    public static List<PlayEntity> playReadList1;
    public static List<PlayEntity> playReadList;
    public static ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MyBind) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化共享参数
        SharedUtil.init(getApplicationContext());

        isFirst = SharedUtil.getBoolean("isFirst");
        isFirstHp = SharedUtil.getBoolean("isFirstHp");
        isFirstMusic = SharedUtil.getBoolean("isFirstMusic");
        isFirstRead = SharedUtil.getBoolean("isFirstRead");
        isFirstMovie = SharedUtil.getBoolean("isFirstMovie");

        playMusicList1 = new ArrayList<>();
        playMusicList = new ArrayList<>();
        playReadList1 = new ArrayList<>();
        playReadList = new ArrayList<>();
        //启动核心服务
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }
}
