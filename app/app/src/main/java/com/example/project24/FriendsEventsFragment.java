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

public class FriendsEventsFragment extends Fragment {
    public static final String EXTRA_EVENTNAAM = "com.example.project2.4.EVENTNAAM";
    public ArrayList<HashMap<String,String>> list;
    private SimpleAdapter sa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends_events, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listView = getView().findViewById(R.id.friendsEventsListView);
        TextView emptyListText = getView().findViewById(R.id.noFriendsEventsText);
        listView.setEmptyView(emptyListText);
        list = new ArrayList<HashMap<String,String>>();

        ApiClient.getFriendsEvents(getContext(), MainActivity.app.getUser_id(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
                    if(!response.getString("result").equals("null")){
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
                                new String[] { "naam","event_ID"},
                                new int[] {R.id.line_one, R.id.line_two});

                        listView.setAdapter(sa);
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
                String[] firstStep = eventnaam.split(",");
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
