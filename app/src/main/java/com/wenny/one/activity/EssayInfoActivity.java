package com.wenny.one.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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
import com.wenny.one.adapter.CommentAdapter;
import com.wenny.one.base.AppContext;
import com.wenny.one.entity.CommentEntity;
import com.wenny.one.entity.EssayEntity;
import com.wenny.one.player.MusicService;
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
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wenny on 2016/11/4.
 */

public class EssayInfoActivity extends BaseActivity implements RetrofitUtil.DownListener, View.OnClickListener {
    @Bind(R.id.contentView)
    public View contentView;


    private CircleImageView author_head;
    private TextView movie_author;
    public TextView create_time;
    public ImageView iv_essay_play;
    public TextView tv_essay_play;
    public TextView hp_title;
    public WebView hp_content;
    public TextView hp_author_introduce;
    public View author_info;
    public View essay_comment;
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


    private View loading;
    private AnimationDrawable animationDrawable;
    private String essayid;
    private MusicService musicService;

    private EssayEntity essayEntity;
    private int position;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_essay_info;
    }

    @Override
    protected void getIntentDatas() {
        Intent intent = getIntent();
        essayid = intent.getStringExtra("essayid");
        position = intent.getIntExtra("position",-1);
    }

    @Override
    protected void init() {
        musicService = AppContext.musicService;

        loading = contentView.findViewById(R.id.loading);
        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);

        author_head = (CircleImageView) contentView.findViewById(R.id.author_head);
        movie_author = (TextView) contentView.findViewById(R.id.movie_author);
        create_time = (TextView) contentView.findViewById(R.id.create_time);
        iv_essay_play = (ImageView) contentView.findViewById(R.id.iv_essay_play);
        hp_title = (TextView) contentView.findViewById(R.id.hp_title);
        tv_essay_play = (TextView) contentView.findViewById(R.id.tv_essay_play);
        hp_author_introduce = (TextView) contentView.findViewById(R.id.hp_author_introduce);
        hp_content = (WebView) contentView.findViewById(R.id.hp_content);
        author_info = contentView.findViewById(R.id.author_info);
        essay_comment = contentView.findViewById(R.id.essay_comment);
        essay_num = contentView.findViewById(R.id.essay_num);

        user_head = (CircleImageView) author_info.findViewById(R.id.author_head);
        read_author = (TextView) author_info.findViewById(R.id.read_author);
        wb_name = (TextView) author_info.findViewById(R.id.wb_name);
        listview = (ListViewInScr) essay_comment.findViewById(R.id.listview);

        praisenum = (CheckBox) essay_num.findViewById(R.id.praisenum);
        commentnum = (TextView) essay_num.findViewById(R.id.commentnum);
        sharenum = (TextView) essay_num.findViewById(R.id.sharenum);
        commentAdapter = new CommentAdapter(this);
        listview.setAdapter(commentAdapter);
        moreComment = essay_comment.findViewById(R.id.tv_more);

        moreComment.setOnClickListener(this);
        iv_essay_play.setOnClickListener(this);
    }

    @Override
    protected void loadDatas() {
        new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.READ_INFO, Constants.ESSAY, essayid), 0x001);
        new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.COMMENT, Constants.ESSAY, essayid), 0x002);

    }

    @Override
    public Object paresJson(String json, int requestCode) {
        if (requestCode == 0x001) {
            return JsonUtil.getEssay(json);
        } else if (requestCode == 0x002) {
            return JsonUtil.getComment(json);
        } else {
            return null;
        }
    }

    @Override
    public void downSucc(Object object, int requestCode) {
        if (object != null) {
            switch (requestCode) {
                case 0x001:
                    essayEntity = (EssayEntity) object;

                    WebViewUtil.subWebView(hp_content,essayEntity.getHp_content());

                    hp_content.setFocusable(false);
                    Glide.with(author_head.getContext()).load(essayEntity.getAuthor().get(0).getWeb_url()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.msg_head).into(author_head);
                    Glide.with(user_head.getContext()).load(essayEntity.getAuthor().get(0).getWeb_url()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.msg_head).into(user_head);
                    movie_author.setText(essayEntity.getAuthor().get(0).getUser_name());
                    create_time.setText(TimeUtil.getDate(essayEntity.getHp_makettime()));
                    hp_title.setText(essayEntity.getHp_title());

                    if(essayEntity.getAudio().equals("")){
                        iv_essay_play.setVisibility(View.INVISIBLE);
                        tv_essay_play.setVisibility(View.INVISIBLE);
                    }
                    hp_author_introduce.setText(essayEntity.getHp_author_introduce());
                    praisenum.setText(essayEntity.getPraisenum() + "");
                    commentnum.setText(essayEntity.getCommentnum() + "");
                    sharenum.setText(essayEntity.getSharenum() + "");

                    if (essayEntity.equals("")) {
                        iv_essay_play.setVisibility(View.INVISIBLE);
                        tv_essay_play.setVisibility(View.INVISIBLE);
                    }
                    animationDrawable.stop();
                    loading.setVisibility(View.GONE);
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
        switch (v.getId()) {
            case R.id.tv_more:
                Intent intent = new Intent(this, CommentActivity.class);
                intent.putExtra("commentdatas", (Serializable) commentDatas);
                startActivity(intent);
                break;
            case R.id.iv_essay_play:
                ImageView iv = (ImageView) v;
                Log.d("print", "onClick:   thisisPlay " + thisisPlay);

                if (thisisPlay) {
                    musicService.stop();
                    iv.setImageResource(R.drawable.selector_artical_play);
                    tv_essay_play.setText("开始");
                    thisisPlay = false;
                } else {
                    musicService.setPlayEntities(AppContext.playReadList1);
                    musicService.playPosition(position);
                    iv.setImageResource(R.drawable.selector_artical_pause);
                    thisisPlay = true;
                    tv_essay_play.setText("暂停");
                }
                break;
        }
    }
    @OnClick(R.id.play)
    public void musicPlay(View view){
        Intent intent = new Intent(this,MusicPlayActivity.class);
        startActivity(intent);
    }
}
