package com.example.project24;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    public final static EventmapApp app = new EventmapApp();
    private GoogleMap map;
    private static final String TAG = MainActivity.class.getSimpleName();

    private View popupWindowView;
    private EditText eventTitle;
    private EditText eventDesc;
    private EditText eventStart;
    private EditText eventEnd;
    private Button eventCancelButton;
    private Button eventCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace this.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        navigationView.setNavigationItemSelectedListener(this);
        ApiClient.getVersion(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id) {
            case R.id.nav_home:
                Intent mainActivity = new Intent(MainActivity.this, MainActivity.class);
                startActivity(mainActivity);
                break;
            case R.id.nav_account:
                Intent accountActivity = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(accountActivity);
                break;
            case R.id.nav_myEvents:
                Intent myEventsActivity = new Intent(MainActivity.this, MyEventsActivity.class);
                startActivity(myEventsActivity);
                break;
            case R.id.nav_friendsEvents:
                Intent friendsEventsActivity = new Intent(MainActivity.this, FriendsEventsActivity.class);
                startActivity(friendsEventsActivity);
                break;
            case R.id.nav_friends:
                Intent friendsActivity = new Intent(MainActivity.this, FriendsActivity.class);
                startActivity(friendsActivity);
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT);
                break;
            case R.id.nav_login:
                Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginActivity);
                break;
            case R.id.nav_register:
                Intent registerActivity = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerActivity);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        LatLng groningen = new LatLng(53.2314884, 6.5677468);
        map.addMarker(new MarkerOptions().position(groningen).title("Groningen"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(groningen, 11));

        ApiClient.getAllEvents(this, 100, new Response.Listener<JSONObject>() {
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
                            LinearLayout info = new LinearLayout(MainActivity.this);
                            info.setOrientation(LinearLayout.VERTICAL);

                            TextView title = new TextView(MainActivity.this);
                            title.setTextColor(Color.BLACK);
                            title.setGravity(Gravity.CENTER);
                            title.setTypeface(null, Typeface.BOLD);
                            title.setText(marker.getTitle());

                            TextView snippet = new TextView(MainActivity.this);
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
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
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
            boolean success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("dasdadf", "asdfadsfasdfa");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
        }
    }

    private void initPopUpViewControls() {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
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
            Toast.makeText(MainActivity.this, "Geen event titel ingevoerd", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (TextUtils.isEmpty(eventDesc.getText())) {
            Toast.makeText(MainActivity.this, "Geen omschrijving ingevoerd.", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (TextUtils.isEmpty(eventStart.getText())) {
            Toast.makeText(MainActivity.this, "Geen start datum en tijd ingeveord.", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (TextUtils.isEmpty(eventEnd.getText())) {
            Toast.makeText(MainActivity.this,"Geen eind datum en tijd ingevoerd.", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (!TextUtils.isEmpty(eventStart.getText())) {
            try {
                format.parse(eventStart.getText().toString());
            } catch (ParseException e) {
                Toast.makeText(MainActivity.this, "Voer AUB een geldige start datum en tijd in", Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (!TextUtils.isEmpty(eventEnd.getText())) {
            try {
                format.parse(eventEnd.getText().toString());
            } catch (ParseException e) {
                Toast.makeText(MainActivity.this, "Voer AUB een geldige eind datum en tijd in", Toast.LENGTH_SHORT).show();
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

                ApiClient.createEvent(MainActivity.this, title, desc, latLng.latitude, latLng.longitude, startDT, endDT, app.getUser_id(), new Response.Listener<JSONObject>() {
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
