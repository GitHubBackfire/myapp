package com.example.backfire.myapp.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.backfire.myapp.R;
import com.example.backfire.myapp.api.ApiManager;
import com.example.backfire.myapp.callbackInterface.IBackCallBack;
import com.example.backfire.myapp.callbackInterface.ISearchCallBack;
import com.example.backfire.myapp.utils.StaticUtil;
import com.example.backfire.myapp.widget.SearchView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by backfire on 2017/11/10.
 */

public class SearchActivity extends BaseActivity  {
    @BindView(R.id.search_view)
    SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        searchView.setOnSearchViewListener(new SearchView.SearchViewListener() {
            @Override
            public void backAction() {
                finish();
            }

            @Override
            public void searchAction(String bookName) {
                Intent intent = new Intent();
                intent.putExtra(StaticUtil.SEARCH_BOOK_NAME,bookName);
                setResult(StaticUtil.RESULT_CODE_SEARCH,intent);
                finish();
            }
        });
    }
}
