package com.wenny.one.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wenny.one.R;
import com.wenny.one.adapter.ListAdapter;
import com.wenny.one.util.Constants;
import com.wenny.one.util.TimeUtil;
import com.wenny.wennylibrary.base.BaseActivity;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wenny on 2016/11/2.
 * 往期列表
 */

public class HomeListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    List<String> listTime = TimeUtil.getListTime("2012-10-23 23:11:49");
    List<String> listTimeShow = TimeUtil.getListTimeShow(listTime);

    private List<String> essayTime = TimeUtil.getListTime("2012-10-4");
    private List<String> essayTimeShow = TimeUtil.getListTimeShow(essayTime);

    private List<String> serialTime = TimeUtil.getListTime("2016-1-1");
    private List<String > serialTimeShow = TimeUtil.getListTimeShow(serialTime);

    private List<String> questionTime = TimeUtil.getListTime("2012-10-6 ");
    private List<String> questionTimeShow = TimeUtil.getListTimeShow(questionTime);

    private List<String> musicTime = TimeUtil.getListTime("2016-1-1");
    private List<String > musicTimeShow = TimeUtil.getListTimeShow(musicTime);


    private ListAdapter adapter;
    @Bind(R.id.listview)
    public ListView listview;

    private String type;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_list;
    }

    @Override
    protected void getIntentDatas() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
    }

    @Override
    protected void init() {
        adapter = new ListAdapter(this);
        listview.setAdapter(adapter);
        listview.setDivider(null);
        listview.setOnItemClickListener(this);
        switch (type) {
            case Constants.HOME:
                adapter.setDatas(listTimeShow);
                break;
            case Constants.ESSAY:
                adapter.setDatas(essayTimeShow);
                break;
            case Constants.SREIALCONTENT:
                adapter.setDatas(serialTimeShow);
                break;
            case Constants.QUESTION:
                adapter.setDatas(questionTimeShow);
                break;
            case Constants.MUSIC:
                adapter.setDatas(musicTimeShow);
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,HomeMothActivity.class);
        switch (type){
            case Constants.HOME:
                intent.putExtra("time",listTime.get(listTime.size() - position -1));
                intent.putExtra("timeShow",listTimeShow.get(position));
                intent.putExtra("type",Constants.HOME);
                break;
            case Constants.ESSAY:
                intent.putExtra("time",essayTime.get(essayTime.size() - position -1));
                intent.putExtra("timeShow",essayTimeShow.get(position));
                intent.putExtra("type",Constants.ESSAY);
                break;
            case Constants.SREIALCONTENT:
                intent.putExtra("time",serialTime.get(serialTime.size() - position -1));
                intent.putExtra("timeShow",serialTimeShow.get(position));
                intent.putExtra("type",Constants.SREIALCONTENT);
                break;
            case Constants.QUESTION:
                intent.putExtra("time",questionTime.get(questionTime.size() - position -1));
                intent.putExtra("timeShow",questionTimeShow.get(position));
                intent.putExtra("type",Constants.QUESTION);
                break;
            case Constants.MUSIC:
                intent = new Intent(this,MusicListActivity.class);
                intent.putExtra("type",Constants.MUSIC);
                intent.putExtra("time",musicTime.get(musicTime.size() - position -1));
                intent.putExtra("timeShow",musicTimeShow.get(position));
                break;
        }
        startActivity(intent);

    }

    @Override
    public boolean isOpenStatus() {
        return false;
    }
}
