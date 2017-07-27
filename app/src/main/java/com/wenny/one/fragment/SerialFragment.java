package com.wenny.one.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wenny.one.R;
import com.wenny.one.activity.CommentActivity;
import com.wenny.one.activity.SerialListActivity;
import com.wenny.one.adapter.CommentAdapter;
import com.wenny.one.entity.CommentEntity;
import com.wenny.one.entity.EssayEntity;
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
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * Created by wenny on 2016/11/1.
 */

public class SerialFragment extends BaseFragment implements RetrofitUtil.DownListener, View.OnClickListener
{

    private SerialEntity serialEntity;

    public static SerialFragment getInstance(ReadEntity.DataBean.SerialBean serialBean){
        SerialFragment serialFragment = new SerialFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("serialBean",serialBean);
        serialFragment.setArguments(bundle);
        return serialFragment;
    }

    private ReadEntity.DataBean.SerialBean serialBean;
    @Bind(R.id.author_head)
    public CircleImageView author_head;
    @Bind(R.id.user_name)
    public TextView user_name;
    @Bind(R.id.maketime)
    public TextView maketime;
    @Bind(R.id.title)
    public TextView title;
    @Bind(R.id.serial_list)
    public ImageView serial_list;
    @Bind(R.id.charge_edt)
    public TextView charge_edt;
    @Bind(R.id.content)
    public WebView content;
    @Bind(R.id.author_info)
    public View author_info;
    @Bind(R.id.searial_comment)
    public View searial_comment;
    @Bind(R.id.serial_num)
    public View serial_num;

    private CircleImageView user_head;
    private TextView read_author;
    private TextView tv_desc;
    private TextView wb_name;

    private ListViewInScr listViewInScr;

    private CheckBox praisenum;
    private TextView commentnum;
    private TextView sharenum;

    private List<List<CommentEntity>> commentDatas;
    private CommentAdapter commentAdapter;
    private int commentPage = 0;
    private View moreComment;


    @Bind(R.id.loading)
    public View loading;
    private AnimationDrawable animationDrawable;

    @Override
    protected int getContentId() {
        return R.layout.fragment_serial;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        serialBean = (ReadEntity.DataBean.SerialBean) bundle.getSerializable("serialBean");
        new RetrofitUtil(getContext()).setDownListener(this).downJson(String.format(Constants.READ_INFO,Constants.SREIALCONTENT,serialBean.getId()),0x001);
        new RetrofitUtil(getContext()).setDownListener(this).downJson(String.format(Constants.COMMENT,"serial",serialBean.getId()),0x002);

    }

    @Override
    protected void init(View view) {

        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);

        super.init(view);
        user_head = (CircleImageView) author_info.findViewById(R.id.author_head);
        read_author = (TextView) author_info.findViewById(R.id.read_author);
        tv_desc = (TextView) author_info.findViewById(R.id.tv_desc);
        wb_name = (TextView) author_info.findViewById(R.id.wb_name);
        wb_name.setVisibility(View.GONE);

        listViewInScr = (ListViewInScr) searial_comment.findViewById(R.id.listview);

        praisenum = (CheckBox) serial_num.findViewById(R.id.praisenum);
        commentnum = (TextView) serial_num.findViewById(R.id.commentnum);
        sharenum = (TextView) serial_num.findViewById(R.id.sharenum);

        content.setFocusable(false);
        listViewInScr.setFocusable(false);
        commentAdapter = new CommentAdapter(getContext());
        listViewInScr.setAdapter(commentAdapter);
        moreComment = searial_comment.findViewById(R.id.tv_more);
        moreComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CommentActivity.class);
                intent.putExtra("commentdatas", (Serializable) commentDatas);
                startActivity(intent);
            }
        });
    }

    @Override
    public Object paresJson(String json, int requestCode) {
        if(requestCode == 0x001) {
            return JsonUtil.getSerial(json);
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
                    serialEntity = (SerialEntity) object;
                    Glide.with(user_head.getContext()).load(serialEntity.getAuthor().getWeb_url()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.msg_head).into(user_head);
                    Glide.with(author_head.getContext()).load(serialEntity.getAuthor().getWeb_url()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.msg_head).into(author_head);
                    user_name.setText(serialEntity.getAuthor().getUser_name());
                    maketime.setText(TimeUtil.getDate(serialEntity.getMaketime()));
                    title.setText(serialEntity.getTitle());
                    charge_edt.setText(serialEntity.getCharge_edt());

                    WebViewUtil.subWebView(content,serialEntity.getContent());

                    read_author.setText(serialEntity.getAuthor().getUser_name());
                    tv_desc.setText(serialEntity.getAuthor().getDesc());
                    praisenum.setText(serialEntity.getPraisenum() + "");
                    commentnum.setText(serialEntity.getCommentnum() + "");
                    sharenum.setText(serialEntity.getSharenum() + "");
                    animationDrawable.stop();
                    loading.setVisibility(View.GONE);
                    break;
                case 0x002:
                    List<CommentEntity> commentEntities = (List<CommentEntity>) object;
                        commentDatas = JsonUtil.getCommentList(commentEntities);
                    if(commentDatas.size() > 0) {
                        commentAdapter.setDatas(commentDatas.get(commentPage));
                    }
                    break;
            }
        }
    }

    @Override
    public void downFail(int requestCode) {

    }

    @OnClick(R.id.serial_list)
    public void onClick(View view){
        Intent intent = new Intent(getContext(), SerialListActivity.class);
        intent.putExtra("Serial_id",serialBean.getSerial_id());
        startActivity(intent);
    }
}
