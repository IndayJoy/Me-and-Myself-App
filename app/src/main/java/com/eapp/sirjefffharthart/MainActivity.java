package com.eapp.sirjefffharthart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> userlist;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initRecyclerView();
    }

    private void initData() {

        userlist = new ArrayList<>();

        userlist.add(new ModelClass(R.drawable.baz, "-BazLuhrmann",  "“Trust yourself that you can do it and get it.”", "_______________"));

        userlist.add(new ModelClass(R.drawable.walt, "-WaltDisney",  "“All our dreams can come true, if we have the courage to pursue them.”", "_______________"));

        userlist.add(new ModelClass(R.drawable.socrates, "-Socrates",  "“Smart people learn from everything and everyone, average people from their experiences, stupid people already have all the answers.”", "_______________"));

        userlist.add(new ModelClass(R.drawable.johann, "-JohannWolfgangVonGoethe",  "“Magic is believing in yourself. If you can make that happen, you can make anything happen”", "_______________"));

        userlist.add(new ModelClass(R.drawable.jaymin, "-Jaymin Shah",  "“No one is to blame for your future situation but yourself. If you want to be successful, then become Successful.”", "_______________"));

        userlist.add(new ModelClass(R.drawable.stephen, "-Stephen Chbosky",  "“Even if we don't have the power to choose where we come from, we can still choose where we go from there.”", "_______________"));

        userlist.add(new ModelClass(R.drawable.holly, "-Holly Black",  "“I haven't had a very good day. I think I might still be hungover and everyone's dead and my root beer's gone.”", "_______________"));

        userlist.add(new ModelClass(R.drawable.charles, "-Charles Darwin",  "“But I am very poorly today & very stupid & I hate everybody & everything. One lives only to make blunders.”", "_______________"));

        userlist.add(new ModelClass(R.drawable.oscar, "-Oscar Wilde",  "“Life is never fair, and perhaps it is a good thing for most of us that it is not.”", "_______________"));

        userlist.add(new ModelClass(R.drawable.johann, "-JohannWolfgangVonGoethe",  "“Everything is hard before it is easy.”", "_______________"));

        userlist.add(new ModelClass(R.drawable.johnstein, "-John Steinbeck",  "“If it is right, it happens—the main thing is not to hurry. Nothing good gets away.”", "_______________"));

        userlist.add(new ModelClass(R.drawable.roalddahl, "-Roald Dahl",  "“It doesn’t matter who you are or what you look like, so long as somebody loves you.”", "_______________"));

        userlist.add(new ModelClass(R.drawable.alice, "-Alice Walker", "“I have learned not to worry about love; but to honor its coming with all my heart.”", "_______________"));

        userlist.add(new ModelClass(R.drawable.nicholas, "-Nicholas Sparks", "“Love is like the wind, you can’t see it but you can feel it.”", "_______________"));

        userlist.add(new ModelClass(R.drawable.henry, "-Henry Miller", "“The one thing we can never get enough of is love. And the one thing we never give enough is love.”", "_______________"));
    }

    private void initRecyclerView() {

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(userlist);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}