package com.wenny.wennylibrary.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 打造万能适配器
 */
public abstract class AbsAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> datas;
    private int[] resids;
//    private List<ViewHolder> viewHolders;


    public AbsAdapter(Context context, int... resids) {
        this.context = context;
        datas = new ArrayList<>();
//        viewHolders = new ArrayList<>();
        this.resids = resids;
    }

    public void setDatas(List<T> datas){
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    public void addDatas(List<T> datas){
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void cleanDatas(){
        datas.clear();
        this.notifyDataSetChanged();
    }
    public List<T> getDatas(){
        return datas;
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
        int type = getItemViewType(position);
         if(convertView != null){
             viewHolder = (ViewHolder) convertView.getTag();
         } else {
                     convertView = LayoutInflater.from(context).inflate(resids[type],null);
                     viewHolder = new ViewHolder(convertView);
//                     viewHolders.add(viewHolder);
                     convertView.setTag(viewHolder);
         }
         bindView(viewHolder,datas.get(position),type,position);
        return convertView;
    }

    public abstract void bindView(ViewHolder viewHolder, T data ,int layoutType,int position);

    /**
     * 作用：缓存item中的控件对象，避免多次findViewById
     */
    public class ViewHolder{

        //类似于map
        SparseArray<View> sparseArray = new SparseArray<>();
        View layoutView;
        public ViewHolder(View LayoutView) {
            this.layoutView = LayoutView;
        }

        public View getView(int id){
            View view = sparseArray.get(id);
            if(view == null){
                view = layoutView.findViewById(id);
                sparseArray.put(id,view);
            }
            return view;
        }

        /**
         * 给TextView设置值
         * @param id
         * @param value
         * @return
         */
        public ViewHolder setTextView(int id,String value){
            TextView textView = (TextView) getView(id);
            textView.setText(value);
            return this;
        }

        public ViewHolder setImageView(int id,String url){
            ImageView imageView = (ImageView) getView(id);
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            return this;
        }
    }

    @Override
    public int getItemViewType(int position) {

        return  LayoutType(position,datas.get(position),datas);
    }

    @Override
    public int getViewTypeCount() {
        return resids.length;
    }

    public abstract int LayoutType(int position,T t,List<T> ts);
}
