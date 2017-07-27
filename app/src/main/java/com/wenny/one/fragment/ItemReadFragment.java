package com.wenny.one.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wenny.one.R;
import com.wenny.one.activity.ReadInfoActivity;
import com.wenny.one.entity.ReadEntity;
import com.wenny.one.util.Constants;
import com.wenny.wennylibrary.base.BaseFragment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wenny on 2016/10/25.
 */

public class ItemReadFragment extends BaseFragment implements View.OnClickListener {

    public static ItemReadFragment getInstance(ReadEntity readEntity,int position){
        ItemReadFragment itemReadFragment = new ItemReadFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("readEntity",readEntity);
        bundle.putInt("position",position);
        itemReadFragment.setArguments(bundle);
        return itemReadFragment;
    }


    @Override
    protected int getContentId() {
        return R.layout.item_read;
    }

    ReadEntity.DataBean.EssayBean essayBean;
    ReadEntity.DataBean.SerialBean serialBean;
    ReadEntity.DataBean.QuestionBean questionBean;
    ReadEntity readEntity;
    int position;
    List<ReadEntity.DataBean.EssayBean> essayBeanList;
    List<ReadEntity.DataBean.SerialBean> serialBeanList;
    List<ReadEntity.DataBean.QuestionBean> questionBeanList;

    private LinearLayout question_ll,essay_ll,serial_ll;
    private TextView essay_title,essay_author,essay_word;
    private TextView serial_title,serial_author,serial_word;
    private TextView question_title,question_author,question_word;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        readEntity = (ReadEntity) bundle.getSerializable("readEntity");
        position = bundle.getInt("position");

        essayBeanList = readEntity.getData().getEssay();
        serialBeanList = readEntity.getData().getSerial();
        questionBeanList = readEntity.getData().getQuestion();

        essayBean = essayBeanList.get(position);
        serialBean = serialBeanList.get(position);
        questionBean = questionBeanList.get(position);
        showDatas();
    }

    @Override
    protected void init(View view) {

        question_ll = (LinearLayout) view.findViewById(R.id.question_ll);
        essay_ll = (LinearLayout) view.findViewById(R.id.essay_ll);
        serial_ll = (LinearLayout) view.findViewById(R.id.serial_ll);

        essay_title = (TextView) view.findViewById(R.id.essay_title);
        essay_author = (TextView) view.findViewById(R.id.essay_author);
        essay_word = (TextView) view.findViewById(R.id.essay_word);

        serial_title = (TextView) view.findViewById(R.id.serial_title);
        serial_author = (TextView) view.findViewById(R.id.serial_author);
        serial_word = (TextView) view.findViewById(R.id.serial_word);

        question_title = (TextView) view.findViewById(R.id.question_title);
        question_author = (TextView) view.findViewById(R.id.question_author);
        question_word = (TextView) view.findViewById(R.id.question_word);

    }

    @Override
    protected void bindListener() {
        essay_ll.setOnClickListener(this);
        question_ll.setOnClickListener(this);
        serial_ll.setOnClickListener(this);
    }

    private void showDatas() {
        question_author.setText(questionBean.getAnswer_title());
        question_title.setText(questionBean.getQuestion_title());
        question_word.setText(questionBean.getAnswer_content());

        essay_author.setText(essayBean.getAuthor().get(0).getUser_name());
        essay_title.setText(essayBean.getHp_title());
        essay_word.setText(essayBean.getGuide_word());

        serial_author.setText(serialBean.getAuthor().getUser_name());
        serial_title.setText(serialBean.getTitle());
        serial_word.setText(serialBean.getExcerpt());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), ReadInfoActivity.class);
        switch (v.getId()){
            case R.id.essay_ll:
                intent.putExtra("type",Constants.ESSAY);
                intent.putExtra("essayBeanList", (Serializable) essayBeanList);
                break;
            case R.id.serial_ll:
                intent.putExtra("type",Constants.SREIALCONTENT);
                intent.putExtra("serialBeanList", (Serializable) serialBeanList);
                break;
            case R.id.question_ll:
                intent.putExtra("type",Constants.QUESTION);
                intent.putExtra("questionBeanList", (Serializable) questionBeanList);
                break;
        }
        intent.putExtra("position",position);
        startActivity(intent);
    }
}
