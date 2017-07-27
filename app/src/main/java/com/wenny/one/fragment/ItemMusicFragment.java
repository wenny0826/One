package com.wenny.one.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wenny.one.R;
import com.wenny.one.activity.CommentActivity;
import com.wenny.one.adapter.CommentAdapter;
import com.wenny.one.base.AppContext;
import com.wenny.one.entity.CommentEntity;
import com.wenny.one.entity.MusicEntity;
import com.wenny.one.entity.PlayEntity;
import com.wenny.one.player.MusicService;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.one.util.TimeUtil;
import com.wenny.one.util.WebViewUtil;
import com.wenny.wennylibrary.base.BaseFragment;
import com.wenny.wennylibrary.util.RetrofitUtil;
import com.wenny.wennylibrary.widget.ListViewInScr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wenny on 2016/10/25.
 */

public class ItemMusicFragment extends BaseFragment implements RetrofitUtil.DownListener, View.OnClickListener {


    public static ItemMusicFragment getInstance(String id, int position) {
        ItemMusicFragment itemMusicFragment = new ItemMusicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("position", position);
        itemMusicFragment.setArguments(bundle);
        return itemMusicFragment;
    }

    @Override
    protected int getContentId() {
        return R.layout.item_music;
    }

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

    @Bind(R.id.music_comment)
    public View music_comment;
    @Bind(R.id.music_play)
    public ImageView music_play;

    private ListViewInScr listViewInScr;
    private CommentAdapter commentAdapter;
    private List<List<CommentEntity>> commentdatas;
    private View moreComment;


    @Bind(R.id.loading)
    public View loading;
    private AnimationDrawable animationDrawable;

    private MusicService musicService;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver broadcastReceiver;
    private int position;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        musicId = bundle.getString("id");
        position = bundle.getInt("position");
        musicService = AppContext.musicService;
        loadMusic();
    }

    @Override
    protected void init(View view) {

        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);

        listViewInScr = (ListViewInScr) music_comment.findViewById(R.id.listview);
        moreComment = music_comment.findViewById(R.id.tv_more);
        moreComment.setOnClickListener(this);

        listViewInScr.setFocusable(false);
        commentAdapter = new CommentAdapter(getContext());
        commentdatas = new ArrayList<>();
        listViewInScr.setAdapter(commentAdapter);
        music_cover = (ImageView) view.findViewById(R.id.music_cover);
        author_head = (CircleImageView) view.findViewById(R.id.author_head);

        music_author = (TextView) view.findViewById(R.id.music_author);
        author_dec = (TextView) view.findViewById(R.id.author_dec);
        music_title = (TextView) view.findViewById(R.id.music_title);
        musicinfo_title = (TextView) view.findViewById(R.id.musicinfo_title);
        music_commentnum = (TextView) view.findViewById(R.id.music_commentnum);
        music_sharenum = (TextView) view.findViewById(R.id.music_sharenum);
        charge_edt = (TextView) view.findViewById(R.id.charge_edt);
        music_maketime = (TextView) view.findViewById(R.id.music_maketime);

        music_rg = (RadioGroup) view.findViewById(R.id.music_rg);
        music_praisenum = (CheckBox) view.findViewById(R.id.music_praisenum);

        music_story = (LinearLayout) view.findViewById(R.id.music_story);
        music_lycir = (LinearLayout) view.findViewById(R.id.music_lycir);
        music_about = (LinearLayout) view.findViewById(R.id.music_about);

        music_story_title = (TextView) view.findViewById(R.id.music_story_title);
        music_story_author = (TextView) view.findViewById(R.id.music_story_author);
        tv_music_story = (WebView) view.findViewById(R.id.tv_music_story);
        tv_music_lyric = (TextView) view.findViewById(R.id.tv_music_lyric);
        tv_music_about = (TextView) view.findViewById(R.id.tv_music_about);


//            scrollView.scrollTo(0,200);
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
        music_play.setOnClickListener(this);
    }

    private void loadMusic() {
        //根据id下载详情
        new RetrofitUtil(getContext()).setDownListener(this).downJson(String.format(Constants.CONTENT, Constants.MUSIC, musicId), 0x001);
        new RetrofitUtil(getContext()).setDownListener(this).downJson(String.format(Constants.COMMENT, Constants.MUSIC, musicId), 0x002);
    }

    @Override
    public Object paresJson(String json, int requestCode) {
        if (requestCode == 0x001) {
            return JsonUtil.getMusicEntity(json);
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

                    PlayEntity playEntity = new PlayEntity(musicEntity.getData().getId(),
                            musicEntity.getData().getTitle(), musicEntity.getData().getAuthor().getUser_name(),
                            musicEntity.getData().getMusic_id(), "music");
                    AppContext.playMusicList.set(position, playEntity);
                    break;
                case 0x002:
                    List<CommentEntity> commentEntities = (List<CommentEntity>) object;
                    commentdatas = JsonUtil.getCommentList(commentEntities);
                    if (commentdatas.size() > 0) {
                        commentAdapter.setDatas(commentdatas.get(0));
                    }

                    break;
            }

        }
    }

    @Override
    public void downFail(int requestCode) {

    }

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

    /**
     * 点击进入更多评论
     *
     * @param v
     */
    private boolean thisisPlay;
    private boolean isPlay;
    private int index = -1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.music_play:
                ImageView iv = (ImageView) v;
                Log.d("print", "onClick:   thisisPlay " + thisisPlay);


                if (thisisPlay) {
                    musicService.stop();
                    iv.setImageResource(R.drawable.selector_play);
                    thisisPlay = false;

                } else {
                    musicService.setPlayEntities(AppContext.playMusicList);
                    musicService.playPosition(position);
                    iv.setImageResource(R.drawable.selector_pause);
                    thisisPlay = true;
                }
                break;
            case R.id.tv_more:
                Intent intent = new Intent(getContext(), CommentActivity.class);
                intent.putExtra("commentdatas", (Serializable) commentdatas);
                startActivity(intent);
                break;
        }
    }
}
