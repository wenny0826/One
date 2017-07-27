package com.wenny.one.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wenny.one.R;
import com.wenny.one.adapter.GuideAdpter;
import com.wenny.wennylibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wenny on 2016/10/24.
 * 第一次进入时的导航页
 */

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.guide_viewPager)
    public ViewPager viewPager;
    @Bind(R.id.guide_ll)
    public LinearLayout guide_ll;
    @Bind(R.id.iv_into)
    public ImageView iv_into;


    private GuideAdpter guideAdpter;
    private List<int[]> ids;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void init() {
        guideAdpter = new GuideAdpter(getSupportFragmentManager());
        ids = new ArrayList<>();
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(this);
        //加载资源
        load();
    }

    private void load() {
        int[] id1 = {
                R.drawable.reading_guide_1, R.drawable.reading_guide_2, R.drawable.reading_guide_3, R.drawable.reading_guide_4, R.drawable.reading_guide_5, R.drawable.reading_guide_6, R.drawable.reading_guide_7, R.drawable.reading_guide_8, R.drawable.reading_guide_9, R.drawable.reading_guide_10,
                R.drawable.reading_guide_11, R.drawable.reading_guide_12, R.drawable.reading_guide_13, R.drawable.reading_guide_14, R.drawable.reading_guide_15, R.drawable.reading_guide_16, R.drawable.reading_guide_17, R.drawable.reading_guide_18, R.drawable.reading_guide_19, R.drawable.reading_guide_20,
                R.drawable.reading_guide_21, R.drawable.reading_guide_22, R.drawable.reading_guide_23, R.drawable.reading_guide_24, R.drawable.reading_guide_25, R.drawable.reading_guide_26, R.drawable.reading_guide_27, R.drawable.reading_guide_28, R.drawable.reading_guide_29, R.drawable.reading_guide_30,
                R.drawable.reading_guide_31, R.drawable.reading_guide_32, R.drawable.reading_guide_33, R.drawable.reading_guide_34, R.drawable.reading_guide_35, R.drawable.reading_guide_36, R.drawable.reading_guide_37, R.drawable.reading_guide_38, R.drawable.reading_guide_39, R.drawable.reading_guide_40,
                R.drawable.reading_guide_41, R.drawable.reading_guide_42, R.drawable.reading_guide_43, R.drawable.reading_guide_44, R.drawable.reading_guide_45, R.drawable.reading_guide_46, R.drawable.reading_guide_47, R.drawable.reading_guide_48, R.drawable.reading_guide_49, R.drawable.reading_guide_50,
                R.drawable.reading_guide_51, R.drawable.reading_guide_52, R.drawable.reading_guide_53, R.drawable.reading_guide_54, R.drawable.reading_guide_55, R.drawable.reading_guide_56};
        int[] id2 = {
                R.drawable.movie_guide_1, R.drawable.movie_guide_2, R.drawable.movie_guide_3, R.drawable.movie_guide_4, R.drawable.movie_guide_5, R.drawable.movie_guide_6, R.drawable.movie_guide_7, R.drawable.movie_guide_8, R.drawable.movie_guide_9, R.drawable.movie_guide_10,
                R.drawable.movie_guide_11, R.drawable.movie_guide_12, R.drawable.movie_guide_13, R.drawable.movie_guide_14, R.drawable.movie_guide_15, R.drawable.movie_guide_16, R.drawable.movie_guide_17, R.drawable.movie_guide_18, R.drawable.movie_guide_19, R.drawable.movie_guide_20,
                R.drawable.movie_guide_21, R.drawable.movie_guide_22, R.drawable.movie_guide_23, R.drawable.movie_guide_24, R.drawable.movie_guide_25, R.drawable.movie_guide_26, R.drawable.movie_guide_27, R.drawable.movie_guide_28, R.drawable.movie_guide_29, R.drawable.movie_guide_30,
                R.drawable.movie_guide_31, R.drawable.movie_guide_32, R.drawable.movie_guide_33, R.drawable.movie_guide_34, R.drawable.movie_guide_35, R.drawable.movie_guide_36, R.drawable.movie_guide_37, R.drawable.movie_guide_38, R.drawable.movie_guide_39, R.drawable.movie_guide_40,
                R.drawable.movie_guide_41, R.drawable.movie_guide_42, R.drawable.movie_guide_43, R.drawable.movie_guide_44, R.drawable.movie_guide_45, R.drawable.movie_guide_46, R.drawable.movie_guide_47, R.drawable.movie_guide_48, R.drawable.movie_guide_49, R.drawable.movie_guide_50,
                R.drawable.movie_guide_51, R.drawable.movie_guide_52, R.drawable.movie_guide_53, R.drawable.movie_guide_54, R.drawable.movie_guide_55, R.drawable.movie_guide_56, R.drawable.movie_guide_57, R.drawable.movie_guide_59, R.drawable.movie_guide_59, R.drawable.movie_guide_60,
                R.drawable.movie_guide_61, R.drawable.movie_guide_62, R.drawable.movie_guide_63
        };
        int[] id3 = {
                R.drawable.music_guide_1, R.drawable.music_guide_2, R.drawable.music_guide_3, R.drawable.music_guide_4, R.drawable.music_guide_5, R.drawable.music_guide_6, R.drawable.music_guide_7, R.drawable.music_guide_8, R.drawable.music_guide_9, R.drawable.music_guide_10,
                R.drawable.music_guide_11, R.drawable.music_guide_12, R.drawable.music_guide_13, R.drawable.music_guide_14, R.drawable.music_guide_15, R.drawable.music_guide_16, R.drawable.music_guide_17, R.drawable.music_guide_18, R.drawable.music_guide_19, R.drawable.music_guide_20,
                R.drawable.music_guide_21, R.drawable.music_guide_22, R.drawable.music_guide_23, R.drawable.music_guide_24, R.drawable.music_guide_25, R.drawable.music_guide_26, R.drawable.music_guide_27, R.drawable.music_guide_28, R.drawable.music_guide_29, R.drawable.music_guide_30,
                R.drawable.music_guide_31, R.drawable.music_guide_32, R.drawable.music_guide_33, R.drawable.music_guide_34, R.drawable.music_guide_35, R.drawable.music_guide_36, R.drawable.music_guide_37, R.drawable.music_guide_38, R.drawable.music_guide_39, R.drawable.music_guide_40,
                R.drawable.music_guide_41, R.drawable.music_guide_42, R.drawable.music_guide_43, R.drawable.music_guide_44, R.drawable.music_guide_45, R.drawable.music_guide_46, R.drawable.music_guide_47, R.drawable.music_guide_48, R.drawable.music_guide_49, R.drawable.music_guide_50,
                R.drawable.music_guide_51, R.drawable.music_guide_52, R.drawable.music_guide_53, R.drawable.music_guide_54, R.drawable.music_guide_55, R.drawable.music_guide_56, R.drawable.music_guide_57, R.drawable.music_guide_58, R.drawable.music_guide_59, R.drawable.music_guide_60,
                R.drawable.music_guide_61, R.drawable.music_guide_62, R.drawable.music_guide_63, R.drawable.music_guide_64, R.drawable.music_guide_65, R.drawable.music_guide_66, R.drawable.music_guide_67, R.drawable.music_guide_68
        };

        int[] id4 = {
                R.drawable.one_guide_1, R.drawable.one_guide_2, R.drawable.one_guide_3, R.drawable.one_guide_4, R.drawable.one_guide_5, R.drawable.one_guide_6, R.drawable.one_guide_7, R.drawable.one_guide_8, R.drawable.one_guide_9, R.drawable.one_guide_10,
                R.drawable.one_guide_11, R.drawable.one_guide_12, R.drawable.one_guide_13, R.drawable.one_guide_14, R.drawable.one_guide_15, R.drawable.one_guide_16, R.drawable.one_guide_17, R.drawable.one_guide_18, R.drawable.one_guide_19, R.drawable.one_guide_20,
                R.drawable.one_guide_21, R.drawable.one_guide_22, R.drawable.one_guide_23, R.drawable.one_guide_24, R.drawable.one_guide_25, R.drawable.one_guide_26, R.drawable.one_guide_27, R.drawable.one_guide_28, R.drawable.one_guide_29, R.drawable.one_guide_30,
                R.drawable.one_guide_31, R.drawable.one_guide_32, R.drawable.one_guide_33, R.drawable.one_guide_34, R.drawable.one_guide_35, R.drawable.one_guide_36, R.drawable.one_guide_37, R.drawable.one_guide_38, R.drawable.one_guide_39, R.drawable.one_guide_40,
                R.drawable.one_guide_41, R.drawable.one_guide_42, R.drawable.one_guide_43, R.drawable.one_guide_44, R.drawable.one_guide_45, R.drawable.one_guide_46, R.drawable.one_guide_47, R.drawable.one_guide_48, R.drawable.one_guide_49, R.drawable.one_guide_50,
                R.drawable.one_guide_51, R.drawable.one_guide_52, R.drawable.one_guide_53, R.drawable.one_guide_54, R.drawable.one_guide_55, R.drawable.one_guide_56, R.drawable.one_guide_57, R.drawable.one_guide_58, R.drawable.one_guide_59, R.drawable.one_guide_60,
                R.drawable.one_guide_61, R.drawable.one_guide_62, R.drawable.one_guide_63, R.drawable.one_guide_64, R.drawable.one_guide_65, R.drawable.one_guide_66, R.drawable.one_guide_67, R.drawable.one_guide_68, R.drawable.one_guide_69, R.drawable.one_guide_70,
                R.drawable.one_guide_71, R.drawable.one_guide_72, R.drawable.one_guide_73, R.drawable.one_guide_74, R.drawable.one_guide_75, R.drawable.one_guide_76, R.drawable.one_guide_77, R.drawable.one_guide_78, R.drawable.one_guide_79, R.drawable.one_guide_80,
                R.drawable.one_guide_81, R.drawable.one_guide_82, R.drawable.one_guide_83, R.drawable.one_guide_84, R.drawable.one_guide_85, R.drawable.one_guide_86, R.drawable.one_guide_87, R.drawable.one_guide_88, R.drawable.one_guide_89, R.drawable.one_guide_90,
                R.drawable.one_guide_91, R.drawable.one_guide_92, R.drawable.one_guide_93, R.drawable.one_guide_94
        };
        ids.add(id1);
        ids.add(id2);
        ids.add(id3);
        ids.add(id4);
        inits();
    }

    private void  inits(){
        guideAdpter.setIds(ids);
        viewPager.setAdapter(guideAdpter);
        viewPager.setOffscreenPageLimit(1);
        //加载指示器

        loadPoint(0);
    }

    /**
     * 加载指示器
     */
    private void loadPoint(int index){
        guide_ll.removeAllViews();
        for (int i = 0; i < ids.size(); i++) {
            ImageView iv = new ImageView(this);
            iv.setPadding(10,10,10,10);
            if (i == index){
                iv.setImageResource(R.drawable.guide_checked_index);
            } else {
                iv.setImageResource(R.drawable.guide_default_index);
            }
            guide_ll.addView(iv);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        loadPoint(position);
        if(position == ids.size() - 1){
            iv_into.setVisibility(View.VISIBLE);
            guide_ll.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.iv_into)
    public void btnClick(View view){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
