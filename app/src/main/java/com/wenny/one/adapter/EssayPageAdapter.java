package com.wenny.one.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wenny.one.base.AppContext;
import com.wenny.one.entity.PlayEntity;
import com.wenny.one.entity.ReadEntity;
import com.wenny.one.fragment.EssayFragment;
import com.wenny.one.fragment.LoadFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenny on 2016/11/1.
 */

public class EssayPageAdapter extends FragmentPagerAdapter {

    private List<ReadEntity.DataBean.EssayBean> essayBeen;
    private List<PlayEntity> playEntities;

    public EssayPageAdapter(FragmentManager fm, List<ReadEntity.DataBean.EssayBean> essayBean) {
        super(fm);
        this.essayBeen = essayBean;
        playEntities = new ArrayList<>();
        //
        for (int i = 0; i < essayBeen.size(); i++) {
            PlayEntity play = new PlayEntity();
            playEntities.add(play);
        }
        AppContext.playReadList = playEntities;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == essayBeen.size()){
            return new LoadFragment();
        } else {
            return EssayFragment.getInstance(essayBeen.get(position).getContent_id(),position);
        }
    }

    @Override
    public int getCount() {
        return essayBeen.size() + 1;
    }
}
