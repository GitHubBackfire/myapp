package com.example.backfire.myapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.backfire.myapp.R;
import com.example.backfire.myapp.bean.DateBean;

import java.util.ArrayList;

/**
 * Created by backfire on 2017/11/17.
 */

public class ButtonsAdapter extends RecyclerView.Adapter<ButtonsAdapter.ButtonsViewHolder> {
    private Context context;

    private ArrayList<DateBean> buttonsArrayList = new ArrayList<>();

    private MyOnButtonClickListener myOnButtonClickListener;
    public void setMyOnButtonClickListener(MyOnButtonClickListener myOnButtonClickListener) {
        this.myOnButtonClickListener = myOnButtonClickListener;
    }


    public ButtonsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ButtonsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popup, parent, false);
        //view.getLayoutParams().height =
        return new ButtonsViewHolder(view,myOnButtonClickListener);
    }

    @Override
    public void onBindViewHolder(ButtonsViewHolder holder, int position) {
        holder.btnItem.setText(buttonsArrayList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return buttonsArrayList.size();
    }
    public ArrayList<DateBean> getButtonsArrayList() {
        return buttonsArrayList;
    }
    //Add items
    public void addItems(ArrayList<DateBean> list) {
        buttonsArrayList.addAll(list);
        notifyDataSetChanged();
    }



    //
    public static class ButtonsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Button btnItem;
        private MyOnButtonClickListener myOnButtonClickListener;
        public ButtonsViewHolder(View itemView,MyOnButtonClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            btnItem = itemView.findViewById(R.id.btn_item);
            myOnButtonClickListener = listener;


        }

        @Override
        public void onClick(View view) {
            if(myOnButtonClickListener != null){
              myOnButtonClickListener.myOnButtonClick(itemView,getLayoutPosition());
            }
        }
    }
    public interface MyOnButtonClickListener {
        void myOnButtonClick(View view, int position);
    }
}
