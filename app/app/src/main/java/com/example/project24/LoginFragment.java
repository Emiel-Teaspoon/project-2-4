package com.example.project24;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment {
    private Button loginButton;
    private Button registerButton;
    private TextInputLayout usernameText;
    private TextInputLayout passwordText;
    private String username;
    private String password;
    private String messageResponse;
    private int userIDResponse;
    private String usernameResponse;
    private String emailResponse;
    private String APIKeyResponse;
    private String JWTResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginButton = getView().findViewById(R.id.loginButton);
        registerButton = getView().findViewById(R.id.loginRegisterButton);
        usernameText = getView().findViewById(R.id.loginUsernameText);
        passwordText = getView().findViewById(R.id.loginPasswordText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameText.getEditText().getText().toString();
                password = passwordText.getEditText().getText().toString();
                ApiClient.loginAccount(getContext(), username, password, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            messageResponse = response.getString("Message");
                        }
                        catch (JSONException ex){}
                        try {
                            JWTResponse = response.getString("token");
                            userIDResponse = Integer.parseInt(response.getString("UserID"));
                            usernameResponse = response.getString("Username");
                            emailResponse = response.getString("Email");
                            APIKeyResponse = response.getString("APIKey");
                        }
                        catch (JSONException ex){}
                        Toast.makeText(getContext(),messageResponse,Toast.LENGTH_SHORT).show();
                        Log.d("Login Response", response.toString());

                        if (messageResponse.equals("Success")){
                            MainActivity.app.setJWT(JWTResponse);
                            MainActivity.app.setUser(userIDResponse,usernameResponse,APIKeyResponse);
                            MainActivity.app.setLoggedIn(true);
                            updateNavHeader(usernameResponse,emailResponse);
                            updateNavItems();
                            getFragmentManager().popBackStack();
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
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterFragment registerFragment = new RegisterFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,registerFragment).addToBackStack(null).commit();
            }
        });



    }
    public void updateNavHeader(String title, String subtitle){
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_header_title);
        TextView navEmail = headerView.findViewById(R.id.nav_header_subtitle);
        navUsername.setText(title);
        navEmail.setText(subtitle);
    }
    public void updateNavItems(){
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        Menu menuView = navigationView.getMenu();
        MenuItem navLogin = menuView.findItem(R.id.nav_login);
        MenuItem navLogout = menuView.findItem(R.id.nav_logout);
        navLogout.setVisible(true);
        navLogin.setVisible(false);
    }
}