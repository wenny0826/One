package com.wenny.one.activity;

import android.content.Intent;
import android.widget.ListView;

import com.wenny.one.R;
import com.wenny.one.adapter.CommentAdapter;
import com.wenny.one.entity.CommentEntity;
import com.wenny.wennylibrary.base.BaseActivity;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wenny on 2016/11/3.
 */

public class CommentActivity extends BaseActivity {

    @Bind(R.id.listview)
    public ListView listView;
    private List<List<CommentEntity>> datas;
    private int page = 0;
    private CommentAdapter commentAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void getIntentDatas() {
        Intent intent = getIntent();
        datas = (List<List<CommentEntity>>) intent.getSerializableExtra("commentdatas");
    }

    @Override
    protected void init() {
        commentAdapter = new CommentAdapter(this);
        listView.setAdapter(commentAdapter);
        if(datas.size() > 0) {
            commentAdapter.setDatas(datas.get(page));
        }
        listView.setDivider(null);
    }
}
