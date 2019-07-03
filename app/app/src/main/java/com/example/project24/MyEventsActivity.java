package com.example.project24;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MyEventsActivity extends Fragment {
    public static final String EXTRA_MYEVENT = "com.example.project2.4.MYEVENTNAAM";
    public ArrayList<HashMap<String,String>> list;
    private SimpleAdapter sa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_my_events, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listView = getView().findViewById(R.id.myEventsListView);
        final TextView textView = getView().findViewById(R.id.noMyEventsText);
        list = new ArrayList<HashMap<String,String>>();

        ApiClient.getEventsById(getContext(), MainActivity.app.getUser_id(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
                    if (!response.getString("result").equals("null")) {
                        textView.setVisibility(View.INVISIBLE);

                        JSONArray result = response.getJSONArray("result");
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject friendsEventsObject = result.getJSONObject(i);
                            HashMap<String, String> item = new HashMap<String, String>();
                            item.put("naam", friendsEventsObject.getString("title"));
                            item.put("event_ID", friendsEventsObject.getString("event_ID"));
                            list.add(item);

                        }


                        sa = new SimpleAdapter(getContext(), list,
                                R.layout.twolines,
                                new String[]{"naam", "event_ID"},
                                new int[]{R.id.line_one, R.id.line_two});

                        listView.setAdapter(sa);
                    } else {
                        textView.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", ""+ error);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                String eventnaam= listView.getItemAtPosition(pos).toString();
                String[] firstStep= eventnaam.split(",");
                String[] secondStep = firstStep[0].split("=");
                String eventId= secondStep[1];
                EventActivity eventActivity = new EventActivity();
                Bundle bundle = new Bundle();
                bundle.putString("event_id",eventId);
                eventActivity.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,eventActivity).addToBackStack(null).commit();
                    }
            });
    }
}


