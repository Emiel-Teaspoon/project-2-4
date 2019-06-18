package com.example.project24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {

    public final static String EXTRA_FRIEND = "com.example.seriesapp.FRIEND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        final ListView listView = findViewById(R.id.listView);
        ArrayList<String> friendsList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "url";

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                friendsList
        );
        listView.setAdapter(adapter);


        //TODO remove test line below
        adapter.add("Bob");

        // TODO Implement JSONArrayRequest to fetch all friends
        // TODO Add the request to the queue

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                String friend = (String) listView.getItemAtPosition(pos);
                Intent intent = new Intent(FriendsActivity.this, FriendDetailActivity.class);
                intent.putExtra(EXTRA_FRIEND, friend);
                startActivity(intent);
            }
        });
    }
}
