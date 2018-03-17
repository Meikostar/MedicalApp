package com.canplay.medical.view;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.canplay.medical.R;
import com.canplay.medical.util.TextUtil;

/**
 * Created by qi_fu on 2017/12/18.
 */

public class BaseSelectDialog {

    TextView tv_content;
    private Context mContext;
    private View mView;
    private PopupWindow mPopupWindow;
    private TextView mButtonCancel;
    private TextView mButtonConfirm;
    private TextView but_title;
    private EditText editText;

    private BindClickListener mBindClickListener;
    private TextView[] views;
    private TextView[] views2;
    private TextView[] views3;
    private int gg;
    private int jl;
    private int kw;
    private View parentView;
    public BaseSelectDialog(Context mContext,View parentView) {
        this.mContext = mContext;
        this.parentView = parentView;
        initView();
        mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);

    }

    public BaseSelectDialog setBindClickListener(BindClickListener mBindClickListener) {
        this.mBindClickListener = mBindClickListener;
        return this;
    }




    public interface BindClickListener {
        void tasteNum(int type);
    }

    public void setTitles(String name,String content) {
        but_title.setVisibility(View.VISIBLE);
        tv_content.setVisibility(View.VISIBLE);
        but_title.setText(name);
        tv_content.setText(content);
    }

    private void initView() {
        mView = View.inflate(mContext, R.layout.dialog_taste_selector, null);
        mButtonCancel = (TextView) mView.findViewById(R.id.but_tsw_cancel);
        mButtonConfirm = (TextView) mView.findViewById(R.id.but_tsw_confirm);
        but_title = (TextView) mView.findViewById(R.id.select_title);
        tv_content = (TextView) mView.findViewById(R.id.tv_content);


        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mBindClickListener.tasteNum(2);
            }
        });
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                    mBindClickListener.tasteNum(1);
            }
        });
    }
    private void setRightorLeft(String right,String left,String content,String title){
        if(TextUtil.isNotEmpty(left)){
            mButtonCancel.setText(left);
        }
        if(TextUtil.isNotEmpty(right)){
            mButtonConfirm.setText(right);
        }
        if(TextUtil.isNotEmpty(content)){
            tv_content.setText(right);
        }

        if(TextUtil.isNotEmpty(title)){
            but_title.setText(title);
        }
    }

    public void show() {
     if(mPopupWindow!=null){
         if (mPopupWindow.isShowing()) {
             mPopupWindow.dismiss();
         } else {
             mPopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
         }
     }
    }
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}

