package com.wenny.one.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wenny.one.R;
import com.wenny.one.widget.GuideSurfaceView;
import com.wenny.wennylibrary.base.BaseFragment;

import butterknife.Bind;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by wenny on 2016/10/24.
 */

public class GuideFragment extends Fragment {

    public static GuideFragment getIntence(int[] animationId){
        GuideFragment guideFragment = new GuideFragment();
        Bundle bundle = new Bundle();
        bundle.putIntArray("animationId",animationId);
        guideFragment.setArguments(bundle);
        return guideFragment;
    }

    private int[] animationId;
    public GuideSurfaceView imageView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("print", "onCreateView:  Fragment" );
        view = inflater.inflate(R.layout.fragment_guide, container, false);
        imageView = (GuideSurfaceView) view.findViewById(R.id.iv_guide);
        return view;
    }

    @Override
    public void  onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        animationId = bundle.getIntArray("animationId");
        imageView.setListIds(animationId);
        imageView.setTime(30);
    }

    @Override
    public void onStart() {
        super.onStart();
//        imageView = (GuideSurfaceView) view.findViewById(R.id.iv_guide);
//        imageView.setListIds(animationId);
//        imageView.setTime(30);
    }
}
