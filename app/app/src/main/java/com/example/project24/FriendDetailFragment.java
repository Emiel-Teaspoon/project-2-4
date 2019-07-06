package com.example.project24;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FriendDetailFragment extends Fragment {

    String friend;
    public ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    private SimpleAdapter sa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            friend = bundle.getString("friend_name","Bart");
        }

        TextView friendNameLabel = getView().findViewById(R.id.nameLabel);
        String nameLabel = "Name:";
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
        String eventLabel = "The events of " + friend;
        friendEventLabel.setText(eventLabel);

        final ListView listView = getView().findViewById(R.id.detailEvents);
        TextView emptyViewText = getView().findViewById(R.id.noFriendEventsText);
        listView.setEmptyView(emptyViewText);

        ApiClient.getFriendEvents(getContext(), friend, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Friend Detail response", response.toString());
                try {

                    JSONArray result = response.getJSONArray("result");
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject eventObject = result.getJSONObject(i);
                        HashMap<String,String> item = new HashMap<String,String>();
                        item.put("naam",eventObject.getString("title"));
                        item.put("event_ID",eventObject.getString("event_ID"));
                        list.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sa = new SimpleAdapter(getContext(), list,
                        R.layout.twolines,
                        new String[] { "naam","event_ID"},
                        new int[] {R.id.line_one, R.id.line_two});

                listView.setAdapter(sa);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "A problem occurred attempting to retrieve the events");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                String eventnaam= listView.getItemAtPosition(pos).toString();
                String[] firstStep= eventnaam.split(",");
                String[] secondStep = firstStep[0].split("=");
                String eventId= secondStep[1];
                EventFragment eventFragment = new EventFragment();
                Bundle bundle = new Bundle();
                bundle.putString("event_id",eventId);
                eventFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, eventFragment).addToBackStack(null).commit();
            }
        });
    }
}
