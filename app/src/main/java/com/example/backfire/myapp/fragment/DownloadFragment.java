package com.example.backfire.myapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.backfire.myapp.R;
import com.example.backfire.myapp.adapter.ViewPageAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by backfire on 2017/11/12.
 */

public class DownloadFragment extends BaseFragment {
    private View view;
    Toolbar toolbar;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private ViewPageAdapter viewPageAdapter;
    private TabLayout.Tab one;
    private TabLayout.Tab two;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_two_tab, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ButterKnife.bind(this, view);
        initViewPager();
        return view;
    }

    private void initViewPager() {
/*        String[] mTitles = new String[]{"书籍", "电影"};
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new LocalBookFragment());
        fragments.add(new LocalFilmFragment());
        viewPageAdapter = new ViewPageAdapter(getChildFragmentManager(),mTitles,fragments);
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        one = tabLayout.getTabAt(0);
        two = tabLayout.getTabAt(1);*/

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
