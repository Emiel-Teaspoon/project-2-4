package com.example.project24;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listView = getView().findViewById(R.id.friendsListView);
        ArrayList<String> friendsList = new ArrayList<>();
        TextView emptyListText = getView().findViewById(R.id.noFriendsText);
        listView.setEmptyView(emptyListText);
        FloatingActionButton addFriendsButton = getView().findViewById(R.id.addFriendsButton);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                friendsList
        );
        listView.setAdapter(adapter);

        ApiClient.getFriends(getContext(),MainActivity.app.getUser_id(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("FriendList Response", response.toString());
                try {
                    JSONArray result = response.getJSONArray("result");
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject friendObject = result.getJSONObject(i);
                        adapter.add(friendObject.getString("username"));
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
                String friend = (String) listView.getItemAtPosition(pos);
                FriendDetailFragment friendDetailFragment = new FriendDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("friend_name",friend);
                friendDetailFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, friendDetailFragment).addToBackStack(null).commit();

            }
        });
        addFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddFriendFragment()).addToBackStack(null).commit();
            }
        });
    }
}
