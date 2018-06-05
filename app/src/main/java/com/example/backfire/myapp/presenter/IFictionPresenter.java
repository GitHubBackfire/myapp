package com.example.backfire.myapp.presenter;

/**
 * Created by backfire on 2017/11/18.
 */

public interface IFictionPresenter extends BasePresenter {
    void getFictionData(String urlname);
    void getTextDetail(String url);
}
