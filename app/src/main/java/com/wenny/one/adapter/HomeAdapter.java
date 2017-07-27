package com.wenny.one.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wenny.one.fragment.ItemHomeFragment;
import com.wenny.one.fragment.LoadFragment;
import com.wenny.one.fragment.RefreshFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenny on 2016/10/25.
 */

public class HomeAdapter extends FragmentPagerAdapter {

    private List<String> datas;
    private int LOAD = 1;
    private int REFRESH = 1;

    public HomeAdapter(FragmentManager fm) {
        super(fm);
        datas = new ArrayList<>();
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new RefreshFragment();
        } else if(position == datas.size() + REFRESH){
            return new LoadFragment();
        } else {
            return ItemHomeFragment.getInstance(datas.get(position - REFRESH));
        }
    }

    @Override
    public int getCount() {
        return datas.size() + LOAD + REFRESH;
    }
}
