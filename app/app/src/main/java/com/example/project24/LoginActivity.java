package com.example.project24;

import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Fragment {
    private Button loginButton;
    private TextInputLayout usernameText;
    private TextInputLayout passwordText;
    private String message;
    private int UserID;
    private String Username;
    private String APIKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginButton = getView().findViewById(R.id.loginButton);
        usernameText = getView().findViewById(R.id.loginUsernameText);
        passwordText = getView().findViewById(R.id.loginPasswordText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiClient.loginAccount(getContext(), usernameText.getEditText().getText().toString(), passwordText.getEditText().getText().toString(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            message = response.getString("Message");
                            UserID = Integer.parseInt(response.getString("UserID"));
                            Username = response.getString("Username");
                            APIKey = response.getString("APIKey");
                        }
                        catch (JSONException ex){}
                        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                        Log.d("Login Response", response.toString());
                        MainActivity.app.setUser(UserID,Username,APIKey);
                        if (message.equals("Success")){
                            MapFragment mapFragment = new MapFragment();
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container,mapFragment).commit();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", "Things did not work");
                    }
                });
            }
        });



    }
}