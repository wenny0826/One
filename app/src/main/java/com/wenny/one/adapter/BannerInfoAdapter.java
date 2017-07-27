package com.wenny.one.adapter;

import android.content.Context;

import com.wenny.one.R;
import com.wenny.one.entity.BannerInfoEntity;
import com.wenny.wennylibrary.adapter.AbsAdapter;

import java.util.List;

/**
 * Created by wenny on 2016/11/4.
 */

public class BannerInfoAdapter extends AbsAdapter<BannerInfoEntity>{


    public BannerInfoAdapter(Context context) {
        super(context, R.layout.item_banner);
    }

    @Override
    public void bindView(ViewHolder viewHolder, BannerInfoEntity data, int layoutType, int position) {
        viewHolder.setTextView(R.id.tv_num,(position + 1) + "");
        viewHolder.setTextView(R.id.tv_title,data.getTitle());
        viewHolder.setTextView(R.id.tv_author,data.getAuthor());
        viewHolder.setTextView(R.id.tv_introduction,data.getIntroduction());
    }

    @Override
    public int LayoutType(int position, BannerInfoEntity bannerInfoEntity, List<BannerInfoEntity> bannerInfoEntities) {
        return 0;
    }
}
