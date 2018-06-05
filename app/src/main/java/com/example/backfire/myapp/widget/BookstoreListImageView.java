package com.example.backfire.myapp.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by backfire on 2017/10/15.
 */

public class BookstoreListImageView extends ImageView {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public BookstoreListImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public BookstoreListImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
