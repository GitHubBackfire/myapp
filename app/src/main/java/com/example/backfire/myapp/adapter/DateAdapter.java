package com.example.backfire.myapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by backfire on 2017/11/17.
 */

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {


    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    //
    public static class DateViewHolder extends RecyclerView.ViewHolder {

        public DateViewHolder(View itemView) {
            super(itemView);
        }
    }
}
