package com.wenny.one.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wenny.one.R;
import com.wenny.one.adapter.EssayListAdapter;
import com.wenny.one.adapter.HomeMothAdapter;
import com.wenny.one.adapter.QuestionListAdapter;
import com.wenny.one.adapter.SerialListAdapter;
import com.wenny.one.base.AppContext;
import com.wenny.one.entity.HpEntity;
import com.wenny.one.entity.PlayEntity;
import com.wenny.one.entity.ReadEntity;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.wennylibrary.base.BaseActivity;
import com.wenny.wennylibrary.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wenny on 2016/11/2.
 * 首页,短篇。连载，问题的每月列表
 */

public class HomeMothActivity extends BaseActivity implements RetrofitUtil.DownListener, HomeMothAdapter.OnItemClickListener, EssayListAdapter.OnItemClickListener, SerialListAdapter.OnItemClickListener, QuestionListAdapter.OnItemClickListener {

    @Bind(R.id.recyclerView)
    public RecyclerView recyclerView;
    @Bind(R.id.tv_title)
    public TextView tv_title;

    private String time;
    private String timeShow;

    @Bind(R.id.loading)
    public View loading;
    private AnimationDrawable animationDrawable;

    private String type;
    private HomeMothAdapter homeMothAdapter;
    private EssayListAdapter essayListAdapter;
    private SerialListAdapter serialListAdapter;
    private QuestionListAdapter questionListAdapter;



    @Override
    protected int getContentViewId() {
        return R.layout.activity_moth;
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
        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);
        tv_title.setText(timeShow);
    }

    @Override
    protected void loadDatas() {
        switch (type) {
            case Constants.HOME:

                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                homeMothAdapter = new HomeMothAdapter(this);
                recyclerView.setAdapter(homeMothAdapter);
                homeMothAdapter.setOnItemClickListener(this);

                new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.MOTH, Constants.HOME, time), 0x001);
                break;
            case Constants.ESSAY:

                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                essayListAdapter = new EssayListAdapter(this);
                recyclerView.setAdapter(essayListAdapter);
                essayListAdapter.setOnItemClickListener(this);

                new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.MOTH, Constants.ESSAY, time), 0x001);
                break;
            case Constants.SREIALCONTENT:

                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                serialListAdapter = new SerialListAdapter(this);
                recyclerView.setAdapter(serialListAdapter);
                serialListAdapter.setOnItemClickListener(this);

                new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.MOTH, Constants.SREIALCONTENT, time), 0x001);
                break;
            case Constants.QUESTION:

                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                questionListAdapter = new QuestionListAdapter(this);
                recyclerView.setAdapter(questionListAdapter);
                questionListAdapter.setOnItemClickListener(this);
                new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.MOTH, Constants.QUESTION, time), 0x001);
                break;
            case Constants.MUSIC:
                new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.MOTH, Constants.MUSIC, time), 0x001);
                break;
        }
    }

    @Override
    public Object paresJson(String json, int requestCode) {
        switch (type) {
            case Constants.HOME:
                return JsonUtil.getListHp(json);
            case Constants.ESSAY:
                return JsonUtil.getListEssay(json);
            case Constants.SREIALCONTENT:
                return JsonUtil.getListSerial(json);
            case Constants.QUESTION:
                return JsonUtil.getListQuestion(json);
            default:
                return null;
        }
    }

    @Override
    public void downSucc(Object object, int requestCode) {
        if (object != null) {
            switch (type) {
                case Constants.HOME:
                    List<HpEntity.DataBean> dataBeen = (List<HpEntity.DataBean>) object;
                    homeMothAdapter.setData(dataBeen);
                    break;
                case Constants.ESSAY:
                    List<ReadEntity.DataBean.EssayBean> essayBeen = (List<ReadEntity.DataBean.EssayBean>) object;
                    essayListAdapter.setEssayBeen(essayBeen);
                    break;
                case Constants.SREIALCONTENT:
                    List<ReadEntity.DataBean.SerialBean> serialBeen = (List<ReadEntity.DataBean.SerialBean>) object;
                    serialListAdapter.setSerialBeen(serialBeen);
                    break;
                case Constants.QUESTION:
                    List<ReadEntity.DataBean.QuestionBean> questionBeen = (List<ReadEntity.DataBean.QuestionBean>) object;
                    questionListAdapter.setQuestionBeen(questionBeen);
                    break;
            }
            animationDrawable.stop();
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void downFail(int requestCode) {

    }

    @Override
    public void onItemClick(View view, int position) {
        switch (type) {
            case Constants.HOME:
                Intent intent = new Intent(this, HomeInfoActivity.class);
                HpEntity.DataBean dataBean = homeMothAdapter.getItemData(position);
                intent.putExtra("dataBean", dataBean);
                startActivity(intent);
                break;
            case Constants.ESSAY:
                ReadEntity.DataBean.EssayBean essayBean = essayListAdapter.getItemData(position);

                AppContext.playReadList1 = essayListAdapter.getPlayEntities();

                intent = new Intent(this, EssayInfoActivity.class);
                intent.putExtra("essayid", essayBean.getContent_id());
                intent.putExtra("position", position);
                startActivity(intent);
                break;
            case Constants.SREIALCONTENT:
                ReadEntity.DataBean.SerialBean serialBean = serialListAdapter.getItemData(position);
                intent = new Intent(this, SerialInfoActivity.class);
                intent.putExtra("serialid", serialBean.getId());
                intent.putExtra("serial_id", serialBean.getSerial_id());
                startActivity(intent);
                break;
            case Constants.QUESTION:
                ReadEntity.DataBean.QuestionBean questionBean = questionListAdapter.getItemData(position);
                Intent intent2 = new Intent(this,QuestionInfoActivity.class);
                intent2.putExtra("questionid",questionBean.getQuestion_id());
                startActivity(intent2);
                break;
        }
    }
}
