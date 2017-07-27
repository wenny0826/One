package com.wenny.one.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wenny.one.R;
import com.wenny.one.activity.HomeListActivity;
import com.wenny.one.adapter.HomeAdapter;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.wennylibrary.base.BaseFragment;
import com.wenny.wennylibrary.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wenny on 2016/10/22.
 */

public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, RetrofitUtil.DownListener {

    @Bind(R.id.home_viewPager)
    public ViewPager viewPager;

    private HomeAdapter homeAdapter;
    private List<String> ids;

    @Bind(R.id.loading)
    public View loading;
    private AnimationDrawable animationDrawable;
    @Override
    protected int getContentId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(View view) {
        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);

        homeAdapter = new HomeAdapter(getChildFragmentManager());
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void loadDatas() {
        loadHome();
    }

    /**
     * 下载hpEntity的id;
     */
    private void loadHome() {
        new RetrofitUtil(getContext()).setDownListener(this).downJson(String.format(Constants.IDLIST, Constants.HOME),0x001);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            viewPager.setCurrentItem(1);
            Toast.makeText(getContext(),"刷新数据",Toast.LENGTH_SHORT).show();
            loadHome();

        }
        if(position == viewPager.getAdapter().getCount() - 1){
            viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 2);
            Intent intent = new Intent(getActivity(), HomeListActivity.class);
            intent.putExtra("type",Constants.HOME);
            startActivity(intent);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public Object paresJson(String json, int requestCode) {
        return JsonUtil.getIdList(json);
    }

    @Override
    public void downSucc(Object object, int requestCode) {
        if (object != null) {
            List<String> datas = (List<String>) object;
            ids = new ArrayList<>();
            ids = datas;
            Log.d("print", "ids: " + ids);
            homeAdapter.setDatas(ids);
            viewPager.setAdapter(homeAdapter);
            viewPager.setCurrentItem(1);
            if(animationDrawable.isRunning()){
                animationDrawable.stop();
                loading.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void downFail(int requestCode) {

    }
}
