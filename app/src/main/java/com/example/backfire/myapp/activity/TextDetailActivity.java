package com.example.backfire.myapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.backfire.myapp.R;
import com.example.backfire.myapp.bean.BookBean;
import com.example.backfire.myapp.bean.DateBean;
import com.example.backfire.myapp.presenter.implPresenter.FictionPresenterImpl;
import com.example.backfire.myapp.presenter.implView.IBookFragment;
import com.example.backfire.myapp.utils.StaticUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by backfire on 2017/11/21.
 */

public class TextDetailActivity extends BaseActivity implements IBookFragment {
    @BindView(R.id.lv_textviews)
    ListView lvTextViews;


    private SimpleAdapter adapter;
    private String textUrl;
    private String textTitle;
    private FictionPresenterImpl fictionPresenterlmpl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_detail);
        ButterKnife.bind(this);
        initData();
        initView();
        getData();
    }

    private void initView() {

    }

    private void initData() {
        textUrl = getIntent().getStringExtra(StaticUtil.TEXT_DETAIL_URL);
        textTitle = getIntent().getStringExtra(StaticUtil.TEXT_TITLE);
        fictionPresenterlmpl = new FictionPresenterImpl(this, this);

    }

    @Override
    public void upTextDetail(ArrayList<Map<String, String>> mapList) {
        adapter = new SimpleAdapter(TextDetailActivity.this, mapList, R.layout.item_fiction_text,
                new String[]{"text"}, new int[]{R.id.tv_item_text});
        lvTextViews.setAdapter(adapter);
        if (mapList.size() >= 0 && mapList != null) {
            saveToLocal(textTitle, mapList);

        }
        readTocalText(textTitle);


    }

    private void saveToLocal(String title, ArrayList<Map<String, String>> mapList) {
        String filePath;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) {
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + title + ".txt";
            Log.i("path", filePath);
        } else {
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + title + ".txt";
        }

        try {
            FileOutputStream fos = openFileOutput(title + ".txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            for (Map<String, String> map : mapList) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    osw.write(entry.getValue());
                    Log.i("txt", entry.getValue());
                }

            }
            fos.flush();
            osw.flush();
            osw.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void readTocalText(String title) {
        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try {

            inputStream = openFileInput(title + ".txt");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String text = bufferedReader.readLine();
            Log.i("read", text);
            inputStream.close();
            bufferedReader.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }


    public void getData() {
        fictionPresenterlmpl.getTextDetail(textUrl);
    }

    //
    @Override
    public void updateList(ArrayList<BookBean> bookBeanArrayList) {

    }

    @Override
    public void getListError() {

    }

    @Override
    public void setDateList(ArrayList<DateBean> dateList) {

    }

    @Override
    public void setCategoryList(ArrayList<DateBean> categoryList) {

    }

    @Override
    public void setTagList(ArrayList<DateBean> tagList) {

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
