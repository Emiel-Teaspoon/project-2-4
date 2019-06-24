package com.example.project24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        Intent intent = getIntent();
        String friend = intent.getStringExtra(FriendsActivity.EXTRA_FRIEND);

        TextView friendNameLabel = findViewById(R.id.nameLabel);
        String nameLabel = "Naam:";
        friendNameLabel.setText(nameLabel);

        TextView friendName = findViewById(R.id.friendName);
        friendName.setText(friend);

        TextView friendEmailLabel = findViewById(R.id.emailLabel);
        String emailLabel = "Email:";
        friendEmailLabel.setText(emailLabel);

        final TextView friendEmail = findViewById(R.id.friendEmail);
        ApiClient.getUserByUsername(this, friend, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Friend Detail response", response.toString());
                try {
                    JSONArray result = response.getJSONArray("result");
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject friendObject = result.getJSONObject(i);
                        friendEmail.setText(friendObject.getString("email"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "A problem occurred attempting to retrieve the events");
            }
        });

        TextView friendEventLabel = findViewById(R.id.eventLabel);
        String eventLabel = "Events van " + friend;
        friendEventLabel.setText(eventLabel);

        final ListView listView = findViewById(R.id.detailEvents);
        ArrayList<String> eventList = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                eventList
        );
        listView.setAdapter(adapter);

        ApiClient.getFriendEvents(this, friend, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Friend Detail response", response.toString());
                try {
                    JSONArray result = response.getJSONArray("result");
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject eventObject = result.getJSONObject(i);
                        adapter.add(eventObject.getString("title") + "#" + eventObject.getString("event_ID"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "A problem occurred attempting to retrieve the events");
            }
        });


    }
}
