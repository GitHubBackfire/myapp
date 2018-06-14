package com.example.backfire.myapp.bean;

import java.util.ArrayList;

/**
 * 一个EpubBookItem表示一页
 * 每一页包含若干个EpubBookItem
 * Created by backfire on 2018/6/14.
 */

public class EpubBookPage {
    private ArrayList<EpubBookItem> epubBookItems;
    public static final int
            LABEL_CONTENTS = 0,
            LABEL_TITLE = 1,
            LABLE_SECTION = 2,
            LABLE_PICTURE = 3;
    private int page;
    private int itemNum;

    EpubBookPage(){}


    class EpubBookItem {
        private String text;
        private int type;
        public EpubBookItem(int type, String text){
            this.type = type;
            this.text = text;
        }

    }

}
