package com.example.backfire.myapp.presenter.implPresenter;

import android.content.Context;

import com.example.backfire.myapp.presenter.implView.IReadEpubBookActivity;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.Observers;
import rx.schedulers.Schedulers;

/**
 * Created by backfire on 2018/6/18.
 */

public class EpubBookPresenterImpl extends BasePresenterImpl {
    private IReadEpubBookActivity iReadEpubBookActivity;
    private Context context;

    EpubBookPresenterImpl(Context context, IReadEpubBookActivity iReadEpubBookActivity){
        this.context = context;
        this.iReadEpubBookActivity = iReadEpubBookActivity;
    }

    //
    public void parseEpubBookByHtml(final String html){
    }

}
