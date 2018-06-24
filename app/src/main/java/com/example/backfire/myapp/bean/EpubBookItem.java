package com.example.backfire.myapp.bean;

import com.example.backfire.myapp.utils.ScreenUtil;
import com.example.backfire.myapp.widget.readview.PageItemView;

/**
 * Created by backfire on 2018/6/16.
 */

public class EpubBookItem {
    private String text;
    private int type;
    private int typeDetail;
    private int itemHeightPx;

    //类型
    public static final int
            LABEL_CONTENTS = 0,
            LABEL_HEADING = 1,
            LABLE_PICTURE = 2;
    //类型细分
    public static final int
            LABEL_HEADING1_TYPE = 11,
            LABEL_HEADING2_TYPE = 12,
            LABEL_HEADING3_TYPE = 13,
            LABEL_HEADING4_TYPE = 14;

    public EpubBookItem(int type, int typeDetail, String text){
        this.type = type;
        this.typeDetail = typeDetail;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getType() {
        return type;
    }

    public int getTypeDetail() {
        return typeDetail;
    }

    public int getItemHeightPx() {
        return itemHeightPx;
    }

    //默认传入0
    public void setItemHeightPx(int textHeightPx) {
        switch(type){
            case LABEL_CONTENTS:
                itemHeightPx = textHeightPx;
                break;
            case LABEL_HEADING:
                switch (typeDetail){
                    case LABEL_HEADING1_TYPE:
                        itemHeightPx = (int)ScreenUtil.dpToPx(PageItemView.HEADING1_VIEW_HEIGHT);
                        break;
                    case LABEL_HEADING2_TYPE:
                        itemHeightPx = (int)ScreenUtil.dpToPx(PageItemView.HEADING2_VIEW_HEIGHT);
                        break;
                    case LABEL_HEADING3_TYPE:
                        itemHeightPx = (int)ScreenUtil.dpToPx(PageItemView.HEADING3_VIEW_HEIGHT);
                        break;
                    case LABEL_HEADING4_TYPE:
                        itemHeightPx = (int)ScreenUtil.dpToPx(PageItemView.HEADING4_VIEW_HEIGHT);
                        break;
                }
                break;
            case LABLE_PICTURE:

                break;
            default:
                break;
        }
    }
}
