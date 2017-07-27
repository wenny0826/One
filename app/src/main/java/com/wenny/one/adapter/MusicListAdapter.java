package com.wenny.one.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wenny.one.R;
import com.wenny.one.base.AppContext;
import com.wenny.one.entity.MusicEntity;
import com.wenny.one.entity.MusicListEntity;
import com.wenny.one.entity.PlayEntity;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.wennylibrary.adapter.AbsAdapter;
import com.wenny.wennylibrary.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenny on 2016/11/3.
 */

public class MusicListAdapter extends AbsAdapter<MusicListEntity> {

    private Context context;
    private List<PlayEntity> playEntities;

    public MusicListAdapter(Context context) {
        super(context, R.layout.item_musiclist);
        this.context = context;
    }

    @Override
    public void setDatas(List<MusicListEntity> datas) {
        super.setDatas(datas);
        playEntities = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            PlayEntity p = new PlayEntity();
            playEntities.add(p);
        }
        AppContext.playMusicList1 = playEntities;
    }

    public List<PlayEntity> getPlayEntities() {
        return playEntities;
    }

    @Override
    public void bindView(ViewHolder viewHolder, MusicListEntity data, int layoutType, final int position) {
        viewHolder.setTextView(R.id.tv_title,data.getTitle());
        viewHolder.setTextView(R.id.tv_author,data.getAuthor().getUser_name());
        ImageView iv_cover = (ImageView) viewHolder.getView(R.id.iv_cover);
        Glide.with(iv_cover.getContext()).load(data.getCover()).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_cover);

        new RetrofitUtil(context).setDownListener(new RetrofitUtil.DownListener() {
            @Override
            public Object paresJson(String json, int requestCode) {
                return JsonUtil.getMusicEntity(json);
            }

            @Override
            public void downSucc(Object object, int requestCode) {
                if(object != null){
                    MusicEntity music = (MusicEntity) object;
                    PlayEntity play = new PlayEntity(music.getData().getId(),music.getData().getTitle(),music.getData().getAuthor().getUser_name(),music.getData().getMusic_id(),"music");
                    AppContext.playMusicList1.set(position,play);
                }
            }
            @Override
            public void downFail(int requestCode) {

            }
        }).downJson(String.format(Constants.CONTENT, Constants.MUSIC, data.getId()),0x001);
    }

    @Override
    public int LayoutType(int position, MusicListEntity musicListEntity, List<MusicListEntity> musicListEntities) {
        return 0;
    }
}
