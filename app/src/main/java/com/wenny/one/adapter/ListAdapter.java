package com.wenny.one.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.wenny.one.R;
import com.wenny.wennylibrary.adapter.AbsAdapter;

import java.util.List;

/**
 * Created by wenny on 2016/11/2.
 */

public class ListAdapter extends AbsAdapter<String> {

    public ListAdapter(Context context) {
        super(context, R.layout.item_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, String data, int layoutType, int position) {
        viewHolder.setTextView(R.id.tv_time,data);
    }

    @Override
    public int LayoutType(int position, String s, List<String> strings) {
        return 0;
    }
}
