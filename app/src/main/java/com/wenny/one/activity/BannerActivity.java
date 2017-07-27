package com.wenny.one.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wenny.one.R;
import com.wenny.one.adapter.BannerInfoAdapter;
import com.wenny.one.entity.BannerInfoEntity;
import com.wenny.one.entity.ReadBannerEntity;
import com.wenny.one.entity.SerialEntity;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.wennylibrary.base.BaseActivity;
import com.wenny.wennylibrary.util.RetrofitUtil;
import com.wenny.wennylibrary.widget.ListViewInScr;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wenny on 2016/11/4.
 */

public class BannerActivity extends BaseActivity implements RetrofitUtil.DownListener, AdapterView.OnItemClickListener {
    @Bind(R.id.ll)
    public LinearLayout ll;
    @Bind(R.id.listview)
    public ListViewInScr listview;
    @Bind(R.id.tv_banner_title)
    public TextView tv_banner_title;
    @Bind(R.id.tv_title)
    public TextView tv_title;
    @Bind(R.id.iv_cover)
    public ImageView iv_cover;
    private ReadBannerEntity banner;
    private BannerInfoAdapter bannerInfoAdapter;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_banner;
    }

    @Override
    protected void getIntentDatas() {
        Intent intent = getIntent();
        banner = (ReadBannerEntity) intent.getSerializableExtra("banner");
    }

    @Override
    protected void init() {
        bannerInfoAdapter = new BannerInfoAdapter(this);
        listview.setAdapter(bannerInfoAdapter);
        ll.setBackgroundColor(Color.parseColor(banner.getBgcolor()));
        listview.setOnItemClickListener(this);
        tv_banner_title.setText(banner.getBottom_text());
        Glide.with(iv_cover.getContext()).load(banner.getCover()).into(iv_cover);
        tv_title.setText(banner.getTitle());
    }

    @Override
    protected void loadDatas() {
        new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.READ_BANNER_INFO,banner.getId()),0x001);
    }

    @Override
    public Object paresJson(String json, int requestCode) {
        return JsonUtil.getBannerInfo(json);
    }

    @Override
    public void downSucc(Object object, int requestCode) {
        if (object != null){
            List<BannerInfoEntity> bannerInfoEntities = (List<BannerInfoEntity>) object;
            bannerInfoAdapter.setDatas(bannerInfoEntities);
        }
    }

    @Override
    public void downFail(int requestCode) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BannerInfoEntity bannerInfoEntity = (BannerInfoEntity) bannerInfoAdapter.getItem(position);
        String type = bannerInfoEntity.getType();
        switch (type){
            case "0":
                break;
            case "1":
                //短篇
                Intent intent = new Intent(this,EssayInfoActivity.class);
                intent.putExtra("essayid",bannerInfoEntity.getItem_id());
                startActivity(intent);
                break;
            case "2":
                //连载
                final Intent intent1 = new Intent(this,SerialInfoActivity.class);
                new RetrofitUtil(this).setDownListener(new RetrofitUtil.DownListener() {
                    @Override
                    public Object paresJson(String json, int requestCode) {
                        return JsonUtil.getSerial(json);
                    }

                    @Override
                    public void downSucc(Object object, int requestCode) {
                        SerialEntity serialEntity = (SerialEntity) object;
                        intent1.putExtra("serialid",serialEntity.getId());
                        intent1.putExtra("serial_id",serialEntity.getSerial_id());
                        startActivity(intent1);
                    }

                    @Override
                    public void downFail(int requestCode) {

                    }
                }).downJson(String.format(Constants.READ_INFO,Constants.SREIALCONTENT,bannerInfoEntity.getItem_id()),0x001);
                startActivity(intent1);
                break;
            case "3":
                //问题
                Intent intent2 = new Intent(this,QuestionInfoActivity.class);
                intent2.putExtra("questionid",bannerInfoEntity.getItem_id());
                startActivity(intent2);
                break;
        }
    }
}
