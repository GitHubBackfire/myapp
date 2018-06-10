package com.example.backfire.myapp.fragment;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.backfire.myapp.R;
import com.example.backfire.myapp.activity.TextDetailActivity;
import com.example.backfire.myapp.adapter.BookStoreAdapter;
import com.example.backfire.myapp.bean.BookBean;
import com.example.backfire.myapp.bean.DateBean;
import com.example.backfire.myapp.presenter.implPresenter.FictionPresenterlmpl;
import com.example.backfire.myapp.presenter.implView.IBookFragment;
import com.example.backfire.myapp.utils.DensityUtil;
import com.example.backfire.myapp.utils.StaticUtil;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by backfire on 2017/9/30.
 */

public class FictionReaderFragment extends BaseFragment implements IBookFragment {
    private View view;
    private FictionPresenterlmpl fictionPresenterlmpl;
    private BookStoreAdapter fictionAdapter;
    private RecyclerView.OnScrollListener loadingMoreListener;
    private ConnectivityManager.NetworkCallback connectivityCallback;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean loading;
    private int currentPage = 1;
    private final String firstPageUrl = "http://www.wcsfa.com/scfbox_list-1-2.html";


    @BindView(R.id.recycle_fiction)
    RecyclerView recyclerFiction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        view = inflater.inflate(R.layout.fragment_fiction, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialDate();
        initialView();
        loadDate();
    }

    private void initialDate() {
        fictionPresenterlmpl = new FictionPresenterlmpl(getContext(), this);
        int mImageWidth = (int) (DensityUtil.getDeviceInfo(getContext())[0] / (3.4f));
        int mImageHeigh = (int) (1.6 * mImageWidth);
        fictionAdapter = new BookStoreAdapter(getContext(),mImageWidth,mImageHeigh);
        fictionAdapter.setMyOnItemClickListener(new BookStoreAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), TextDetailActivity.class);
                String url =fictionAdapter.getBookBeanArrayList().get(position).getUrl();
                String title= fictionAdapter.getBookBeanArrayList().get(position).getTitle();
                intent.putExtra(StaticUtil.TEXT_DETAIL_URL,url);
                intent.putExtra(StaticUtil.TEXT_TITLE,title);
                getActivity().startActivity(intent);
            }
        });
    }

    private void initialView() {
        initialListener();
        mLinearLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerFiction.setLayoutManager(mLinearLayoutManager);
        recyclerFiction.setAdapter(fictionAdapter);
        recyclerFiction.addOnScrollListener(loadingMoreListener);

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
                        loadMoreDate();
                    }
                }
            }
        };
    }

    private void loadDate() {
        currentPage = 1;
        fictionPresenterlmpl.getFictionData(firstPageUrl);

    }


    private void loadMoreDate() {
        if(currentPage <= StaticUtil.FICTION_PAGE_MAX){
            currentPage++;
            String pageUrl = "http://www.wcsfa.com/scfbox_list-"+currentPage+"-2.html";
            fictionPresenterlmpl.getFictionData(pageUrl);
        }
        else{
            //无更多数据
        }

    }


    @Override
    public void updateList(ArrayList<BookBean> bookBeanArrayList) {
        fictionAdapter.addItems(bookBeanArrayList);
        loading =false;
    }

    @Override
    public void getListError() {

    }


    //无需实现
    @Override
    public void setDateList(ArrayList<DateBean> dateList) {

    }

    @Override
    public void setCategoryList(ArrayList<DateBean> categoryList) {

    }

    @Override
    public void setTagList(ArrayList<DateBean> tagList) {

    }



    @Override
    public void upTextDetail(ArrayList<Map<String, String>> mapList) {

    }


    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String error) {

    }
}
