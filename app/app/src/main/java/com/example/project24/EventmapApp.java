package com.example.project24;

import android.app.Application;
import android.util.Log;

public class EventmapApp extends Application {

    private int user_id = 1;
    private String userName;
    private String ApiKey;
    private String JWT;

    public EventmapApp(){

    }

    public void setUser(int id, String name, String key) {
        user_id = id;
        userName = name;
        ApiKey = key;
    }

    public String getApiKey() {
        Log.d("api",""+ ApiKey);
        return ApiKey;
    }

    public int getUser_id() {
        Log.d("user",""+user_id);
        return user_id;
    }


    public void setJWT(String jwt){
        JWT = jwt;
    }
    public String getJWT(){
        Log.d("jwt",""+JWT);
        return JWT;
    }

    public String getUserName() {return userName;}


}
