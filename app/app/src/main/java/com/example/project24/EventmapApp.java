package com.example.project24;

import android.app.Application;

public class EventmapApp extends Application {

    private int user_id = 1;
    private String userName;
    private String ApiKey;
    private String JWT;
    private boolean loggedIn =false;

    public EventmapApp(){

    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setUser(int id, String name, String key) {
        user_id = id;
        userName = name;
        ApiKey = key;
    }

    public String getApiKey() {
        return ApiKey;
    }

    public int getUser_id() {
        return user_id;
    }


    public void setJWT(String jwt){
        JWT = jwt;
    }

    public String getJWT(){
        return JWT;
    }

    public String getUserName() {return userName;}


}
