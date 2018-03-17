package com.canplay.medical.mvp.activity.mine;


import android.widget.TextView;

import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.view.ClearEditText;
import com.canplay.medical.view.NavigationBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 修改密码
 */
public class EditorPwsActivity extends BaseActivity {


    @BindView(R.id.navigationbar)
    NavigationBar navigationbar;
    @BindView(R.id.et_old_psw)
    ClearEditText etOldPsw;
    @BindView(R.id.et_psw)
    ClearEditText etPsw;
    @BindView(R.id.et_sure_psw)
    ClearEditText etSurePsw;
    @BindView(R.id.tv_save)
    TextView tvSave;


    @Override
    public void initViews() {
        setContentView(R.layout.activity_editor_psw);
        ButterKnife.bind(this);
        navigationbar.setNavigationBarListener(this);




    }

    @Override
    public void bindEvents() {


    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
