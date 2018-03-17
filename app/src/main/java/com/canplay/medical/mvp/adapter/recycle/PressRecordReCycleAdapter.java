package com.canplay.medical.mvp.adapter.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.canplay.medical.R;
import com.canplay.medical.mvp.adapter.viewholder.PressRecordkViewHolder;
import com.canplay.medical.util.TimeUtil;


import static android.R.attr.data;

/**
 * Created by mykar on 17/4/12.
 */
public class PressRecordReCycleAdapter extends BaseRecycleViewAdapter {
    private Context context;

    public PressRecordReCycleAdapter(Context context, int type){

        this.context=context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.press_record_item, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new PressRecordkViewHolder(view);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            PressRecordkViewHolder holders = (PressRecordkViewHolder) holder;
            if(position!=0){
                holders.line1.setVisibility(View.VISIBLE);
            }else {
                holders.line1.setVisibility(View.GONE);

            }
        if(position%2==0){
            if(position!=0){
                holders.line1.setVisibility(View.VISIBLE);
            }
            holders.iv_cyc.setVisibility(View.VISIBLE);
        }else {

            holders.iv_cyc.setVisibility(View.INVISIBLE);
        }
//            HISTORY data= (HISTORY) datas.get(position);
//            if(position!=0){
//                holders.ll_bg.setVisibility(View.VISIBLE);
//
//            }
//            holders.tvTime.setText(TimeUtil.formatToMf(data.getH_add_time())+"\n"+TimeUtil.formatToMD(data.getH_add_time()));
//            holders.tvContent.setText(data.getComment()+"\t\t\t\t\t\t");





    }

    @Override
    public int getItemCount() {
        int count = 6;

        if (datas != null && datas.size() > 0) {
            count = datas.size();
        }

        return count;
    }
}
