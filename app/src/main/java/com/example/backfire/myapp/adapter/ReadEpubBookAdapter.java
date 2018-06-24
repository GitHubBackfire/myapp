package com.example.backfire.myapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.backfire.myapp.R;
import com.example.backfire.myapp.bean.BookBean;
import com.example.backfire.myapp.bean.EpubBook;
import com.example.backfire.myapp.bean.EpubBookPage;
import com.example.backfire.myapp.widget.readview.PageItemView;

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
        //return new BookStoreAdapter.BookStoreHolder(LayoutInflater.from(context).inflate(R.layout.item_bookstore, parent, false), myOnItemClickListener, myOnItemLongClickListener);
        return  new ReadEpubBookViewHolder(new PageItemView(context));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PageItemView view = ((PageItemView)holder.itemView);
        view.setEpubBookPage(epubBookPages.get(position));

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

    public static class ReadEpubBookViewHolder extends RecyclerView.ViewHolder{

        public ReadEpubBookViewHolder(PageItemView pageItemView) {
            super(pageItemView);
        }
    }


}
