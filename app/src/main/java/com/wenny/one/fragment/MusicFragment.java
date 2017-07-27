package com.wenny.one.fragment;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wenny.one.R;
import com.wenny.one.activity.HomeListActivity;
import com.wenny.one.adapter.MusicAdapter;
import com.wenny.one.base.AppContext;
import com.wenny.one.entity.PlayEntity;
import com.wenny.one.player.MusicService;
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

public class MusicFragment extends BaseFragment implements ViewPager.OnPageChangeListener, RetrofitUtil.DownListener {

    private List<String> ids;

    @Bind(R.id.music_viewPger)
    public ViewPager viewPager;

    private MusicAdapter musicAdapter;

    @Bind(R.id.loading)
    public View loading;
    private AnimationDrawable animationDrawable;


    @Override
    protected int getContentId() {
        return R.layout.fragment_music;
    }


    @Override
    protected void init(View view) {
        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);

        musicAdapter = new MusicAdapter(getChildFragmentManager(),getContext());
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(10);

    }

    @Override
    protected void loadDatas() {
        loadMusicId();

    }

    /**
     * 下载音乐的idlist
     */
    private void loadMusicId(){
        new RetrofitUtil(getContext()).setDownListener(this).downJson(String.format(Constants.IDLIST, Constants.MUSIC),0x001);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            viewPager.setCurrentItem(1);
            AppContext.musicService.cleanList();
            Toast.makeText(getContext(),"刷新数据",Toast.LENGTH_SHORT).show();
            loadMusicId();
        }
        if(position == viewPager.getAdapter().getCount() - 1){
            viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 2);
            Intent intent = new Intent(getActivity(), HomeListActivity.class);
            intent.putExtra("type",Constants.MUSIC);
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
            musicAdapter.setDatas(ids);
            viewPager.setAdapter(musicAdapter);
            viewPager.setCurrentItem(1);
            animationDrawable.stop();
            loading.setVisibility(View.GONE);
            List<PlayEntity> list = new ArrayList<>();
            for (int i = 0; i < datas.size(); i++) {
                PlayEntity play = new PlayEntity();
                list.add(play);
            }
            AppContext.playMusicList = list;
        }
    }

    @Override
    public void downFail(int requestCode) {

    }
}
