 package com.eapp.sirjefffharthart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

 public class OTP extends AppCompatActivity {
     DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectsirjeff-default-rtdb.firebaseio.com/");
     private EditText otp1,otp2,otp3,otp4,otp5,otp6,try1,try2,try3,try4,try5;
     private Button BtnVerify;
     String phonenumber;
     String otpid;
     FirebaseAuth mAuth;

     private int selectedETposition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        phonenumber=getIntent().getStringExtra("Mobile").toString();
        BtnVerify=(Button) findViewById(R.id.BtnVerify);
        mAuth=FirebaseAuth.getInstance();
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);

        try1 = findViewById(R.id.try1);
        try2 = findViewById(R.id.try2);
        try3 = findViewById(R.id.try3);
        try4 = findViewById(R.id.try4);
        try5 = findViewById(R.id.try5);


        final TextView emailOTP = findViewById(R.id.emailOTP);
        final TextView ccpOTP = findViewById(R.id.ccpOTP);

        //getting email, moblie, and ccp from registration activuty through intent
        final String getEmail = getIntent().getStringExtra("email");
        final String getCcp = getIntent().getStringExtra("Mobile");
        final String getFullname = getIntent().getStringExtra("fullname");
        final String getPassword_reg = getIntent().getStringExtra("password_reg");
        final String getPassword_con = getIntent().getStringExtra("password_con");
        final String getUsername = getIntent().getStringExtra("Username");

        emailOTP.setText(getEmail);
        ccpOTP.setText(getCcp);
        try1.setText(getEmail);
        try2.setText(getCcp);
        try3.setText(getFullname);
        try4.setText(getPassword_reg);
        try5.setText(getUsername);


        otp1.addTextChangedListener(textWatcher);
        otp2.addTextChangedListener(textWatcher);
        otp3.addTextChangedListener(textWatcher);
        otp4.addTextChangedListener(textWatcher);
        otp5.addTextChangedListener(textWatcher);
        otp6.addTextChangedListener(textWatcher);

        showKeyboard(otp1);
        initiateotp();
        BtnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String generateOtp = otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString()+otp5.getText().toString()+otp6.getText().toString();

                if (generateOtp.length()!=6) {
                        if (otp1.getText().toString().isEmpty())
                            Toast.makeText(OTP.this, "Blank Field can not be processed", Toast.LENGTH_SHORT).show();
                        else if (otp2.getText().toString().isEmpty())
                            Toast.makeText(OTP.this, "Blank Field can not be processed", Toast.LENGTH_SHORT).show();
                        else if (otp3.getText().toString().isEmpty())
                            Toast.makeText(OTP.this, "Blank Field can not be processed", Toast.LENGTH_SHORT).show();
                        else if (otp4.getText().toString().isEmpty())
                            Toast.makeText(OTP.this, "IBlank Field can not be processed", Toast.LENGTH_SHORT).show();
                        else if (otp5.getText().toString().isEmpty())
                            Toast.makeText(OTP.this, "Blank Field can not be processed", Toast.LENGTH_SHORT).show();
                        else if (otp6.getText().toString().isEmpty())
                            Toast.makeText(OTP.this, "Blank Field can not be processed", Toast.LENGTH_SHORT).show();

                }
                else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, generateOtp);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void showKeyboard(EditText otp){
        otp.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otp, InputMethodManager.SHOW_IMPLICIT);
    }
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if(s.length() >0){
                if (selectedETposition == 0){

                    selectedETposition = 1;
                    showKeyboard(otp2);
                }
                else if(selectedETposition == 1){

                    selectedETposition = 2;
                    showKeyboard(otp3);
                }
                else if(selectedETposition == 2){

                    selectedETposition = 3;
                    showKeyboard(otp4);
                }
                else if(selectedETposition == 3){

                    selectedETposition = 4;
                    showKeyboard(otp5);
                }
                else if(selectedETposition == 4){

                    selectedETposition = 5;
                    showKeyboard(otp6);
                }
            }

        }
    };
     private void initiateotp(){
         PhoneAuthProvider.getInstance().verifyPhoneNumber(
                 phonenumber,
                 60,
                 TimeUnit.SECONDS,
                 this,
                 new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                     @Override
                     public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                         otpid=s;
                     }

                     @Override
                     public void onVerificationCompleted( PhoneAuthCredential phoneAuthCredential) {
                         signInWithPhoneAuthCredential(phoneAuthCredential);
                     }

                     @Override
                     public void onVerificationFailed( FirebaseException e) {
                         Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 }
         );
     }
     private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
         mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful()){
                     save();
                     startActivity(new Intent(OTP.this, Login.class));
                     finish();
                 }else{
                     Toast.makeText(OTP.this, "Signing Code Error", Toast.LENGTH_SHORT).show();
                 }
             }
         });
     }
     @Override
     public boolean onKeyUp(int keyCode, KeyEvent event){

         if (keyCode == KeyEvent.KEYCODE_DEL){
             if (selectedETposition == 5){

                 selectedETposition =4;
                 showKeyboard(otp5);
             }
             else if(selectedETposition == 4){

                 selectedETposition = 3;
                 showKeyboard(otp4);
             }
             else if(selectedETposition == 3){

                 selectedETposition =2 ;
                 showKeyboard(otp3);
             }
             else if(selectedETposition == 2){

                 selectedETposition = 1;
                 showKeyboard(otp2);
             }
             else if(selectedETposition == 1){
                 selectedETposition = 0;
                 showKeyboard(otp1);
             }
             return true;
         }
         else{
             return super.onKeyUp(keyCode, event);
         }
     }
     private void save (){

         String email = try1.getText().toString();
         String mobile = try2.getText().toString();
         String fullname = try3.getText().toString();
         String password = try4.getText().toString();
         String username = try5.getText().toString();

            databaseReference.child("users").child(username).child("Fullname").setValue(fullname);
            databaseReference.child("users").child(username).child("Email").setValue(email);
            databaseReference.child("users").child(username).child("Mobile").setValue(mobile);
            databaseReference.child("users").child(username).child("Password").setValue(password);
            databaseReference.child("users").child(username).child("Gender").setValue("");

         Toast.makeText(this, "Registered Complete!", Toast.LENGTH_SHORT).show();

     }
}