package com.canplay.medical.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.canplay.medical.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J on 2016/6/13.
 */
public class CycleWheelView extends ListView {
    public static final String TAG = CycleWheelView.class.getSimpleName();
    public static final int COLOR_DIVIDER_DEFALUT = Color.parseColor("#747474");
    public static final int HEIGHT_DIVIDER_DEFAULT = 2;
    public static final int COLOR_SOLID_DEFAULT = Color.parseColor("#3e4043");
    public static final int COLOR_SOLID_SELET_DEFAULT = Color.parseColor("#faa91c");
    public static final int WHEEL_SIZE_DEFAULT = 3;

    public Handler mHandler;

    public CycleWheelViewAdapter mAdapter;

    /**
     * Labels
     */
    public List<String> mLabels;
    public List<Long> mLabel;

    /**
     * Color Of Selected Label
     * 选择标签的颜色
     */
    public int mLabelSelectColor = Color.WHITE;

    /**
     * Color Of Unselected Label
     * 没有标签的颜色
     */
    public int mLabelColor = Color.GRAY;

    /**
     * Gradual Alph
     */
    public float mAlphaGradual = 0.7f;

    /**
     * Color Of Divider
     * 分频器的颜色
     */
    public int dividerColor = COLOR_DIVIDER_DEFALUT;

    /**
     * Height Of Divider
     * 分频器的高度
     */
    public int dividerHeight = HEIGHT_DIVIDER_DEFAULT;

    /**
     * Color of Selected Solid
     * 颜色选择固体
     */
    public int seletedSolidColor = COLOR_SOLID_SELET_DEFAULT;

    /**
     * Color of Unselected Solid
     */
    public int solidColor = COLOR_SOLID_DEFAULT;

    /**
     * Size Of Wheel , it should be odd number like 3 or greater
     */
    public int mWheelSize = WHEEL_SIZE_DEFAULT;

    /**
     * res Id of Wheel Item Layout
     */
    public int mItemLayoutId;

    /**
     * res Id of Label TextView
     */
    public int mItemLabelTvId;

    /**
     * Height of Wheel Item
     */
    public int mItemHeight;

    public boolean cylceEnable;

    public int mCurrentPositon;

    /**
     * 选中的字体大小
     */
    public float LabelSelectSize=-1;
    /**
     * 未选中的字体大小
     */
    public float LabelSize=-1;

    public WheelItemSelectedListener mItemSelectedListener;

    public CycleWheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CycleWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CycleWheelView(Context context) {
        super(context);
    }
    private void init() {
        mHandler = new Handler();
        mItemLayoutId = R.layout.item_cyclewheel;
        mItemLabelTvId = R.id.tv_label_item_wheel;
        mAdapter = new CycleWheelViewAdapter();
        setVerticalScrollBarEnabled(false);
        setScrollingCacheEnabled(false);
        setCacheColorHint(Color.TRANSPARENT);
        setFadingEdgeLength(0);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setDividerHeight(0);
        setAdapter(mAdapter);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    View itemView = getChildAt(0);
                    if (itemView != null) {
                        float deltaY = itemView.getY();
                        if (deltaY == 0) {
                            return;
                        }
                        if (Math.abs(deltaY) < mItemHeight / 2) {
                            smoothScrollBy(getDistance(deltaY), 50);
                        } else {
                            smoothScrollBy(getDistance(mItemHeight + deltaY), 50);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                refreshItems();
            }
        });
    }

    private int getDistance(float scrollDistance) {
        if (Math.abs(scrollDistance) <= 2) {
            return (int) scrollDistance;
        } else if (Math.abs(scrollDistance) < 12) {
            return scrollDistance > 0 ? 2 : -2;
        } else {
            return (int) (scrollDistance / 6);
        }
    }

    private void refreshItems() {
        int offset = mWheelSize / 2;
        int firstPosition = getFirstVisiblePosition();
        int position = 0;
        if (getChildAt(0) == null) {
            return;
        }
        if (Math.abs(getChildAt(0).getY()) <= mItemHeight / 2) {
            position = firstPosition + offset;
        } else {
            position = firstPosition + offset + 1;
        }
        if (position == mCurrentPositon) {
            return;
        }
        mCurrentPositon = position;
        if (mItemSelectedListener != null) {
            mItemSelectedListener.onItemSelected(getSelection(), getSelectLabel());
        }
        resetItems(firstPosition, position, offset);
    }

    private void resetItems(int firstPosition, int position, int offset){
        for (int i = position - offset - 1; i < position + offset + 1; i++) {
            View itemView = getChildAt(i - firstPosition);
            if (itemView == null) {
                continue;
            }
            TextView labelTv = (TextView) itemView.findViewById(mItemLabelTvId);
            if (position == i) {
                labelTv.setTextColor(mLabelSelectColor);
                if(LabelSelectSize!=-1){
                    labelTv.setTextSize(LabelSelectSize);
                }
                itemView.setAlpha(1f);
            } else {
                labelTv.setTextColor(mLabelColor);
                if(LabelSize!=-1){
                    labelTv.setTextSize(LabelSize);
                }
                int delta = Math.abs(i - position);
                double alpha = Math.pow(mAlphaGradual, delta);
                itemView.setAlpha((float) alpha);
            }
        }
    }

    public void setLabelSelectSize(float labelSelectSize) {
        LabelSelectSize = labelSelectSize;
    }

    public void setLabelSize(float labelSize) {
        LabelSize = labelSize;
    }

    /**
     * 设置滚轮的刻度列表
     *
     * @param labels
     */
    public void setLabels(List<String> labels,List<Long> mLabel) {
        mLabels = labels;
        this.mLabel=mLabel;
        mAdapter.setData(mLabels);
        mAdapter.notifyDataSetChanged();
        initView();
    }

    /**
     * 设置滚轮滚动监听
     *
     * @param mItemSelectedListener
     */
    public void setOnWheelItemSelectedListener(WheelItemSelectedListener mItemSelectedListener) {
        this.mItemSelectedListener = mItemSelectedListener;
    }

    /**
     * 获取滚轮的刻度列表
     *
     * @return
     */
    public List<String> getLabels() {
        return mLabels;
    }

    /**
     * 获取滚轮的刻度列表长度
     * @return
     */
    public int getLabelCount(){
        return mLabels==null?0:mLabels.size();
    }

    /**
     * 设置滚轮是否为循环滚动
     *
     * @param enable true-循环 false-单程
     */
    public void setCycleEnable(boolean enable) {
        if (cylceEnable != enable) {
            cylceEnable = enable;
            mAdapter.notifyDataSetChanged();
            setSelection(getSelection());
        }

    }

    /*
     * 滚动到指定位置
     */
    @Override
    public void setSelection(final int position) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                CycleWheelView.super.setSelection(getPosition(position));
            }
        });
    }

    public void selection(final int position){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                CycleWheelView.this.setSelection(position);
            }
        });
    }


    private int getPosition(int positon) {
        if (mLabels == null || mLabels.size() == 0) {
            return 0;
        }
        if (cylceEnable) {
            int d = Integer.MAX_VALUE / 2 / mLabels.size();
            return positon + d * mLabels.size();
        }
        return positon;
    }

    /**
     * 获取当前滚轮位置
     *
     * @return
     */
    public int getSelection() {
        if (mCurrentPositon == 0) {
            mCurrentPositon = mWheelSize / 2;
        }
        if (mLabels==null || mLabels.size()==0){
            return 0;
        }
        return (mCurrentPositon - mWheelSize / 2) % mLabels.size();
    }

    /**
     * 获取当前滚轮位置的刻度
     *
     * @return
     */
    public String getSelectLabel() {
        int position = getSelection();
        position = position < 0 ? 0 : position;
        try {
            return mLabels.get(position);
        } catch (Exception e) {
            return "";
        }
    }
    public long getSelectLabelId() {
        int position = getSelection();
        position = position < 0 ? 0 : position;
        try {
            return mLabel.get(position);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 如果需要自定义滚轮每个Item，调用此方法设置自定义Item布局，自定义布局中需要一个TextView来显示滚轮刻度
     *
     * @param itemResId 布局文件Id
     * @param labelTvId 刻度TextView的资源Id
     */
    public void setWheelItemLayout(int itemResId, int labelTvId) {
        mItemLayoutId = itemResId;
        mItemLabelTvId = labelTvId;
        mAdapter = new CycleWheelViewAdapter();
        mAdapter.setData(mLabels);
        setAdapter(mAdapter);
        initView();
    }

    /**
     * 设置未选中刻度文字颜色
     *
     * @param labelColor
     */
    public void setLabelColor(int labelColor) {
        this.mLabelColor = labelColor;
        resetItems(getFirstVisiblePosition(), mCurrentPositon, mWheelSize/2);
    }

    /**
     * 设置选中刻度文字颜色
     *
     * @param labelSelectColor
     */
    public void setLabelSelectColor(int labelSelectColor) {
        this.mLabelSelectColor = labelSelectColor;
        resetItems(getFirstVisiblePosition(), mCurrentPositon, mWheelSize/2);
    }

    /**
     * 设置滚轮刻度透明渐变值
     *
     * @param alphaGradual
     */
    public void setAlphaGradual(float alphaGradual) {
        this.mAlphaGradual = alphaGradual;
        resetItems(getFirstVisiblePosition(), mCurrentPositon, mWheelSize/2);
    }

    /**
     * 设置滚轮可显示的刻度数量，必须为奇数，且大于等于3
     *
     * @param wheelSize
     * @throws CycleWheelViewException 滚轮数量错误
     */
    public void setWheelSize(int wheelSize) throws CycleWheelViewException  {
        if (wheelSize < 3 || wheelSize % 2 != 1) {
            throw new CycleWheelViewException("Wheel Size Error , Must Be 3,5,7,9...");
        } else {
            mWheelSize = wheelSize;
            initView();
        }
    }

    /**
     * 设置块的颜色
     * @param unselectedSolidColor 未选中的块的颜色
     * @param selectedSolidColor 选中的块的颜色
     */
    public void setSolid(int unselectedSolidColor, int selectedSolidColor){
        this.solidColor = unselectedSolidColor;
        this.seletedSolidColor = selectedSolidColor;
        initView();
    }

    /**
     * 设置分割线样式
     * @param dividerColor  分割线颜色
     * @param dividerHeight 分割线高度(px)
     */
    public void setDivider(int dividerColor, int dividerHeight){
        this.dividerColor = dividerColor;
        this.dividerHeight = dividerHeight;
    }

    @SuppressWarnings("deprecation")
    private void initView() {
        mItemHeight = measureHeight();
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = mItemHeight * mWheelSize;
        mAdapter.setData(mLabels);
        mAdapter.notifyDataSetChanged();
        Drawable backgroud = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                int viewWidth = getWidth();
                Paint dividerPaint = new Paint();
                dividerPaint.setColor(dividerColor);
                dividerPaint.setStrokeWidth(dividerHeight);
                Paint seletedSolidPaint = new Paint();
                seletedSolidPaint.setColor(seletedSolidColor);
                Paint solidPaint = new Paint();
                solidPaint.setColor(solidColor);
                canvas.drawRect(0, 0, viewWidth, mItemHeight * (mWheelSize / 2), solidPaint);
                canvas.drawRect(0, mItemHeight * (mWheelSize / 2 + 1), viewWidth, mItemHeight
                        * (mWheelSize), solidPaint);
                canvas.drawRect(0, mItemHeight * (mWheelSize / 2), viewWidth, mItemHeight
                        * (mWheelSize / 2 + 1), seletedSolidPaint);
                canvas.drawLine(0, mItemHeight * (mWheelSize / 2), viewWidth, mItemHeight
                        * (mWheelSize / 2), dividerPaint);
                canvas.drawLine(0, mItemHeight * (mWheelSize / 2 + 1), viewWidth, mItemHeight
                        * (mWheelSize / 2 + 1), dividerPaint);
            }

            @Override
            public void setAlpha(int alpha) {
            }

            @Override
            public void setColorFilter(ColorFilter cf) {
            }

            @Override
            public int getOpacity() {
                return PixelFormat.UNKNOWN;
            }
        };
        setBackgroundDrawable(backgroud);
    }

    private int measureHeight() {
        View itemView = LayoutInflater.from(getContext()).inflate(mItemLayoutId, null);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        itemView.measure(w, h);
        int height = itemView.getMeasuredHeight();
        // int width = itemView.getMeasuredWidth();
        return height;
    }

    public interface WheelItemSelectedListener {
        public void onItemSelected(int position, String label);
    }

    public class CycleWheelViewException extends Exception {
        private static final long serialVersionUID = 1L;

        public CycleWheelViewException(String detailMessage) {
            super(detailMessage);
        }
    }

    public class CycleWheelViewAdapter extends BaseAdapter {

        private List<String> mData = new ArrayList<String>();

        public void setData(List<String> mWheelLabels) {
            mData.clear();
            if (mWheelLabels!=null) {
                mData.addAll(mWheelLabels);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (cylceEnable) {
                return Integer.MAX_VALUE;
            }
            return (mData==null?0:mData.size()) + mWheelSize - 1;
        }

        @Override
        public Object getItem(int position) {
            return "";
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(mItemLayoutId, null);
            }
            TextView textView = (TextView) convertView.findViewById(mItemLabelTvId);
            if (position < mWheelSize / 2
                    || (!cylceEnable && position >= mData.size() + mWheelSize / 2)) {
                textView.setText("");
                convertView.setVisibility(View.VISIBLE);
            } else {
                String s=null;
                if(mData.size()==1){
                    if(!TextUtils.isEmpty(mData.get(0))){
                        s=mData.get(0);
                        textView.setText(s);
                        convertView.setVisibility(View.VISIBLE);
                    }
                }else {
                    if(mData!=null && mData.size()>0){
                        int i = (position - mWheelSize / 2);
                        //    LogUtils.e("CycleWheelViewAdapter", String.valueOf(i) + "---" + "position=" + position + " mData.size()=" + mData.size());
                        int a = i % mData.size();
                        //    LogUtils.e("CycleWheelViewAdapter", String.valueOf(a) );

                        if (!TextUtils.isEmpty(mData.get(a))) {
                            s = mData.get((position - mWheelSize / 2) % mData.size());
                        }
                        textView.setText(s);
                        convertView.setVisibility(View.VISIBLE);
                    }else {
                        textView.setText(" ");
                    }
                }
            }
            return convertView;
        }
    }
}