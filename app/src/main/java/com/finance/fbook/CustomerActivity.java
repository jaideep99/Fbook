package com.finance.fbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CustomerActivity extends AppCompatActivity implements BillAdapter.OnItemClickListener{

    TextView shortname,name,number;
    TextView gavebtn, gotbtn;
    ImageView back,info;
    ProgressBar progress;

    private EditText amount,description,interest_rate,time;
    private LinearLayout savebtn,iwrapper;
    private CheckBox interest;
    private ImageView calender;
    private TextView datetxt,backed;


    List<bill> itemlist =  new ArrayList<bill>();

    RecyclerView recycler;
    BillAdapter adapter;
    LinearLayoutManager manager;

    Dialog detail_dialog;

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener datelistener;


    String myFormat = "dd MMM yyyy . hh:mm a"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

    String date = sdf.format(myCalendar.getTime());

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference billref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Customers");

    boolean icheck = false;
    boolean isgave = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        final icontact cust = (icontact) getIntent().getSerializableExtra("contact");
        name = (TextView) findViewById(R.id.name);
        number = (TextView) findViewById(R.id.number);
        gavebtn = (TextView) findViewById(R.id.gave);
        gotbtn = (TextView) findViewById(R.id.got);
        back = (ImageView) findViewById(R.id.back);
        shortname = (TextView) findViewById(R.id.shortname);
        progress = (ProgressBar) findViewById(R.id.progress);


        name.setText(cust.getName());
        number.setText(cust.getNumber());
        billref = billref.child(cust.getUid()).child("Bills");

        String stname = "";
        String sp[] = cust.getName().split(" ");
        for(int i=0;i<Math.min(2,sp.length);i++)
        {
            stname+= sp[i].charAt(0);
        }
        stname = stname.toUpperCase();
        shortname.setText(stname);

        manager =  new LinearLayoutManager(this);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(manager);
        adapter = new BillAdapter(itemlist);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        detail_dialog = new Dialog(this);
        detail_dialog.setContentView(R.layout.details);
        detail_dialog.setCanceledOnTouchOutside(false);

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
        
        setup();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        gavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isgave = true;
                detail_dialog.show();
            }
        });

        gotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isgave = false;
                detail_dialog.show();
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                load_entries();
            }
        }).start();

    }

    private void load_entries() {

        billref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    bill x = ds.getValue(bill.class);
                    itemlist.add(x);
                }

                adapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updatelabel() {

        date = sdf.format(myCalendar.getTime());
        datetxt.setText(date);
    }


    private void setup() {

        amount = (EditText) detail_dialog.findViewById(R.id.amount);
        description = (EditText) detail_dialog.findViewById(R.id.desc);
        interest_rate = (EditText) detail_dialog.findViewById(R.id.interest_rate);
        time = (EditText) detail_dialog.findViewById(R.id.time);
        interest = (CheckBox) detail_dialog.findViewById(R.id.checkbox);
        datetxt = (TextView) detail_dialog.findViewById(R.id.date);
        calender = (ImageView) detail_dialog.findViewById(R.id.calender);
        savebtn = (LinearLayout) detail_dialog.findViewById(R.id.savebtn);
        iwrapper = (LinearLayout) detail_dialog.findViewById(R.id.iwrapper);
        backed = (TextView) detail_dialog.findViewById(R.id.backed);
        datetxt.setText(date);


        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CustomerActivity.this,R.style.DialogTheme, datelistener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(icheck)
                {
                    interest.setChecked(false);
                    icheck = false;
                    iwrapper.setVisibility(View.GONE);
                }
                else
                {
                    interest.setChecked(true);
                    icheck = true;
                    iwrapper.setVisibility(View.VISIBLE);
                }
            }
        });

        backed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detail_dialog.dismiss();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount.getText()!=null)
                {
                    if(interest.isChecked())
                    {
                        if( interest_rate.getText()!=null && time.getText()!=null)
                        {
                            DatabaseReference pushref = billref.push();
                            int amnt = Integer.parseInt(amount.getText().toString());
                            float rate = Float.parseFloat(interest_rate.getText().toString());
                            int dur = Integer.parseInt(time.getText().toString());
                            bill entry = new bill(pushref.getKey(),amnt,0,description.getText().toString(),date,dur,rate,true,isgave);
                            pushref.setValue(entry);
                            itemlist.add(entry);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    else
                    {
                        DatabaseReference pushref = billref.push();
                        int amnt = Integer.parseInt(amount.getText().toString());
                        bill entry = new bill(pushref.getKey(),amnt,0,description.getText().toString(),date,0,0,false,isgave);
                        pushref.setValue(entry);
                        itemlist.add(entry);
                        adapter.notifyDataSetChanged();
                    }

                    detail_dialog.dismiss();
                }
                else
                {
                    amount.setError("Required!!");
                }
            }
        });

    }

    @Override
    public void onItemClick(bill x) {
        Intent i =  new Intent(CustomerActivity.this,DetailActivity.class);
        i.putExtra("bill",x);
        startActivity(i);
    }
}
