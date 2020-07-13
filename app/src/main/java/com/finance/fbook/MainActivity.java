package com.finance.fbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs;
    private ViewPager pager;
    private ImageView addcust,notes;
    FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.viewpager);
        addcust = (ImageView) findViewById(R.id.addc);
        notes = (ImageView) findViewById(R.id.notes);


        adapter = new FragmentAdapter(getSupportFragmentManager(),MainActivity.this);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        tabs.getTabAt(0).setIcon(R.drawable.home);
        tabs.getTabAt(1).setIcon(R.drawable.more);

        addcust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ContactActivity.class);
                startActivity(i);
            }
        });
    }
}
