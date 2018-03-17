package com.canplay.medical.mvp.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.canplay.medical.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by mykar on 17/4/12.
 */
public class HomeDoctorViewHolder extends RecyclerView.ViewHolder  {

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
    public HomeDoctorViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}
