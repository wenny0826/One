package com.wenny.one.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wenny.one.entity.ReadEntity;
import com.wenny.one.fragment.LoadFragment;
import com.wenny.one.fragment.QuestFragment;

import java.util.List;

/**
 * Created by wenny on 2016/11/1.
 */

public class QuestPagerAdapter extends FragmentPagerAdapter {

    private List<ReadEntity.DataBean.QuestionBean> questionBeen;

    public QuestPagerAdapter(FragmentManager fm, List<ReadEntity.DataBean.QuestionBean> questionBeen) {
        super(fm);
        this.questionBeen = questionBeen;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == questionBeen.size()){
            return new LoadFragment();
        } else {
            return QuestFragment.getInstance(questionBeen.get(position));
        }
    }

    @Override
    public int getCount() {
        return questionBeen.size() + 1;
    }
}
