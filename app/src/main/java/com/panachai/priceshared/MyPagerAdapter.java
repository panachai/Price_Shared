package com.panachai.priceshared;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class MyPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_NUM = 2;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public int getCount() {
        return PAGE_NUM;
    }

    public Fragment getItem(int position) {

        if (position == 0){
            return NewsfeedFragment.newInstance(); //test bundle
        }
        else if (position == 1)
            return WaitforUseFragment.newInstance();
        return null;
    }




}
