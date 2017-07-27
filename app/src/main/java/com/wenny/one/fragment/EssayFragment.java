package com.wenny.one.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.wenny.one.adapter.CommentAdapter;
import com.wenny.one.base.AppContext;
import com.wenny.one.entity.CommentEntity;
import com.wenny.one.entity.EssayEntity;
import com.wenny.one.entity.PlayEntity;
import com.wenny.one.entity.ReadEntity;
import com.wenny.one.player.MusicService;
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
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wenny on 2016/11/1.
 */

public class EssayFragment extends BaseFragment implements RetrofitUtil.DownListener, View.OnClickListener {

    private EssayEntity essayEntity;

    public static EssayFragment getInstance(String  essayid,int position){
        EssayFragment essayFragment = new EssayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("essayid",essayid);
        bundle.putInt("position",position);
        essayFragment.setArguments(bundle);
        return essayFragment;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_essay;
    }
    @Bind(R.id.author_head)
    public CircleImageView author_head;
    @Bind(R.id.movie_author)
    public TextView movie_author;
    @Bind(R.id.create_time)
    public TextView create_time;
    @Bind(R.id.iv_essay_play)
    public ImageView iv_essay_play;
    @Bind(R.id.tv_essay_play)
    public TextView tv_essay_play;
    @Bind(R.id.hp_title)
    public TextView hp_title;
    @Bind(R.id.hp_content)
    public WebView hp_content;
    @Bind(R.id.hp_author_introduce)
    public TextView hp_author_introduce;
    @Bind(R.id.author_info)
    public View author_info;
    @Bind(R.id.essay_comment)
    public View essay_comment;
    @Bind(R.id.essay_num)
    public View essay_num;

    private CircleImageView user_head;
    private TextView read_author;
    private TextView wb_name;

    private ListViewInScr listview;

    private CheckBox praisenum;
    private TextView commentnum;
    private TextView sharenum;

    private List<List<CommentEntity>> commentDatas;
    private int commentPage = 0;
    private CommentAdapter commentAdapter;
    private View moreComment;

    private MusicService musicService;

    @Bind(R.id.loading)
    public View loading;
    private AnimationDrawable animationDrawable;
    private String essayid;
    private int position;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        essayid = bundle.getString("essayid");
        position = bundle.getInt("position");
        show();
    }

    private void show(){
        new RetrofitUtil(getContext()).setDownListener(this).downJson(String.format(Constants.READ_INFO,Constants.ESSAY,essayid),0x001);
        new RetrofitUtil(getContext()).setDownListener(this).downJson(String.format(Constants.COMMENT,Constants.ESSAY,essayid),0x002);
    }

    @Override
    protected void init(View view) {
        musicService = AppContext.musicService;

        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);

        user_head = (CircleImageView) author_info.findViewById(R.id.author_head);
        read_author = (TextView) author_info.findViewById(R.id.read_author);
        wb_name = (TextView) author_info.findViewById(R.id.wb_name);
        listview = (ListViewInScr) essay_comment.findViewById(R.id.listview);


        praisenum = (CheckBox) essay_num.findViewById(R.id.praisenum);
        commentnum = (TextView) essay_num.findViewById(R.id.commentnum);
        sharenum = (TextView) essay_num.findViewById(R.id.sharenum);
        commentAdapter = new CommentAdapter(getContext());
        listview.setAdapter(commentAdapter);
        moreComment = essay_comment.findViewById(R.id.tv_more);
        moreComment.setOnClickListener(this);
        iv_essay_play.setOnClickListener(this);
    }


    @Override
    protected void loadDatas() {
    }

    @Override
    public Object paresJson(String json, int requestCode) {
        if(requestCode == 0x001) {
            return JsonUtil.getEssay(json);
        } else if(requestCode == 0x002){
            return JsonUtil.getComment(json);
        }else {
            return null;
        }
    }

    @Override
    public void downSucc(Object object, int requestCode) {
        if(object != null){
            switch (requestCode){
                case 0x001:
                    essayEntity = (EssayEntity) object;

                    WebViewUtil.subWebView(hp_content,essayEntity.getHp_content());

                    hp_content.setFocusable(false);
                    Glide.with(author_head.getContext()).load(essayEntity.getAuthor().get(0).getWeb_url()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.msg_head).into(author_head);
                    Glide.with(user_head.getContext()).load(essayEntity.getAuthor().get(0).getWeb_url()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.msg_head).into(user_head);
                    movie_author.setText(essayEntity.getAuthor().get(0).getUser_name());
                    create_time.setText(TimeUtil.getDate(essayEntity.getHp_makettime()));
                    hp_title.setText(essayEntity.getHp_title());

                    hp_author_introduce.setText(essayEntity.getHp_author_introduce());
                    praisenum.setText(essayEntity.getPraisenum() + "");
                    commentnum.setText(essayEntity.getCommentnum() + "");
                    sharenum.setText(essayEntity.getSharenum() + "");
                    if(essayEntity.getAudio().equals("")){
                        iv_essay_play.setVisibility(View.INVISIBLE);
                        tv_essay_play.setVisibility(View.INVISIBLE);
                    }
                    animationDrawable.stop();
                    loading.setVisibility(View.GONE);
                    PlayEntity playEntity = new PlayEntity(essayEntity.getContent_id(),essayEntity.getHp_title(),essayEntity.getHp_author(),essayEntity.getAudio(),"essay");
                    AppContext.playReadList.set(position,playEntity);
                    break;
                case 0x002:
                    List<CommentEntity> commentEntities = (List<CommentEntity>) object;
                    commentDatas = JsonUtil.getCommentList(commentEntities);
                    commentAdapter.setDatas(commentDatas.get(commentPage));
                    break;
            }
        }
    }

    @Override
    public void downFail(int requestCode) {
        animationDrawable.stop();
        loading.setVisibility(View.GONE);
    }
    private boolean thisisPlay;
    private boolean isPlay;
    private int index = -1;

    @Override
    public void onStart() {
        super.onStart();
        index = musicService.getIndex();
        isPlay = musicService.isPlay();
        if (isPlay && index == position) {
            iv_essay_play.setImageResource(R.drawable.selector_artical_pause);
            tv_essay_play.setText("暂停");
            thisisPlay = true;
        } else {
            iv_essay_play.setImageResource(R.drawable.selector_artical_play);
            tv_essay_play.setText("开始");
            thisisPlay = false;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_essay_play:
                ImageView iv = (ImageView) v;
                Log.d("print", "onClick:   thisisPlay " + thisisPlay);

                if (thisisPlay) {
                    musicService.stop();
                    iv.setImageResource(R.drawable.selector_artical_play);
                    tv_essay_play.setText("开始");
                    thisisPlay = false;
                } else {
                    musicService.setPlayEntities(AppContext.playReadList);
                    musicService.playPosition(position);
                    iv.setImageResource(R.drawable.selector_artical_pause);
                    thisisPlay = true;
                    tv_essay_play.setText("暂停");
                }
                break;
            case R.id.tv_more:
                Intent intent = new Intent(getContext(), CommentActivity.class);
                intent.putExtra("commentdatas", (Serializable) commentDatas);
                startActivity(intent);
                break;


        }
    }
}
