package com.example.backfire.myapp.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.backfire.myapp.R;
import com.example.backfire.myapp.bean.BookBean;
import com.example.backfire.myapp.widget.BookstoreListImageView;

import java.util.ArrayList;


/**
 * 我的书屋和科幻星云小说列表的adapter
 * Created by backfire on 2017/10/11.
 */

public class BookStoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_LOADING_MORE = -1;
    private static final int TYPE_NORMAL = 1;
    private boolean loadingMore;
    //图片宽度自适应屏幕宽度
    private int mImageWidth;
    private int mImageHeigh;


    private ArrayList<BookBean> bookBeanArrayList = new ArrayList<>();
    private Context context;

    private MyOnItemClickListener myOnItemClickListener;
    private MyOnItemLongClickListener myOnItemLongClickListener;



    public void setMyOnItemClickListener(MyOnItemClickListener listener) {
        myOnItemClickListener = listener;
    }

    public void setMyOnItemLongClickListener(MyOnItemLongClickListener listener){
        myOnItemLongClickListener = listener;
    }


    public BookStoreAdapter(Context context) {

        this.context = context;
    }

    public BookStoreAdapter(Context context, int width, int height) {
        this.context = context;
        mImageWidth = width;
        mImageHeigh = height;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NORMAL:
                return new BookStoreHolder(LayoutInflater.from(context).inflate(R.layout.item_bookstore, parent, false), myOnItemClickListener, myOnItemLongClickListener);
            case TYPE_LOADING_MORE:
                return new LoadingMoreHolder(LayoutInflater.from(context).inflate(R.layout.infinite_loading, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_NORMAL:
                //
                bindViewHolderNormal((BookStoreHolder) holder, position);
                break;
            case TYPE_LOADING_MORE:
                //
                bindLoadingViewHold((LoadingMoreHolder) holder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return bookBeanArrayList.size();
    }

    public ArrayList<BookBean> getBookBeanArrayList() {
        return bookBeanArrayList;
    }


    @Override
    public int getItemViewType(int position) {
        if (position < getDataItemCount() && getDataItemCount() > 0) {
            return TYPE_NORMAL;
        }
        return TYPE_LOADING_MORE;
    }

    private int getDataItemCount() {
        return bookBeanArrayList.size();
    }

    private int getLoadingMoreItemPosition() {
        return loadingMore ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }


    //Add items
    public void addItems(ArrayList<BookBean> list) {
        bookBeanArrayList.addAll(list);
        notifyDataSetChanged();
    }

    public void deleteAllItems() {
        bookBeanArrayList.clear();
        notifyDataSetChanged();


    }

    private void bindLoadingViewHold(LoadingMoreHolder holder, int position) {
        holder.progressBar.setVisibility(loadingMore == true ? View.VISIBLE : View.INVISIBLE);

    }

    private void bindViewHolderNormal(final BookStoreHolder holder, final int position) {
        holder.tvTitle.setText(bookBeanArrayList.get(position).getTitle());
      /*  holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookDetailWebActivity.class);
                intent.putExtra(StaticUtil.BOOK_DETAIL_URL, bookBeanArrayList.get(position).getUrl());
                context.startActivity(intent);
            }
        });*/

        Glide.with(context)
                .load(bookBeanArrayList.get(position).getImgUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                }).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop().override(mImageWidth, mImageHeigh)
                .into(holder.imageView);


    }


    private static class LoadingMoreHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingMoreHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }

    private static class BookStoreHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener{
        BookstoreListImageView imageView;
        TextView tvTitle;
        LinearLayout linearLayout;
        MyOnItemClickListener listener;
        MyOnItemLongClickListener longClickListener;

        public BookStoreHolder(View itemView, MyOnItemClickListener listener, MyOnItemLongClickListener longClickListener) {
            super(itemView);
            this.listener = listener;
            this.longClickListener = longClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            imageView = itemView.findViewById(R.id.img_book);
            tvTitle = itemView.findViewById(R.id.tv_book);
            linearLayout = itemView.findViewById(R.id.lin_container);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClick(view, getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if(longClickListener != null){
                longClickListener.onItemLongClick(getLayoutPosition());
            }
            return  true;
        }
    }

    public interface MyOnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface MyOnItemLongClickListener{
        void onItemLongClick(int position);

    }


}
