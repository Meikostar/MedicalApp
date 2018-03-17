package com.canplay.medical.mvp.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.medical.R;


/**
 * Created by mykar on 17/4/12.
 */
public class PressRecordkViewHolder extends RecyclerView.ViewHolder {


    public TextView tvTime;
    public TextView tvTimeDeal;
    public TextView one;
    public TextView two;
    public TextView three;
    public View line1;
    public View line2;
    public ImageView iv_cyc;


    public PressRecordkViewHolder(View itemView) {
        super(itemView);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        tvTimeDeal = (TextView) itemView.findViewById(R.id.tv_time_deal);
        one = (TextView) itemView.findViewById(R.id.tv_one);
        two = (TextView) itemView.findViewById(R.id.tv_two);
        three = (TextView) itemView.findViewById(R.id.tv_three);
        iv_cyc = (ImageView) itemView.findViewById(R.id.iv_cyc);
        line1 =  itemView.findViewById(R.id.line1);
        line2 =  itemView.findViewById(R.id.line2);


    }

}
