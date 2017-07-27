package com.wenny.one.fragment;

import android.app.AlertDialog;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wenny.one.R;
import com.wenny.one.adapter.MovieAdApter;
import com.wenny.one.entity.MovieEntity;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.wennylibrary.base.BaseFragment;
import com.wenny.wennylibrary.util.RetrofitUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wenny on 2016/10/22.
 */

public class MovieFragment extends BaseFragment implements ViewPager.OnPageChangeListener, RetrofitUtil.DownListener {

    private List<MovieEntity> movieList;

    @Bind(R.id.movie_viewPager)
    public ViewPager movie_viewPager;

    private MovieAdApter movieAdApter;

    @Bind(R.id.loading)
    public View loading;
    private AnimationDrawable animationDrawable;

    @Override
    protected int getContentId() {
        return R.layout.fragment_movie;
    }

    @Override
    protected void init(View view) {
        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);

        movieAdApter = new MovieAdApter(getChildFragmentManager());
        movie_viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void loadDatas() {
        loadMovieList();
    }

    private void loadMovieList() {
        new RetrofitUtil(getContext()).setDownListener(this).downJson(Constants.MOVIE_LIST, 0x001);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            movie_viewPager.setCurrentItem(1);
            Toast.makeText(getContext(), "刷新数据", Toast.LENGTH_SHORT).show();
            loadDatas();
            return;
        }
        if (position == movie_viewPager.getAdapter().getCount() - 1) {
            movie_viewPager.setCurrentItem(movie_viewPager.getAdapter().getCount() - 2);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public Object paresJson(String json, int requestCode) {
        return JsonUtil.getMovieList(json);
    }

    @Override
    public void downSucc(Object object, int requestCode) {
        if (object != null) {
            movieList = (List<MovieEntity>) object;
            movieAdApter.setMovieEntities(movieList);
            movie_viewPager.setAdapter(movieAdApter);
            movie_viewPager.setCurrentItem(1);
            animationDrawable.stop();
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void downFail(int requestCode) {

    }
}
