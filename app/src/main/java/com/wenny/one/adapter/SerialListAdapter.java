package com.wenny.one.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wenny.one.R;
import com.wenny.one.entity.ReadEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenny on 2016/11/3.
 */

public class SerialListAdapter extends RecyclerView.Adapter<SerialListAdapter.MyViewHolder> {

    private Context context;
    private List<ReadEntity.DataBean.SerialBean> serialBeen;

    private OnItemClickListener onItemClickListener;

    public SerialListAdapter(Context context) {
        this.context = context;
        serialBeen = new ArrayList<>();
    }

    public void setSerialBeen(List<ReadEntity.DataBean.SerialBean> serialBeen) {
        this.serialBeen = serialBeen;
        this.notifyDataSetChanged();
    }

    public ReadEntity.DataBean.SerialBean getItemData(int position){
        return serialBeen.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_serial, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.serial_author.setText(serialBeen.get(position).getAuthor().getUser_name());
        holder.serial_title.setText(serialBeen.get(position).getTitle());
        holder.serial_word.setText(serialBeen.get(position).getExcerpt());
    }

    @Override
    public int getItemCount() {
        return serialBeen.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView serial_title,serial_author,serial_word;

        public MyViewHolder(final View itemView) {
            super(itemView);
            serial_word = (TextView) itemView.findViewById(R.id.serial_word);
            serial_title = (TextView) itemView.findViewById(R.id.serial_title);
            serial_author = (TextView) itemView.findViewById(R.id.serial_author);
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
