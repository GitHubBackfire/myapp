package com.example.backfire.myapp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.example.backfire.myapp.R;
import com.example.backfire.myapp.adapter.ReadEpubBookAdapter;
import com.example.backfire.myapp.bean.EpubBookItem;
import com.example.backfire.myapp.bean.EpubBookPage;
import com.example.backfire.myapp.presenter.implPresenter.EpubBookPresenterImpl;
import com.example.backfire.myapp.utils.FileUtil;
import com.example.backfire.myapp.utils.ScreenUtil;
import com.example.backfire.myapp.utils.StaticUtil;
import com.example.backfire.myapp.utils.StringUtil;
import com.example.backfire.myapp.widget.readview.PageItemView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;

public class ReadBookActivity extends BaseActivity {
    @BindView(R.id.recycler_book)
    RecyclerView recyclerBook;

    ReadEpubBookAdapter readEpubBookAdapter;
    EpubBookPresenterImpl epubBookPresenter;

    ArrayList<EpubBookPage> epubBookPages = new ArrayList<>();
    ArrayList<EpubBookItem> epubBookItems = new ArrayList<>();

    private int currentPage = 0;
    private int currentUsableHeight = ScreenUtil.getScreenHeight();

    //当页存不下去的String,一个临时储存变量
    private String strBelongNextPage = "";
    private EpubBookItem belongNextPageBookItem = null;
    private EpubBookPage epubBookPage;


    private String localBookPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        ButterKnife.bind(this);
        localBookPath = getIntent().getStringExtra(StaticUtil.LOCAL_BOOKS_FILE_NAME);
        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerBook.setLayoutManager(manager);
        // 将SnapHelper attach 到RecyclrView
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerBook);

        readEpubBookAdapter = new ReadEpubBookAdapter(this);
        recyclerBook.setAdapter(readEpubBookAdapter);
    }

    private void initData() {
        openEpubBook(localBookPath);
    }

    private void openEpubBook(String path){
        Log.i("path1",path);
        File file = new File(path);
        try {
            EpubReader epubReader = new EpubReader();
            InputStream inputStream = new FileInputStream(file);
            Book book = epubReader.readEpub(inputStream);
           /* Spine spine = book.getSpine();
            List<SpineReference> spineReferences = spine.getSpineReferences();
            if(spineReferences != null && spineReferences.size() > 0){
               *//* Resource resource = spineReferences.get(1).getResource();
                byte[] data = resource.getData();
                String strHtml = StringUtil.bytes2Hex(data);
                Log.i("spine",resource.getData().toString());
                Log.i("spine2",strHtml);*//*
                for(SpineReference spineReference:spineReferences){
                    byte[] data = spineReference.getResource().getData();
                    String strHtml = StringUtil.bytes2Hex(data);
                }
            }*/
            List<Resource> contents = book.getContents();
            if (contents != null && contents.size() > 0) {
                try {
                    for(Resource resource:contents){
                        InputStream inputStreamContent = resource.getInputStream();
                        String str = StringUtil.convertStreamToString(inputStreamContent);
                        Log.i("content",str);
                        parseEpubBook(str);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void unzipEpubBook(String path,String name){
        String targetPath = FileUtil.getLocalBooksFilePath()+"/"+name;
        FileUtil.singleZip(path,targetPath);
        parseEpubBook(targetPath);
    }


    private void getSomething(List<Resource> contents){
    }

    private void parseEpubBook(String html){
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("body");
        Log.i("htmlhtml",html);

        for(Element element:elements){
            String text = element.select("p").text();
            String headingText = "";
            String imgUrl = "";
            String h1 = "", h2 = "", h3 = "", h4 = "";
            int currentType = -1;
            int currentTypeDetail = -1;

            if(TextUtils.isEmpty(text)){
                h1 = element.select("h1").text();
                if(TextUtils.isEmpty(h1)){
                    h2 = element.select("h2").text();
                    if(TextUtils.isEmpty(h2)){
                        h3 = element.select("h3").text();
                        if(TextUtils.isEmpty("h3")){
                            h4 = element.select("h4").text();
                            if(TextUtils.isEmpty(h4)){
                                break;
                            }else{
                                currentType = EpubBookItem.LABEL_HEADING;
                                currentTypeDetail = EpubBookItem.LABEL_HEADING4_TYPE;
                                headingText = h4;
                                createEpubBookItem(currentType, currentTypeDetail, headingText);
                                break;
                            }
                        }else{
                            currentType = EpubBookItem.LABEL_HEADING;
                            currentTypeDetail = EpubBookItem.LABEL_HEADING3_TYPE;
                            headingText = h3;
                            createEpubBookItem(currentType, currentTypeDetail, headingText);
                            break;
                        }
                    }else{
                        currentType = EpubBookItem.LABEL_HEADING;
                        currentTypeDetail = EpubBookItem.LABEL_HEADING2_TYPE;
                        headingText = h2;
                        createEpubBookItem(currentType, currentTypeDetail, headingText);
                        break;
                    }
                }else{
                    currentType = EpubBookItem.LABEL_HEADING;
                    currentTypeDetail = EpubBookItem.LABEL_HEADING1_TYPE;
                    headingText = h1;
                    createEpubBookItem(currentType, currentTypeDetail, headingText);
                    break;
                }
            }else{
                currentType = EpubBookItem.LABEL_CONTENTS;
                imgUrl = "";
                createEpubBookItems(currentType, currentTypeDetail, text);
                break;
            }
        }

    }

    //一个标题为一个Item
    private void createEpubBookItem(int type, int typeDetail, String text){
        EpubBookItem epubBookItem = new EpubBookItem(type, typeDetail, text);
        epubBookItem.setItemHeightPx(0);

        if(currentUsableHeight - epubBookItem.getItemHeightPx() >= 0){
            epubBookItems.add(epubBookItem);
            currentUsableHeight -= epubBookItem.getItemHeightPx();
        }else{
            createEpubBookPage();
            belongNextPageBookItem = new EpubBookItem(type, typeDetail, text);
        }
    }

    //一段文字为若干个item
    private void createEpubBookItems(int type, int typeDetail, String text){
        //总共能容纳的最大行数
        int currentMaxLine = PageItemView.getCurrentMaxLines(currentUsableHeight);
        text.substring(0,2);

    }

    private void createEpubBookPage() {

        if (epubBookItems != null && epubBookItems.size() >= 0) {
            currentUsableHeight = EpubBookPage.PAGE_HEIGHT_PX;
            currentPage++;
            EpubBookPage epubBookPage = new EpubBookPage(currentPage, epubBookItems);
            epubBookPages.add(epubBookPage);
            epubBookItems.clear();
        }else{

        }
    }


}
