package com.nhh.news24h.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;


import com.nhh.news24h.R;
import com.nhh.news24h.adapter.ExplandableListAdapter;
import com.nhh.news24h.adapter.ViewPagerAdapter;
import com.nhh.news24h.fragment.Fragment24H;
import com.nhh.news24h.fragment.FragmentVnExpress;
import com.nhh.news24h.fragment.FragmentXemVN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener {
    private Toolbar tbMenu;
    private DrawerLayout dlMenu;
    private ViewPager vpPaper;
    private TabLayout tabPaper;
    private ViewPagerAdapter pagerAdapter;
    private ExpandableListView expandableListView;
    private ExplandableListAdapter mMenuAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private static Context mContext;
    public static int[] icon = {R.drawable.icon24h, R.drawable.vnexpressicon,
            R.drawable.vietnamneticon, R.drawable.xemvnicon};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        initView();
    }


    private void initView() {
        tbMenu = findViewById(R.id.tb_main);
        dlMenu = findViewById(R.id.dl_menu);
        vpPaper = findViewById(R.id.vp_paper);
        tabPaper = findViewById(R.id.tab_title);
        expandableListView = findViewById(R.id.nav_menulist);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        setSupportActionBar(tbMenu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.menu);
        actionBar.setTitle("News24H");

        vpPaper.setAdapter(pagerAdapter);
        tabPaper.setupWithViewPager(vpPaper);

        prepareListData();
        mMenuAdapter = new ExplandableListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(mMenuAdapter);
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("DEBUG", "header item clicked");
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dlMenu.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Context getContext() {
        return mContext;
    }


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding data header
        listDataHeader.add("24H");
        listDataHeader.add("VnExpress");
        listDataHeader.add("Vietnamnet");
        listDataHeader.add("XemVN");


        // Adding child data
        List<String> heading1 = new ArrayList<String>();
        heading1.add("Tin tức trong ngày");
        heading1.add("Bóng đá");
        heading1.add("ASIAN CUP 2019");
        heading1.add("An ninh - Hình sự");
        heading1.add("Thời trang");
        heading1.add("Tài chính – Bất động sản");
        heading1.add("Làm đẹp");
        heading1.add("Phim");
        heading1.add("Ca nhạc - MTV");
        heading1.add("Công nghệ thông tin");


        List<String> heading2 = new ArrayList<String>();
        heading2.add("Trang chủ");
        heading2.add("Thời sự");
        heading2.add("Thế giới");
        heading2.add("Kinh doanh");
        heading2.add("Startup");
        heading2.add("Khoa học");
        heading2.add("Số hóa");
        heading2.add("Xe");


        List<String> heading3 = new ArrayList<String>();
        heading3.add("Tin nổi bật");
        heading3.add("Pháp luật");
        heading3.add("Công nghệ");
        heading3.add("Kinh doanh");
        heading3.add("Giáo dục");
        heading3.add("Thời sự");
        heading3.add("Giải trí");
        heading3.add("Sức khỏe");
        heading3.add("Thể thao");
        heading3.add("Bất động sản");


        List<String> heading4 = new ArrayList<String>();
        heading4.add("Trang chủ");
        heading4.add("Hot");
        heading4.add("Bình chọn");


        listDataChild.put(listDataHeader.get(0), heading1);// Header, Child data
        listDataChild.put(listDataHeader.get(1), heading2);
        listDataChild.put(listDataHeader.get(2), heading3);
        listDataChild.put(listDataHeader.get(3), heading4);

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        switch (groupPosition) {
            case 0:
                vpPaper.setCurrentItem(0);
                switch (childPosition) {
                    case 0:
                        new Fragment24H.Paper24HData().execute("https://www.24h.com.vn/upload/rss/tintuctrongngay.rss");
                        break;
                    case 1:
                        new Fragment24H.Paper24HData().execute("https://www.24h.com.vn/upload/rss/bongda.rss");
                        break;
                    case 2:
                        new Fragment24H.Paper24HData().execute("https://www.24h.com.vn/upload/rss/asiancup2019.rss");
                        break;
                    case 3:
                        new Fragment24H.Paper24HData().execute("https://www.24h.com.vn/upload/rss/anninhhinhsu.rss");
                        break;
                    case 4:
                        new Fragment24H.Paper24HData().execute("https://www.24h.com.vn/upload/rss/thoitrang.rss");
                        break;
                    case 5:
                        new Fragment24H.Paper24HData().execute("https://www.24h.com.vn/upload/rss/taichinhbatdongsan.rss");
                        break;
                    case 6:
                        new Fragment24H.Paper24HData().execute("https://www.24h.com.vn/upload/rss/lamdep.rss");
                        break;
                    case 7:
                        new Fragment24H.Paper24HData().execute("https://www.24h.com.vn/upload/rss/phim.rss");
                        break;
                    case 8:
                        new Fragment24H.Paper24HData().execute("https://www.24h.com.vn/upload/rss/canhacmtv.rss");
                        break;
                    case 9:
                        new Fragment24H.Paper24HData().execute("https://www.24h.com.vn/upload/rss/congnghethongtin.rss");
                        break;


                }
                break;

            case 1:
                vpPaper.setCurrentItem(1);
                switch (childPosition) {
                    case 0:
                        new FragmentVnExpress.VnExpressData().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
                        break;
                    case 1:
                        new FragmentVnExpress.VnExpressData().execute("https://vnexpress.net/rss/thoi-su.rss");
                        break;
                    case 2:
                        new FragmentVnExpress.VnExpressData().execute("https://vnexpress.net/rss/the-gioi.rss");
                        break;
                    case 3:
                        new FragmentVnExpress.VnExpressData().execute("https://vnexpress.net/rss/kinh-doanh.rss");
                        break;
                    case 4:
                        new FragmentVnExpress.VnExpressData().execute("https://vnexpress.net/rss/startup.rss");
                        break;
                    case 5:
                        new FragmentVnExpress.VnExpressData().execute("https://vnexpress.net/rss/khoa-hoc.rss");
                        break;
                    case 6:
                        new FragmentVnExpress.VnExpressData().execute("https://vnexpress.net/rss/so-hoa.rss");
                        break;
                    case 7:
                        new FragmentVnExpress.VnExpressData().execute("https://vnexpress.net/rss/oto-xe-may.rss");
                        break;
                }
                break;

            case 2:
                vpPaper.setCurrentItem(2);
                break;

            case 3:
                vpPaper.setCurrentItem(3);
                switch (childPosition) {
                    case 0:
                        new FragmentXemVN.XemVNData().execute("https://xem.vn/rss/");
                        break;
                    case 1:
                        new FragmentXemVN.XemVNData().execute("https://xem.vn/hot.rss/");
                        break;

                    case 2:
                        new FragmentXemVN.XemVNData().execute("https://xem.vn/vote.rss/");
                        break;
                }
                break;
        }
        dlMenu.closeDrawers();

        return true;
    }
}
