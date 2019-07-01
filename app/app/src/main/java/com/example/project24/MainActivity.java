package com.example.project24;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    public final static EventmapApp app = new EventmapApp();
    MapFragment mapFragment = new MapFragment();
    View headerView;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(app.isLoggedIn()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new AccountActivity()).addToBackStack(null).commit();
                    drawer.closeDrawer(GravityCompat.START);
                } else{
                    Toast.makeText(getBaseContext(), "Not logged in", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
        Menu menuView = navigationView.getMenu();
        MenuItem navLogout = menuView.findItem(R.id.nav_logout);
        navLogout.setVisible(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                mapFragment).addToBackStack(null).commit();

        ApiClient.getVersion(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                Log.i("Backbutton", "popping backstack");
                getSupportFragmentManager().popBackStack();
            } else {
                Log.i("Backbutton", "nothing on backstack, calling super");
                super.onBackPressed();
            }
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    mapFragment).addToBackStack(null).commit();
                break;
            case R.id.nav_myEvents:
                if(app.isLoggedIn()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyEventsActivity()).addToBackStack(null).commit();
                } else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new LoginActivity()).addToBackStack(null).commit();
                    Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_friendsEvents:
                if(app.isLoggedIn()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FriendsEventsActivity()).addToBackStack(null).commit();
                } else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new LoginActivity()).addToBackStack(null).commit();
                    Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_friends:
                if(app.isLoggedIn()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FriendsActivity()).addToBackStack(null).commit();
                } else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new LoginActivity()).addToBackStack(null).commit();
                    Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_addfriends:
                if(app.isLoggedIn()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddFriend()).addToBackStack(null).commit();
                } else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new LoginActivity()).addToBackStack(null).commit();
                    Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_logout:
                app.setLoggedIn(false);
                app.setJWT(null);
                TextView navUsername = headerView.findViewById(R.id.nav_header_title);
                TextView navEmail = headerView.findViewById(R.id.nav_header_subtitle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        mapFragment).addToBackStack(null).commit();
                navUsername.setText("Not logged in");
                navEmail.setText(null);
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_login:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LoginActivity()).addToBackStack(null).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
