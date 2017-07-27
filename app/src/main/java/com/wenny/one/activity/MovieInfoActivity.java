
package com.wenny.one.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wenny.one.R;
import com.wenny.one.adapter.CommentAdapter;
import com.wenny.one.entity.CommentEntity;
import com.wenny.one.entity.MovieDetail;
import com.wenny.one.entity.MovieStoryEntity;
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
 * Created by wenny on 2016/10/31.
 */

public class MovieInfoActivity extends BaseActivity implements RetrofitUtil.DownListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    @Bind(R.id.tv_title)
    public TextView tv_title;
    @Bind(R.id.author_head)
    public CircleImageView author_head;
    @Bind(R.id.iv_cover)
    public ImageView iv_cover;
    @Bind(R.id.movie_author)
    public TextView movie_author;
    @Bind(R.id.create_time)
    public TextView create_time;
    @Bind(R.id.story_title)
    public TextView story_title;
    @Bind(R.id.webView)
    public WebView webView;
    @Bind(R.id.title)
    public TextView title;
    @Bind(R.id.movie_rg)
    public RadioGroup movie_rg;
    @Bind(R.id.gross)
    public TableLayout gross;
    @Bind(R.id.photos)
    public LinearLayout photos;
    @Bind(R.id.plot)
    public TextView plot;
    @Bind(R.id.praisenum)
    public CheckBox praisenum;
    @Bind(R.id.movie_comment)
    public View movie_comment;
    @Bind(R.id.photos_hsv)
    public HorizontalScrollView photos_hsv;

    private String movieId;
    private ListViewInScr listViewInScr;
    private CommentAdapter commentAdapter;
    private List<List<CommentEntity>> commentdatas;
    private View moreComment;

    @Bind(R.id.loading)
    public View loading;
    private AnimationDrawable animationDrawable;
    private MovieDetail movieDetail;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_movie_info;
    }


    @Override
    protected void getIntentDatas() {
        Intent intent = getIntent();
        movieId = intent.getStringExtra("movieId");
    }

    @Override
    protected void init() {
        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);

        movie_rg.setOnCheckedChangeListener(this);
        listViewInScr = (ListViewInScr) movie_comment.findViewById(R.id.listview);
        commentAdapter = new CommentAdapter(this);
        listViewInScr.setAdapter(commentAdapter);
        commentdatas = new ArrayList<>();

        moreComment = movie_comment.findViewById(R.id.tv_more);
        moreComment.setOnClickListener(this);
        iv_cover.setOnClickListener(this);
    }

    @Override
    protected void loadDatas() {
        new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.MOVIE_STORY, movieId), 0x001);
        new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.MOVIE_DETAIL, movieId), 0x002);
        //下载评论
        new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.COMMENT,Constants.MOVIE,movieId), 0x003);
    }

    @Override
    public Object paresJson(String json, int requestCode) {
        if (requestCode == 0x001) {
            return JsonUtil.getMovieStory(json);
        } else if (requestCode == 0x002) {
            return JsonUtil.getMovieDetail(json);
        } else if(requestCode == 0x003){
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
                    List<MovieStoryEntity> movieStoryEntities = (List<MovieStoryEntity>) object;
                    MovieStoryEntity datas = movieStoryEntities.get(0);
                    Glide.with(author_head.getContext()).load(datas.getUser().getWeb_url()).placeholder(R.drawable.msg_head).diskCacheStrategy(DiskCacheStrategy.ALL).into(author_head);
                    movie_author.setText(datas.getUser().getUser_name());
                    create_time.setText(TimeUtil.getDate(datas.getInput_date()));
                    story_title.setText(datas.getTitle());

                    WebViewUtil.subWebView(webView,datas.getContent());
                    praisenum.setText(datas.getPraisenum() + "");
                    animationDrawable.stop();
                    loading.setVisibility(View.GONE);
                    break;
                case 0x002:
                    movieDetail = (MovieDetail) object;
                    tv_title.setText(movieDetail.getTitle());
                    Glide.with(iv_cover.getContext()).load(movieDetail.getDetailcover()).placeholder(R.drawable.movie_placeholder_0).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_cover);
                    //循环添加剧照
                    List<String> photo = movieDetail.getPhoto();
                    for (int i = 0; i < photo.size(); i++) {
                        ImageView iv = new ImageView(MovieInfoActivity.this);
                        iv.setScaleType(ImageView.ScaleType.FIT_XY);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(400, 400);
                        lp.setMargins(4,4,4,4);
                        Glide.with(iv.getContext()).load(photo.get(i)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.default_music_cover).into(iv);
                        photos.addView(iv,lp);
                    }
                    plot.setText(movieDetail.getInfo());
                    break;
                case 0x003:
                    List<CommentEntity> commentEntities = (List<CommentEntity>) object;
                    commentdatas = JsonUtil.getCommentList(commentEntities);
                    if (commentEntities.size() > 0) {
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_gross:
                title.setText("一个•电影表");
                gross.setVisibility(View.VISIBLE);
                photos_hsv.setVisibility(View.GONE);
                plot.setVisibility(View.GONE);
                break;
            case R.id.rb_still:
                title.setText("剧照");
                gross.setVisibility(View.GONE);
                photos_hsv.setVisibility(View.VISIBLE);
                plot.setVisibility(View.GONE);
                break;
            case R.id.rb_plot:
                title.setText("影片信息");
                gross.setVisibility(View.GONE);
                photos_hsv.setVisibility(View.GONE);
                plot.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public boolean isOpenStatus() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_cover:
                Intent intent1 = new Intent(this, PlayerActivity.class);
                intent1.putExtra("movieDetail", movieDetail);
                startActivity(intent1);
                break;
            case R.id.tv_more:
                Intent intent = new Intent(this, CommentActivity.class);
                intent.putExtra("commentdatas", (Serializable) commentdatas);
                startActivity(intent);
                break;
        }
    }
}
