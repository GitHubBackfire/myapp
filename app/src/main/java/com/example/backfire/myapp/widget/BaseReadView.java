package com.example.backfire.myapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义阅读View
 * Created by backfire on 2017/11/24.
 */

public class BaseReadView extends View {

    public BaseReadView(Context context) {
        super(context);
    }

    public BaseReadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseReadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseReadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
