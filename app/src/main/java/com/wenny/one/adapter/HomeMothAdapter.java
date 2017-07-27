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
import com.wenny.one.entity.HpEntity;
import com.wenny.one.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenny on 2016/11/2.
 */

public class HomeMothAdapter extends RecyclerView.Adapter<HomeMothAdapter.MyViewHolder>{

    private Context context;
    private List<HpEntity.DataBean> data;
    private OnItemClickListener onItemClickListener;

    public HomeMothAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<HpEntity.DataBean> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public HpEntity.DataBean getItemData(int position){
        return data.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_moth, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(holder.hp_img_url.getContext()).load(data.get(position).getHp_img_url()).placeholder(R.drawable.default_hp_image).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.hp_img_url);
        holder.hp_content.setText(data.get(position).getHp_content());
        holder.hp_makettime.setText(TimeUtil.getDate(data.get(position).getHp_makettime()));
        holder.hp_title.setText(data.get(position).getHp_title());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView hp_img_url;
        TextView hp_title,hp_makettime,hp_content;

        public MyViewHolder(final View itemView) {
            super(itemView);
            hp_content = (TextView) itemView.findViewById(R.id.hp_content);
            hp_makettime = (TextView) itemView.findViewById(R.id.hp_makettime);
            hp_title = (TextView) itemView.findViewById(R.id.hp_title);
            hp_img_url = (ImageView) itemView.findViewById(R.id.hp_img_url);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onItemClickListener != null){
                            onItemClickListener.onItemClick(itemView,getAdapterPosition());
                        }
                    }
                });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
