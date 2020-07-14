package com.finance.fbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactActivity extends AppCompatActivity implements ContactItemAdapter.OnItemClickListener {

    RecyclerView recycler;
    SearchView search;
    Dialog loading;
    LinearLayoutManager manager;
    private ContactItemAdapter adapter;
    androidx.appcompat.widget.Toolbar toolbar;
    List<contact> contacts =  new ArrayList<contact>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        toolbar =  (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        search = (SearchView) findViewById(R.id.search);
        search.setImeOptions(EditorInfo.IME_ACTION_DONE);

        recycler = (RecyclerView) findViewById(R.id.recycler);
        manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);
        adapter = new ContactItemAdapter(this,contacts);
        adapter.setOnItemClickListener(ContactActivity.this);
        recycler.setAdapter(adapter);

        loading = new Dialog(this);
        loading.setContentView(R.layout.loading);
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getContactList();
            }
        }).start();


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filterList(s);
                return false;
            }
        });




    }


    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contacts.add(new contact(name,phoneNo));
                        break;
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }

        if (contacts.size() > 0) {
            Collections.sort(contacts, new Comparator<contact>() {
                @Override
                public int compare(final contact object1, final contact object2) {
                    return object1.getName().compareTo(object2.getName());
                }
            });
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading.dismiss();
                adapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public void onItemClick(contact customer) {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Customers");
        DatabaseReference ref = root.push();
        ref.setValue(customer);
        Intent i =  new Intent(ContactActivity.this,CustomerActivity.class);
        icontact temp = new icontact(customer.getName(),customer.getNumber(),ref.getKey());
        i.putExtra("contact", (Serializable) temp);
        startActivity(i);

    }
}
