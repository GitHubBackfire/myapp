package com.example.backfire.myapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.backfire.myapp.bean.Tab;
import com.example.backfire.myapp.fragment.BookFragment;
import com.example.backfire.myapp.fragment.DownloadFragment;
import com.example.backfire.myapp.fragment.FilmFragment;
import com.example.backfire.myapp.fragment.ReviewLogFragment;
import com.example.backfire.myapp.utils.FileUtil;
import com.example.backfire.myapp.utils.PermissonUtil;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    private LayoutInflater mInflater;
    private FragmentTabHost mTabHost;
    private Fragment mFragment;
    private List<Tab> mTabs = new ArrayList<>();
    private boolean isInitTab = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            if(!isInitTab){
                if(mTabHost == null){
                    initTab();
                }
            }
        }
        PermissonUtil.verifyStoragePermissions(this);
    }




    private void initTab() {
        isInitTab = true;
        Tab tabBook = new Tab(R.string.tabBook,R.drawable.selector_book, BookFragment.class);
        Tab tabFilm = new Tab(R.string.tabFilm,R.drawable.selector_film, FilmFragment.class);
        Tab tabDownload = new Tab(R.string.tabDownload,R.drawable.selector_download, DownloadFragment.class);
        Tab tabSetting = new Tab(R.string.tabSetting,R.drawable.selector_review_log, ReviewLogFragment.class);
        mTabs.add(tabBook);
        mTabs.add(tabFilm);
        mTabs.add(tabDownload);
        mTabs.add(tabSetting);
        mInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        mTabHost.setup(this,this.getSupportFragmentManager(),R.id.real_tabcontent);

        for(Tab tab:mTabs){
            TabHost.TabSpec tabspec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabspec.setIndicator(buildIndicator(tab));
            mTabHost.addTab(tabspec,tab.getFragment(),null);

        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {

            }
        });

        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        mTabHost.setCurrentTab(0);
    }

    private View buildIndicator(Tab tab){
        View view = mInflater.inflate(R.layout.tab_indicator,null);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);
        ImageView icon = (ImageView) view.findViewById(R.id.iv_icon);
        icon.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());
        return view;

    }

    //    when recycle view scroll bottom,need loading more date and show the more view.
    public interface LoadingMore {

        void loadingStart();

        void loadingfinish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
