package com.example.project24;

import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class EventFragment extends Fragment {
    String naam;
    int id;
    int event_ID;
    private TextView eventNaam;
    private TextView eventAdres;
    private TextView eventBegin;
    private TextView eventEinde;
    private TextView eventMaker;
    private TextView eventBeschrijvig;
    private ImageView eventImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventNaam = getView().findViewById(R.id.textView28);
        eventAdres = getView().findViewById(R.id.textView30);
        eventBegin = getView().findViewById(R.id.textView32);
        eventEinde = getView().findViewById(R.id.textView34);
        eventMaker = getView().findViewById(R.id.textView36);
        eventImage = getView().findViewById(R.id.eventImage);
        eventBeschrijvig = getView().findViewById(R.id.textView26);


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
                        eventNaam.setText(EventInfoObject.getString("title"));
                        eventBegin.setText(EventInfoObject.getString("eventStartDT"));
                        new DownloadImageTask(eventImage).execute(EventInfoObject.getString("image"));
                        eventEinde.setText(EventInfoObject.getString("eventEndDT"));
                        eventMaker.setText(EventInfoObject.getString("username"));
                        eventBeschrijvig.setText(EventInfoObject.getString("description"));
                        double langi = Double.parseDouble(EventInfoObject.getString("latitude"));
                        double longi = Double.parseDouble(EventInfoObject.getString("longitude"));
                        Geocoder coder;
                        List<Address> address;
                        coder = new Geocoder(getContext(), Locale.getDefault());
                        address = coder.getFromLocation(langi,longi,1);
                        String adres = address.get(0).getAddressLine(0);
                        eventAdres.setText(adres);


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
