package com.example.project24;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventActivity extends Fragment {
    String naam;
    int id;
    int event_ID;
    TextView eventnaam;
    TextView eventadres;
    TextView eventbegin;
    TextView eventeinde;
    TextView eventmaker;
    TextView eventbeschrijvig;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_event, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventnaam = getView().findViewById(R.id.textView28);
        eventadres = getView().findViewById(R.id.textView30);
        eventbegin = getView().findViewById(R.id.textView32);
        eventeinde = getView().findViewById(R.id.textView34);
        eventmaker = getView().findViewById(R.id.textView36);
        eventbeschrijvig = getView().findViewById(R.id.textView26);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            event_ID = Integer.valueOf(bundle.getString("event_id"));

            ApiClient.getEventByEventID(getContext(),event_ID, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("response", response.toString());
                    try {
                        JSONArray result = response.getJSONArray("result");
                        JSONObject EventInfoObject = result.getJSONObject(0);
                        Log.d("result",EventInfoObject.toString());
                        eventnaam.setText(EventInfoObject.getString("title"));
                        eventadres.setText(EventInfoObject.getString("latitude"));
                        eventbegin.setText(EventInfoObject.getString("eventStartDT"));
                        eventeinde.setText(EventInfoObject.getString("eventEndDT"));
                        eventmaker.setText(EventInfoObject.getString("event_owner"));
                        eventbeschrijvig.setText(EventInfoObject.getString("description"));




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
    }
}
}
