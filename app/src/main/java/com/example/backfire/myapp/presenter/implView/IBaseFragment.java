package com.example.backfire.myapp.presenter.implView;

/**
 * Created by backfire on 2017/9/18.
 */

public interface IBaseFragment {
    void showProgressDialog();
    void hideProgressDialog();
    void showError(String error);
}
