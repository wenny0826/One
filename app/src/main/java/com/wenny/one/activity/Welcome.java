package com.wenny.one.activity;

import android.content.Intent;
import android.widget.ImageView;

import com.wenny.one.R;
import com.wenny.wennylibrary.base.BaseActivity;
import com.wenny.wennylibrary.util.SharedUtil;

import butterknife.Bind;

public class Welcome extends BaseActivity {

    @Bind(R.id.iv_logo)
    public ImageView iv_logo;
    private boolean isFirst;//是否第一次进入app

    @Override
    protected int getContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void init() {
        isFirst = SharedUtil.getBoolean("isFirst");
        iv_logo.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFirst){
                    //跳转到主页面
                    startActivity(new Intent(Welcome.this,MainActivity.class));
                } else {
                    //跳转到导航页
                    startActivity(new Intent(Welcome.this,GuideActivity.class));
                }
                finish();
            }
        },3000);
    }
}
