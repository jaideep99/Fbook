package com.finance.fbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.tabs.TabLayout;

public class DetailActivity extends AppCompatActivity {

    ImageView back,info;
    ProgressBar progress;
    TabLayout tabs;
    DetFragAdapter adapter;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bill mbill = (bill) getIntent().getSerializableExtra("bill");

        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (TabLayout) findViewById(R.id.tabs);
        adapter = new DetFragAdapter(getSupportFragmentManager(),DetailActivity.this);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
    }
}
