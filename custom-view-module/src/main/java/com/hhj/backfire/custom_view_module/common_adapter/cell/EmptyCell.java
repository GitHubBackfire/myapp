package com.hhj.backfire.custom_view_module.common_adapter.cell;

import android.view.ViewGroup;

import com.hhj.backfire.custom_view_module.common_adapter.base.CommonBaseCell;
import com.hhj.backfire.custom_view_module.common_adapter.base.CommonBaseViewHolder;

/**
 * Created by backfire on 2018/4/1.
 */

public class EmptyCell extends CommonBaseCell {
    public EmptyCell(Object data) {
        super(data);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public CommonBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CommonBaseViewHolder holder, int position) {

    }
}
