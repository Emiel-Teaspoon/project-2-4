package com.example.project24;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class AccountActivity extends Fragment {

    private View mView;

    private View passwordResetView;
    private TextInputLayout currentPW;
    private TextInputLayout newPWOne;
    private TextInputLayout newPWTwo;
    private Button passwordResetCancelButton;
    private Button passwordResetAcceptButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_account, container, false);
        mView = view;
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUserData();
        initOnClickListeners();
    }

    private void loadUserData() {
        TextView usernameField = mView.findViewById(R.id.accountUsername);
        usernameField.setText(MainActivity.app.getUserName());

        final TextView emailField = mView.findViewById(R.id.accountEmail);
        ApiClient.getUserByUserID(getContext(), MainActivity.app.getUser_id(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Friend Detail response", response.toString());
                try {
                    JSONArray result = response.getJSONArray("result");
                    JSONObject friendObject = result.getJSONObject(0);
                    emailField.setText(friendObject.getString("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "A problem occurred attempting to retrieve the user data");
            }
        });
    }

    private void initOnClickListeners() {
        Button passwordResetButton = mView.findViewById(R.id.passwordResetButton);
        passwordResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mView.getContext());
                dialogBuilder.setTitle("Change Password");
                dialogBuilder.setCancelable(false);

                initPopUpViewControls();
                dialogBuilder.setView(passwordResetView);
                final AlertDialog dialog = dialogBuilder.create();
                dialog.show();
                initPopupOnClickListeners(dialog);
            }
        });
    }

    private void initPopUpViewControls() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        passwordResetView = inflater.inflate(R.layout.password_reset_window, null);

        currentPW = passwordResetView.getRootView().findViewById(R.id.currentPW);
        newPWOne = passwordResetView.getRootView().findViewById(R.id.newPWOne);
        newPWTwo = passwordResetView.getRootView().findViewById(R.id.newPWTwo);

        passwordResetCancelButton = passwordResetView.getRootView().findViewById(R.id.passwordResetCancel);
        passwordResetAcceptButton = passwordResetView.getRootView().findViewById(R.id.passwordResetAccept);
    }

    private void initPopupOnClickListeners(final AlertDialog dialog) {
        passwordResetAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current = currentPW.getEditText().getText().toString();
                String newOne = newPWOne.getEditText().getText().toString();
                String newTwo = newPWTwo.getEditText().getText().toString();

                if (validPasswordCheck(current, newOne, newTwo)) {
                    ApiClient.changeUserPassword(getContext(), MainActivity.app.getUserName(), current, newOne, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Password Change success", response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Password Change error", error.toString());
                        }
                    });
                    dialog.cancel();
                }
            }
        });

        passwordResetCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private boolean validPasswordCheck(String current, String pwOne, String pwTwo) {
        Pattern pattern = RegisterActivity.PASSWORD_PATTERN;
        if (current.isEmpty()) {
            currentPW.setError("No current password");
            return false;
        } else if (pwOne.isEmpty()) {
            newPWOne.setError("No new password");
            return false;
        } else if (pwTwo.isEmpty()) {
            newPWTwo.setError("Repeat new password");
            return false;
        } else if (!pwOne.equals(pwTwo)) {
            newPWTwo.setError("Passwords are not equal");
            return false;
        } else if (!pattern.matcher(pwOne).matches()) {
            newPWOne.setError("Password does not meet criteria");
            return false;
        } else {
            currentPW.setError(null);
            newPWOne.setError(null);
            newPWTwo.setError(null);
            return true;
        }
    }



}
