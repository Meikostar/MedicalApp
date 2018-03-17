package com.canplay.medical.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.medical.R;
import com.canplay.medical.mvp.component.OnChangeListener;


/***
 * 功能描述:
 * 作者:chenwei
 * 时间:2016/12/16
 * 版本:1.0
 ***/

public class BottonNevgBar extends LinearLayout implements View.OnClickListener {
    private ImageView[] imageViews = new ImageView[5];
    private TextView[] textViews = new TextView[5];
    private Context mContext;
    private View view;
    private OnChangeListener mListener;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_nvg_1:
                if (mListener!=null){
                    mListener.onChagne(0);
                }
                break;
            case R.id.ll_nvg_2:
                if (mListener!=null){
                    mListener.onChagne(1);
                }
                break;
            case R.id.ll_nvg_3:
                if (mListener!=null){
                    mListener.onChagne(2);
                }
                break;
            case R.id.ll_nvg_4:
                if (mListener!=null){
                    mListener.onChagne(3);
                }
                break;
        }
    }

    public BottonNevgBar(Context context) {
        super(context);
        init(context);
    }

    public BottonNevgBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottonNevgBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.view_nevg_bar,this);
        textViews[0] = (TextView)view.findViewById(R.id.tv_checkbox_text1);
        textViews[1]  = (TextView)view.findViewById(R.id.tv_checkbox_text2);
        textViews[2]  = (TextView)view.findViewById(R.id.tv_checkbox_text3);
        textViews[3]  = (TextView)view.findViewById(R.id.tv_checkbox_text4);

         imageViews[0]  = (ImageView)view.findViewById(R.id.iv_checkbox_image1);
         imageViews[1]  = (ImageView)view.findViewById(R.id.iv_checkbox_image2);
         imageViews[2]  = (ImageView)view.findViewById(R.id.iv_checkbox_image3);
         imageViews[3]  = (ImageView)view.findViewById(R.id.iv_checkbox_image4);

        view.findViewById(R.id.ll_nvg_1).setOnClickListener(this);
        view.findViewById(R.id.ll_nvg_2).setOnClickListener(this);
        view.findViewById(R.id.ll_nvg_3).setOnClickListener(this);
        view.findViewById(R.id.ll_nvg_4).setOnClickListener(this);

    }

    public void setSelect(int index){
        for (int i=0;i<4;i++){
            if (index == i){
                textViews[i].setTextColor(ContextCompat.getColor(mContext,R.color.blue));
            }else {
                textViews[i].setTextColor(ContextCompat.getColor(mContext,R.color.bottom));
            }
        }

    }

    public void setOnChangeListener(OnChangeListener listener){
        this.mListener = listener;
    }


}
