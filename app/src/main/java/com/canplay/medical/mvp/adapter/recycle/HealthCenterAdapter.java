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
import com.canplay.medical.mvp.adapter.viewholder.HealtCenterHolder;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by mykar on 17/4/12.
 */
public class HealthCenterAdapter extends BaseRecycleViewAdapter {

    private Context context;

    public HealthCenterAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, null);

        return new HealtCenterHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HealtCenterHolder holders = (HealtCenterHolder) holder;
//       final ORDER data= (ORDER) datas.get(position);
        holders.ll_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickListener(0,"");
            }
        });
     }

    @Override
    public int getItemCount() {
        int count = 5;

        if (datas != null && datas.size() > 0) {
            count = datas.size();
        }

        return count;
    }

    public void setClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
    public OnItemClickListener listener;
    public interface  OnItemClickListener{
        void clickListener(int poiston, String id);
    }
}
