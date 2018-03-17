package com.canplay.medical.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.canplay.medical.R;
import com.canplay.medical.base.BaseFragment;
import com.canplay.medical.base.RxBus;
import com.canplay.medical.base.SubscriptionBean;
import com.canplay.medical.mvp.adapter.RemindMedicatAdapter;
import com.canplay.medical.view.NavigationBar;
import com.canplay.medical.view.RegularListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.functions.Action1;


/**
 * 用药提醒
 */
public class RemindMedicatFragment extends BaseFragment {



    @BindView(R.id.rl_menu)
    ListView rlMenu;
;
    Unbinder unbinder;
    private RemindMedicatAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remind_medical, null);
        unbinder = ButterKnife.bind(this, view);

        initView();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void initView() {
        adapter=new RemindMedicatAdapter(getActivity(),null,rlMenu);
        rlMenu.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }






    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
