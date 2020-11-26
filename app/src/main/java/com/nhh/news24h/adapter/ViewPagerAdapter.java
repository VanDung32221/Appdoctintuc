package com.nhh.news24h.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nhh.news24h.fragment.Fragment24H;
import com.nhh.news24h.fragment.FragmentVietNamNet;
import com.nhh.news24h.fragment.FragmentVnExpress;
import com.nhh.news24h.fragment.FragmentXemVN;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private String titles[] = new String[]{"24H", "VnExpress", "VietNamNet", "XemVN"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment24H();

            case 1:
                return new FragmentVnExpress();

            case 2:
                return new FragmentVietNamNet();

            case 3:
                return new FragmentXemVN();

        }
        return null;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
