package com.example.backfire.myapp.bean;

import com.example.backfire.myapp.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 一个EpubBookItem表示一页
 * 每一页包含若干个EpubBookItem
 * Created by backfire on 2018/6/14.
 */

public class EpubBookPage {
    public final static int PAGE_HEIGHT_PX = ScreenUtil.getScreenHeight();
    private ArrayList<EpubBookItem> epubBookItems;
    private int currentPage;

    public ArrayList<EpubBookItem> getEpubBookItems() {
        return epubBookItems;
    }

    public EpubBookPage(int currentPage, ArrayList<EpubBookItem> epubBookItems){
        this.currentPage = currentPage;
        this.epubBookItems = epubBookItems;
    }

    public int getCurrentPage() {
        return currentPage;
    }

}
