package com.example.backfire.myapp.widget.readview;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.backfire.myapp.bean.EpubBookItem;
import com.example.backfire.myapp.bean.EpubBookPage;
import com.example.backfire.myapp.utils.ScreenUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * width,height设置为match_content
 * Created by backfire on 2018/6/13.
 */

public class PageItemView extends LinearLayout {
    private Context context;

    private ArrayList<PageTextViewItem> textViews;
    private ArrayList<ThumbnailsImageView> thumbnailsImageViews;



    //dp
    public final static int
            HEADING1_VIEW_HEIGHT = 22,
            HEADING2_VIEW_HEIGHT = 20,
            HEADING3_VIEW_HEIGHT = 18,
            HEADING4_VIEW_HEIGHT = 16;
    private final static int PAGE_FOOTING_VIEW_HEIGHT = 12;

    //sp
    public final static int
            HEADING1_TEXT_SIZE = 20,
            HEADING2_TEXT_SIZE = 18,
            HEADING3_TEXT_SIZE = 16,
            HEADING4_TEXT_SIZE = 14;
    public final static int CONTEXT_TEXT_SIZE = 12;
    public final static float CONTEXT_TEXT_LineSpacingExtra = 1.5f;
    public final static float CONTEXT_TEXT_LineSpacingMultiplier  = 1f;
    private final static int PAGE_Footing_TEXT_SIZE = 10;


    private EpubBookPage epubBookPage;

    public PageItemView(Context context) {
        super(context);
        init(context);
    }

    public PageItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PageItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.setOrientation(VERTICAL);

    }

    public void setEpubBookPage(EpubBookPage epubBookPage) {
        this.epubBookPage = epubBookPage;
        createChildView(this.epubBookPage);
    }

    private void createChildView(EpubBookPage epubBookPage) {
        if(epubBookPage != null ){
            if(epubBookPage.getEpubBookItems() != null){
                for(EpubBookItem epubBookItem : epubBookPage.getEpubBookItems()){
                    if(epubBookItem.getType() == EpubBookItem.LABEL_HEADING){
                        createHeadingView(epubBookItem.getTypeDetail());
                    }else if(epubBookItem.getType() == EpubBookItem.LABEL_CONTENTS){
                        createContentView(epubBookItem.getTypeDetail());
                    }else if(epubBookItem.getType() == EpubBookItem.LABLE_PICTURE){
                        createPictureView(epubBookItem.getTypeDetail());
                    }else{
                        //
                    }
                }
            }
            createPageFootingView(Integer.toString(epubBookPage.getCurrentPage()));
        }
    }

    private void createHeadingView(int type) {
        TextView textView = new TextView(context);
        textView.setWidth(getWidth());
        textView.setMaxLines(1);
        textView .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        switch(type){
           case EpubBookItem.LABEL_HEADING1_TYPE:
               textView.setHeight(ScreenUtil.dpToPxInt(HEADING1_VIEW_HEIGHT));
               textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, HEADING1_TEXT_SIZE);
               break;
           case EpubBookItem.LABEL_HEADING2_TYPE:
               textView.setHeight(ScreenUtil.dpToPxInt(HEADING2_VIEW_HEIGHT));
               textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, HEADING2_TEXT_SIZE);
               break;
           case EpubBookItem.LABEL_HEADING3_TYPE:
               textView.setHeight(ScreenUtil.dpToPxInt(HEADING3_VIEW_HEIGHT));
               textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, HEADING3_TEXT_SIZE);
               break;
           default:
               textView.setHeight(0);
               break;
       }

    }

    private void createContentView(int type){
        ContentTextView textView = new ContentTextView(context);
        textView.setLineSpacing(CONTEXT_TEXT_LineSpacingExtra, CONTEXT_TEXT_LineSpacingMultiplier);
        textView.setWidth(getWidth());

    }

    private void createPageFootingView(String page){
        ContentTextView textView = new ContentTextView(context);
        textView.setWidth(getWidth());
        textView.setHeight(ScreenUtil.dpToPxInt(PAGE_FOOTING_VIEW_HEIGHT));
        textView.setMaxLines(1);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, PAGE_Footing_TEXT_SIZE);
        textView.setGravity(Gravity.CENTER);
        textView.setText(page);
        addView(textView);
    }

    private void createPictureView(int type){

    }

    //helper
    public static int getCurrentMaxLines(int currentUsableHeight){
        return (int)Math.ceil(currentUsableHeight
                /(ScreenUtil.dpToPx(CONTEXT_TEXT_LineSpacingExtra) + ScreenUtil.spToPx(CONTEXT_TEXT_SIZE)));

    }

}
