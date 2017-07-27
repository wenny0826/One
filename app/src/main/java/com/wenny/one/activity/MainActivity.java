package com.wenny.one.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wenny.one.R;
import com.wenny.one.fragment.HomeFragment;
import com.wenny.one.fragment.MovieFragment;
import com.wenny.one.fragment.MusicFragment;
import com.wenny.one.fragment.ReadingFragment;
import com.wenny.wennylibrary.base.BaseActivity;
import com.wenny.wennylibrary.util.SharedUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wenny on 2016/10/22.
 */

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private RadioGroup radioGroup;
    @Bind(R.id.tab_bottom)
    public View tabBottom;
    @Bind(R.id.tab_title)
    public View tabTitle;

    private TextView tvTitle;
    private ImageView ivTitle;
    private ImageView ivSearch;
    private ImageView ivUser;
    private ImageView play;

    private boolean isFirstHp;
    private boolean isFirstRead;
    private boolean isFirstMusic;

    @Bind(R.id.iv_hp_guide)
    public ImageView iv_hp_guide;
    @Bind(R.id.iv_read_guide)
    public ImageView iv_read_guide;
    @Bind(R.id.iv_music_guide)
    public ImageView iv_music_guide;



    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        SharedUtil.putBoolean("isFirst",true);
        //头部title
        tvTitle = (TextView) tabTitle.findViewById(R.id.tv_title);
        ivSearch = (ImageView) tabTitle.findViewById(R.id.iv_search);
        ivUser = (ImageView) tabTitle.findViewById(R.id.iv_user);
        ivTitle = (ImageView) tabTitle.findViewById(R.id.iv_title);
        play = (ImageView) tabTitle.findViewById(R.id.play);

        radioGroup = (RadioGroup) tabBottom.findViewById(R.id.tab_rg);
        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.getChildAt(0).performClick();//模拟点击第一项
        play.setOnClickListener(this);
    }


    /**
     * radioGroup的点击监听事件
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_home:
                showFragment(R.id.tab_content,new HomeFragment());
                tvTitle.setVisibility(View.GONE);
                ivTitle.setVisibility(View.VISIBLE);

                isFirstHp = SharedUtil.getBoolean("isFirstHp");
                if(!isFirstHp){
                    iv_hp_guide.setVisibility(View.VISIBLE);
                } else {
                    iv_hp_guide.setVisibility(View.GONE);
                }

                break;
            case R.id.rb_reading:
                showFragment(R.id.tab_content,new ReadingFragment());
                ivTitle.setVisibility(View.GONE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("阅读");

                isFirstRead = SharedUtil.getBoolean("isFirstRead");
                if (isFirstRead) {
                    iv_read_guide.setVisibility(View.GONE);
                } else {
                    iv_read_guide.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.rb_music:
                showFragment(R.id.tab_content,new MusicFragment());
                ivTitle.setVisibility(View.GONE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("音乐");

                isFirstMusic = SharedUtil.getBoolean("isFirstMusic");
                if (isFirstMusic){
                    iv_music_guide.setVisibility(View.GONE);
                } else {
                    iv_music_guide.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.rb_move:
                showFragment(R.id.tab_content,new MovieFragment());
                ivTitle.setVisibility(View.GONE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("电影");
                break;
        }
    }

    /**
     * 关闭沉浸式状态栏
     * @return
     */
    @Override
    public boolean isOpenStatus() {
        return false;
    }

    /**
     * 点击隐藏自身
     * @param view
     */
    @OnClick({R.id.iv_hp_guide,R.id.iv_read_guide,R.id.iv_music_guide})
    public void dismiss(View view){
        switch (view.getId()){
            case R.id.iv_hp_guide:
                view.setVisibility(View.GONE);
                SharedUtil.putBoolean("isFirstHp",true);
                break;
            case R.id.iv_music_guide:
                view.setVisibility(View.GONE);
                SharedUtil.putBoolean("isFirstMusic",true);
                break;
            case R.id.iv_read_guide:
                view.setVisibility(View.GONE);
                SharedUtil.putBoolean("isFirstRead",true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,MusicPlayActivity.class);
        startActivity(intent);
    }
}
