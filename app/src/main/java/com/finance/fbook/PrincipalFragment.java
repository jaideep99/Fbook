package com.finance.fbook;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class PrincipalFragment extends Fragment {

    Context context;
    private TextView amount;
    private RecyclerView recycler;
    private TextView pay,got;
    private LinearLayoutManager manager;

    Dialog rec_dialog;

    private EditText amnt,description;
    private LinearLayout savebtn;
    private ImageView calender;
    private TextView datetxt,backed;

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener datelistener;

    String myFormat = "dd MMM yyyy . hh:mm a"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

    String date = sdf.format(myCalendar.getTime());

    public PrincipalFragment(Context con) {
        this.context = con;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_principal, container, false);


        amount = (TextView) root.findViewById(R.id.total);
        recycler = (RecyclerView) root.findViewById(R.id.recycler);
        manager = new LinearLayoutManager(context);
        pay = (TextView) root.findViewById(R.id.pay);
        got = (TextView) root.findViewById(R.id.got);

        rec_dialog = new Dialog(context);
        rec_dialog.setContentView(R.layout.detpay);
        rec_dialog.setCanceledOnTouchOutside(false);

        datelistener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updatelabel();

            }

        };


        setup_dialog();

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        got.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return  root;
    }



    private void setup_dialog() {

        amnt = (EditText) rec_dialog.findViewById(R.id.amount);
        description = (EditText) rec_dialog.findViewById(R.id.desc);
        datetxt = (TextView) rec_dialog.findViewById(R.id.date);
        calender = (ImageView) rec_dialog.findViewById(R.id.calender);
        savebtn = (LinearLayout) rec_dialog.findViewById(R.id.savebtn);

        backed = (TextView) rec_dialog.findViewById(R.id.backed);
        datetxt.setText(date);


        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context,R.style.DialogTheme, datelistener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount.getText()!=null)
                {


                }
                else
                {
                    amount.setError("Required!!");
                }
            }
        });
    }

    private void updatelabel() {

        date = sdf.format(myCalendar.getTime());
        datetxt.setText(date);
    }

}
