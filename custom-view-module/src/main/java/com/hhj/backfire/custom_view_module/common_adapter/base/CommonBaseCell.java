package com.hhj.backfire.custom_view_module.common_adapter.base;

/**
 * Created by backfire on 2018/4/1.
 */

public abstract  class CommonBaseCell<T> implements Cell {
    private T mData;

    public CommonBaseCell(T data){
        mData = data;
    }


    @Override
    public void releaseResource() {
        //Do nothing
        //If something need to release , subclass implements it .
    }
}
