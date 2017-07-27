package com.wenny.one.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wenny.one.fragment.GuideFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenny on 2016/10/24.
 */

public class GuideAdpter extends FragmentPagerAdapter {

    private List<int[]> ids;

    public GuideAdpter(FragmentManager fm) {
        super(fm);
        ids = new ArrayList<>();
    }

    public void setIds(List<int[]> ids) {
        this.ids = ids;
    }

    @Override
    public Fragment getItem(int position) {
        return GuideFragment.getIntence(ids.get(position));
    }

    @Override
    public int getCount() {
        return ids.size();
    }

}
