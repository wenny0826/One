package com.wenny.one.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wenny.one.R;
import com.wenny.one.entity.SerialListEntity;
import com.wenny.wennylibrary.adapter.AbsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenny on 2016/11/1.
 */

public class SerialListGridAdapter extends BaseAdapter {

    private Context context;
    private List<SerialListEntity.DataBean.ListBean> datas;

    public SerialListGridAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void setDatas(List<SerialListEntity.DataBean.ListBean> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_seriallist,null);
            viewHolder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            convertView.setTag(viewHolder);
        }
        viewHolder.tv_num.setText(datas.get(position).getNumber());
        return convertView;
    }
    public class ViewHolder{
        TextView tv_num;
    }
}
