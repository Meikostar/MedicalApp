package com.canplay.medical.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.medical.R;
import com.canplay.medical.bean.Euip;

import java.util.List;


public class RemindItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<Euip> list;

    public RemindItemAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public interface ItemCliks{
        void getItem(Euip menu, int type);//type 1表示点击事件2 表示长按事件
    }
    private ItemCliks listener;
    public void setClickListener(ItemCliks listener){
        this.listener=listener;
    }
    public void setData(List<Euip> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list!=null?list.size():2;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ResultViewHolder holder;
        if (view == null){
            holder = new ResultViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_remind_item, parent, false);
            holder.name= (TextView) view.findViewById(R.id.tv_name);
            holder.tvNumber= (TextView) view.findViewById(R.id.tv_number);
            holder.img= (ImageView) view.findViewById(R.id.iv_img);
            holder.tvCount= (TextView) view.findViewById(R.id.tv_count);

            view.setTag(holder);
        }else{
            holder = (ResultViewHolder) view.getTag();
        }

        return view;


    }

    public  class ResultViewHolder{

        TextView name;

        TextView tvNumber;
        TextView tvCount;
        ImageView img;
        LinearLayout ll_item;

    }
}
