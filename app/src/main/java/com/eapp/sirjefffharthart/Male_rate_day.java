package com.eapp.sirjefffharthart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.text.DateFormat;
import java.util.Calendar;

public class Male_rate_day extends AppCompatActivity {
    DatabaseReference databaseReference;
    EditText mood,journal;
    TextView save;
    ImageView happy, angry, sad, worried, shy, excited;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_male_rate_day);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectsirjeff-default-rtdb.firebaseio.com/");

        happy = (ImageView) findViewById(R.id.happy);
        angry = (ImageView) findViewById(R.id.angry);
        sad = (ImageView) findViewById(R.id.sad);
        worried = (ImageView) findViewById(R.id.worried);
        shy = (ImageView) findViewById(R.id.shy);
        excited = (ImageView) findViewById(R.id.excited);
        save = (TextView) findViewById(R.id.save);
        mood = (EditText) findViewById(R.id.mood);
        journal = (EditText) findViewById(R.id.journal);

        final String getUsername = getIntent().getStringExtra("Username");

        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mood.setText("Happy");
                Toast.makeText(Male_rate_day.this, "Im So Happy for you!", Toast.LENGTH_SHORT).show();
            }
        });
        angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mood.setText("Angry");
                Toast.makeText(Male_rate_day.this, "Pls. Dont be Angry", Toast.LENGTH_SHORT).show();
            }
        });
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mood.setText("Sad");
                Toast.makeText(Male_rate_day.this, "I hope you be okay sooner or later!", Toast.LENGTH_SHORT).show();
            }
        });
        worried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mood.setText("Worried");
                Toast.makeText(Male_rate_day.this, "Don't be so Worried you can do it!", Toast.LENGTH_SHORT).show();
            }
        });
        shy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mood.setText("Shy");
                Toast.makeText(Male_rate_day.this, "You can Do it again!", Toast.LENGTH_SHORT).show();
            }
        });
        excited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mood.setText("Excited");
                Toast.makeText(Male_rate_day.this, "Let's Go!", Toast.LENGTH_SHORT).show();
            }
        });
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(getUsername)){
                            String moodGet = mood.getText().toString();
                            String journGet = journal.getText().toString();
                            databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Mood").setValue(moodGet);
                            databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Journal").setValue(journGet);
                            databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("progress3").setValue(100);
                            Toast.makeText(Male_rate_day.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}