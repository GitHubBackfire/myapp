package com.example.backfire.myapp.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.backfire.myapp.R;
import com.example.backfire.myapp.utils.StaticUtil;
import com.example.backfire.myapp.widget.SearchView;
import butterknife.BindView;
import butterknife.ButterKnife;

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
