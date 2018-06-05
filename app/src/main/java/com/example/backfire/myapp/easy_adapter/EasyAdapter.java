package com.example.backfire.myapp.easy_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.backfire.myapp.callbackInterface.DataHelper;

import java.util.List;

/**
 * Created by backfire on 2017/12/12.
 */

public class EasyAdapter<T> extends RecyclerView.Adapter<EasyHolder> implements DataHelper<T> {
    protected Context mContext;
    protected List<T> mList;
    protected int[] layoutIds;
    protected LayoutInflater mLInflater;

    private SparseArray<View> mConvertViews = new SparseArray<>();

    public EasyAdapter(Context context, List<T> list, int... layoutIds) {
        this.mContext = context;
        this.mList = list;
        this.layoutIds = layoutIds;
        this.mLInflater = LayoutInflater.from(mContext);
    }


    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < 0 || viewType > layoutIds.length) {
            throw new ArrayIndexOutOfBoundsException("index");
        }
        if (layoutIds.length == 0) {
            throw new IllegalArgumentException("not layout");
        }
        int layoutId = layoutIds[viewType];
        View view = mConvertViews.get(layoutId);
        if (view == null) {
            view = mLInflater.inflate(layoutId, parent, false);
        }
        EasyHolder viewHolder = (EasyHolder) view.getTag();

        return null;
    }


    @Override
    public void onBindViewHolder(EasyHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIndex(position, mList.get(position));

    }

    /**
     * 指定item布局样式在layoutIds的索引。默认为第一个
     *
     * @param position
     * @param item
     * @return
     */
    private int getLayoutIndex(int position, T item) {
        return 0;
    }

    //
    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public boolean addAll(List<T> list) {
        return false;
    }

    @Override
    public boolean addAll(int position, List<T> list) {
        return false;
    }

    @Override
    public void add(T data) {

    }

    @Override
    public void add(int position, T data) {

    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(T data) {
        return false;
    }

    @Override
    public T getData(int index) {
        return null;
    }

    @Override
    public void modify(T oldData, T newData) {

    }

    @Override
    public void modify(int index, T newData) {

    }

    @Override
    public boolean remove(T data) {
        return false;
    }

    @Override
    public void remove(int index) {

    }

}
