package com.finance.fbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerActivity extends AppCompatActivity {

    TextView shortname,name,number;
    TextView gavebtn, gotbtn;
    TextView status,total;
    ImageView back;

    Dialog detail_dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        icontact cust = (icontact) getIntent().getSerializableExtra("contact");
        name = (TextView) findViewById(R.id.name);
        number = (TextView) findViewById(R.id.number);
        gavebtn = (TextView) findViewById(R.id.gave);
        gotbtn = (TextView) findViewById(R.id.got);
        status = (TextView) findViewById(R.id.status);
        total = (TextView) findViewById(R.id.total);
        back = (ImageView) findViewById(R.id.back);
        shortname = (TextView) findViewById(R.id.shortname);
        detail_dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        detail_dialog.setContentView(R.layout.details);
        detail_dialog.setCanceledOnTouchOutside(false);
        name.setText(cust.getName());
        number.setText(cust.getNumber());

        String stname = "";
        String sp[] = cust.getName().split(" ");
        for(int i=0;i<Math.min(2,sp.length);i++)
        {
            stname+= sp[i].charAt(0);
        }
        stname = stname.toUpperCase();
        shortname.setText(stname);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        gavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detail_dialog.show();
            }
        });


    }
}
