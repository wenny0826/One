package com.wenny.one.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenny.one.R;
import com.wenny.one.adapter.EssayPageAdapter;
import com.wenny.one.adapter.QuestPagerAdapter;
import com.wenny.one.adapter.SerialPagerAdapter;
import com.wenny.one.entity.ReadEntity;
import com.wenny.one.util.Constants;
import com.wenny.wennylibrary.base.BaseActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wenny on 2016/11/1.
 * 阅读点击进入的连载，短篇，问题的详情页
 */

public class ReadInfoActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_read_info;
    }

    private String type;
    private List<ReadEntity.DataBean.EssayBean> essayBeen;
    private List<ReadEntity.DataBean.SerialBean> serialBeen;
    private List<ReadEntity.DataBean.QuestionBean> questionBeen;
    private EssayPageAdapter essayPageAdapter;
    private SerialPagerAdapter serialPagerAdapter;
    private QuestPagerAdapter questPagerAdapter;
    private int position;
    @Bind(R.id.viewpager)
    public ViewPager viewpager;
    @Bind(R.id.tv_title)
    public TextView tv_title;

    @Bind(R.id.play)
    public ImageView play;

    @Override
    protected void getIntentDatas() {
        super.getIntentDatas();
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        position = intent.getIntExtra("position", -1);
        switch (type) {
            case Constants.ESSAY:
                essayBeen = (List<ReadEntity.DataBean.EssayBean>) intent.getSerializableExtra("essayBeanList");
                break;
            case Constants.SREIALCONTENT:
                serialBeen = (List<ReadEntity.DataBean.SerialBean>) intent.getSerializableExtra("serialBeanList");
                break;
            case Constants.QUESTION:
                questionBeen = (List<ReadEntity.DataBean.QuestionBean>) intent.getSerializableExtra("questionBeanList");
                break;
        }
        viewpager.addOnPageChangeListener(this);
    }

    @Override
    protected void init() {
        switch (type) {
            case Constants.ESSAY:
                tv_title.setText("短篇");
                essayPageAdapter = new EssayPageAdapter(getSupportFragmentManager(), essayBeen);
                viewpager.setAdapter(essayPageAdapter);
                break;
            case Constants.SREIALCONTENT:
                tv_title.setText("连载");
                serialPagerAdapter = new SerialPagerAdapter(getSupportFragmentManager(), serialBeen);
                viewpager.setAdapter(serialPagerAdapter);
                break;
            case Constants.QUESTION:
                tv_title.setText("问题");
                questPagerAdapter = new QuestPagerAdapter(getSupportFragmentManager(), questionBeen);
                viewpager.setAdapter(questPagerAdapter);
                break;
        }
        viewpager.setCurrentItem(position);
        viewpager.setOffscreenPageLimit(10);
    }

    @Override
    public boolean isOpenStatus() {
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == viewpager.getAdapter().getCount() - 1) {
            viewpager.setCurrentItem(viewpager.getAdapter().getCount() - 2);
            //跳转到往期列表
            Intent intent = new Intent(this, HomeListActivity.class);
            switch (type) {
                case Constants.ESSAY:
                    intent.putExtra("type", Constants.ESSAY);
                    break;
                case Constants.SREIALCONTENT:
                    intent.putExtra("type", Constants.SREIALCONTENT);
                    break;
                case Constants.QUESTION:
                    intent.putExtra("type", Constants.QUESTION);
                    break;
            }
            startActivity(intent);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.play)
    public void musicPlay(View view){
        Intent intent = new Intent(this,MusicPlayActivity.class);
        startActivity(intent);
    }
}
