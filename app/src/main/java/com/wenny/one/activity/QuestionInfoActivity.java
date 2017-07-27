package com.wenny.one.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wenny.one.R;
import com.wenny.one.adapter.CommentAdapter;
import com.wenny.one.entity.CommentEntity;
import com.wenny.one.entity.QuestionEntity;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.one.util.TimeUtil;
import com.wenny.one.util.WebViewUtil;
import com.wenny.wennylibrary.base.BaseActivity;
import com.wenny.wennylibrary.util.RetrofitUtil;
import com.wenny.wennylibrary.widget.ListViewInScr;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wenny on 2016/11/4.
 */

public class QuestionInfoActivity extends BaseActivity implements RetrofitUtil.DownListener, View.OnClickListener {

    @Bind(R.id.contentView)
    public View contentView;

    public TextView question_title;
    public TextView question_content;
    public TextView answer_title;
    public TextView question_makettime;
    public WebView answer_content;
    public TextView charge_edt;
    public View question_comment;
    public View question_num;

    private ListViewInScr listview;
    private CheckBox praisenum;
    private TextView commentnum;
    private TextView sharenum;

    private List<List<CommentEntity>> commmentDatas;
    private CommentAdapter commentAdapter;
    private int commentPage = 0;
    private View moreComment;
    private QuestionEntity questionEntity;



    @Bind(R.id.loading)
    public View loading;
    private AnimationDrawable animationDrawable;
    private String questionid;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_question_info;
    }

    @Override
    protected void getIntentDatas() {
        Intent intent = getIntent();
        questionid = intent.getStringExtra("questionid");
    }

    @Override
    protected void init() {
        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);

        question_title = (TextView) contentView.findViewById(R.id.question_title);
        question_content = (TextView) contentView.findViewById(R.id.question_content);
        answer_title = (TextView) contentView.findViewById(R.id.answer_title);
        question_makettime = (TextView) contentView.findViewById(R.id.question_makettime);
        charge_edt = (TextView) contentView.findViewById(R.id.charge_edt);
        answer_content = (WebView) contentView.findViewById(R.id.answer_content);
        question_comment = contentView.findViewById(R.id.question_comment);
        question_num = contentView.findViewById(R.id.question_num);

        listview = (ListViewInScr) question_comment.findViewById(R.id.listview);
        listview.setFocusable(false);
        praisenum = (CheckBox) question_num.findViewById(R.id.praisenum);
        commentnum = (TextView) question_num.findViewById(R.id.commentnum);
        sharenum = (TextView) question_num.findViewById(R.id.sharenum);
        commentAdapter = new CommentAdapter(this);
        listview.setAdapter(commentAdapter);
        moreComment = question_comment.findViewById(R.id.tv_more);
        moreComment.setOnClickListener(this);
    }

    @Override
    protected void loadDatas() {
        answer_content.setFocusable(false);
        new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.READ_INFO,Constants.QUESTION,questionid),0x001);
        //下载评论
        new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.COMMENT,Constants.QUESTION,questionid),0x002);

    }

    @Override
    public Object paresJson(String json, int requestCode) {
        if(requestCode == 0x001) {
            return JsonUtil.getQuestion(json);
        } else if(requestCode == 0x002){
            return JsonUtil.getComment(json);
        } else {
            return null;
        }    }

    @Override
    public void downSucc(Object object, int requestCode) {
        if (object != null){
            switch (requestCode){
                case 0x001:
                    questionEntity = (QuestionEntity) object;
                    question_title.setText(questionEntity.getQuestion_title());
                    question_content.setText(questionEntity.getQuestion_content());
                    answer_title.setText(questionEntity.getAnswer_title());
                    question_makettime.setText(TimeUtil.getDate(questionEntity.getQuestion_makettime()));

                    WebViewUtil.subWebView(answer_content,questionEntity.getAnswer_content());
                    charge_edt.setText(questionEntity.getCharge_edt());
                    praisenum.setText(questionEntity.getPraisenum() + "");
                    commentnum.setText(questionEntity.getCommentnum() + "");
                    sharenum.setText(questionEntity.getSharenum() + "");
                    animationDrawable.stop();
                    loading.setVisibility(View.GONE);
                    break;
                case 0x002:
                    List<CommentEntity> commentEntities = (List<CommentEntity>) object;
                    commmentDatas = JsonUtil.getCommentList(commentEntities);
                    commentAdapter.setDatas(commmentDatas.get(commentPage));
                    break;
            }
        }
    }

    @Override
    public void downFail(int requestCode) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("commentdatas", (Serializable) commmentDatas);
        startActivity(intent);
    }
}
