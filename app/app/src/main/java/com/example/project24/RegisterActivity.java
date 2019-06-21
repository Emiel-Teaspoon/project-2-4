package com.example.project24;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Button registerButton = findViewById(R.id.registerButton);
        final EditText usernameText = findViewById(R.id.registerUsernameText);
        final EditText passwordText = findViewById(R.id.registerPasswordText);
        final EditText emailText = findViewById(R.id.registerEmailText);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiClient.registerAccount(getBaseContext(), usernameText.getText().toString(), passwordText.getText().toString(),emailText.getText().toString(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Register Response", response.toString());
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
