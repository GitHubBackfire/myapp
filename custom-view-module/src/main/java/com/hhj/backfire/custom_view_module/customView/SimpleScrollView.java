package com.hhj.backfire.custom_view_module.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by backfire on 2018/4/19.
 */

public class SimpleScrollView extends ViewGroup {
    private int mScreenHeight;
    private int mChildCount;
    public SimpleScrollView(Context context) {
        super(context);
        mScreenHeight = getScreenHeight(context);
    }

    public SimpleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenHeight = getScreenHeight(context);

    }

    public SimpleScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mChildCount = getChildCount();
        for(int i = 0 ; i < mChildCount; i ++){
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) getLayoutParams();
        marginLayoutParams.height = mScreenHeight;
        setLayoutParams(marginLayoutParams);
        for(int j = 0; j < mChildCount; j++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                child.layout(i, j * mScreenHeight,
                        i2, (j + 1) * mScreenHeight);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);

    }

    private int getScreenHeight(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return  displayMetrics.heightPixels;
    }



}
