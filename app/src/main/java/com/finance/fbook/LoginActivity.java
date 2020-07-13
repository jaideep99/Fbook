package com.finance.fbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class LoginActivity extends AppCompatActivity {


    private EditText code,num,otpcode;
    private LinearLayout otp,otpd;
    private Dialog otp_dialog;

    String TAG = "phoneauth";

    String codesent;
    FirebaseAuth mAuth;
    DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        otp = (LinearLayout) findViewById(R.id.otp);
        code = (EditText) findViewById(R.id.ccode);
        num = (EditText) findViewById(R.id.mobile);

        otp_dialog = new Dialog(this);

        otp_dialog.setContentView(R.layout.otp);
        otp_dialog.setCanceledOnTouchOutside(false);

        otpcode = (EditText) otp_dialog.findViewById(R.id.otp);
        otpd = (LinearLayout) otp_dialog.findViewById(R.id.otpverify);

        mAuth = FirebaseAuth.getInstance();



        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode();
            }
        });

        otpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode();
            }
        });

    }

    private void verifyCode() {

        String code = otpcode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesent, code);

        signInWithPhoneAuthCredential(credential);

        otp_dialog.dismiss();
    }

    private void verifyCode(String smscode) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesent, smscode);

        signInWithPhoneAuthCredential(credential);

        otp_dialog.dismiss();
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String mnum = code.getText().toString()+ num.getText().toString();
                            storeMobile(mnum);
                            todb(mnum);
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);

                        } else {
                            Toast.makeText(LoginActivity.this,"OTP incorrect!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void todb(final String mnum) {

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mdatabase = FirebaseDatabase.getInstance().getReference();
        mdatabase.child("Users").child(uid).child("mobile").setValue(mnum);

        mdatabase.child("Users").child(uid).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null)
                {
                    String n = (String) snapshot.getValue();
                    setName(n);
                }
                else
                {
                    mdatabase.child("Users").child(uid).child("name").setValue("User");
                    setName("User");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void storeMobile(String res) {

        SharedPreferences ref = getApplicationContext().getSharedPreferences("Fbook",MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putString("mobile",res);
        editor.commit();

    }

    private void setName(String s) {

        SharedPreferences ref = getApplicationContext().getSharedPreferences("Fbook",MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putString("name",s);
        editor.commit();

    }

    private void sendVerificationCode() {


        String phoneNumber = code.getText().toString()+ num.getText().toString();

        if(phoneNumber.isEmpty()){
            num.setError("Phone number is required!");
            num.requestFocus();
            return;
        }

        if(phoneNumber.length()<10)
        {
            num.setError("Enter a invalid phone number!");
            num.requestFocus();
            return;
        }



        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                LoginActivity.this,               // Activity (for callback binding)
                mCallbacks);

        otp_dialog.show();

        Toast.makeText(LoginActivity.this,"code sent", Toast.LENGTH_SHORT).show();

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String getotp = phoneAuthCredential.getSmsCode();
            if(getotp!=null)
            {
                otpcode.setText(getotp);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                verifyCode(getotp);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Toast.makeText(LoginActivity.this,"code received",Toast.LENGTH_SHORT).show();
            codesent = s;

        }
    };

}
