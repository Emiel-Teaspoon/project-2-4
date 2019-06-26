package com.example.project24;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

class ApiClient {

    private static final String API_CALL = "https://spicymemes.app/eventmap/public/";
    private static final String TAG = ApiClient.class.getSimpleName();
    private static final int MY_SOCKET_TIMEOUT_MS = 60000;

    private static void sendObjectRequest(final Context context, int method, String action, final HashMap<String, String> params, Response.Listener<JSONObject> response, final Response.ErrorListener errorListener) {
        Log.i(TAG, "Class is JSONObject");
        final EventmapApp app = (EventmapApp)context.getApplicationContext();

        Log.i(TAG, API_CALL + action);

        Request<JSONObject> request = new JsonObjectRequest(method, API_CALL + action, null, response, errorListener)
        {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }

            public Map<String, String> getHeaders()
            {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("ApiKey", app.getApiKey());
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    static void getVersion(final Context context)
    {
        String action = "version";

        sendObjectRequest(context, Request.Method.GET, action, null, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i(TAG, "Major:  " + response.getInt("major") + " " + "Minor:  " + response.getInt("minor"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    static void getFriends(final Context context, final int userID, final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        String action = "getFollowers/" + userID;
        sendObjectRequest(context, Request.Method.GET, action, null, responseListener, errorListener);
    }
    static void getFriendsEvents(final Context context, final int userID,final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        String action = "FollowerEvents/" + userID;
        sendObjectRequest(context, Request.Method.GET, action, null, responseListener, errorListener);
    }

    static void getFriendEvents(final Context context, final String username, final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        String action = "EventsByUsername/" + username;
        sendObjectRequest(context, Request.Method.GET, action, null, responseListener, errorListener);
    }

    static void getAllEvents(final Context context, final int limit, final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        String action = "Events/" + limit;
        sendObjectRequest(context, Request.Method.GET, action, null, responseListener, errorListener);
    }

    // Nog niet getest of het werkt
    static void loginAccount(final Context context, String username,String password, final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        String action = "login";
        HashMap<String, String> hmap = new HashMap<>();
        hmap.put("username",username);
        hmap.put("password",password);
        sendObjectRequest(context, Request.Method.POST, action,hmap, responseListener, errorListener);
    }
    // ook nog niet getest
    static void registerAccount(final Context context, String username,String password,String email, final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        String action = "user" ;
        HashMap<String, String> hmap = new HashMap<>();
        hmap.put("username",username);
        hmap.put("password",password);
        hmap.put("email",email);
        sendObjectRequest(context, Request.Method.POST, action,hmap, responseListener, errorListener);
    }

    static void getUserByUsername(final Context context, final String username, final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        String action = "findUserByUsername/" + username;
        sendObjectRequest(context, Request.Method.GET, action, null, responseListener, errorListener);
    }

    static void createEvent(Context context, String title, String description, Double latitude, Double longitude, String startDT, String endDT,
                            int owner, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String action = "addEvent/" + title + "/" + description + "/img/" + latitude + "/" + longitude + "/" + 0 + "/" + startDT + "/" + endDT + "/" + owner;
        action = action.replaceAll(" ", "%20");
        sendObjectRequest(context, Request.Method.POST, action, null, responseListener, errorListener);
    }
}
