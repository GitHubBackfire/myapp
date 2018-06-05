package com.example.backfire.myapp.presenter.implView;

import com.example.backfire.myapp.bean.BookBean;
import com.example.backfire.myapp.bean.DateBean;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by backfire on 2017/9/30.
 */

public interface IBookFragment extends IBaseFragment {
    void updateList(ArrayList<BookBean> bookBeanArrayList);
    void setDateList(ArrayList<DateBean> dateList);
    void setCategoryList(ArrayList<DateBean> categoryList);
    void setTagList(ArrayList<DateBean> tagList);
    void upTextDetail(ArrayList<Map<String, String>> mapList);

}
