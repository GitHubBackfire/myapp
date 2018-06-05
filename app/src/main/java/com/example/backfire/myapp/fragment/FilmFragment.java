package com.example.backfire.myapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.backfire.myapp.bean.FilmBean;
import com.example.backfire.myapp.presenter.implView.IFilmFragment;

import java.util.ArrayList;

/**
 * Created by backfire on 2017/11/4.
 */

public class FilmFragment extends BaseFragment implements IFilmFragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void updateList(ArrayList<FilmBean> filmBeanArrayList) {

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
