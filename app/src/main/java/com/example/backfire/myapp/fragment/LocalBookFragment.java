package com.example.backfire.myapp.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.backfire.myapp.R;
import com.example.backfire.myapp.activity.ReadBookActivity;
import com.example.backfire.myapp.adapter.BookStoreAdapter;
import com.example.backfire.myapp.bean.BookBean;
import com.example.backfire.myapp.presenter.implPresenter.LocalBookPresenterImlp;
import com.example.backfire.myapp.presenter.implView.ILocalBookFragment;
import com.example.backfire.myapp.utils.FileUtil;
import com.example.backfire.myapp.utils.ScreenUtil;
import com.example.backfire.myapp.utils.StaticUtil;
import java.io.File;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * just test open epub book...
 * Created by backfire on 2018/6/12
 *
 */

public class LocalBookFragment extends BaseFragment implements ILocalBookFragment{
    private View view;
    @BindView(R.id.recycle_local)
    RecyclerView recyclerLocal;
    private BookStoreAdapter bookStoreAdater;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<BookBean> epubBooks;
    private LocalBookPresenterImlp localBookPresenterImlp;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        view = inflater.inflate(R.layout.fragment_book_download, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initalData();

        getLocalEpubBooks();
        if(epubBooks.size() >= 0){
            bookStoreAdater.addItems(epubBooks);
        }else{
            localBookPresenterImlp = new LocalBookPresenterImlp(getContext(),this);
            localBookPresenterImlp.copyEpubBooks();
        }
    }

    private void initalData() {
        int mImageWidth = (int) (ScreenUtil.getDeviceInfo(getContext())[0] / (3.4f));
        int mImageHeigh = (int) (1.6 * mImageWidth);
        bookStoreAdater = new BookStoreAdapter(getContext(),mImageWidth,mImageHeigh);
        mLinearLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerLocal.setLayoutManager(mLinearLayoutManager);
        recyclerLocal.setAdapter(bookStoreAdater);
        bookStoreAdater.setMyOnItemClickListener(new BookStoreAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ReadBookActivity.class);
                intent.putExtra(StaticUtil.LOCAL_BOOKS_FILE_NAME, epubBooks.get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    private void getLocalEpubBooks(){
        epubBooks = new ArrayList<>();
        File localBooksFile = new File(FileUtil.getLocalBooksFilePath());
        if(localBooksFile.exists() && localBooksFile.isDirectory()){
            for(File file : localBooksFile.listFiles()){
                String type = file.getName().substring(file.getName().lastIndexOf(".")+1, file.getName().length());
                if(type.equals("epub")){
                    String bookPath = file.getAbsolutePath();
                    String bookName = bookPath.substring(bookPath.lastIndexOf("/")+1, bookPath.lastIndexOf("."));
                    BookBean bookBean = new BookBean(bookName,bookPath,"");
                    epubBooks.add(bookBean);
                }
            }
        }

    }

    @Override
    public void updataList(boolean isSuccess) {
        if(isSuccess){
            getLocalEpubBooks();
            if(epubBooks.size() >= 0){
                bookStoreAdater.addItems(epubBooks);
            }else{
                //无本地epub文件
            }
        }
    }
}
