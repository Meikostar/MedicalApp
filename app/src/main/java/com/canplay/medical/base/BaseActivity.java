package com.canplay.medical.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.canplay.medical.R;
import com.canplay.medical.base.manager.AppManager;
import com.canplay.medical.permission.PermissionGen;
import com.canplay.medical.view.NavigationBar;

import rx.Subscription;

/**
 * @Title:基础Activity类
 * @Description:每个Activity必须继承该类
 * @Author: LLC
 * @Since:2015-3-19
 */
public abstract class BaseActivity extends AppCompatActivity implements Handler.Callback,  NavigationBar.NavigationBarListener{

    private FrameLayout decorView;
    public Handler mHandler;
    public static String BUNDLE_STRING = "BUNDLE_STRING";
    public int type=0;//=1表示不对其做无操作返回
    private View bodyView;
    private Subscription mSubscription;

    private ProgressDialog pd;
    private Toolbar toolbar;
    private NavigationBar navigationBar;
    public void initNavi(int id){
        navigationBar = (NavigationBar) findViewById(id);
        navigationBar.setNavigationBarListener(this);
    }
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
    /**
     * 获取全局控制器
     *
     * @return
     */
    public AppComponent getAppComponent(){
        return ((BaseApplication) getApplication()).getAppComponent();
    }
    public Handler handler = new Handler();




    @Override
    protected void onCreate(Bundle savedInstanceState){
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);

        translucentStatusBar();//状态栏
        //添加activity
        AppManager.getInstance(this).addActivity(this);
        decorView = (FrameLayout) findViewById(Window.ID_ANDROID_CONTENT);
        mHandler = new Handler(this);
        initViews();
        bindEvents();
        initData();

    }
    @Override
    public void navigationRight() {

    }
    @Override
    public void navigationimg() {

    }


    @Override
    public void navigationLeft() {
        finish();
    }
    private void translucentStatusBar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){//5.0及以上
            View decorView = getWindow().getDecorView();
            decorView.setFitsSystemWindows(false);
            getWindow().setStatusBarColor(getResources().getColor(R.color.slow_black));
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4到5.0
            /*WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);*/
        }
    }

    /**
     * 初始化页面
     *
     * @param layoutId
     */
    public void initBodyView(int layoutId){
        if(layoutId > 0){
            View view = getLayoutInflater().inflate(layoutId, null);
            initBodyView(view);
        }
    }



    /**
     * 初始化页面，不带actionbar
     *
     * @param layoutId
     */
    public void initBodyViewNoTile(int layoutId){
        if(layoutId > 0){
            View view = getLayoutInflater().inflate(layoutId, null);
            addBodyViewNoTitle(view);
        }
    }

    /**
     * 初始化页面，带有标题栏
     *
     * @param bodyView
     */
    public void initBodyView(View bodyView){
        if(bodyView != null){
            this.bodyView = bodyView;
            addBodyView(bodyView);
        }
    }


    /**
     * 添加身体布局到根布局中
     *
     * @param v
     */
    private void addBodyView(View v){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.topMargin = (int) getResources().getDimension(R.dimen.title_bar_height);
        decorView.addView(v, layoutParams);
    }

    /**
     * 添加身体布局到根布局中
     *
     * @param v
     */
    private void addBodyViewNoTitle(View v){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        decorView.addView(v, layoutParams);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            AppManager.getInstance(this).finishActivity(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
     * 初始Ul
     */
    public abstract void initViews();

    /*
     * 监听事件
     */
    public abstract void bindEvents();

    /**
     * 初始化其他资源
     */
    public abstract void initData();

    @Override
    protected void onResume(){


        super.onResume();
    }
    //进度条
    public void showProgress(String msg) {
        if (pd == null) {
            pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
            pd.setCanceledOnTouchOutside(false);
        }
        if (msg == null) {
            msg = "加载中...";
        }
        pd.setMessage(msg);
        pd.show();
    }

    public void dimessProgress() {
        if (pd != null) {
            pd.dismiss();
        }
    }

    /**
     * 开启新activity
     *
     * @param activity
     * @Description:
     */
    public Intent startActivity(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        return intent;
    }

    /**
     * 开启新activity
     *
     * @param activity
     * @Description:
     */
    public Intent startActivityForResult(Class activity){
        Intent intent = new Intent(this, activity);
        startActivityForResult(intent, 0);
        return intent;
    }

    /**
     * 开启新activity
     *
     * @param activity
     * @Description:
     */
    public Intent startActivity(Class activity, Bundle bundle){
        Intent intent = new Intent(this, activity);
        intent.putExtra(BUNDLE_STRING, bundle);
        startActivity(intent);
        return intent;
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    public void skipActivity(Activity aty, Class<?> cls){
        showActivity(aty, cls);
        AppManager.getInstance(this).finishActivity(aty);
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    public void skipActivity(Activity aty, Intent it){
        showActivity(aty, it);
        AppManager.getInstance(this).finishActivity(aty);
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    public void skipActivity(Activity aty, Class<?> cls, Bundle extras){
        showActivity(aty, cls, extras);
        AppManager.getInstance(this).finishActivity(aty);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    public void showActivity(Activity aty, Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    public void showActivity(Activity aty, Intent it){
        aty.startActivity(it);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    public void showActivity(Activity aty, Class<?> cls, Bundle extras){
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }



    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
    /*
     * 结束自己
     */
    public void finishActivity(){
        //结束Activity&从栈中移除该Activity
        AppManager.getInstance(this).finishActivity();
    }

    /*
    结束某个activity
     */
    public void finishActivity(Activity activity){
        AppManager.getInstance(this).finishActivity(activity);
    }


    /**
     * 关闭键盘
     */
    public void closeKeyBoard() {
        try {
            if(this.getCurrentFocus() == null){
                return;
            }
            IBinder iBinder = this.getCurrentFocus().getWindowToken();
            if (iBinder == null) {
                return;
            }
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(iBinder, inputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String oldMsg;
    protected Toast toast = null;
    private long oneTime = 0;
    private long twoTime = 0;

    public void showToasts(String s){
        if(toast == null){
            toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        }else{
            twoTime = System.currentTimeMillis();
            if(s.equals(oldMsg)){
                if(twoTime - oneTime > Toast.LENGTH_SHORT){
                    toast.show();
                }
            }else{
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }
    //动态权限
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    public void showToast(int resId){
        showToasts(getString(resId));
    }

    @Override
    protected void onDestroy(){
        if(toast != null){
            toast.cancel();
            toast = null;
        }
        if (mSubscription != null) {
            RxBus.getInstance().unSub(mSubscription);
        }
        super.onDestroy();
    }
}
