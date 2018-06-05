package com.example.backfire.myapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.example.backfire.myapp.fragment.BookStoreFragment;
import com.example.backfire.myapp.fragment.FictionReaderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentPagerAdapter将fragment保存在内存中
 * FragmentStatePagerAdapter只保存当前fragment
 * Created by backfire on 2017/11/23.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private ArrayList<Fragment> fragments;
    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
