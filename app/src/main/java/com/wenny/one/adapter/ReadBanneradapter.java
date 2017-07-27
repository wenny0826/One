package com.wenny.one.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wenny.one.R;
import com.wenny.one.activity.BannerActivity;
import com.wenny.one.entity.ReadBannerEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;

/**
 * Created by wenny on 2016/10/23.
 */

public class ReadBanneradapter extends PagerAdapter {

    private List<ReadBannerEntity> datas;
    private Context context;
    private List<ImageView> imageViews;
//    private OnClickListener onClickListener;

//    public void setOnClickListener(OnClickListener onClickListener) {
//        this.onClickListener = onClickListener;
//    }

    public ReadBanneradapter(final Context context, List<ReadBannerEntity> readBannerEntities) {
        this.context = context;
        datas = readBannerEntities;
        imageViews = new ArrayList<>();
        for (int i = 0; i < readBannerEntities.size(); i++) {
            ImageView iv = new ImageView(context);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(iv.getContext()).load(readBannerEntities.get(i).getCover()).placeholder(R.drawable.default_reading_banner_image).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
            imageViews.add(iv);
        }
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = imageViews.get(position);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BannerActivity.class);
                intent.putExtra("banner", datas.get(position));
                context.startActivity(intent);
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViews.get(position));
    }

//    public interface OnClickListener{
//        void onClick(View view,ReadBannerEntity banner);
//    }
}
