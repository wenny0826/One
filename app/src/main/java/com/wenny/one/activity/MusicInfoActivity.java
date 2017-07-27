package com.wenny.one.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wenny.one.R;
import com.wenny.one.adapter.CommentAdapter;
import com.wenny.one.base.AppContext;
import com.wenny.one.entity.CommentEntity;
import com.wenny.one.entity.MusicEntity;
import com.wenny.one.player.MusicService;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.one.util.TimeUtil;
import com.wenny.one.util.WebViewUtil;
import com.wenny.wennylibrary.base.BaseActivity;
import com.wenny.wennylibrary.util.RetrofitUtil;
import com.wenny.wennylibrary.widget.ListViewInScr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wenny on 2016/11/3.
 */

public class MusicInfoActivity extends BaseActivity implements View.OnClickListener, RetrofitUtil.DownListener {

    @Bind(R.id.musicView)
    public View musicView;

    private String musicId;
    private ImageView music_cover;
    private CircleImageView author_head;
    private TextView music_author, author_dec, music_title, musicinfo_title,
            music_commentnum, music_sharenum, charge_edt, music_maketime;
    private RadioGroup music_rg;
    private CheckBox music_praisenum;

    private LinearLayout music_story, music_lycir, music_about;

    private TextView music_story_title, music_story_author, tv_music_lyric, tv_music_about;
    private WebView tv_music_story;

    private View music_comment;

    private ListViewInScr listViewInScr;
    private CommentAdapter commentAdapter;
    private List<List<CommentEntity>> commentdatas;
    private View moreComment;

    private MusicService musicService;
    private int position;
    private ImageView music_play;
    @Bind(R.id.play)
    public ImageView play;


    private View loading;
    private AnimationDrawable animationDrawable;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_musicinfo;
    }

    @Override
    protected void getIntentDatas() {
        Intent intent = getIntent();
        musicId = intent.getStringExtra("musicid");
        position = intent.getIntExtra("position",-1);
    }

    @Override
    protected void init() {

        musicService = AppContext.musicService;

        music_comment = musicView.findViewById(R.id.music_comment);
        loading = musicView.findViewById(R.id.loading);

        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);

        listViewInScr = (ListViewInScr) music_comment.findViewById(R.id.listview);
        moreComment = music_comment.findViewById(R.id.tv_more);
        moreComment.setOnClickListener(this);
        play.setOnClickListener(this);

        listViewInScr.setFocusable(false);
        commentAdapter = new CommentAdapter(this);
        commentdatas = new ArrayList<>();
        listViewInScr.setAdapter(commentAdapter);
        music_cover = (ImageView) musicView.findViewById(R.id.music_cover);
        author_head = (CircleImageView) musicView.findViewById(R.id.author_head);

        music_author = (TextView) musicView.findViewById(R.id.music_author);
        author_dec = (TextView) musicView.findViewById(R.id.author_dec);
        music_title = (TextView) musicView.findViewById(R.id.music_title);
        musicinfo_title = (TextView) musicView.findViewById(R.id.musicinfo_title);
        music_commentnum = (TextView) musicView.findViewById(R.id.music_commentnum);
        music_sharenum = (TextView) musicView.findViewById(R.id.music_sharenum);
        charge_edt = (TextView) musicView.findViewById(R.id.charge_edt);
        music_maketime = (TextView) musicView.findViewById(R.id.music_maketime);

        music_rg = (RadioGroup) musicView.findViewById(R.id.music_rg);
        music_praisenum = (CheckBox) musicView.findViewById(R.id.music_praisenum);

        music_story = (LinearLayout) musicView.findViewById(R.id.music_story);
        music_lycir = (LinearLayout) musicView.findViewById(R.id.music_lycir);
        music_about = (LinearLayout) musicView.findViewById(R.id.music_about);

        music_story_title = (TextView) musicView.findViewById(R.id.music_story_title);
        music_story_author = (TextView) musicView.findViewById(R.id.music_story_author);
        tv_music_story = (WebView) musicView.findViewById(R.id.tv_music_story);
        tv_music_lyric = (TextView) musicView.findViewById(R.id.tv_music_lyric);
        tv_music_about = (TextView) musicView.findViewById(R.id.tv_music_about);
        music_play = (ImageView) musicView.findViewById(R.id.music_play);
        music_play.setOnClickListener(this);

        music_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_music_story:
                        music_story.setVisibility(View.VISIBLE);
                        music_lycir.setVisibility(View.GONE);
                        music_about.setVisibility(View.GONE);
                        musicinfo_title.setText("音乐故事");
                        break;
                    case R.id.rb_music_lyric:
                        music_story.setVisibility(View.GONE);
                        music_lycir.setVisibility(View.VISIBLE);
                        music_about.setVisibility(View.GONE);
                        musicinfo_title.setText("歌词");
                        break;
                    case R.id.rb_music_about:
                        music_story.setVisibility(View.GONE);
                        music_lycir.setVisibility(View.GONE);
                        music_about.setVisibility(View.VISIBLE);
                        musicinfo_title.setText("歌曲信息");
                        break;
                }
            }
        });
        tv_music_story.setFocusable(false);
    }

    @Override
    protected void loadDatas() {
        new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.CONTENT, Constants.MUSIC, musicId),0x001);
        new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.COMMENT,Constants.MUSIC,musicId), 0x002);
    }


    @Override
    public Object paresJson(String json, int requestCode) {
        if(requestCode == 0x001) {
            return JsonUtil.getMusicEntity(json);
        }else if(requestCode == 0x002){
            return JsonUtil.getComment(json);
        }else {
            return null;
        }
    }

    @Override
    public void downSucc(Object object, int requestCode) {
        if (object != null) {
            switch (requestCode){
                case 0x001:
                    MusicEntity musicEntity = (MusicEntity) object;
                    tv_music_about.setText(musicEntity.getData().getInfo());
                    tv_music_lyric.setText(musicEntity.getData().getLyric());

                    WebViewUtil.subWebView(tv_music_story,musicEntity.getData().getStory());
                    music_story_title.setText(musicEntity.getData().getStory_title());
                    music_story_author.setText(musicEntity.getData().getStory_author().getUser_name());

                    music_author.setText(musicEntity.getData().getAuthor().getUser_name());
                    author_dec.setText(musicEntity.getData().getAuthor().getDesc());

                    music_title.setText(musicEntity.getData().getTitle());
                    charge_edt.setText(musicEntity.getData().getCharge_edt());
                    music_maketime.setText(TimeUtil.getDate(musicEntity.getData().getMaketime()));
                    music_commentnum.setText(musicEntity.getData().getCommentnum() + "");
                    music_sharenum.setText(musicEntity.getData().getSharenum() + "");
                    music_praisenum.setText(musicEntity.getData().getPraisenum() + "");

                    Glide.with(music_cover.getContext()).load(musicEntity.getData().getCover()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.default_music_cover).into(music_cover);
                    Glide.with(author_head.getContext()).load(musicEntity.getData().getAuthor().getWeb_url()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.msg_head).into(author_head);

                    animationDrawable.stop();
                    loading.setVisibility(View.GONE);

                    break;
                case 0x002:
                    List<CommentEntity> commentEntities = (List<CommentEntity>) object;
                    commentdatas = JsonUtil.getCommentList(commentEntities);
                    commentAdapter.setDatas(commentdatas.get(0));
                    break;
            }

        }
    }
    @Override
    public void downFail(int requestCode) {
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
            music_play.setImageResource(R.drawable.selector_pause);
            thisisPlay = true;

        } else {
            music_play.setImageResource(R.drawable.selector_play);
            thisisPlay = false;

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.music_play:
                ImageView iv = (ImageView) v;

                if (thisisPlay) {
                    musicService.stop();
                    iv.setImageResource(R.drawable.selector_play);
                    thisisPlay = false;

                } else {
                    musicService.setPlayEntities(AppContext.playMusicList1);
                    musicService.playPosition(position);
                    iv.setImageResource(R.drawable.selector_pause);
                    thisisPlay = true;
                }
                break;
            case R.id.tv_more:
                Intent intent = new Intent(this, CommentActivity.class);
                intent.putExtra("commentdatas", (Serializable) commentdatas);
                startActivity(intent);
                break;
            case R.id.play:
                startActivity(new Intent(this,MusicPlayActivity.class));
                break;
        }
    }
}
