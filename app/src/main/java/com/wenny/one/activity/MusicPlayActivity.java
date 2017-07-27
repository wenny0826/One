package com.wenny.one.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wenny.one.R;
import com.wenny.one.base.AppContext;
import com.wenny.one.entity.PlayEntity;
import com.wenny.one.player.MusicService;
import com.wenny.wennylibrary.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wenny on 2016/11/9.
 */

public class MusicPlayActivity extends BaseActivity {

    private MusicService musicService;

    private LocalBroadcastManager localBroadcastManager;
    //全局广播
    private BroadcastReceiver broadcastReceiver;

    private PlayEntity playEntity;
    @Bind(R.id.tv_title)
    public TextView tv_title;
    @Bind(R.id.tv_author)
    public TextView tv_author;
    @Bind(R.id.tv_progress)
    public TextView tv_progress;
    @Bind(R.id.tv_max)
    public TextView tv_max;
    @Bind(R.id.seekbar)
    public SeekBar seekbar;
    @Bind(R.id.last)
    public ImageView last;
    @Bind(R.id.next)
    public ImageView next;
    @Bind(R.id.play)
    public ImageView play;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_music_play;
    }

    @Override
    protected void init() {
        musicService = AppContext.musicService;
        //获得当前播放的歌曲
        playEntity = musicService.getPlayEntity();
        Log.d("print", "init:   playEntity" + playEntity);
        if (playEntity != null) {
            show();
        }

        //接收本地广播更新进度条
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case "action.one.intent.progress":
                        int progress = intent.getIntExtra("progress", -1);
                        int max = intent.getIntExtra("max", -1);

                        //设置Seekbar
                        seekbar.setMax(max);
                        seekbar.setProgress(progress);

                        //设置时间
                        Date date1 = new Date(progress);
                        Date date2 = new Date(max);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                        tv_progress.setText(simpleDateFormat.format(date1));
                        tv_max.setText(simpleDateFormat.format(date2));
                        break;
                    case "action.one.showui":
                        playEntity = (PlayEntity) intent.getSerializableExtra("playEntity");
                        show();
                        break;
                    case "action.one.stop":
                        break;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.one.intent.progress");
        intentFilter.addAction("action.one.showui");
        intentFilter.addAction("action.one.stop");
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void show() {
        tv_title.setText(playEntity.getPlayTitle());
        tv_author.setText(playEntity.getPlayAuthor());
    }

    @OnClick({R.id.last, R.id.next, R.id.play})
    public void musicPlay(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.last:
                intent = new Intent("action.one.above");
                break;
            case R.id.play:
                intent = new Intent("action.one.play");
                break;
            case R.id.next:
                intent = new Intent("action.one.next");
                break;
        }
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
