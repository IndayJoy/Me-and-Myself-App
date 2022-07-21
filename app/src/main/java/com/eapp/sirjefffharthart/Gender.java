package com.eapp.sirjefffharthart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class Gender extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectsirjeff-default-rtdb.firebaseio.com/");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);


        final TextView user = findViewById(R.id.user);
        final ImageView getMale = findViewById(R.id.male);
        final ImageView getFemale = findViewById(R.id.female);

        final String getUsername = getIntent().getStringExtra("Username");


        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(getUsername)){
                    final String getName = snapshot.child(getUsername).child("Fullname").getValue(String.class);

                    user.setText(getName+"!");

                    getMale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseReference.child("users").child(getUsername).child("Gender").setValue("Male");
                            Toast.makeText(Gender.this, "Bot says : Gender Update....", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Gender.this, Male_dashboard.class);
                            intent.putExtra("Username", getUsername);
                            startActivity(intent);
                            finish();
                        }
                    });
                    getFemale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseReference.child("users").child(getUsername).child("Gender").setValue("Female");
                            Toast.makeText(Gender.this, "Bot says : Gender Update....", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Gender.this, Female_dashboard.class);
                            intent.putExtra("Username", getUsername);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}