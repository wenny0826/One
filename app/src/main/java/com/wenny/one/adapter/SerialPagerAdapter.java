package com.wenny.one.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wenny.one.entity.ReadEntity;
import com.wenny.one.fragment.LoadFragment;
import com.wenny.one.fragment.SerialFragment;

import java.util.List;

/**
 * Created by wenny on 2016/11/1.
 */

public class SerialPagerAdapter extends FragmentPagerAdapter {

    private List<ReadEntity.DataBean.SerialBean> serialBeen;

    public SerialPagerAdapter(FragmentManager fm, List<ReadEntity.DataBean.SerialBean> serialBeen) {
        super(fm);
        this.serialBeen = serialBeen;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == serialBeen.size()){
            return new LoadFragment();
        } else {
            return SerialFragment.getInstance(serialBeen.get(position));
        }
    }

    @Override
    public int getCount() {
        return serialBeen.size();
    }
}
