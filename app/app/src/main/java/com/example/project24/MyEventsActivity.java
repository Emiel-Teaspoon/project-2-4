package com.example.project24;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyEventsActivity extends Fragment {
    public static final String EXTRA_MYEVENT = "com.example.project2.4.MYEVENTNAAM";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_my_events, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listView = getView().findViewById(R.id.MyEventList);
        String[] resultaten = new String[]{};

        final List<String> res_list = new ArrayList<String>(Arrays.asList(resultaten));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_1, res_list);
        listView.setAdapter(arrayAdapter);
        res_list.add("Mc Donalds");
        res_list.add("KFC");
        res_list.add("SUBWAY");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                String eventnaam= listView.getItemAtPosition(pos).toString();
                EventActivity eventActivity = new EventActivity();
                Bundle bundle = new Bundle();
                bundle.putString("event_naam",eventnaam);
                eventActivity.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,eventActivity).commit();
                    }
            });
    }
}


