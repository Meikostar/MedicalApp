package com.canplay.medical.mvp.activity.mine;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.mvp.adapter.recycle.PressRecordReCycleAdapter;
import com.canplay.medical.util.LogUtils;
import com.canplay.medical.view.DivItemDecoration;
import com.canplay.medical.view.NavigationBar;
import com.google.zxing.WriterException;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.yzq.zxinglibrary.encode.CodeCreator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * x血压测量记录
 */
public class BloodPressRecordActivity extends BaseActivity {


    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.super_recycle_view)
    SuperRecyclerView mSuperRecyclerView;

    private PressRecordReCycleAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private final int TYPE_PULL_REFRESH = 1;
    private final int TYPE_PULL_MORE = 2;
    private final int TYPE_REMOVE = 3;
    private int id;
    private int currpage=1;


    @Override
    public void initViews() {
        setContentView(R.layout.activity_blood_press_record);
        ButterKnife.bind(this);
        navigationBar.setNavigationBarListener(this);


        navigationBar.setNavigationBarListener(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mSuperRecyclerView.setLayoutManager(mLinearLayoutManager);
        mSuperRecyclerView.addItemDecoration(new DivItemDecoration(2, true));
        mSuperRecyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        adapter=new PressRecordReCycleAdapter(this,0);
        mSuperRecyclerView.setAdapter(adapter);


    }

    @Override
    public void bindEvents() {
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // mSuperRecyclerView.showMoreProgress();

//                model.getOrderHistory(id,1,TYPE_PULL_REFRESH,BloodPressRecordActivity.this);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSuperRecyclerView.hideMoreProgress();
                        LogUtils.e("result", "name2");
                    }
                }, 2000);
            }
        };
        mSuperRecyclerView.setRefreshListener(refreshListener);

    }
//    public void onDataLoaded( int loadType, final long ServerTotalSize, List<HISTORY> datas) {
//
//        if (loadType == TYPE_PULL_REFRESH) {
//            list.clear();
//            for (HISTORY info : datas) {
//                list.add(info);
//            }
//        } else {
//            for (HISTORY info : datas) {
//                list.add(info);
//            }
//        }
//        adapter.setDatas(list);
//        adapter.notifyDataSetChanged();
//        mSuperRecyclerView.hideMoreProgress();
//
//        if (adapter.getDatas().size() < ServerTotalSize) {
//            mSuperRecyclerView.setupMoreListener(new OnMoreListener() {
//                @Override
//                public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
//                    currpage++;
//                    mSuperRecyclerView.showMoreProgress();
//
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (adapter.getDatas().size() <= ServerTotalSize)
//                                mSuperRecyclerView.hideMoreProgress();
//                            LogUtils.e("result", "name3");
//                            model.getOrderHistory(id,currpage,TYPE_PULL_MORE,OrderHistoryRecordActivity.this);
//                        }
//                    }, 2000);
//                }
//            }, 1);
//        } else {
//            mSuperRecyclerView.hideMoreProgress();
//            mSuperRecyclerView.removeMoreListener();
//        }
//
//
//    }


    @Override
    public void initData() {

    }

}
