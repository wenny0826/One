package com.wenny.one.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wenny.one.R;
import com.wenny.one.entity.HpEntity;
import com.wenny.one.util.TimeUtil;
import com.wenny.wennylibrary.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by wenny on 2016/11/2.
 */

public class HomeInfoActivity extends BaseActivity {

    @Bind(R.id.contentView)
    public View contentView;

    private ImageView hp_img;
    private TextView hp_title, hp_author, hp_makettime, hp_praisenum, hp_content;

    private View loading;

    private HpEntity.DataBean hpEntity;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home_info;
    }

    @Override
    protected void getIntentDatas() {
        Intent intent = getIntent();
        hpEntity = (HpEntity.DataBean) intent.getSerializableExtra("dataBean");
    }

    @Override
    protected void init() {
        hp_author = (TextView) contentView.findViewById(R.id.hp_author);
        hp_title = (TextView) contentView.findViewById(R.id.hp_title);
        hp_makettime = (TextView) contentView.findViewById(R.id.hp_makettime);
        hp_praisenum = (TextView) contentView.findViewById(R.id.hp_praisenum);
        hp_img = (ImageView) contentView.findViewById(R.id.hp_img);
        hp_content = (TextView) contentView.findViewById(R.id.hp_content);
        loading = contentView.findViewById(R.id.loading);
        loading.setVisibility(View.GONE);


        hp_author.setText(hpEntity.getHp_author());
        hp_title.setText(hpEntity.getHp_title());
        String makettime = hpEntity.getHp_makettime();

        hp_makettime.setText(TimeUtil.getDates(makettime));
        hp_praisenum.setText(hpEntity.getPraisenum() + "");
        hp_content.setText(hpEntity.getHp_content());
        Glide.with(hp_img.getContext()).load(hpEntity.getHp_img_url()).placeholder(R.drawable.default_hp_image).diskCacheStrategy(DiskCacheStrategy.ALL).into(hp_img);

    }
}
