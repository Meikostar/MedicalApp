package com.canplay.medical.mvp.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.mvp.adapter.CollectionAdapter;
import com.canplay.medical.view.NavigationBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的收藏
 */
public class CollectionActivity extends BaseActivity  {


    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.listview)
    ListView listview;
    private CollectionAdapter adapter;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        navigationBar.setNavigationBarListener(this);
        adapter=new CollectionAdapter(this,null,listview);
        listview.setAdapter(adapter);

    }

    @Override
    public void bindEvents() {


    }


    @Override
    public void initData() {

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
