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
import android.widget.ProgressBar;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment implements ContactAdapter.OnItemClickListener{

    private RecyclerView recycler;
    private SearchView searchbar;
    private ContactAdapter adapter;
    private LinearLayoutManager manager;
    ProgressBar progressBar;
    List<icontact> contacts = new ArrayList<icontact>();


    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_home, container, false);

        recycler = (RecyclerView) rootview.findViewById(R.id.recycler);
        searchbar = (SearchView) rootview.findViewById(R.id.search);
        progressBar = (ProgressBar) rootview.findViewById(R.id.progress);


        manager = new LinearLayoutManager(getActivity().getApplicationContext());
        recycler.setLayoutManager(manager);

        adapter = new ContactAdapter(contacts);
        adapter.setOnItemClickListener(this);
        recycler.setAdapter(adapter);

        
        retrieve_data();


        return rootview;
    }

    private void retrieve_data() {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Customers");

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren())
                {
                    HashMap<String,String> x = (HashMap) ds.getValue();
                    contacts.add(new icontact(x.get("name"),x.get("number"),ds.getKey()));
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onItemClick(int pos) {
        Intent i =  new Intent(getActivity().getApplicationContext(),CustomerActivity.class);
        i.putExtra("contact", (Serializable) contacts.get(pos));
        startActivity(i);
    }
}
