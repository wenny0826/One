package com.wenny.wennylibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.wenny.wennylibrary.R;
import com.wenny.wennylibrary.util.ScreenUtil;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    //当前正在展示的Fragment
    private BaseFragment showFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        ButterKnife.bind(this);
        getIntentDatas();
        //注册EventBus
        EventBus.getDefault().register(this);
        //获得FragmentManager对象
        fragmentManager = getSupportFragmentManager();


        init();
        bindListener();
        loadDatas();

        /**
         * 沉浸式状态栏
         */
        if (isOpenStatus()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }

            //获得状态栏的高度
            int height = ScreenUtil.getStatusHeight(this);
            if (height != -1) {
                //设置Padding
                View view = findViewById(R.id.actionbar);
                if (view != null) {
                    view.setPadding(0, height, 0, 0);

                    if (view instanceof Toolbar) {
                        setSupportActionBar((Toolbar) view);

                        //隐藏标题
                        getSupportActionBar().setDisplayShowTitleEnabled(false);
                    }
                }
            }
        }
    }

    /**
     * 获得通过意图传递过来的数据
     */
    protected void getIntentDatas() {
    }

    ;

    /**
     * EventBus的接收数据
     *
     * @param value
     */
    @Subscribe
    public void onEvent(Object value) {
        Log.d("print", "onEvent: " + value);
    }

    /**
     * 获得要显示的布局ID
     *
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 初始化
     */
    protected void init() {
    }

    /**
     * 加载数据
     */
    protected void loadDatas() {
    }

    /**
     * 绑定监听
     */
    protected void bindListener() {
    }

    /**
     * 以动画的方式启动activity
     *
     * @param intent
     * @param animinid
     * @param animoutid
     */
    public void startActivityForAnimation(Intent intent, int animinid, int animoutid) {
        startActivity(intent);
        overridePendingTransition(animinid, animoutid);
    }

    /**
     * 展示Fragment
     */
    protected void showFragment(int resid, BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //隐藏正在暂时的Fragment
        if (showFragment != null) {
            fragmentTransaction.hide(showFragment);
        }

        //展示需要显示的Fragment对象
        Fragment mFragment = fragmentManager.findFragmentByTag(fragment.getClass().getName());
        if (mFragment != null) {
            fragmentTransaction.show(mFragment);
            showFragment = (BaseFragment) mFragment;
        } else {
            fragmentTransaction.add(resid, fragment, fragment.getClass().getName());
            showFragment = fragment;
        }

        fragmentTransaction.commit();
    }

    /**
     * 启动Activity 可以携带Bundle ,选择是否Finish
     */
    public void startActivity(Class<? extends Activity> target, Bundle bundle, boolean finish) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        if (bundle != null)
            intent.putExtra(getPackageName(), bundle);
        startActivity(intent);
        if (finish)
            finish();
    }

    /**
     * 获取上面方法携带的Bundle bundle名为包名
     */
    public Bundle getBundle() {
        if (getIntent() != null && getIntent().hasExtra(getPackageName()))
            return getIntent().getBundleExtra(getPackageName());
        else
            return null;
    }


    /**
     * 是否打开沉浸式状态栏
     *
     * @return
     */
    public boolean isOpenStatus() {
        return false;
    }

    public void finishSelf(View view) {
        this.finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }
}
