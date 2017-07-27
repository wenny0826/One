package com.wenny.wennylibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by wenny on 2016/9/30.
 */

public class GridViewInList extends GridView {
    public GridViewInList(Context context) {
        this(context,null);
    }

    public GridViewInList(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GridViewInList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
