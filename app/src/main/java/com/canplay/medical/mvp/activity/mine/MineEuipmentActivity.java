package com.canplay.medical.mvp.activity.mine;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.bean.Euip;
import com.canplay.medical.mvp.activity.account.LoginActivity;
import com.canplay.medical.mvp.adapter.EuipmentAdapter;
import com.canplay.medical.permission.PermissionConst;
import com.canplay.medical.permission.PermissionGen;
import com.canplay.medical.permission.PermissionSuccess;
import com.canplay.medical.view.NavigationBar;
import com.canplay.medical.view.PhotoPopupWindow;
import com.canplay.medical.view.RegularListView;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的设备
 */
public class MineEuipmentActivity extends BaseActivity {


    @BindView(R.id.line)
    View line;
    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.rl_menu)
    RegularListView rlMenu;
    private EuipmentAdapter adapter;
    private PhotoPopupWindow mWindowAddPhoto;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_mine_equipment);
        ButterKnife.bind(this);
        navigationBar.setNavigationBarListener(this);
        adapter=new EuipmentAdapter(this);
        rlMenu.setAdapter(adapter);
        mWindowAddPhoto = new PhotoPopupWindow(this);
        mWindowAddPhoto.setCont("解除绑定","取消");
    }

    @Override
    public void bindEvents() {
        navigationBar.setNavigationBarListener(new NavigationBar.NavigationBarListener() {
            @Override
            public void navigationLeft() {
            }

            @Override
            public void navigationRight() {
                PermissionGen.with(MineEuipmentActivity.this)//动态权限
                        .addRequestCode(PermissionConst.REQUECT_CODE_CAMERA)
                        .permissions(Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .request();
            }

            @Override
            public void navigationimg() {

            }
        });
        adapter.setClickListener(new EuipmentAdapter.ItemCliks() {
            @Override
            public void getItem(Euip menu,int type) {
              if(type==1){//点击事件

              }else {//长按事件
                  mWindowAddPhoto.showAsDropDown(line);
              }
            }
        });

        mWindowAddPhoto.setSureListener(new PhotoPopupWindow.ClickListener() {
            @Override
            public void clickListener(int type) {
                if(type==1){//解除绑定

                }else {//取消

                }
            }
        });
    }


   private int REQUEST_CODE_SCAN=6;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                showToasts("扫描结果为：" +content);
//                result.setText("扫描结果为：" + content);
            }
        }
    }

    @Override
    public void initData() {

    }

    @PermissionSuccess(requestCode = PermissionConst.REQUECT_CODE_CAMERA)
    public void requestSdcardSuccess() {
        Intent intent = new Intent(MineEuipmentActivity.this, CaptureActivity.class);
         /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
         * 也可以不传这个参数
         * 不传的话  默认都为默认不震动  其他都为true
         * */

        //ZxingConfig config = new ZxingConfig();
        //config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）
        //config.setPlayBeep(true);//是否播放提示音
        //config.setShake(true);//是否震动
        //config.setShowAlbum(true);//是否显示相册
        //config.setShowFlashLight(true);//是否显示闪光灯
        //intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    public String path;



}
