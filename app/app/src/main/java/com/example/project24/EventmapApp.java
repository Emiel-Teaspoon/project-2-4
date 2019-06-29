package com.example.project24;

import android.app.Application;

public class EventmapApp extends Application {

    private int user_id = 1;
    private String userName;
    private String ApiKey;
    private String JWT = "jsonWebToken";

    public EventmapApp(){

    }

    public void setUser(int id, String name, String key) {
        user_id = id;
        userName = name;
        ApiKey = key;
    }

    public String getApiKey() {
        return "The Key";
    }

    public int getUser_id() {return user_id;}

    public void setJWT(String jwt){
        JWT = jwt;
    }
    public String getJWT(){
        return JWT;
    }

}
