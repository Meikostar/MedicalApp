package com.canplay.medical.view;


import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class SlidingMenuRight extends FrameLayout {

    private View menuView, mainView;
    private int menuWidth;
    private Status status = Status.Close;
    private Status preStatus = Status.Close; // 前一次保持的状态
    private ViewDragHelper mdDragHelper;

    private OnStatusListener listener;

    public interface OnStatusListener {

        void statusChanged(Status status);

    }

    public void setOnStatusListener(OnStatusListener listener) {
        this.listener = listener;
    }

    public enum Status {
        Open, Close
    }

    public SlidingMenuRight(Context context) {
        this(context, null);
    }

    public SlidingMenuRight(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenuRight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mdDragHelper = ViewDragHelper.create(this, callback);
    }

    Callback callback = new Callback() {

        @Override
        public boolean tryCaptureView(View view, int arg1) {
            return true;
        }

        public int getViewHorizontalDragRange(View child) {
            return menuWidth;
        }

        ;

        @Override
        public int clampViewPositionHorizontal(View child, int right, int dx) {
            if (child == mainView) {
                if (right < 0)
                    return 0;
                else if (right > menuWidth)
                    return menuWidth;
                else
                    return right;
            } else if (child == menuView) {
                if (right > 0)
                    return 0;
                else if (right > menuWidth)
                    return menuWidth;
                else
                    return right;
            }
            return 0;
        }

        public void onViewPositionChanged(View changedView, int left, int top,
                                          int dx, int dy) {
            if (changedView == mainView)
                menuView.offsetLeftAndRight(dx);
            else
                mainView.offsetLeftAndRight(dx);
            invalidate();
        }

        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == mainView) {
                if (status == Status.Open) {
                    close();
                    return;
                }
                if (xvel == 0
                        && Math.abs(mainView.getLeft()) > menuWidth / 2.0f) {
                    open();
                } else if (xvel > 0) {
                    open();
                } else {
                    close();
                }
            } else {
                if (xvel == 0
                        && Math.abs(mainView.getLeft()) > menuWidth / 2.0f) {
                    open();
                } else if (xvel > 0) {
                    open();
                } else {
                    close();
                }
            }
        }

    };

    /**
     * 打开菜单
     */
    public void open() {
        if (mdDragHelper.smoothSlideViewTo(mainView, menuWidth, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        preStatus = status;
        status = Status.Open;
        if (listener != null && preStatus == Status.Close) {
            listener.statusChanged(status);
        }
    }

    /**
     * 关闭菜单
     */
    public void close() {
        if (mdDragHelper.smoothSlideViewTo(mainView, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        preStatus = status;
        status = Status.Close;
        if (listener != null && preStatus == Status.Open) {
            listener.statusChanged(status);
        }
    }

    /**
     * 切换菜单状态
     */
    public void toggle() {
        if (status == Status.Close) {
            open();
        } else {
            close();
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // 开始执行动画
        if (mdDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new IllegalArgumentException("子view的数量必须为2个");
        }
        menuView = getChildAt(0);
        mainView = getChildAt(1);
        int width = mainView.getLayoutParams().width;
        menuWidth = width-menuView.getLayoutParams().width;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
//        menuView.layout(-menuWidth, 0, 0, menuView.getMeasuredHeight());
        menuView.layout(0, 0,menuWidth, menuView.getMeasuredHeight());
        mainView.layout(0, 0, right, bottom);
//        mainView.layout(left, top, 0, 0);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mdDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (status == Status.Open) {
            mdDragHelper.processTouchEvent(event);
        }
        return true;
    }

}

