package com.wenny.one.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wenny.one.R;
import com.wenny.one.activity.MovieInfoActivity;
import com.wenny.one.adapter.MovieItemAdapter;
import com.wenny.one.entity.MovieEntity;
import com.wenny.wennylibrary.base.BaseFragment;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wenny on 2016/10/25.
 */

public class ItemMovieFragment extends BaseFragment implements MovieItemAdapter.OnItemCLickListener {

    public static ItemMovieFragment getInstance(List<MovieEntity> movieEntities){
        ItemMovieFragment itemMovieFragment = new ItemMovieFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("movieEntities", (Serializable) movieEntities);
        itemMovieFragment.setArguments(bundle);
        return itemMovieFragment;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_item_movie;
    }

    private List<MovieEntity> movieEntities;

    @Bind(R.id.movie_recyclerView)
    public RecyclerView recyclerView;
    private MovieItemAdapter movieAdapter;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        movieEntities = (List<MovieEntity>) bundle.getSerializable("movieEntities");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movieAdapter = new MovieItemAdapter(getContext());
        recyclerView.setAdapter(movieAdapter);
        if(movieEntities != null){
            movieAdapter.setDatas(movieEntities);
            movieAdapter.setOnItemCLickListener(this);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), MovieInfoActivity.class);
        MovieEntity movieEntity = movieEntities.get(position);
        intent.putExtra("movieId",movieEntity.getId());
        startActivity(intent);
    }
}
