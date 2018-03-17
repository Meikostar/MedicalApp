package com.canplay.medical.mvp.adapter.recycle;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.canplay.medical.R;
import com.canplay.medical.bean.ORDER;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by mykar on 17/4/12.
 */
public class HomeDoctorRecycleAdapter extends BaseRecycleViewAdapter {

    private Context context;

    public HomeDoctorRecycleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_doctor_item, null);

        return new OrderMangerViewHolder(view);
    }
//0待接单，1待结账 2已完成，4已撤销
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        OrderMangerViewHolder holders = (OrderMangerViewHolder) holder;
      final ORDER data= (ORDER) datas.get(position);

    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (datas != null && datas.size() > 0) {
            count = datas.size();
        }

        return count;
    }
    public static class OrderMangerViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.fl_bg)
        FrameLayout flBg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_number)
        TextView tv_number;
        @BindView(R.id.card)
        CardView cardView;
        public OrderMangerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
    public void setClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
    public OnItemClickListener listener;
    public interface  OnItemClickListener{
        void clickListener(int poiston,String id);
    }
}
