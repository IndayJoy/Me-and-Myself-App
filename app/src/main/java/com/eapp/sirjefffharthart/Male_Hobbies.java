package com.eapp.sirjefffharthart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Male_Hobbies extends AppCompatActivity {
    DatabaseReference databaseReference;
    public ListView listView;
    TextView btn,sample,reset;
    ImageView refresh;
    EditText wew1,wew2,hobo;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_male_hobbies);
        databaseReference=FirebaseDatabase.getInstance().getReference("users");

        hobo = (EditText) findViewById(R.id.hob);
        sample = (TextView) findViewById(R.id.sample);
        reset = (TextView) findViewById(R.id.reset);
        refresh = (ImageView) findViewById(R.id.refresh);
        listView = (ListView) findViewById(R.id.lv_data);
        btn = (TextView) findViewById(R.id.btn);
        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,arrayList);
        listView.setAdapter(arrayAdapter);
        final String getUsername = getIntent().getStringExtra("Username");

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
                Toast.makeText(Male_Hobbies.this, "Removed : " + arrayList.get(i), Toast.LENGTH_SHORT).show();
                removeItem(i);
                return false;
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(getUsername).child("Hobbies").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(getUsername).child("Hobbies").removeValue();
                        Toast.makeText(Male_Hobbies.this, "Hobbies Reset", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

                databaseReference.child(getUsername).child("Hobbies").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

                        String value = dataSnapshot.getValue(Users.class).toString();
                        arrayList.add(value);
                        arrayAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(Users.class).toString();
                        arrayList.remove(value);
                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hob = hobo.getText().toString();

                Users users = new Users(hob);
                databaseReference.child(getUsername).child("Hobbies").push().setValue(users);
                hobo.setText("");
                Toast.makeText(Male_Hobbies.this, "Then Save it", Toast.LENGTH_SHORT).show();
            }
        });
            }

    private void removeItem(int remove) {
        arrayList.remove(remove);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String getUsername = getIntent().getStringExtra("Username");
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());
        int progress = 100;
        int progList = listView.getCount();
        int Total = progress / progList;
        int Totals = 0;
        int id = item.getItemId();
        if (id == R.id.item_done){
            String itemSelected = "Selected Items : ";
            for (int i = 0;i<listView.getCount();i++){
                databaseReference.child(getUsername).child("Done").child(currentDate).child("Hobbies"+i).setValue("");
                if (listView.isItemChecked(i)){
                    itemSelected += listView.getItemAtPosition(i) + "\n";
                    databaseReference.child(getUsername).child("Done").child(currentDate).child("Hobbies" + i).setValue(listView.getItemAtPosition(i));
                    int count = listView.getCheckedItemCount();
                    Totals = Total * count ;
                    databaseReference.child(getUsername).child("Done").child(currentDate).child("progress2").setValue(Totals);
                    if (listView.getCheckedItemCount() == listView.getCount()) {
                        databaseReference.child(getUsername).child("Done").child(currentDate).child("StatusHob").setValue("All Done");
                    } else {
                        databaseReference.child(getUsername).child("Done").child(currentDate).child("StatusHob").setValue("Not All Done");
                    }
                }
            }
            Toast.makeText(this,    itemSelected, Toast.LENGTH_SHORT).show();
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}