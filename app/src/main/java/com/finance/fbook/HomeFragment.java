package com.finance.fbook;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {

    private RecyclerView recycler;
    private SearchView searchbar;
    private ContactAdapter adapter;
    private LinearLayoutManager manager;
    List<String> contacts = new ArrayList<String>();


    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_home, container, false);

        recycler = (RecyclerView) rootview.findViewById(R.id.recycler);
        searchbar = (SearchView) rootview.findViewById(R.id.search);

        contacts.add("Shaik Khalil");
        contacts.add("Varshith Bumble");
        contacts.add("Jaideep Reddy");
        contacts.add("Guru");

        manager = new LinearLayoutManager(getActivity().getApplicationContext());
        recycler.setLayoutManager(manager);

        adapter = new ContactAdapter(contacts);
        recycler.setAdapter(adapter);


        return rootview;
    }




}
