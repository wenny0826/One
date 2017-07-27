package com.wenny.one.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.wenny.one.fragment.ItemHomeFragment;
import com.wenny.one.fragment.ItemMusicFragment;
import com.wenny.one.fragment.LoadFragment;
import com.wenny.one.fragment.RefreshFragment;
import com.wenny.one.player.MusicService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenny on 2016/10/25.
 */

public class MusicAdapter extends FragmentPagerAdapter {

    private List<String> datas;
    private int LOAD = 1;
    private int REFRESH = 1;


    public MusicAdapter(FragmentManager fm, Context context) {
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
            return ItemMusicFragment.getInstance(datas.get(position - REFRESH),position-1);
        }
    }

    @Override
    public int getCount() {
        return datas.size() + LOAD + REFRESH;
    }
}
