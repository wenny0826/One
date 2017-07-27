package com.wenny.one.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wenny.one.entity.ReadEntity;
import com.wenny.one.fragment.ItemReadFragment;
import com.wenny.one.fragment.LoadFragment;
import com.wenny.one.fragment.RefreshFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenny on 2016/10/25.
 */

public class ReadAdapter extends FragmentPagerAdapter {

    private ReadEntity readEntity;
    private List<ReadEntity.DataBean.EssayBean> essayBeen;
    private List<ReadEntity.DataBean.SerialBean> serialBeen;
    private List<ReadEntity.DataBean.QuestionBean> questionBeen;

    private int LOAD = 1;
    private int REFRESH = 1;

    public ReadAdapter(FragmentManager fm) {
        super(fm);
        readEntity = new ReadEntity();
        essayBeen = new ArrayList<>();
        serialBeen = new ArrayList<>();
        questionBeen = new ArrayList<>();
    }

    public void setReadEntity(ReadEntity readEntity) {
        this.readEntity = readEntity;
        essayBeen = readEntity.getData().getEssay();
        serialBeen = readEntity.getData().getSerial();
        questionBeen = readEntity.getData().getQuestion();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new RefreshFragment();
        } else if(position == essayBeen.size() + REFRESH){
            return new LoadFragment();
        } else {
            return ItemReadFragment.getInstance(readEntity,position - REFRESH);
        }
    }

    @Override
    public int getCount() {
        return essayBeen.size() + LOAD + REFRESH;
    }
}
