package com.wenny.one.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wenny.one.entity.MovieEntity;
import com.wenny.one.fragment.ItemMovieFragment;
import com.wenny.one.fragment.LoadFragment;
import com.wenny.one.fragment.RefreshFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenny on 2016/10/25.
 */

public class MovieAdApter extends FragmentPagerAdapter {

    private List<MovieEntity> movieEntities;
    private int LOAD = 1;
    private int REFRESH = 1;

    public MovieAdApter(FragmentManager fm) {
        super(fm);
        movieEntities = new ArrayList<>();
    }

    public void setMovieEntities(List<MovieEntity> movieEntities) {
        this.movieEntities = movieEntities;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RefreshFragment();
            case 1:
                return ItemMovieFragment.getInstance(movieEntities);
            case 2:
                return new LoadFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
