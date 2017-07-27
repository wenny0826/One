package com.wenny.one.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wenny.one.R;
import com.wenny.one.entity.CommentEntity;
import com.wenny.one.util.TimeUtil;
import com.wenny.wennylibrary.adapter.AbsAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wenny on 2016/10/31.
 * 评论的适配器
 */

public class CommentAdapter extends AbsAdapter<CommentEntity> implements View.OnClickListener {

    private Context context;
    public CommentAdapter(Context context) {
        super(context, R.layout.item_comment);
        this.context = context;
    }

    @Override
    public void bindView(ViewHolder viewHolder, CommentEntity data, int layoutType, int position) {
        if(layoutType == 0){
            viewHolder.setTextView(R.id.user_name,data.getUser().getUser_name());
            viewHolder.setTextView(R.id.create_time, TimeUtil.getDate(data.getCreated_at()));
            TextView score = (TextView) viewHolder.getView(R.id.score);
            if(data.getScore() != null){
                if(data.getScore().equals("null")){
                    score.setVisibility(View.INVISIBLE);
                } else {
                    score.setText(data.getScore() + "");
                }
            } else {
                score.setVisibility(View.INVISIBLE);
            }
            viewHolder.setTextView(R.id.praisenum,data.getPraisenum() + "");
            final TextView content = (TextView) viewHolder.getView(R.id.tv_content);
            final TextView expansion = (TextView) viewHolder.getView(R.id.expansion);
            content.setText(data.getContent());
            content.post(new Runnable() {
                @Override
                public void run() {
                    int lines =  content.getLineCount();
                    int maxlines = content.getMaxLines();
                    if(lines > maxlines){
                        expansion.setVisibility(View.VISIBLE);
                        expansion.setTag(content);
                    } else {
                        expansion.setVisibility(View.GONE);
                    }
                }
            });
            LinearLayout user_ll = (LinearLayout) viewHolder.getView(R.id.user_ll);
            CircleImageView user_head = (CircleImageView) viewHolder.getView(R.id.user_head);
            Glide.with(user_head.getContext()).load(data.getUser().getWeb_url()).placeholder(R.drawable.msg_head).diskCacheStrategy(DiskCacheStrategy.ALL).into(user_head);
            user_ll.setTag(data.getUser());
            user_ll.setOnClickListener(this);
            View view = viewHolder.getView(R.id.line);
            if(position == getCount()-1){
                view.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int LayoutType(int position, CommentEntity commentEntity, List<CommentEntity> datas) {
        return 0;
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.user_ll:
                CommentEntity.UserBean userBean = (CommentEntity.UserBean) v.getTag();
                Toast.makeText(context,userBean.getUser_name(),Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
