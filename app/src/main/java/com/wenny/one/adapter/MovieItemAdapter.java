package com.wenny.one.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wenny.one.R;
import com.wenny.one.entity.MovieEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wenny on 2016/10/24.
 */

public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.MyViewHolder> {

    private Context context;
    private List<MovieEntity> datas;
    private int[] imgIds = {R.drawable.movie_placeholder_0,R.drawable.movie_placeholder_1,R.drawable.movie_placeholder_2,R.drawable.movie_placeholder_3,R.drawable.movie_placeholder_4};

    private OnItemCLickListener onItemCLickListener;

    private Random random;
    public MovieItemAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
        random = new Random();
    }

    public void setDatas(List<MovieEntity> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.movie_score.setText(datas.get(position).getScore());
        Glide.with(holder.movie_cover.getContext()).load(datas.get(position).getCover()).placeholder(imgIds[random.nextInt(5)]).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.movie_cover);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView movie_cover;
        TextView movie_score;

        public MyViewHolder(final View itemView) {
            super(itemView);
            movie_cover = (ImageView) itemView.findViewById(R.id.movie_cover);
            movie_score = (TextView) itemView.findViewById(R.id.movie_score);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemCLickListener != null){
                        onItemCLickListener.onItemClick(itemView,getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnItemCLickListener{
        void onItemClick(View view,int position);
    }
}
