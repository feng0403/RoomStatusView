package com.feng.roomstatusview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * @author yuanjian 15-5-14.
 */
public class MHorizontalScrollView extends HorizontalScrollView {
    private OnScrollChangeCallback changeCallback;
//    private GestureDetector detector;

    public MHorizontalScrollView(Context context) {
        super(context);
//        detector = new GestureDetector(context, new ScrollDetector());
    }

    public MHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        detector = new GestureDetector(context, new ScrollDetector());
    }

    public MHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        detector = new GestureDetector(context, new ScrollDetector());
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldL, int oldT) {
        super.onScrollChanged(l, t, oldL, oldT);

        //setOnScrollChangeListener 这个方法 dded in API level 23
        //所以需要写接口回调出去滑动的距离
        if (changeCallback != null) {
            changeCallback.onChange(l, t);
        }
    }

   /* @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev) || detector.onTouchEvent(ev);
    }*/

    public void setChangeCallback(OnScrollChangeCallback changeCallback) {
        this.changeCallback = changeCallback;
    }

    public interface OnScrollChangeCallback {
        void onChange(int w, int h);
    }

    private static class ScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                return true;
            }
            return false;
        }
    }
}
