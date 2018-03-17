package com.canplay.medical.mvp.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.medical.R;
import com.canplay.medical.util.DensityUtil;


/**
 * Created by mykar on 17/4/12.
 */
public class DishesViewHolder extends RecyclerView.ViewHolder  {

    public ImageView img;
    public TextView tvName;
    public TextView tvPrice;
    public TextView typeName;
    public CardView card;
    public LinearLayout ll_bg;
    public DishesViewHolder(View itemView) {
        super(itemView);
        img= (ImageView) itemView.findViewById(R.id.iv_img);
        tvName= (TextView) itemView.findViewById(R.id.tv_name);
        tvPrice= (TextView) itemView.findViewById(R.id.tv_price);

        card= (CardView) itemView.findViewById(R.id.card);

    }
    public void messureHeight(Context mContext){

        ViewGroup.LayoutParams goods_params=card.getLayoutParams();

        int width=(int) DensityUtil.getWidth(mContext)/2;

        goods_params.width=width-DensityUtil.dip2px(mContext,20);

        goods_params.height=DensityUtil.dip2px(mContext,160);

        card.setLayoutParams(goods_params);
    }
}
