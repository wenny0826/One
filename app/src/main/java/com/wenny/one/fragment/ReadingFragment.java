package com.wenny.one.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wenny.one.R;
import com.wenny.one.activity.BannerActivity;
import com.wenny.one.activity.ReadListActivity;
import com.wenny.one.adapter.ReadBanneradapter;
import com.wenny.one.adapter.ReadAdapter;
import com.wenny.one.base.AppContext;
import com.wenny.one.entity.ReadBannerEntity;
import com.wenny.one.entity.ReadEntity;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.wennylibrary.base.BaseFragment;
import com.wenny.wennylibrary.util.RetrofitUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wenny on 2016/10/22.
 */

public class ReadingFragment extends BaseFragment implements ViewPager.OnPageChangeListener, RetrofitUtil.DownListener {

    //banner相关
    @Bind(R.id.read_viewPagerB)
    public ViewPager autoScrollViewPager;
    private List<ReadBannerEntity> readBannerEntities;
    private ReadBanneradapter readBanneradapter;

    //内容相关
    @Bind(R.id.read_viewPagerC)
    public ViewPager read_viewPager;
    private ReadAdapter readAdapter;


    @Bind(R.id.loading)
    public View loading;
    private AnimationDrawable animationDrawable;

    @Override
    protected int getContentId() {
        return R.layout.fragment_reading;
    }

    @Override
    protected void init(View view) {
        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);
        readAdapter = new ReadAdapter(getChildFragmentManager());
        read_viewPager.addOnPageChangeListener(this);
//        readBanneradapter.setOnClickListener(this);
    }

    @Override
    protected void loadDatas() {
        //下载头部
        loadReadId();
        //下载阅读内容
        lodReadDatas();
    }

    /**
     * 下载头部数据
     */
    private void loadReadId() {
        new RetrofitUtil(getContext()).setDownListener(this).downJson(Constants.READ_BANNER, 0x001);
    }

    /**
     * 下载阅读内容
     */
    private void lodReadDatas() {
        new RetrofitUtil(getContext()).setDownListener(this).downJson(Constants.READ_CONTENT, 0x002);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            read_viewPager.setCurrentItem(1);
            Toast.makeText(getContext(), "刷新数据", Toast.LENGTH_SHORT).show();
            lodReadDatas();
        }
        if (position == read_viewPager.getAdapter().getCount() - 1) {
            read_viewPager.setCurrentItem(read_viewPager.getAdapter().getCount() - 2);
            startActivity(new Intent(getActivity(), ReadListActivity.class));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public Object paresJson(String json, int requestCode) {
        if (requestCode == 0x001) {
            return JsonUtil.getReadBanner(json);
        } else if (requestCode == 0x002) {
            return JsonUtil.getReadEntity(json);
        } else {
            return null;
        }
    }

    @Override
    public void downSucc(Object object, int requestCode) {
        if (object != null) {
            switch (requestCode) {
                case 0x001:
                    List<ReadBannerEntity> ss = (List<ReadBannerEntity>) object;
                    readBannerEntities = ss;
                    readBanneradapter = new ReadBanneradapter(getActivity(), readBannerEntities);
                    autoScrollViewPager.setAdapter(readBanneradapter);
                    break;
                case 0x002:
                    ReadEntity readEntities = (ReadEntity) object;
                    readAdapter.setReadEntity(readEntities);
                    read_viewPager.setAdapter(readAdapter);
                    read_viewPager.setCurrentItem(1);
                    animationDrawable.stop();
                    loading.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public void downFail(int requestCode) {

    }

//    @Override
//    public void onClick(View view, ReadBannerEntity banner) {
//        Intent intent = new Intent(getContext(), BannerActivity.class);
//        intent.putExtra("banner",banner);
//        startActivity(intent);
//    }
}
