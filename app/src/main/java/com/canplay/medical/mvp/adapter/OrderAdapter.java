package com.canplay.medical.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.canplay.medical.R;
import com.canplay.medical.bean.ORDER;
import com.canplay.medical.util.TextUtil;
import com.canplay.medical.util.TimeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<ORDER> list;

    public OrderAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public interface addListener {
        void getItem( String total);
    }

    public List<ORDER> getDatas(){
        return list;
    }
    private Map<Integer, Integer> map = new HashMap<>();
    private int type;
    private int state=-1;
    public void setData(List<ORDER> list,int type) {
        this.list = list;
        this.type = type;
        notifyDataSetChanged();
    }
    public void setState(int state) {
        this.state = state;
        notifyDataSetChanged();
    }
    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();
    }
    private double totalMoney;
    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
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
        final ViewHolder holder;
        if (view == null) {

            view = LayoutInflater.from(mContext).inflate(R.layout.order_detail_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }



        return view;


    }

    //0待接单，1待结账 2已完成，4已撤销
    public class ResultViewHolder {

        TextView name;
        TextView tv_count;

    }

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    private ClickListener listener;

    public interface ClickListener {
        void clickListener(int type, String id);
    }



  static   class ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_detail)
        TextView tvDetail;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_less)
        ImageView tvLess;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_counts)
        TextView tvCounts;
        @BindView(R.id.tv_add)
        ImageView tvAdd;
        @BindView(R.id.ll_editor)
        LinearLayout llEditor;
        @BindView(R.id.tv_remark)
        TextView tvRemark;
        @BindView(R.id.ll_remark)
        LinearLayout llRemark;
        @BindView(R.id.line)
        View line;
       @BindView(R.id.lines)
        View lines;
       @BindView(R.id.liness)
       View liness;
       @BindView(R.id.tv_orderno)
        TextView tv_orderno;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.ll_total)
        LinearLayout llTotal;
       @BindView(R.id.ll_order)
       LinearLayout ll_order;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
