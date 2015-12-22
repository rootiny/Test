package com.grandstream.confctrol.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zhyjiang on 12/22/15.
 */
public class CanScrollViewPager extends ViewPager {
    private boolean mCanScroll = false;

    public CanScrollViewPager(Context context) {
        super(context);
    }

    public CanScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mCanScroll) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mCanScroll) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    public boolean canScrollble() {
        return mCanScroll;
    }

    public void setScrollble(boolean canScroll) {
        this.mCanScroll = canScroll;
    }

}
