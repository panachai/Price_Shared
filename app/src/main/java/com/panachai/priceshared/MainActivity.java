package com.panachai.priceshared;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    MyPagerAdapter adapter;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.pager);//ทำ view pager
        pager.setAdapter(adapter);



    }

}
