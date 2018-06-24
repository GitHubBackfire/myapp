package com.example.backfire.myapp.presenter.implPresenter;


import com.example.backfire.myapp.presenter.BasePresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by backfire on 2017/9/19.
 */

public class BasePresenterImpl implements BasePresenter {

    private CompositeSubscription mCompositeSubscription;

    public void addSubscription(Subscription s) {
        if(this.mCompositeSubscription == null){
            this.mCompositeSubscription = new CompositeSubscription();
        }
    }
    @Override
    public void unsubscrible() {
        if(this.mCompositeSubscription != null){
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
