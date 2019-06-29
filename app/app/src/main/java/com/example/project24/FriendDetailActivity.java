package com.example.project24;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendDetailActivity extends Fragment {

    String friend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_friend_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            friend = bundle.getString("friend_name","Bart");
        }

        TextView friendNameLabel = getView().findViewById(R.id.nameLabel);
        String nameLabel = "Naam:";
        friendNameLabel.setText(nameLabel);

        TextView friendName = getView().findViewById(R.id.friendName);
        friendName.setText(friend);

        TextView friendEmailLabel = getView().findViewById(R.id.emailLabel);
        String emailLabel = "Email:";
        friendEmailLabel.setText(emailLabel);

        final TextView friendEmail = getView().findViewById(R.id.friendEmail);
        ApiClient.getUserByUsername(getContext(), friend, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Friend Detail response", response.toString());
                try {
                    JSONArray result = response.getJSONArray("result");
                    JSONObject friendObject = result.getJSONObject(0);
                    friendEmail.setText(friendObject.getString("email"));
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

        TextView friendEventLabel = getView().findViewById(R.id.eventLabel);
        String eventLabel = "Events van " + friend;
        friendEventLabel.setText(eventLabel);

        final ListView listView = getView().findViewById(R.id.detailEvents);
        ArrayList<String> eventList = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                eventList
        );
        listView.setAdapter(adapter);

        ApiClient.getFriendEvents(getContext(), friend, new Response.Listener<JSONObject>() {
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
