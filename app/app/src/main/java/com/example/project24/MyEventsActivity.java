package com.example.project24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyEventsActivity extends AppCompatActivity {
    public static final String EXTRA_MYEVENT = "com.example.project2.4.MYEVENTNAAM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);
        final ListView listView = findViewById(R.id.MyEventList);
        String[] resultaten = new String[]{};

        final List<String> res_list = new ArrayList<String>(Arrays.asList(resultaten));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, res_list);
        listView.setAdapter(arrayAdapter);
        res_list.add("Mc Donalds");
        res_list.add("KFC");
        res_list.add("SUBWAY");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                String eventnaam= listView.getItemAtPosition(pos).toString();
                Intent intent = new Intent(MyEventsActivity.this, EventActivity.class);
                intent.putExtra(EXTRA_MYEVENT, eventnaam);
                startActivity(intent);
                    }
            });
    }
}


