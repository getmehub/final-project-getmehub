package com.mm.witty.Viewpage;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentActivity;


import com.mm.witty.R;


/**
 * Created by MacBook on 8/11/17.
 */

public class cartoons extends FragmentActivity{
ViewPager viewPager;
 SwipeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xkcd_witty);
        viewPager = (ViewPager)findViewById(R.id.view_pager);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new SwipeAdapter(this);
        viewPager.setAdapter(adapter);


    }
}