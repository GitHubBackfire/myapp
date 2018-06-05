package com.hhj.backfire.custom_view_module.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhj.backfire.custom_view_module.R;

import org.w3c.dom.Attr;

/**
 * Created by backfire on 2018/4/18.
 */

public class CustomTopBar extends RelativeLayout implements View.OnClickListener{
    private String mLeftText;
    private String mRightText;
    private int mLeftTextColor;
    private int mRightTextColor;
    private String mTitle;
    private Drawable mLeftBackground;
    private Drawable mRightBackground;
    private int mTitleTextColor;
    private float mTitleTextSize;

    private Button mLeftBtn;
    private Button mRightBtn;
    private TextView mTitleTxt;

    private LayoutParams mLeftLayoutParams;
    private LayoutParams mRightLayoutParams;
    private LayoutParams mTitleLayoutParams;

    private CustomTopBarClickListener mCustomTopBarClickListener;


    // 如果View是在Java代码里面new的，则调用第一个构造函数
    public CustomTopBar(Context context) {
        super(context);
    }

    // 如果View是在Java代码里面new的，则调用第一个构造函数
    public CustomTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomTopBar);
        //从TypedArray中取出对应的值来为要设置的属性赋值
        mLeftTextColor = ta.getColor(
                R.styleable.CustomTopBar_leftTextColor, 0);
        mLeftBackground = ta.getDrawable(
                R.styleable.CustomTopBar_leftBackground);
        mLeftText = ta.getString(R.styleable.CustomTopBar_leftText);

        mRightTextColor = ta.getColor(
                R.styleable.CustomTopBar_rightTextColor, 0);
        mRightBackground = ta.getDrawable(
                R.styleable.CustomTopBar_rightBackground);

        mRightText = ta.getString(R.styleable.CustomTopBar_rightText);

        mTitleTextSize = ta.getDimension(
                R.styleable.CustomTopBar_titleTextSize, 10);
        mTitleTextColor = ta.getColor(
                R.styleable.CustomTopBar_titleTextColor, 0);
        mTitle = ta.getString(R.styleable.CustomTopBar_title);

        ta.recycle();
        initView(context);
    }

    // 一般是在第二个构造函数里主动调用
    // 如View有style属性时
    public CustomTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //API21之后才使用
    // 不会自动调用
    // 一般是在第二个构造函数里主动调用
    // 如View有style属性时
    public CustomTopBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(Context context) {
        mLeftBtn = new Button(context);
        mRightBtn = new Button(context);
        mTitleTxt = new TextView(context);

        mLeftBtn.setTextColor(mLeftTextColor);
        mLeftBtn.setText(mLeftText);
        mLeftBtn.setBackground(mLeftBackground);

        mRightBtn.setTextColor(mRightTextColor);
        mRightBtn.setText(mRightText);
        mRightBtn.setBackground(mRightBackground);

        mLeftLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        //就是相当于在布局文件中设置
        mLeftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(mLeftBtn,mLeftLayoutParams);

        mRightLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mRightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        addView(mRightBtn,mRightLayoutParams);

        mTitleLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mTitleLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

    }

    public void setCustomTopBaarClickListner(CustomTopBarClickListener customTopBarClickListener){
        mCustomTopBarClickListener = customTopBarClickListener;
    }

    @Override
    public void onClick(View view) {
        if(view == mLeftBtn){
            mCustomTopBarClickListener.leftClick();
        }else if(view == mRightBtn){
            mCustomTopBarClickListener.rightClick();
        }else if(view == mTitleTxt){

        }
    }


    public interface CustomTopBarClickListener{
        void leftClick();
        void rightClick();
    }


}
