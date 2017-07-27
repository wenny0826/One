package com.wenny.one.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wenny.one.R;
import com.wenny.one.adapter.MusicListAdapter;
import com.wenny.one.base.AppContext;
import com.wenny.one.entity.MusicListEntity;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.wennylibrary.base.BaseActivity;
import com.wenny.wennylibrary.util.RetrofitUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wenny on 2016/11/3.
 */

public class MusicListActivity extends BaseActivity implements RetrofitUtil.DownListener, AdapterView.OnItemClickListener {

    @Bind(R.id.listview)
    public ListView listView;
    @Bind(R.id.tv_title)
    public TextView tv_title;
    @Bind(R.id.iv_playing)
    public ImageView iv_playing;
    @Bind(R.id.iv_play_list)
    public ImageView iv_play_list;

    private MusicListAdapter musicListAdapter;

    private String type;
    private String time;
    private String timeShow;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_musiclist;
    }

    @Override
    protected void getIntentDatas() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        time = intent.getStringExtra("time");
        timeShow = intent.getStringExtra("timeShow");
    }

    @Override
    protected void init() {
        musicListAdapter = new MusicListAdapter(this);
        listView.setAdapter(musicListAdapter);
        tv_title.setText(timeShow);
        listView.setDivider(null);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.MOTH, Constants.MUSIC, time), 0x001);

    }

    @Override
    public Object paresJson(String json, int requestCode) {
        return JsonUtil.getListMusic(json);
    }

    @Override
    public void downSucc(Object object, int requestCode) {
        if (object != null) {
            List<MusicListEntity> musicListEntities = (List<MusicListEntity>) object;
            musicListAdapter.setDatas(musicListEntities);
        }
    }

    @Override
    public void downFail(int requestCode) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        MusicListEntity musicListEntity = (MusicListEntity) listView.getAdapter().getItem(position);
        Intent intent = new Intent(this,MusicInfoActivity.class);
        intent.putExtra("musicid",musicListEntity.getId());
        startActivity(intent);
    }
}
