package com.canplay.medical.mvp.adapter.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.medical.R;


/**
 * Created by mykar on 17/4/12.
 */
public class HealtCenterHolder extends RecyclerView.ViewHolder  {

    public ImageView img;
    public TextView name;
    public TextView phone;
    public LinearLayout ll_bg;


    public HealtCenterHolder(View itemView) {
        super(itemView);
        img= (ImageView) itemView.findViewById(R.id.img);
        name= (TextView) itemView.findViewById(R.id.tv_name);
        phone= (TextView) itemView.findViewById(R.id.tv_number);
        ll_bg= (LinearLayout) itemView.findViewById(R.id.ll_bg);



    }

}
