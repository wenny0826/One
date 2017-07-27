package com.wenny.one.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wenny.one.R;
import com.wenny.one.entity.HpEntity;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.one.util.TimeUtil;
import com.wenny.wennylibrary.base.BaseFragment;
import com.wenny.wennylibrary.util.RetrofitUtil;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;

/**
 * Created by wenny on 2016/10/25.
 */

public class ItemHomeFragment extends BaseFragment implements RetrofitUtil.DownListener {

    public static ItemHomeFragment getInstance(String id){
        ItemHomeFragment itemHomeFragment = new ItemHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        itemHomeFragment.setArguments(bundle);
        return itemHomeFragment;
    }

    @Override
    protected int getContentId() {
        return R.layout.item_home;
    }

    private String homeId;
    private ImageView hp_img;
    private TextView hp_title, hp_author, hp_makettime, hp_praisenum, hp_content;
    @Bind(R.id.ll)
    public LinearLayout ll;

    @Bind(R.id.loading)
    public View loading;
    private AnimationDrawable animationDrawable;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        homeId = bundle.getString("id");
        loadHome();
    }

    @Override
    protected void init(View view) {
        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);

        hp_author = (TextView) view.findViewById(R.id.hp_author);
        hp_title = (TextView) view.findViewById(R.id.hp_title);
        hp_makettime = (TextView) view.findViewById(R.id.hp_makettime);
        hp_praisenum = (TextView) view.findViewById(R.id.hp_praisenum);
        hp_img = (ImageView) view.findViewById(R.id.hp_img);
        hp_content = (TextView) view.findViewById(R.id.hp_content);
    }

    private void loadHome() {
        //根据id下载详情
        new RetrofitUtil(getContext()).setDownListener(this).downJson(String.format(Constants.CONTENT, Constants.HOME, homeId),0x001);
    }

    @Override
    public Object paresJson(String json, int requestCode) {
        return JsonUtil.getHpEntity(json);
    }

    @Override
    public void downSucc(Object object, int requestCode) {
        if (object != null) {
            HpEntity hpEntity = (HpEntity) object;
            hp_author.setText(hpEntity.getData().getHp_author());
            hp_title.setText(hpEntity.getData().getHp_title());
            String makettime = hpEntity.getData().getHp_makettime();

            hp_makettime.setText(TimeUtil.getDates(makettime));
            hp_praisenum.setText(hpEntity.getData().getPraisenum() + "");
            hp_content.setText(hpEntity.getData().getHp_content());
            Glide.with(hp_img.getContext()).load(hpEntity.getData().getHp_img_url()).placeholder(R.drawable.default_hp_image).diskCacheStrategy(DiskCacheStrategy.ALL).into(hp_img);
            animationDrawable.stop();
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void downFail(int requestCode) {

    }
}
