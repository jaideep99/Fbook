package com.finance.fbook;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;


public class MoreFragment extends Fragment {

    RelativeLayout logout;
    TextView editname,name;
    EditText newname;
    Dialog name_dialog;
    LinearLayout set;

    public MoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_more, container, false);

        editname = (TextView) rootview.findViewById(R.id.editname);
        name_dialog = new Dialog(getActivity());
        name_dialog.setContentView(R.layout.name_dialog);

        newname = (EditText) name_dialog.findViewById(R.id.name);
        name = (TextView) rootview.findViewById(R.id.name);
        name.setText(getName());

        set = (LinearLayout) name_dialog.findViewById(R.id.setname);

        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_dialog.show();
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x = newname.getText().toString();
                name.setText(x);
                name_dialog.dismiss();

                final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();
                mdatabase.child("Users").child(uid).child("name").setValue(x);
                setName(x);


            }
        });

        logout = (RelativeLayout) rootview.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i =  new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return rootview;
    }

    private void setName(String s) {

        SharedPreferences ref = getActivity().getApplicationContext().getSharedPreferences("Fbook",MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putString("name",s);
        editor.commit();

    }

    private String getName()
    {
        SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences("Fbook",MODE_PRIVATE);
        return prefs.getString("name","User");
    }

}
