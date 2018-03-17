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
import com.canplay.medical.util.TextUtil;

import java.util.List;


public class EuipmentAdapter extends BaseAdapter {
    private Context mContext;
    private List<Euip> list;

    public EuipmentAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public interface ItemCliks{
        void getItem(Euip menu,int type);//type 1表示点击事件2 表示长按事件
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
        return list!=null?list.size():6;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.equip_item, parent, false);
            holder.name= (TextView) view.findViewById(R.id.tv_name);
            holder.tv_count= (TextView) view.findViewById(R.id.tv_number);
            holder.img= (ImageView) view.findViewById(R.id.iv_img);
            holder.ll_item= (LinearLayout) view.findViewById(R.id.ll_bg);
            view.setTag(holder);
        }else{
            holder = (ResultViewHolder) view.getTag();
        }
//        if(TextUtil.isNotEmpty(list.get(position).classifyName)){
//            holder.name.setText(list.get(position).classifyName);
//        }
//        if(TextUtil.isNotEmpty(list.get(position).sort)){
//            holder.tv_count.setText(list.get(position).sort);
//        }

         holder.ll_item.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 listener.getItem(null, 1);
             }
         });
         holder.ll_item.setOnLongClickListener(new View.OnLongClickListener() {
             @Override
             public boolean onLongClick(View v) {
                 listener.getItem(null, 2);
                 return true;
             }
         });
        return view;


    }

    public  class ResultViewHolder{

        TextView name;
        TextView tv_count;
        ImageView img;
        LinearLayout ll_item;

    }
}
