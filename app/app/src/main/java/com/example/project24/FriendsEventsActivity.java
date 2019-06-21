package com.example.project24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendsEventsActivity extends AppCompatActivity {
    public static final String EXTRA_EVENTNAAM = "com.example.project2.4.EVENTNAAM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_events);
        final ListView listView = findViewById(R.id.friendsEvents);
        String[] resultaten = new String[]{};

        final List<String> res_list = new ArrayList<String>(Arrays.asList(resultaten));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, res_list);
        listView.setAdapter(arrayAdapter);

        ApiClient.getFriendsEvents(this, 1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
                    JSONArray result = response.getJSONArray("result");
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject friendsEventsObject = result.getJSONObject(i);
                        arrayAdapter.add(friendsEventsObject.getString("title"));
                        Log.d("test",friendsEventsObject.getString("title"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Things did not work");
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                String eventnaam= listView.getItemAtPosition(pos).toString();
                Intent intent = new Intent(FriendsEventsActivity.this, EventActivity.class);
                intent.putExtra(EXTRA_EVENTNAAM, eventnaam);
                startActivity(intent);
            }
        });

        }

}
