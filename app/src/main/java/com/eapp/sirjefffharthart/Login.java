package com.eapp.sirjefffharthart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectsirjeff-default-rtdb.firebaseio.com/");

    private boolean passwordShowing =  false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameET = findViewById(R.id.usernameET);
        final EditText passwordET = findViewById(R.id.passwordET);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final TextView signUpBtn = findViewById(R.id.signUpBtn);
        final Button signInBtn = findViewById(R.id.signInBtn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernameEt = usernameET.getText().toString();
                String passwordEt = passwordET.getText().toString();

                if (usernameEt.isEmpty() || passwordEt.isEmpty()){
                    Toast.makeText(Login.this, "Please, Enter Email and Password!", Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(usernameEt)){

                                //Reading a user registered

                                final String getPassword = snapshot.child(usernameEt).child("Password").getValue(String.class);

                                if (getPassword.equals(passwordEt)){
                                    Toast.makeText(Login.this, "Successfully Login", Toast.LENGTH_SHORT).show();

                                    final String getGender = snapshot.child(usernameEt).child("Gender").getValue(String.class);
                                    if (getGender.isEmpty()) {
                                        Intent intent = new Intent(Login.this, Gender.class);
                                        intent.putExtra("Username", usernameEt);
                                        startActivity(intent);
                                        finish();
                                    }else if (getGender.equals("Male")){
                                        Intent intent = new Intent(Login.this, Male_dashboard.class);
                                        intent.putExtra("Username", usernameEt);
                                        startActivity(intent);
                                        finish();
                                    }else if (getGender.equals("Female")){
                                        Intent intent = new Intent(Login.this, Female_dashboard.class);
                                        intent.putExtra("Username", usernameEt);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                                else{
                                    Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(Login.this, "Wrong Username", Toast.LENGTH_SHORT).show();
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

                    passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.show_password);
                }else{
                    passwordShowing=true;

                    passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.hide_password);
                }
                passwordET.setSelection(passwordET.length());
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registration.class));
            }
        });
    }
}