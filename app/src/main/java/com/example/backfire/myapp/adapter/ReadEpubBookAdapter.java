package com.example.backfire.myapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.backfire.myapp.bean.BookBean;
import com.example.backfire.myapp.bean.EpubBook;
import com.example.backfire.myapp.bean.EpubBookPage;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * 横向滑动的RecyclerView实现阅读器效果
 * Created by backfire on 2018/6/13.
 */

public class ReadEpubBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<EpubBookPage> epubBookPages = new ArrayList<>();
    private Context context;

    public ReadEpubBookAdapter(Context context){
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return epubBookPages.size();
    }

    //Add items
    public void addItems(ArrayList<EpubBookPage> list) {
        epubBookPages.addAll(list);
        notifyDataSetChanged();
    }

    public void deleteAllItems() {
        epubBookPages.clear();
        notifyDataSetChanged();
    }

}
