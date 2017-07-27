package com.wenny.one.player;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.wenny.one.entity.PlayEntity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wenny on 2016/11/9.
 */

public class MusicService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, Serializable {

    private List<PlayEntity> playEntities;//播放列表
    private MediaPlayer mediaPlayer;
    private String playName;//当前播放歌曲的名称
    private String playAuthor;//当前播放歌曲的演唱者
    private String type;
    private int index = -1;//播放的歌曲下标
    private boolean isPlay = false;//是否正在播放歌曲


    private LocalBroadcastManager localBroadcastManager;

    //通知相关
//    private NotificationManager notificationManager;
//    private Notification notification;

    private BroadcastReceiver broadcastReceiver;


    @Override
    public void onCreate() {
        mediaPlayer = new MediaPlayer();
        playEntities = new ArrayList<>();
        mediaPlayer.setOnCompletionListener(this);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        //全局广播
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()){
                    case "action.one.play":
                        //暂停/播放
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.pause();
                        } else {
                            if(index == -1){
                                playPosition(0);
                            } else {
                                mediaPlayer.start();
                            }
                        }
                        break;
                    case "action.one.above":
                        above();
                        break;
                    case "action.one.next":
                        next();
                        break;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.one.play");
        intentFilter.addAction("action.one.above");
        intentFilter.addAction("action.one.next");
        registerReceiver(broadcastReceiver, intentFilter);

    }

    //播放歌曲
    public void playPosition(int position){
        if(index != position){
            index = position;
            play();
        }
    }

    //或得播放歌曲的下标
    public int getIndex() {
        return index;
    }

    //开始播放歌曲
    public void play(){
        mediaPlayer.reset();//重置播放器

        try {
            mediaPlayer.setDataSource(playEntities.get(index).getPalyUrl());
            mediaPlayer.prepareAsync();//进入就绪状态
            mediaPlayer.setOnPreparedListener(this);

            playName = playEntities.get(index).getPlayTitle();
            playAuthor = playEntities.get(index).getPlayAuthor();
            type = playEntities.get(index).getType();
            //发送更新Ui的广播
            changeUi();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(int position,PlayEntity playEntity){
        playEntities.set(position,playEntity);
    }

    //上一曲
    public void above(){
        if(index == -1){
            return;
        }

        if(--index == -1){
            index = playEntities.size() - 1;
        }

        play();

    }

    //发送更新ui的广播
    public void changeUi(){
        Intent intent = new Intent("action.one.showui");
        intent.putExtra("playEntity",playEntities.get(index));
        localBroadcastManager.sendBroadcast(intent);
    }

    //下一曲
    public void next(){
        if(index == -1){
            return;
        }

        if(++index == playEntities.size()){
            index = 0;
        }

        play();

    }

    public PlayEntity getPlayEntity() {
        if(index > 0) {
            return playEntities.get(index);
        }else {
            return null;
        }
    }

    //停止播放
    public void stop(){
        mediaPlayer.pause();
    }
    /**
     * 定时器
     */
    private Timer timer = null;
    public void timer(){
        timer = new Timer();
        //发送广播更新进度条
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(mediaPlayer.isPlaying()){
                    Intent intent = new Intent("action.one.intent.progress");
                    intent.putExtra("progress", mediaPlayer.getCurrentPosition());
                    intent.putExtra("max", mediaPlayer.getDuration());
                    localBroadcastManager.sendBroadcast(intent);
                } else {
                    timer.cancel();
                    timer = null;
                }
            }
        }, 0, 1000);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBind();
    }

    //传递音乐列表
    public void setPlayEntities(List<PlayEntity> playEntities) {
        this.playEntities = playEntities;
    }

    public List<PlayEntity> getPlayEntities() {
        return playEntities;
    }


    public boolean isPlay() {
        return isPlay;
    }
    //清除播放列表
    public void cleanList(){
        playEntities.clear();
    }


    /**
     * 歌曲播放完成
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        if(index == playEntities.size() - 1){
            index = 0;
        }

        play();
    }

    //异步准备完成
    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
        isPlay = true;
        //开启定时器
        timer();

    }

    public class MyBind extends Binder {

        /**
         * 返回Service对象
         * @return
         */
        public MusicService getService(){
            return MusicService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
