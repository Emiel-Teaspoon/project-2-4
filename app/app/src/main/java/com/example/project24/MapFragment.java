package com.example.project24;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private View popupWindowView;
    private EditText eventTitle;
    private EditText eventDesc;
    private EditText eventStart;
    private EditText eventEnd;
    private Button eventCancelButton;
    private Button eventCreateButton;

    private static final String TAG = MainActivity.class.getSimpleName();
    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment!= null){
            mapFragment.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        LatLng groningen = new LatLng(53.2314884, 6.5677468);
        map.addMarker(new MarkerOptions().position(groningen).title("Groningen"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(groningen, 11));
        Log.d("message", "set spot on map");

        ApiClient.getAllEvents(getContext(), 100, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("All events response", response.toString());
                try {
                    JSONArray result = response.getJSONArray("result");
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject eventObject = result.getJSONObject(i);
                        LatLng position = new LatLng(eventObject.getDouble("latitude"), eventObject.getDouble("longitude"));
                        String markerInfo = "Description: " + eventObject.getString("description") + "\n" +
                                "Starting date: " + eventObject.getString("eventStartDT") + "\n" +
                                "End date: " + eventObject.getString("eventEndDT");
                        map.addMarker(new MarkerOptions().position(position).title(eventObject.getString("title")).snippet(markerInfo));
                    }
                    map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                        @Override
                        public View getInfoWindow(Marker marker) {
                            return null;
                        }

                        @Override
                        public View getInfoContents(Marker marker) {
                            LinearLayout info = new LinearLayout(getContext());
                            info.setOrientation(LinearLayout.VERTICAL);

                            TextView title = new TextView(getContext());
                            title.setTextColor(Color.BLACK);
                            title.setGravity(Gravity.CENTER);
                            title.setTypeface(null, Typeface.BOLD);
                            title.setText(marker.getTitle());

                            TextView snippet = new TextView(getContext());
                            snippet.setTextColor(Color.GRAY);
                            snippet.setText(marker.getSnippet());

                            info.addView(title);
                            info.addView(snippet);

                            return info;
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("All events error", error.toString());
            }
        });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                dialogBuilder.setTitle("Maak nieuw event");
                dialogBuilder.setCancelable(false);

                initPopUpViewControls();
                dialogBuilder.setView(popupWindowView);
                final AlertDialog dialog = dialogBuilder.create();
                dialog.show();
                initPopupOnClickListeners(dialog, latLng);
            }
        });

        try {
            boolean success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("dasdadf", "asdfadsfasdfa");
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
        }
    }

    private void initPopUpViewControls() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        popupWindowView = inflater.inflate(R.layout.popup_window, null);

        eventTitle = popupWindowView.findViewById(R.id.eventTitle);
        eventDesc = popupWindowView.findViewById(R.id.eventDesc);
        eventStart = popupWindowView.findViewById(R.id.eventStartTime);
        eventEnd = popupWindowView.findViewById(R.id.eventEndTime);

        eventCancelButton = popupWindowView.findViewById(R.id.eventPopupCancel);
        eventCreateButton = popupWindowView.findViewById(R.id.eventCreate);
    }

    private boolean verifyEventInput() {
        boolean valid = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:SS");

        if (TextUtils.isEmpty(eventTitle.getText())) {
            Toast.makeText(getContext(), "Geen event titel ingevoerd", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (TextUtils.isEmpty(eventDesc.getText())) {
            Toast.makeText(getContext(), "Geen omschrijving ingevoerd.", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (TextUtils.isEmpty(eventStart.getText())) {
            Toast.makeText(getContext(), "Geen start datum en tijd ingeveord.", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (TextUtils.isEmpty(eventEnd.getText())) {
            Toast.makeText(getContext(),"Geen eind datum en tijd ingevoerd.", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (!TextUtils.isEmpty(eventStart.getText())) {
            try {
                format.parse(eventStart.getText().toString());
            } catch (ParseException e) {
                Toast.makeText(getContext(), "Voer AUB een geldige start datum en tijd in", Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (!TextUtils.isEmpty(eventEnd.getText())) {
            try {
                format.parse(eventEnd.getText().toString());
            } catch (ParseException e) {
                Toast.makeText(getContext(), "Voer AUB een geldige eind datum en tijd in", Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }
        return valid;
    }

    private void initPopupOnClickListeners(final AlertDialog dialog, final LatLng latLng) {
        eventCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verifyEventInput()){
                    return;
                }
                String title = eventTitle.getText().toString();
                String desc = eventDesc.getText().toString();
                String startDT = eventStart.getText().toString();
                String endDT = eventStart.getText().toString();

                ApiClient.createEvent(getContext(), title, desc, latLng.latitude, latLng.longitude, startDT, endDT, MainActivity.app.getUser_id(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Event Create Test", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Event create error", error.toString());
                    }
                });
                dialog.cancel();
            }
        });

        eventCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
}



