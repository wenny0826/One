package com.wenny.one.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wenny.one.R;
import com.wenny.one.entity.EssayEntity;
import com.wenny.one.entity.PlayEntity;
import com.wenny.one.entity.ReadEntity;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.wennylibrary.adapter.AbsAdapter;
import com.wenny.wennylibrary.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenny on 2016/11/3.
 */

public class EssayListAdapter extends RecyclerView.Adapter<EssayListAdapter.MyViewHolder> {

    private Context context;
    private List<ReadEntity.DataBean.EssayBean> essayBeen;
    private List<PlayEntity> playEntities;

    private OnItemClickListener onItemClickListener;

    public EssayListAdapter(Context context) {
        this.context = context;
        essayBeen = new ArrayList<>();
        playEntities = new ArrayList<>();
    }

    public void setEssayBeen(List<ReadEntity.DataBean.EssayBean> essayBeen) {
        this.essayBeen = essayBeen;
        for (int i = 0; i < essayBeen.size(); i++) {
            PlayEntity p = new PlayEntity();
            playEntities.add(p);
        }
        this.notifyDataSetChanged();
    }

    public ReadEntity.DataBean.EssayBean getItemData(int position) {
        return essayBeen.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_essay, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.essay_title.setText(essayBeen.get(position).getHp_title());
        if (essayBeen.get(position).getAuthor().size() > 0) {
            holder.essay_author.setText(essayBeen.get(position).getAuthor().get(0).getUser_name());
        } else {
            holder.essay_author.setVisibility(View.GONE);
        }
        if(essayBeen.get(position).isHas_audio())
        new RetrofitUtil(context).setDownListener(new RetrofitUtil.DownListener() {
            @Override
            public Object paresJson(String json, int requestCode) {
                return JsonUtil.getEssay(json);
            }

            @Override
            public void downSucc(Object object, int requestCode) {
                if (object != null) {
                    EssayEntity essayEntity = (EssayEntity) object;
                    PlayEntity playEntity = new PlayEntity(essayEntity.getContent_id(), essayEntity.getHp_title(), essayEntity.getHp_author(), essayEntity.getAudio(), "essay");
                    playEntities.set(position,playEntity);
                }
            }

            @Override
            public void downFail(int requestCode) {

            }
        }).downJson(String.format(Constants.READ_INFO, Constants.ESSAY, essayBeen.get(position).getContent_id()), 0x001);

        holder.essay_word.setText(essayBeen.get(position).getGuide_word());
    }

    public List<PlayEntity> getPlayEntities() {
        return playEntities;
    }

    @Override
    public int getItemCount() {
        return essayBeen.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView essay_title, essay_author, essay_word;

        public MyViewHolder(final View itemView) {
            super(itemView);
            essay_word = (TextView) itemView.findViewById(R.id.essay_word);
            essay_title = (TextView) itemView.findViewById(R.id.essay_title);
            essay_author = (TextView) itemView.findViewById(R.id.essay_author);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemView, getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
