package com.wenny.one.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wenny.one.R;
import com.wenny.one.entity.ReadEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenny on 2016/11/3.
 */

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.MyViewHolder> {

    private Context context;
    private List<ReadEntity.DataBean.QuestionBean> questionBeen;

    private OnItemClickListener onItemClickListener;

    public QuestionListAdapter(Context context) {
        this.context = context;
        questionBeen = new ArrayList<>();
    }

    public void setQuestionBeen(List<ReadEntity.DataBean.QuestionBean> questionBeen) {
        this.questionBeen = questionBeen;
        this.notifyDataSetChanged();
    }

    public ReadEntity.DataBean.QuestionBean getItemData(int position){
        return questionBeen.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.question_title.setText(questionBeen.get(position).getQuestion_title());
        holder.question_author.setText(questionBeen.get(position).getAnswer_title());
        holder.question_word.setText(questionBeen.get(position).getAnswer_content());
    }

    @Override
    public int getItemCount() {
        return questionBeen.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView question_title,question_author,question_word;

        public MyViewHolder(final View itemView) {
            super(itemView);
            question_word = (TextView) itemView.findViewById(R.id.question_word);
            question_title = (TextView) itemView.findViewById(R.id.question_title);
            question_author = (TextView) itemView.findViewById(R.id.question_author);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(itemView,getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
