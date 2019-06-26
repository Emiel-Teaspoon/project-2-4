package com.example.project24;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private TextInputLayout usernameText;
    private TextInputLayout passwordText;
    private String message;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.loginButton);
        usernameText = findViewById(R.id.loginUsernameText);
        passwordText = findViewById(R.id.loginPasswordText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiClient.loginAccount(getBaseContext(), usernameText.getEditText().getText().toString(), passwordText.getEditText().getText().toString(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            message = response.get("Message").toString();
                        }
                        catch (JSONException ex){}
                        Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT);
                        Log.d("Login Response", response.toString());

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