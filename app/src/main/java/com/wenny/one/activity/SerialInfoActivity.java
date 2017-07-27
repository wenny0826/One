package com.wenny.one.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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
import com.wenny.one.entity.CommentEntity;
import com.wenny.one.entity.SerialEntity;
import com.wenny.one.entity.SerialListEntity;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.one.util.WebViewUtil;
import com.wenny.wennylibrary.base.BaseActivity;
import com.wenny.wennylibrary.util.RetrofitUtil;
import com.wenny.wennylibrary.widget.ListViewInScr;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wenny on 2016/11/1.
 * 连载的详情页
 */

public class SerialInfoActivity extends BaseActivity implements RetrofitUtil.DownListener, View.OnClickListener {

    @Bind(R.id.contentView)
    public View contentView;

    private CircleImageView author_head;
    private TextView user_name;
    private TextView maketime;
    private TextView title;
    private ImageView serial_list;
    private WebView content;
    private TextView charge_edt;

    private View author_info;
    private View searial_comment;
    private View serial_num;

    private CircleImageView user_head;
    private TextView read_author;
    private TextView tv_desc;
    private TextView wb_name;

    private ListViewInScr listview;

    private CheckBox praisenum;
    private TextView commentnum;
    private TextView sharenum;

//    private SerialListEntity.DataBean.ListBean listBean;
    private SerialEntity serialEntity;
    private List<List<CommentEntity>> commentDatas;
    private CommentAdapter commentAdapter;
    private int commentPage = 0;

    @Bind(R.id.loading)
    public View loading;
    private AnimationDrawable animationDrawable;

    private String serialid;
    private String serial_id;

    private View moreComment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_serialinfo;
    }

    @Override
    protected void init() {
        animationDrawable = JsonUtil.loading(loading, R.id.iv_loading);

        author_head = (CircleImageView) contentView.findViewById(R.id.author_head);
        user_name = (TextView) contentView.findViewById(R.id.user_name);
        maketime = (TextView) contentView.findViewById(R.id.maketime);
        title = (TextView) contentView.findViewById(R.id.title);
        serial_list = (ImageView) contentView.findViewById(R.id.serial_list);

        content = (WebView) contentView.findViewById(R.id.content);
        charge_edt = (TextView) contentView.findViewById(R.id.charge_edt);

        author_info = contentView.findViewById(R.id.author_info);
        searial_comment = contentView.findViewById(R.id.searial_comment);
        serial_num = contentView.findViewById(R.id.serial_num);

        user_head = (CircleImageView) author_info.findViewById(R.id.author_head);
        read_author = (TextView) author_info.findViewById(R.id.read_author);
        tv_desc = (TextView) author_info.findViewById(R.id.tv_desc);
        wb_name = (TextView) author_info.findViewById(R.id.wb_name);

        listview = (ListViewInScr) searial_comment.findViewById(R.id.listview);

        praisenum = (CheckBox) serial_num.findViewById(R.id.praisenum);
        commentnum = (TextView) serial_num.findViewById(R.id.commentnum);
        sharenum = (TextView) serial_num.findViewById(R.id.sharenum);

        content.setFocusable(false);
        listview.setFocusable(false);
        wb_name.setVisibility(View.GONE);
        commentAdapter = new CommentAdapter(this);
        listview.setAdapter(commentAdapter);
        serial_list.setOnClickListener(this);

        moreComment = searial_comment.findViewById(R.id.tv_more);
        moreComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SerialInfoActivity.this, CommentActivity.class);
                intent.putExtra("commentdatas", (Serializable) commentDatas);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void getIntentDatas() {
        Intent intent = getIntent();
        serialid = intent.getStringExtra("serialid");
        serial_id = intent.getStringExtra("serial_id");
    }

    @Override
    protected void loadDatas() {
        new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.READ_INFO,Constants.SREIALCONTENT,serialid),0x001);
        new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.COMMENT,"serial",serialid),0x002);
    }

    @Override
    public boolean isOpenStatus() {
        return false;
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
                    maketime.setText(serialEntity.getMaketime());
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
                    commentAdapter.setDatas(commentDatas.get(commentPage));
                    break;
            }
        }
    }

    @Override
    public void downFail(int requestCode) {

    }

    public void onClick(View view){
        Intent intent = new Intent(this, SerialListActivity.class);
        intent.putExtra("Serial_id",serial_id);
        startActivity(intent);
    }
}
