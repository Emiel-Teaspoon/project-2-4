package com.example.project24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {

    public final static String EXTRA_FRIEND = "com.example.seriesapp.FRIEND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        final ListView listView = findViewById(R.id.listView);
        ArrayList<String> friendsList = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                friendsList
        );
        listView.setAdapter(adapter);

        ApiClient.getFriends(this, 1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Succes", "Response");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Things did not work");
            }
        });

        //TODO remove test line below
        adapter.add("Bob");

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
