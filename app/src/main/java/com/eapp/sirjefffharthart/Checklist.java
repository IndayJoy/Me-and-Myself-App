package com.eapp.sirjefffharthart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;

public class Checklist extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectsirjeff-default-rtdb.firebaseio.com/");
    private int CurrentProgress = 0;
    private int add = 0;
    private AlertDialog.Builder dialogBuilder ;
    private AlertDialog dialog;
    private CheckBox act1, act2, act3, act4, act5;
    TextView sample1,date,Record, b, set;
    EditText Daily1,Daily2,Daily3,Daily4,Daily5,Date1,today;
    Button setdata;
    ImageView refresh,exit1 ;
    private String getUsername;
    private  boolean show = false;


    String sample[] = {"3x 15reps normal Push ups","1x 15reps Triangular Push ups","1x 15reps Wide Hands Push-Up","2x 15reps Archer Push-Up","1x 15reps Power and Clap Push-Ups"};
    String sample2[] = {"2x 2mins Squat","2x 15reps Squat Jumps","2mins Wall sits","15reps both sides Leg raises","Go for a Walk"};
    String sample3[] = {"2x 15reps Hockey Grip Pullup","2x 15reps Wide-Grip Pullup","1x 10reps Close-Grip Pullup"};
    String sample4[] = {"Do a Plank for 2-5mins","Side Plank for both sides 2mins ","2x 15reps Tuck-crunch-extend"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);



        sample1 = (TextView) findViewById(R.id.sample);
        Record = (TextView) findViewById(R.id.Record);
        refresh = (ImageView) findViewById(R.id.refresh);
        exit1 = (ImageView) findViewById(R.id.exit1);
        today = (EditText) findViewById(R.id.today);
        act1 = (CheckBox) findViewById(R.id.act1);
        act2 = (CheckBox) findViewById(R.id.act2);
        act3 = (CheckBox) findViewById(R.id.act3);
        act4 = (CheckBox) findViewById(R.id.act4);
        b = (TextView) findViewById(R.id.b);
        set = (TextView) findViewById(R.id.set);
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());
        final String getUsername = getIntent().getStringExtra("Username");

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act1.setText("Loading.........");
                act2.setText("Loading.........");
                act3.setText("Loading.........");
                act4.setText("Loading.........");

                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());

                today.setText(currentDate);

                String now = today.getText().toString();

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(getUsername)){
                            final String D1 = snapshot.child(getUsername).child("Activities").child(now).child("Daily1").getValue(String.class);
                            final String D2 = snapshot.child(getUsername).child("Activities").child(now).child("Daily2").getValue(String.class);
                            final String D3 = snapshot.child(getUsername).child("Activities").child(now).child("Daily3").getValue(String.class);
                            final String D4 = snapshot.child(getUsername).child("Activities").child(now).child("Daily4").getValue(String.class);

                            act1.setText(D1);
                            act2.setText(D2);
                            act3.setText(D3);
                            act4.setText(D4);

                            show=true;

                            Record.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAct();
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                act1 = (CheckBox) findViewById(R.id.act1);
                act2 = (CheckBox) findViewById(R.id.act2);
                act3 = (CheckBox) findViewById(R.id.act3);
                act4 = (CheckBox) findViewById(R.id.act4);

                String D1 = act1.getText().toString();
                String D2 = act2.getText().toString();
                String D3 = act3.getText().toString();
                String D4 = act4.getText().toString();
                if (act1.isChecked()){
                    CurrentProgress = CurrentProgress + 25;
                    add = add+25;
                    databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("progress").setValue(add);

                    databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Donedaily1").setValue(D1);

                    databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Status").setValue("Not All Done");
                }
                if(act2.isChecked()){
                    CurrentProgress = CurrentProgress + 25;
                    add = add+25;
                    databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("progress").setValue(add);

                    databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Donedaily2").setValue(D2);

                    databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Status").setValue("Not All Done");
                }
                if(act3.isChecked()){
                    CurrentProgress = CurrentProgress + 25;
                    add = add+25;
                    databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("progress").setValue(add);

                    databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Donedaily3").setValue(D3);

                    databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Status").setValue("Not All Done");
                }
                if(act4.isChecked()){
                    CurrentProgress = CurrentProgress + 25;
                    add = add+25;
                    databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("progress").setValue(add);

                    databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Donedaily4").setValue(D4);

                    databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Status").setValue("Not All Done");
                }
                if (act1.isChecked() && act2.isChecked() && act3.isChecked() && act4.isChecked()){
                    databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Status").setValue("All Done");
                }else{
                    CurrentProgress = CurrentProgress + 0;
                    add = add+0;
                }
                Toast.makeText(Checklist.this, "Recorded", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void setAct() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.addactmale, null);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());


        setdata = (Button) popupView.findViewById(R.id.setdata);
        date = (TextView) popupView.findViewById(R.id.date);
        Date1 = (EditText) popupView.findViewById(R.id.Date1);
        Daily1 = (EditText) popupView.findViewById(R.id.Daily1);
        Daily2 = (EditText) popupView.findViewById(R.id.Daily2);
        Daily3 = (EditText) popupView.findViewById(R.id.Daily3);
        Daily4 = (EditText) popupView.findViewById(R.id.Daily4);
        exit1 = (ImageView) popupView.findViewById(R.id.exit1);



        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();
        date.setText(currentDate);


        setdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int num1  =  random.nextInt(sample.length);
                int num2  =  random.nextInt(sample2.length);
                int num3  =  random.nextInt(sample3.length);
                int num4  =  random.nextInt(sample4.length);

                Daily1.setText(sample[num1]);
                Daily2.setText(sample2[num2]);
                Daily3.setText(sample3[num3]);
                Daily4.setText(sample4[num4]);
                Date1.setText(currentDate);

                String D1 = Daily1.getText().toString();
                String D2 = Daily2.getText().toString();
                String D3 = Daily3.getText().toString();
                String D4 = Daily4.getText().toString();
                String Date = Date1.getText().toString();



                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        getUsername = getIntent().getStringExtra("Username");
                        if (snapshot.hasChild(getUsername)) {

                            databaseReference.child("users").child(getUsername).child("Activities").child(Date).child("Daily1").setValue(D1);
                            databaseReference.child("users").child(getUsername).child("Activities").child(Date).child("Daily2").setValue(D2);
                            databaseReference.child("users").child(getUsername).child("Activities").child(Date).child("Daily3").setValue(D3);
                            databaseReference.child("users").child(getUsername).child("Activities").child(Date).child("Daily4").setValue(D4);
                            databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Donedaily1").setValue("");
                            databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Donedaily2").setValue("");
                            databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Donedaily3").setValue("");
                            databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("Donedaily4").setValue("");
                            databaseReference.child("users").child(getUsername).child("Done").child(currentDate).child("progress").setValue(0);
                            Toast.makeText(Checklist.this, "Activity Set", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        exit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}