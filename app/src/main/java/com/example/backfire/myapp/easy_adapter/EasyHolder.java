package com.example.backfire.myapp.easy_adapter;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.backfire.myapp.callbackInterface.EasyViewHelper;

/**
 * Created by backfire on 2017/12/12.
 */

public class EasyHolder extends RecyclerView.ViewHolder implements EasyViewHelper.RecyclerView<EasyHolder> {
    public EasyHolder(View itemView) {
        super(itemView);
    }

    @Override
    public EasyHolder setText(int viewId, String value) {
        return null;
    }

    @Override
    public EasyHolder setTextColor(int viewId, int color) {
        return null;
    }

    @Override
    public EasyHolder setTextColorRes(int viewId, int colorRes) {
        return null;
    }

    @Override
    public EasyHolder setImageResource(int viewId, int imgResId) {
        return null;
    }

    @Override
    public EasyHolder setBackgroundColor(int viewId, int color) {
        return null;
    }

    @Override
    public EasyHolder setBackgroundColorRes(int viewId, int colorRes) {
        return null;
    }

    @Override
    public EasyHolder setImageDrawable(int viewId, Drawable drawable) {
        return null;
    }

    @Override
    public EasyHolder setImageDrawableRes(int viewId, int drawableRes) {
        return null;
    }

    @Override
    public EasyHolder setImageUrl(int viewId, String imgUrl) {
        return null;
    }

    @Override
    public EasyHolder setImageBitmap(int viewId, Bitmap imgBitmap) {
        return null;
    }

    @Override
    public EasyHolder setVisible(int viewId, boolean visible) {
        return null;
    }

    @Override
    public EasyHolder setVisible(int viewId, int visible) {
        return null;
    }

    @Override
    public EasyHolder setTag(int viewId, Object tag) {
        return null;
    }

    @Override
    public EasyHolder setTag(int viewId, int key, Object tag) {
        return null;
    }

    @Override
    public EasyHolder setChecked(int viewId, boolean checked) {
        return null;
    }

    @Override
    public EasyHolder setAlpha(int viewId, float value) {
        return null;
    }

    @Override
    public EasyHolder setTypeface(int viewId, Typeface typeface) {
        return null;
    }

    @Override
    public EasyHolder setTypeface(Typeface typeface, int... viewIds) {
        return null;
    }

    @Override
    public EasyHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        return null;
    }
}
