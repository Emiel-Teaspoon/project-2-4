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

        final ListView listView = findViewById(R.id.friendDetailEvents);
        ArrayList<String> eventList = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                eventList
        );
        listView.setAdapter(adapter);

        TextView textView = findViewById(R.id.friendName);
        textView.setText(friend);

        ApiClient.getFriendEvents(this, friend, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
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
