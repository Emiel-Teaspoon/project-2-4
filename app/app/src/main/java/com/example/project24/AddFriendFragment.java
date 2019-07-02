package com.example.project24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class AddFriendFragment extends Fragment {
    public ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    private SimpleAdapter sa;
    private ListView listView;
    private TextView text;
    private Button btn;
    private String messageResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_friend, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = getView().findViewById(R.id.addFriendsListView);
        text = getView().findViewById(R.id.addFriendsEditText);

        btn = getView().findViewById(R.id.addFriendsButton3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zoekopdracht = text.getText().toString();
                int user_id = MainActivity.app.getUser_id();
                Log.d("zoekopdracht", zoekopdracht);

                ApiClient.searchUserByUsername(getContext(), zoekopdracht,user_id, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        try {
                            JSONArray result = response.getJSONArray("result");
                            JSONObject friendsEventsObject = result.getJSONObject(0);
                            HashMap<String, String> item = new HashMap<String, String>();
                            item.put("naam", friendsEventsObject.getString("username"));
                            item.put("user_id", friendsEventsObject.getString("user_id"));
                            list.add(item);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        sa = new SimpleAdapter(getContext(), list,
                                R.layout.twolines,
                                new String[]{"naam", "user_id"},
                                new int[]{R.id.line_one, R.id.line_two});

                        listView.setAdapter(sa);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", "Things did not work");
                    }
                });
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                String friendNaam = listView.getItemAtPosition(pos).toString();
                String[] firstStep = friendNaam.split(",");
                String[] secondStep = firstStep[0].split("=");
                String friendiD = secondStep[1];
                Log.d("check", friendiD);
                ApiClient.followFriend(getContext(), MainActivity.app.getUser_id(), Integer.valueOf(friendiD), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            messageResponse = response.getString("Message");


                        } catch (JSONException ex) {
                        }
                        if (messageResponse.equals("Success")){
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new FriendsActivity()).addToBackStack(null).commit();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", "" + error);
                    }

                });


            }
        });
    }
}
