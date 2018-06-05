package com.example.backfire.myapp.presenter.implPresenter;

import android.content.Context;
import android.util.Log;

import com.example.backfire.myapp.api.ApiManager;
import com.example.backfire.myapp.bean.BookBean;
import com.example.backfire.myapp.bean.DateBean;
import com.example.backfire.myapp.presenter.IBookstroePresenter;
import com.example.backfire.myapp.presenter.implView.IBookFragment;
import com.example.backfire.myapp.utils.StaticUtil;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by backfire on 2017/10/1.
 */

public class BookStorePresenterlmpl extends BasePresenterlmpl implements IBookstroePresenter {
    private IBookFragment iBookFragment;
    private Context context;

    public BookStorePresenterlmpl(Context context, IBookFragment iBookFragment) {
        this.context = context;
        this.iBookFragment = iBookFragment;
    }

    @Override
    public void getBookstoreData(String date, int page) {
        Subscription subscription = ApiManager.getInstance().getBookApiService().getBookResponseBody(date, page)
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
    public void getBookstoreData(String searchCondition) {
        Subscription subscription = ApiManager.getInstance().getBookApiService().getSearchResponseBody(searchCondition)
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
    public void getButtonMessage(String date, int page) {
        Subscription subscription = ApiManager.getInstance().getBookApiService().getButtonResponseBody(date, page)
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
                        iBookFragment.setDateList(getBookDateList(document));
                        iBookFragment.setTagList(getTagList(document));
                        iBookFragment.setCategoryList(getCategoryList(document));
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void getTagData(String tag, int page) {
        Subscription subscription = ApiManager.getInstance().getBookApiService().getTagResponseBody(tag, page)
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

    private ArrayList<BookBean> documentToList(Document document) {
        ArrayList<BookBean> bookBeanList = new ArrayList<>();
        if (document != null) {
            Elements elements = document.select("div#container").select("div#container-inner").select("div#primary").first().select("ul").select("li");
            for (Element element : elements) {

                String title = element.select("div.content").select("h2").select("a").attr("title");
                String url = element.select("div.thumbnail").select("div.img").select("a").attr("abs:href");
                String imgUrl = element.select("div.thumbnail").select("div.img").select("img").attr("abs:src");
                BookBean bookBean = new BookBean(title, url, imgUrl);
                bookBeanList.add(bookBean);
            }
        }

        return bookBeanList;
    }

    private ArrayList<DateBean> getBookDateList(Document document) {
        ArrayList<DateBean> dateBeanList = new ArrayList<>();
        if (document != null) {
            Elements elements = document.select("div#container").select("div#container-inner").select("div#sidebar").first().select("ul").select("li");
            dateBeanList = new ArrayList<>();
            for (int i = 0; i < elements.size() && i <= StaticUtil.DATE_MAX_NUM; i++) {
                String text = elements.get(i).select("a").text();
                String dateUrl = elements.get(i).select("a").attr("abs:href");
                String date = dateUrl.substring(StaticUtil.BOOK_BASE_URL.length() + 6, dateUrl.length());
                Log.i("date",text+","+date);
                DateBean dateBean = new DateBean(text, date);
                dateBeanList.add(dateBean);
            }

        }
        return dateBeanList;
    }


    private ArrayList<DateBean> getCategoryList(Document document) {
        ArrayList<DateBean> categoryBeanList = new ArrayList<>();
        if (document != null) {
            Elements elements = document.select("div#container").select("div#menu").first().select("ul").select("li");
            for (Element element : elements) {
                String category = element.select("a").text().trim();
                String categoryUrl = element.select("a").attr("abs:href");
                String categoryUrlReally = categoryUrl.substring(0,category.lastIndexOf("/")+1)+category;
                DateBean dateBean = new DateBean(category, categoryUrlReally);
                categoryBeanList.add(dateBean);
            }
        }
        return categoryBeanList;
    }

    private ArrayList<DateBean> getTagList(Document document) {
        ArrayList<DateBean> tagBeanList = new ArrayList<>();
        if (document != null) {
            Elements elements = document.select("div#container").select("div#footer").select("div.footbar").select("ul").first().select("a");
            for (Element element : elements) {
                String text = element.text();
                //String tagUrl = element.attr("abs:href");
                //String tagUrlReally = tagUrl.substring(0,tagUrl.lastIndexOf("/")+1)+text;
                DateBean dateBean = new DateBean(text, text);
                tagBeanList.add(dateBean);
            }

        }
        return tagBeanList;


    }

}
