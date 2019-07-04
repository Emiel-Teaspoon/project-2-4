package com.example.project24;

import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {
    static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    private Button registerButton;
    private TextInputLayout usernameText;
    private TextInputLayout passwordText;
    private TextInputLayout passwordRepeatText;
    private TextInputLayout emailText;
    private String username;
    private String password;
    private String passwordRepeat;
    private String email;
    private String message;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerButton = getView().findViewById(R.id.registerButton);
        usernameText = getView().findViewById(R.id.registerUsernameText);
        passwordText = getView().findViewById(R.id.registerPasswordText);
        passwordRepeatText = getView().findViewById(R.id.registerPasswordRepeatText);
        emailText = getView().findViewById(R.id.registerEmailText);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameText.getEditText().getText().toString();
                password = passwordText.getEditText().getText().toString();
                passwordRepeat = passwordRepeatText.getEditText().getText().toString();
                email = emailText.getEditText().getText().toString();

                if (confirmInput()){
                ApiClient.registerAccount(getContext(), username, password,email, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            message = response.getString("Message");
                        }
                        catch (JSONException ex){}
                        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                        Log.d("Register Response", response.toString());
                            if (message.equals("Success")){
                                getFragmentManager().popBackStack();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", "Things did not work");
                        Log.e("Error", "Things did not work");
                    }
                });
                }
            }
        });
    }

    private boolean validatePassword(String passwordInput, String passwordRepeatInput)
    {
        if (passwordInput.isEmpty()) {
            passwordText.setError("Password can't be empty");
            return false;
        } else if (!passwordInput.equals(passwordRepeatInput)){
            passwordRepeatText.setError("Passwords do not match");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            passwordText.setError("Password is weak");
            return false;
        } else  {
            passwordText.setError(null);
            passwordRepeatText.setError(null);
            return true;
        }
    }

    private boolean validateEmail(String emailInput)
    {
        if (emailInput.isEmpty()){
            emailText.setError("Email can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            emailText.setError("Email is not correct");
            return false;
        } else  {
            emailText.setError(null);
            return true;
        }
    }

    private boolean validateUsername(String usernameInput)
    {
        if (usernameInput.isEmpty()){
            usernameText.setError("Username can't be empty");
            return false;
        } else if (usernameInput.length()<4){
            usernameText.setError("Username is too short");
            return false;
        } else  {
            usernameText.setError(null);
            return true;
        }
    }
    private boolean confirmInput(){
        if(!validateEmail(email) | !validateUsername(username) | !validatePassword(password,passwordRepeat)){
            return false;
        }
        return true;
    }

}
