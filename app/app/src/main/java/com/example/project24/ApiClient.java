package com.example.project24;

import android.app.DownloadManager;
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

import static com.example.project24.MainActivity.app;

class ApiClient {

    private static final String API_CALL = "https://spicymemes.app/eventmap/public/";
    private static final String TAG = ApiClient.class.getSimpleName();
    private static final int MY_SOCKET_TIMEOUT_MS = 60000;

    private static void sendObjectRequest(final Context context, int method, String action, final JSONObject object, Response.Listener<JSONObject> response, final Response.ErrorListener errorListener) {
        Log.i(TAG, "Class is JSONObject");
        Log.i(TAG, API_CALL + action);
        Request<JSONObject> request = new JsonObjectRequest(method, API_CALL + action, object, response, errorListener) {
//            @Override
//            protected Map<String, String> getParams() {
//                HashMap<String, String> params = new HashMap<>();
//                return params;
//            }

            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("ApiKey", MainActivity.app.getApiKey());
                headers.put("Authorization","Bearer "+ MainActivity.app.getJWT());
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

    static void getVersion(final Context context) {
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

    static void getFriendsEvents(final Context context, final int userID, final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
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
    static void loginAccount(final Context context, String username, String password, final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        String action = "login";
        HashMap<String, String> hmap = new HashMap<>();
        hmap.put("username", username);
        hmap.put("password", password);
        JSONObject parameters = new JSONObject(hmap);
        sendObjectRequest(context, Request.Method.POST, action, parameters, responseListener, errorListener);
    }

    // ook nog niet getest
    static void registerAccount(final Context context, String username, String password, String email, final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        String action = "user";
        HashMap<String, String> hmap = new HashMap<>();
        hmap.put("username", username);
        hmap.put("password", password);
        hmap.put("email", email);
        JSONObject parameters = new JSONObject(hmap);
        sendObjectRequest(context, Request.Method.POST, action, parameters, responseListener, errorListener);
    }

    static void getUserByUsername(final Context context, final String username, final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        String action = "findUserByUsername/" + username;
        sendObjectRequest(context, Request.Method.GET, action, null, responseListener, errorListener);
    }

    static void getUserByUserID(final Context context, final int userID, final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        String action = "findUserByUserID/" + userID;
        sendObjectRequest(context, Request.Method.GET, action, null, responseListener, errorListener);
    }

    static void createEvent(Context context, String title, String description, Double latitude, Double longitude, String startDT, String endDT,
                            int owner, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String action = "addEvent";
        HashMap<String, String> hmap = new HashMap<>();
        hmap.put("title", title);
        hmap.put("description", description);
        hmap.put("img", "/img/");
        hmap.put("latd", latitude.toString());
        hmap.put("lotd", longitude.toString());
        hmap.put("attendees", "0");
        hmap.put("eventStartDT", startDT);
        hmap.put("eventEndDT", endDT);
        hmap.put("owner", Integer.toString(owner));
        JSONObject parameters = new JSONObject(hmap);

        sendObjectRequest(context, Request.Method.POST, action, parameters, responseListener, errorListener);
    }

    static void getEventsById(final Context context, final int userID, final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        String action = "EventsByUserID/" + userID;
        sendObjectRequest(context, Request.Method.GET, action, null, responseListener, errorListener);
    }
    static void getEventByEventID(final Context context, final int eventID,final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        String action = "EventsByEventID/" + eventID;
        sendObjectRequest(context, Request.Method.GET, action, null, responseListener, errorListener);
    }

    static void changeUserPassword(Context context, String username, String oldPassword, String newPassword, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String action = "user/password";
        HashMap<String, String> hmap = new HashMap<>();
        hmap.put("username", username);
        hmap.put("oldPassword", oldPassword);
        hmap.put("newPassword", newPassword);
        JSONObject parameters = new JSONObject(hmap);

        sendObjectRequest(context, Request.Method.PUT, action, parameters, responseListener, errorListener);
    }
    static void followFriend(final Context context, int user_ID , int followe_ID, final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        String action = "follow";
        HashMap<String, String> hmap = new HashMap<>();
        hmap.put("user_id", Integer.toString(user_ID));
        hmap.put("follower_id", Integer.toString(followe_ID));
        JSONObject parameters = new JSONObject(hmap);
        sendObjectRequest(context, Request.Method.POST, action, parameters, responseListener, errorListener);
    }
}
