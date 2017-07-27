package com.wenny.one.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wenny.one.R;
import com.wenny.one.activity.CommentActivity;
import com.wenny.one.adapter.CommentAdapter;
import com.wenny.one.entity.CommentEntity;
import com.wenny.one.entity.QuestionEntity;
import com.wenny.one.entity.ReadEntity;
import com.wenny.one.entity.SerialEntity;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.one.util.TimeUtil;
import com.wenny.one.util.WebViewUtil;
import com.wenny.wennylibrary.base.BaseFragment;
import com.wenny.wennylibrary.util.RetrofitUtil;
import com.wenny.wennylibrary.widget.ListViewInScr;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wenny on 2016/11/1.
 */

public class QuestFragment extends BaseFragment implements RetrofitUtil.DownListener, View.OnClickListener {
    private QuestionEntity questionEntity;

    public static QuestFragment getInstance(ReadEntity.DataBean.QuestionBean questionBean){
        QuestFragment questFragment = new QuestFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("serialBean",questionBean);
        questFragment.setArguments(bundle);
        return questFragment;
    }
    @Override
    protected int getContentId() {
        return R.layout.fragment_question;
    }

    ReadEntity.DataBean.QuestionBean questionBean;
    @Bind(R.id.question_title)
    public TextView question_title;
    @Bind(R.id.question_content)
    public TextView question_content;
    @Bind(R.id.answer_title)
    public TextView answer_title;
    @Bind(R.id.question_makettime)
    public TextView question_makettime;
    @Bind(R.id.answer_content)
    public WebView answer_content;
    @Bind(R.id.charge_edt)
    public TextView charge_edt;
    @Bind(R.id.question_comment)
    public View question_comment;
    @Bind(R.id.question_num)
    public View question_num;

    private ListViewInScr listview;
    private CheckBox praisenum;
    private TextView commentnum;
    private TextView sharenum;

    private List<List<CommentEntity>> commmentDatas;
    private CommentAdapter commentAdapter;
    private int commentPage = 0;
    private View moreComment;


    @Bind(R.id.loading)
    public View loading;
    private AnimationDrawable animationDrawable;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        questionBean = (ReadEntity.DataBean.QuestionBean) bundle.getSerializable("serialBean");
        answer_content.setFocusable(false);
        new RetrofitUtil(getContext()).setDownListener(this).downJson(String.format(Constants.READ_INFO,Constants.QUESTION,questionBean.getQuestion_id()),0x001);
        //下载评论
        new RetrofitUtil(getContext()).setDownListener(this).downJson(String.format(Constants.COMMENT,Constants.QUESTION,questionBean.getQuestion_id()),0x002);
    }

    @Override
    protected void init(View view) {
        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);

        listview = (ListViewInScr) question_comment.findViewById(R.id.listview);
        listview.setFocusable(false);
        praisenum = (CheckBox) question_num.findViewById(R.id.praisenum);
        commentnum = (TextView) question_num.findViewById(R.id.commentnum);
        sharenum = (TextView) question_num.findViewById(R.id.sharenum);
        commentAdapter = new CommentAdapter(getContext());
        listview.setAdapter(commentAdapter);
        moreComment = question_comment.findViewById(R.id.tv_more);
        moreComment.setOnClickListener(this);
    }

    @Override
    public Object paresJson(String json, int requestCode) {
        if(requestCode == 0x001) {
            return JsonUtil.getQuestion(json);
        } else if(requestCode == 0x002){
            return JsonUtil.getComment(json);
        } else {
            return null;
        }
    }

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
        Intent intent = new Intent(getContext(), CommentActivity.class);
        intent.putExtra("commentdatas", (Serializable) commmentDatas);
        startActivity(intent);
    }
}
