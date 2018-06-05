package com.example.backfire.myapp.presenter.implPresenter;

import android.content.Context;
import android.util.Log;

import com.example.backfire.myapp.api.ApiManager;
import com.example.backfire.myapp.bean.BookBean;
import com.example.backfire.myapp.presenter.IBookstroePresenter;
import com.example.backfire.myapp.presenter.IFictionPresenter;
import com.example.backfire.myapp.presenter.implView.IBookFragment;
import com.example.backfire.myapp.utils.StaticUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by backfire on 2017/10/1.
 */

public class FictionPresenterlmpl extends BasePresenterlmpl implements IFictionPresenter {
    private IBookFragment iBookFragment;
    private ArrayList<BookBean> bookBeanList;
    private Context context;


    public FictionPresenterlmpl(Context context, IBookFragment iBookFragment) {
        this.context = context;
        this.iBookFragment = iBookFragment;
    }


    @Override
    public void getFictionData(String urlname) {
        Subscription subscription = ApiManager.getInstance().getFictionService().getFictionResponseBody(urlname)
                .map(new Func1<ResponseBody, Document>() {
                    @Override
                    public Document call(ResponseBody responseBody) {

                        String html = null;

                        try {
                            html = responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (html != null && html.length() > 0) {
                            return Jsoup.parse(html);
                        }
                        return Jsoup.parse(StaticUtil.ERROR_HTML);

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Document>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Document document) {
                        iBookFragment.updateList(documentToList(document));
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void getTextDetail(String url) {
        Subscription subscription = ApiManager.getInstance().getFictionService().getFictionResponseBody(url)
                .map(new Func1<ResponseBody, Document>() {
                    @Override
                    public Document call(ResponseBody responseBody) {

                        String html = null;

                        try {
                            html = responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (html != null && html.length() > 0) {
                            return Jsoup.parse(html);
                        }
                        return Jsoup.parse(StaticUtil.ERROR_HTML);

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Document>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Document document) {
                            iBookFragment.upTextDetail(getBasicDetail(document));

                    }
                });
        addSubscription(subscription);
    }


    /**
     * @param document
     * @return
     */
    private ArrayList<BookBean> documentToList(Document document) {
        ArrayList<BookBean> bookBeanList = new ArrayList<>();
        if (document != null) {
            Elements masterElements = document.select("div.list_item").first().select("ul").select("li");
            for (Element element : masterElements) {
                String imgUrl = element.select("a.thumb").select("img").attr("src");
                String textUrl = element.select("a.thumb").attr("href");
                String title = element.select("a.list_item_t").text();
                String brief = element.select("a.thumb").select("div.thumb_info").select("p").text();
                String time = element.select("div.list_item_tag").select("span").text();
                BookBean bookBean = new BookBean(title, StaticUtil.FICTION_BASE_URL + textUrl, StaticUtil.FICTION_BASE_URL + imgUrl);
                bookBeanList.add(bookBean);

            }

        }
        return bookBeanList;
    }

    public ArrayList<Map<String, String>> getBasicDetail(Document document) {
        ArrayList<Map<String, String>> mapList = new ArrayList<>();

        Elements articleElements = document.select("div.article").first().select("div.text").select("p");

        for (int i = 0; i < articleElements.size(); i++) {
            Map<String, String> singleLine = new HashMap<>();
            if (i == 0) {
            }
            String line = articleElements.get(i).select("span").text();
            if (line.length() > 0 && line.toString().trim().length() > 0) {
                singleLine.put("text", line);
            }
            mapList.add(singleLine);
        }
        return mapList;
    }


}
