package com.eapp.sirjefffharthart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class Male_dashboard extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectsirjeff-default-rtdb.firebaseio.com/");

    private int CurrentProgress = 0;
    private int CurrentProgress2 = 0;
    private int CurrentProgress3 = 0;
    private int add = 0;
    private int count = 0;
    private ProgressBar progressBar,progressBar2, progressBar3;
    private Button startProgress,hobbies,family;
    private TextView muscle,info, history;
    private AlertDialog.Builder dialogBuilder,dialogBuilder1,dialogBuilder2 ;
    private AlertDialog dialog,dialog1,dialog2;
    private EditText edit_fullname,edit_email;
    private Button update, che;
    private ImageView exit,refresh ;
    private String getUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_male_dashboard);


        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar3 = findViewById(R.id.progress3);
        startProgress = findViewById(R.id.startProgress);
        hobbies = findViewById(R.id.hobbies);
        family = findViewById(R.id.family);
        history = findViewById(R.id.history);
        refresh = findViewById(R.id.refresh);

        final TextView logout = findViewById(R.id.logoutBtn);
        final TextView user = findViewById(R.id.user);
        final TextView mobuser = findViewById(R.id.mobUser);
        final TextView emailuser = findViewById(R.id.emailUser);
        final TextView editBtn = findViewById(R.id.edit);



        final String getUsername = getIntent().getStringExtra("Username");
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(getUsername)){
                            Integer progress = 0;
                            Integer progress2 = 0;
                            Integer progress3 = 0;
                            progress = snapshot.child(getUsername).child("Done").child(currentDate).child("progress").getValue(Integer.class);
                            progress2 = snapshot.child(getUsername).child("Done").child(currentDate).child("progress2").getValue(Integer.class);
                            progress3 = snapshot.child(getUsername).child("Done").child(currentDate).child("progress3").getValue(Integer.class);

                            if (!progress.equals(0)){
                                CurrentProgress =  progress;
                                progressBar.setProgress(CurrentProgress);
                            }else{
                                Toast.makeText(Male_dashboard.this, "NO Value", Toast.LENGTH_SHORT).show();
                            }
                            if (!progress2.equals(0)){
                                CurrentProgress2 =  progress2;
                                progressBar2.setProgress(CurrentProgress2);
                            }else{
                                Toast.makeText(Male_dashboard.this, "NO Value", Toast.LENGTH_SHORT).show();
                            }
                            if (!progress3.equals(0)){
                                CurrentProgress3 =  progress3;
                                progressBar3.setProgress(CurrentProgress3);
                            }else{
                                Toast.makeText(Male_dashboard.this, "NO Value", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(Male_dashboard.this, "NO Value", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Male_dashboard.this, "No Value", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHistory();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Male_dashboard.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(getUsername)){
                    content();
                    final String getName = snapshot.child(getUsername).child("Fullname").getValue(String.class);
                    final String getEmail = snapshot.child(getUsername).child("Email").getValue(String.class);
                    final String getMobile = snapshot.child(getUsername).child("Mobile").getValue(String.class);

                    user.setText("Name: "+getName);
                    emailuser.setText("Email: "+getEmail);
                    mobuser.setText("Mobile #: "+getMobile);


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        startProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Male_dashboard.this, Checklist.class);
                intent.putExtra("Username", getUsername);
                startActivity(intent);
            }
        });
        hobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Male_dashboard.this, Male_Hobbies.class);
                intent.putExtra("Username", getUsername);
                startActivity(intent);
            }
        });
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Male_dashboard.this, Male_rate_day.class);
                intent.putExtra("Username", getUsername);
                startActivity(intent);
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextDialog();
            }
        });
    }
    private void viewHistory(){
        dialogBuilder1 = new AlertDialog.Builder(this);
        final View popupView1 = getLayoutInflater().inflate(R.layout.popup_view, null);

        EditText searh1 = (EditText) popupView1.findViewById(R.id.search);
        Button find1 = (Button) popupView1.findViewById(R.id.find);



        dialogBuilder1.setView(popupView1);
        dialog1 = dialogBuilder1.create();
        dialog1.show();

        dialogBuilder2 = new AlertDialog.Builder(this);
        final View popupView2 = getLayoutInflater().inflate(R.layout.popup_views, null);

        TextView val1 = (TextView) popupView2.findViewById(R.id.val1);
        TextView val2 = (TextView) popupView2.findViewById(R.id.val2);
        TextView val3 = (TextView) popupView2.findViewById(R.id.val3);
        TextView valjourn = (TextView) popupView2.findViewById(R.id.valjourn);
        TextView id_date = (TextView) popupView2.findViewById(R.id.id_date);
        ImageView exit15 = (ImageView) popupView2.findViewById(R.id.exit15);

        dialogBuilder2.setView(popupView2);
        dialog2 = dialogBuilder2.create();


        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());

        String getUsername = getIntent().getStringExtra("Username");

        find1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String search = searh1.getText().toString();
                        if (snapshot.hasChild(getUsername)){
                            final String value = snapshot.child(getUsername).child("Done").child(search).child("Status").getValue(String.class);
                            final String value1 = snapshot.child(getUsername).child("Done").child(search).child("StatusHob").getValue(String.class);
                            final String value2 = snapshot.child(getUsername).child("Done").child(search).child("Mood").getValue(String.class);
                            final String value3 = snapshot.child(getUsername).child("Done").child(search).child("Journal").getValue(String.class);
                            dialog2.show();
                            id_date.setText("Date Viewing : "+search);
                            val1.setText("Activity Status : "+value);
                            val2.setText("Hobbies Status : "+value1);
                            val3.setText("My Mood : "+value2);
                            valjourn.setText(value3);
                        }else{
                            Toast.makeText(Male_dashboard.this, "No", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        exit15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
    }
    private void content(){

        count++;

        refresh(1000);
    }
    private void refresh(int milliseconds){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };
        handler.postDelayed(runnable,milliseconds);
    }

    private void editTextDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.popup, null);

        edit_fullname = (EditText) popupView.findViewById(R.id.edit_fullname);
        edit_email = (EditText) popupView.findViewById(R.id.edit_Email);

        update = (Button) popupView.findViewById(R.id.update_data);
        exit = (ImageView) popupView.findViewById(R.id.exit1);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getUsername = getIntent().getStringExtra("Username");
                if(snapshot.hasChild(getUsername)){

                    final String getName = snapshot.child(getUsername).child("Fullname").getValue(String.class);
                    final String getEmail = snapshot.child(getUsername).child("Email").getValue(String.class);

                    edit_fullname.setText(getName);
                    edit_email.setText(getEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        getUsername = getIntent().getStringExtra("Username");
                        if(snapshot.hasChild(getUsername)){
                            String name = edit_fullname.getText().toString();
                            String email = edit_email.getText().toString();

                            databaseReference.child("users").child(getUsername).child("Fullname").setValue(name);
                            databaseReference.child("users").child(getUsername).child("Email").setValue(email);

                            Toast.makeText(Male_dashboard.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}