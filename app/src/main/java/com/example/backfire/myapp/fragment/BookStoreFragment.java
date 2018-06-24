package com.example.backfire.myapp.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backfire.myapp.R;
import com.example.backfire.myapp.activity.BookDetailWebActivity;
import com.example.backfire.myapp.activity.SearchActivity;
import com.example.backfire.myapp.adapter.BookStoreAdapter;
import com.example.backfire.myapp.adapter.ButtonsAdapter;
import com.example.backfire.myapp.bean.BookBean;
import com.example.backfire.myapp.bean.DateBean;
import com.example.backfire.myapp.presenter.implPresenter.BookStorePresenterImpl;
import com.example.backfire.myapp.presenter.implView.IBookFragment;
import com.example.backfire.myapp.utils.ScreenUtil;
import com.example.backfire.myapp.utils.StaticUtil;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by backfire on 2017/9/30.
 */

public class BookStoreFragment extends BaseFragment implements IBookFragment, View.OnClickListener {

    private View view;
    private BookStorePresenterImpl bookStorePresenterImpl;
    private BookStoreAdapter bookStoreAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private RecyclerView.OnScrollListener loadingMoreListener;
    private ConnectivityManager.NetworkCallback connectivityCallback;
    private boolean loading;

    private int currentPage = 1;
    private String currentDate;
    private String currentTag;
    private String currentCategory;

    private final static int STATE_DATE = 1;
    private final static int STATE_TAG = 2;
    private final static int STATE_CATEGORY = 3;
    private int currentState = STATE_DATE;

    private boolean isInitTagButtons = false;

    @BindView(R.id.recycle_book)
    RecyclerView recyclerBook;

    @BindView(R.id.relative_search)
    RelativeLayout relativeSearch;

    @BindView(R.id.btn_date)
    Button btnDate;
    @BindView(R.id.btn_tag)
    Button btnTag;
    @BindView(R.id.btn_category)
    Button btnCategory;
    @BindView(R.id.tv_book_name)
    TextView tvBookName;

    @BindView(R.id.lin_buttons)
    LinearLayout linButtons;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private View popupContainer;
    private PopupWindow popupButtons;
    private ButtonsAdapter tagAdapter;
    private ButtonsAdapter categoryAdapter;
    private ButtonsAdapter dateAdapter;
    private RecyclerView recyclerButtons;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        view = inflater.inflate(R.layout.fragment_bookstore, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialDate();
        initialView();
        loadButtonDate();
        getCurrentDate();
        loadDateData(currentDate);

    }

    private void getCurrentDate(){
        int year;
        int month;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Calendar calendar = null;
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
        }else{
            year = 2018;
            month = 1;
        }
        currentDate = year+"/"+month;
    }


    @Override
    public void onPause() {
        super.onPause();
        popupButtons.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        popupButtons.dismiss();
        popupButtons = null;
        if(loadingMoreListener != null){
            loadingMoreListener = null;
        }
    }

    private void initialDate() {
        int mImageWidth = (int) (ScreenUtil.getDeviceInfo(getContext())[0] / (3.4f));
        int mImageHeigh = (int) (1.6 * mImageWidth);
        bookStorePresenterImpl = new BookStorePresenterImpl(getContext(), this);
        bookStoreAdapter = new BookStoreAdapter(getContext(),mImageWidth,mImageHeigh);
        bookStoreAdapter.setMyOnItemClickListener(new BookStoreAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //do something
                Intent intent = new Intent(getContext(), BookDetailWebActivity.class);
                intent.putExtra(StaticUtil.BOOK_DETAIL_URL, bookStoreAdapter.getBookBeanArrayList().get(position).getUrl());
                getContext().startActivity(intent);

            }
        });
        bookStoreAdapter.setMyOnItemLongClickListener(new BookStoreAdapter.MyOnItemLongClickListener() {
            @Override
            public void onItemLongClick( int position) {
                Toast.makeText(getContext(),"长按"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initialView() {
        relativeSearch.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnTag.setOnClickListener(this);
        btnCategory.setOnClickListener(this);
        setPopupWindow();
        initialListener();
        mLinearLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerBook.setLayoutManager(mLinearLayoutManager);
        recyclerBook.setAdapter(bookStoreAdapter);
        recyclerBook.addOnScrollListener(loadingMoreListener);
    }

    private void initialTagButtons(){
        //初始化下拉按钮中的数据
        isInitTagButtons = false;
        tagAdapter = new ButtonsAdapter(getContext());
        categoryAdapter = new ButtonsAdapter(getContext());
        dateAdapter = new ButtonsAdapter(getContext());

        tagAdapter.setMyOnButtonClickListener(new ButtonsAdapter.MyOnButtonClickListener() {
            @Override
            public void myOnButtonClick(View view, int position) {
                currentPage = 1;
                currentState = STATE_TAG;
                currentTag = tagAdapter.getButtonsArrayList().get(position).getParam();
                loadTagData(currentTag);


            }
        });
        dateAdapter.setMyOnButtonClickListener(new ButtonsAdapter.MyOnButtonClickListener() {
            @Override
            public void myOnButtonClick(View view, int position) {
                currentPage = 1;
                currentState = STATE_DATE;
                currentDate = dateAdapter.getButtonsArrayList().get(position).getParam();
                loadDateData(currentDate);


            }
        });
        categoryAdapter.setMyOnButtonClickListener(new ButtonsAdapter.MyOnButtonClickListener() {
            @Override
            public void myOnButtonClick(View view, int position) {
                currentPage = 1;
                currentState = STATE_CATEGORY;
                currentCategory = categoryAdapter.getButtonsArrayList().get(position).getParam();
                loadCategoryData(currentCategory);

            }
        });
    }



    private void initialListener() {
        loadingMoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //向下滚动
                {
                    int visibleItemCount = mLinearLayoutManager.getChildCount();
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    int pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        loadMoreData(currentState);

                    }
                }
            }
        };
    }

    private void loadSearchData(String bookName){
        if(bookStoreAdapter != null){
            bookStoreAdapter.deleteAllItems();
            bookStorePresenterImpl.getBookstoreData(bookName);
        }
    }


    private void loadDateData(String date) {
        if(bookStoreAdapter != null){
            bookStoreAdapter.deleteAllItems();
            bookStorePresenterImpl.getBookstoreData(date, currentPage);
        }

    }

    private void loadTagData(String tag) {
        if(bookStoreAdapter != null){
            bookStoreAdapter.deleteAllItems();
            bookStorePresenterImpl.getTagData(tag,currentPage);
        }
    }

    private void loadCategoryData(String category) {
        bookStoreAdapter.deleteAllItems();

    }


    private void loadMoreData(int state) {
        currentPage++;
        switch (currentState) {
            case STATE_DATE:
                bookStorePresenterImpl.getBookstoreData(currentDate,currentPage);
                break;
            case STATE_TAG:
                bookStorePresenterImpl.getTagData(currentTag,currentPage);
                break;
            case STATE_CATEGORY:
                break;
        }

    }

    private void loadOtherData(String date) {

        currentPage = 1;
        bookStorePresenterImpl.getBookstoreData(date, currentPage);
    }




    private void loadButtonDate() {
        bookStorePresenterImpl.getButtonMessage("2017/11", 1);
        if(!isInitTagButtons){
            initialTagButtons();
        }
    }


    @Override
    public void updateList(ArrayList<BookBean> bookBeanArrayList) {
        //开始更新UI
        /*switch (currentState) {
            case STATE_DATE:
                break;
            case STATE_TAG:
                break;
            case STATE_CATEGORY:
                break;
        }*/
        bookStoreAdapter.addItems(bookBeanArrayList);
        loading = false;

    }

    @Override
    public void getListError() {
        currentPage --;
    }

    /**
     * 设置下拉按钮中的值
     *
     * @param dateList
     */

    @Override
    public void setDateList(ArrayList<DateBean> dateList) {
        dateAdapter.addItems(dateList);
    }

    @Override
    public void setCategoryList(ArrayList<DateBean> categoryList) {
        categoryAdapter.addItems(categoryList);
    }

    @Override
    public void setTagList(ArrayList<DateBean> tagList) {
        tagAdapter.addItems(tagList);
    }

    @Override
    public void upTextDetail(ArrayList<Map<String, String>> mapList) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_search:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                //for result
                startActivityForResult(intent,StaticUtil.REQUEST_CODE_SEARCH);
                break;
            case R.id.btn_date:
                showDateWindow();
                break;
            case R.id.btn_tag:
                showTagPopupWindow();
                break;
            case R.id.btn_category:
                showCategoryWindow();
                break;

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == StaticUtil.REQUEST_CODE_SEARCH && resultCode == StaticUtil.RESULT_CODE_SEARCH){
            String bookName = data.getStringExtra(StaticUtil.SEARCH_BOOK_NAME);
            loadSearchData(bookName);
            tvBookName.setText(bookName);

        }

    }

    private void showTagPopupWindow() {
        recyclerButtons.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        popupButtons.showAsDropDown(linButtons);
        recyclerButtons.setAdapter(tagAdapter);


    }

    private void showCategoryWindow() {
        recyclerButtons.setLayoutManager(new LinearLayoutManager(getContext()));
        popupButtons.showAsDropDown(linButtons);
        recyclerButtons.setAdapter(categoryAdapter);

    }

    private void showDateWindow() {
        recyclerButtons.setLayoutManager(new LinearLayoutManager(getContext()));
        popupButtons.showAsDropDown(linButtons);
        recyclerButtons.setAdapter(dateAdapter);
    }


    private void setPopupWindow() {
        popupContainer = LayoutInflater.from(getContext()).inflate(R.layout.popup_dropdown, null);
        popupButtons = new PopupWindow(getContext());
        popupButtons.setContentView(popupContainer);
        popupButtons.setBackgroundDrawable(new ColorDrawable(0XFFFFFF));
        popupButtons.setOutsideTouchable(false);
        popupButtons.setFocusable(true);
        popupButtons.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupButtons.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        recyclerButtons = popupContainer.findViewById(R.id.recycler_buttons);

    }


    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {

    }
}
