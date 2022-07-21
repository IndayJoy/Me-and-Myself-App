package com.eapp.sirjefffharthart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class Registration extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectsirjeff-default-rtdb.firebaseio.com/");
    private boolean passwordShowing =  false;

    CountryCodePicker ccp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EditText fullname = findViewById(R.id.fullname);
        final EditText email = findViewById(R.id.email);
        final EditText mobile = findViewById(R.id.mobile);
        final EditText username = findViewById(R.id.username);

        final EditText password_reg = findViewById(R.id.password_reg);
        final EditText password_con = findViewById(R.id.password_con);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final ImageView passwordIcon_con = findViewById(R.id.passwordIcon_con);

        final AppCompatButton signUpBtn = findViewById(R.id.signUpBtn);
        final TextView signInBtn = findViewById(R.id.signInBtn);


        ccp =(CountryCodePicker) findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(mobile);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getMobileTxt = mobile.getText().toString();
                final String getEmailTxt = email.getText().toString();
                final String getPassRegTxt = password_reg.getText().toString();
                final String getPassConTxt = password_con.getText().toString();
                final String getFullname = fullname.getText().toString();
                final String getUsername = username.getText().toString();


                if (getEmailTxt.isEmpty() || getMobileTxt.isEmpty() || getPassRegTxt.isEmpty() || getPassConTxt.isEmpty() || getFullname.isEmpty()){
                    Toast.makeText(Registration.this, "Please, Fill up All fields", Toast.LENGTH_SHORT).show();
                }else if(!getPassRegTxt.equals(getPassConTxt)){
                    Toast.makeText(Registration.this, "Your password and confirmation password did not Match", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Registration.this, "Pls, Try Again", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(getUsername)){
                                Toast.makeText(Registration.this, "Username is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intent = new Intent(Registration.this, OTP.class);
                                intent.putExtra("fullname", getFullname);
                                intent.putExtra("Username", getUsername);
                                intent.putExtra("Mobile", ccp.getFullNumberWithPlus().replace("", ""));
                                intent.putExtra("mobile", getMobileTxt);
                                intent.putExtra("email", getEmailTxt);
                                intent.putExtra("password_reg", getPassRegTxt);
                                intent.putExtra("password_con", getPassConTxt);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordShowing){
                    passwordShowing=false;

                    password_reg.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.show_password);
                }else{
                    passwordShowing=true;

                    password_reg.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.hide_password);
                }
                password_reg.setSelection(password_reg.length());
            }
        });
        passwordIcon_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordShowing){
                    passwordShowing=false;

                    password_con.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon_con.setImageResource(R.drawable.show_password);
                }else{
                    passwordShowing=true;

                    password_con.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon_con.setImageResource(R.drawable.hide_password);
                }
                password_con.setSelection(password_con.length());
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}