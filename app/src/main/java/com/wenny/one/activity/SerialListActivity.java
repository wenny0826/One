package com.wenny.one.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.wenny.one.R;
import com.wenny.one.adapter.SerialListGridAdapter;
import com.wenny.one.entity.ReadEntity;
import com.wenny.one.entity.SerialListEntity;
import com.wenny.one.util.Constants;
import com.wenny.one.util.JsonUtil;
import com.wenny.wennylibrary.base.BaseActivity;
import com.wenny.wennylibrary.util.RetrofitUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wenny on 2016/11/1.
 * 连载的每一集列表
 */

public class SerialListActivity extends BaseActivity implements RetrofitUtil.DownListener, AdapterView.OnItemClickListener {


    @Bind(R.id.gridView)
    public GridView gridView;
    @Bind(R.id.title)
    public TextView title;
    @Bind(R.id.isFriested)
    public TextView isFriested;

    private SerialListEntity serialListEntity;
    private SerialListGridAdapter listGridAdapter;
    private String serial_id;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_seriallist;
    }

    @Override
    protected void getIntentDatas() {
        Intent intent = getIntent();
        serial_id = intent.getStringExtra("Serial_id");
    }

    @Override
    protected void init() {
        listGridAdapter = new SerialListGridAdapter(this);
        gridView.setAdapter(listGridAdapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        new RetrofitUtil(this).setDownListener(this).downJson(String.format(Constants.SERIAL_LIST,serial_id),0x001);
    }

    @Override
    public Object paresJson(String json, int requestCode) {
        return JsonUtil.getSerialList(json);
    }

    @Override
    public void downSucc(Object object, int requestCode) {
        if(object != null){
            serialListEntity = (SerialListEntity) object;
            title.setText(serialListEntity.getData().getTitle());
            listGridAdapter.setDatas(serialListEntity.getData().getList());
            if(!serialListEntity.getData().getFinished().equals("0")){
                isFriested.setText("(完结)");
            } else {
                isFriested.setText("(未完结)");
            }
        }
    }

    @Override
    public void downFail(int requestCode) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SerialListEntity.DataBean.ListBean listBean = (SerialListEntity.DataBean.ListBean) listGridAdapter.getItem(position);
        Intent intent = new Intent(this,SerialInfoActivity.class);
        intent.putExtra("serialid",listBean.getId());
        intent.putExtra("serial_id",listBean.getSerial_id());
        startActivity(intent);
    }
}
