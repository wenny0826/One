package com.wenny.one.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wenny.one.R;
import com.wenny.one.adapter.ListAdapter;
import com.wenny.one.util.Constants;
import com.wenny.one.util.TimeUtil;
import com.wenny.wennylibrary.base.BaseActivity;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wenny on 2016/11/3.
 */

public class ReadListActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener {

    @Bind(R.id.read_rg)
    public RadioGroup read_rg;
    @Bind(R.id.listview)
    public ListView listview;
    private ListAdapter listAdapter;

    private List<String> essayTime = TimeUtil.getListTime("2012-10-4");
    private List<String> essayTimeShow = TimeUtil.getListTimeShow(essayTime);

    private List<String> serialTime = TimeUtil.getListTime("2016-1-1");
    private List<String > serialTimeShow = TimeUtil.getListTimeShow(serialTime);

    private List<String> questionTime = TimeUtil.getListTime("2012-10-6 ");
    private List<String> questionTimeShow = TimeUtil.getListTimeShow(questionTime);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_read_list;
    }

    @Override
    protected void init() {
        listAdapter = new ListAdapter(this);
        listview.setAdapter(listAdapter);
        read_rg.setOnCheckedChangeListener(this);
        read_rg.getChildAt(0).performClick();
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_essay:
                listAdapter.setDatas(essayTimeShow);
                break;
            case R.id.rb_serial:
                listAdapter.setDatas(serialTimeShow);
                break;
            case R.id.rb_question:
                listAdapter.setDatas(questionTimeShow);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,HomeMothActivity.class);
        switch (read_rg.getCheckedRadioButtonId()){
            case R.id.rb_essay:
                intent.putExtra("time",essayTime.get(essayTime.size() - position -1));
                intent.putExtra("timeShow",essayTimeShow.get(position));
                intent.putExtra("type", Constants.ESSAY);
                break;
            case R.id.rb_serial:
                intent.putExtra("time",serialTime.get(serialTime.size() - position -1));
                intent.putExtra("timeShow",serialTimeShow.get(position));
                intent.putExtra("type",Constants.SREIALCONTENT);
                break;
            case R.id.rb_question:
                intent.putExtra("time",questionTime.get(questionTime.size() - position -1));
                intent.putExtra("timeShow",questionTimeShow.get(position));
                intent.putExtra("type",Constants.QUESTION);
                break;
        }
        startActivity(intent);
    }
}
