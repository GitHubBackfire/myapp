package com.example.backfire.myapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.example.backfire.myapp.fragment.BookStoreFragment;
import com.example.backfire.myapp.fragment.FictionReaderFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * FragmentPagerAdapter将fragment保存在内存中
 * FragmentStatePagerAdapter只保存当前fragment
 * Created by backfire on 2017/11/23.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {
    //length >= 2
    private String[] titles = new String[]{};
    private List<Fragment> fragmentList;

    public ViewPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public ViewPageAdapter(FragmentManager fragmentManager, String[] titles) {
        super(fragmentManager);
        this.titles = titles;
    }

    public ViewPageAdapter(FragmentManager fragmentManager, String[] titles, List<Fragment> fragmentList) {
        super(fragmentManager);
        this.titles = titles;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        /*if (position == 1) {
            return new FictionReaderFragment();
        } else {
            return new BookStoreFragment();
        }*/
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];

    }
}

