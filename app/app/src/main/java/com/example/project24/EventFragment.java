package com.example.project24;

import androidx.fragment.app.Fragment;

import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EventFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_event, container, false);
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
                        eventbegin.setText(EventInfoObject.getString("eventStartDT"));
                        eventeinde.setText(EventInfoObject.getString("eventEndDT"));
                        eventmaker.setText(EventInfoObject.getString("username"));
                        eventbeschrijvig.setText(EventInfoObject.getString("description"));
                        double langi = Double.parseDouble(EventInfoObject.getString("latitude"));
                        double longi = Double.parseDouble(EventInfoObject.getString("longitude"));
                        Geocoder coder;
                        List<Address> address;
                        coder = new Geocoder(getContext(), Locale.getDefault());
                        address = coder.getFromLocation(langi,longi,1);
                        String adres = address.get(0).getAddressLine(0);
                        eventadres.setText(adres);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", ""+ error);
                }
            });
    }
}
}
