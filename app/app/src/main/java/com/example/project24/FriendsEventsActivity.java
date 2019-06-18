package com.example.project24;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendsEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_events);
        ListView listView = findViewById(R.id.friendsEvents);
        String[] resultaten = new String[]{};

        final List<String> res_list = new ArrayList<String>(Arrays.asList(resultaten));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, res_list);
        listView.setAdapter(arrayAdapter);


        res_list.add("Mc Donalds");
        res_list.add("Reef and Beef");
        }

}
