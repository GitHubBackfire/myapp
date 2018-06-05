package com.example.backfire.myapp.presenter;

/**
 * Created by backfire on 2017/10/1.
 */

public interface IBookstroePresenter extends BasePresenter {
    void getBookstoreData(String date,int page);
    void getButtonMessage(String date,int page);
    void getTagData(String tag,int page);
    void getBookstoreData(String searchCondition);

}
