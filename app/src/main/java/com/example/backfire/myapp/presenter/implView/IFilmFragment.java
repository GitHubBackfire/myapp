package com.example.backfire.myapp.presenter.implView;

import com.example.backfire.myapp.bean.BookBean;
import com.example.backfire.myapp.bean.DateBean;
import com.example.backfire.myapp.bean.FilmBean;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by backfire on 2017/12/12.
 */

public interface IFilmFragment extends IBaseFragment {
    void updateList(ArrayList<FilmBean> filmBeanArrayList);
}
