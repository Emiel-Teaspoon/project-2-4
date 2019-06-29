package com.example.project24;

import android.app.Application;

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

    public String getApiKey() {return "The Key";}

    public int getUser_id() {return user_id;}


    public void setJWT(String jwt){
        JWT = jwt;
    }
    public String getJWT(){
        return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVc2VybmFtZSI6InRlc3QiLCJVc2VySUQiOiIxMSIsIkFQSUtleSI6IllUUXpaakF3TURabVpqWTJZekk1WlRZMFltUTNOamMxWVdNd056UTRaalU9In0.xEqdhG19iZMxH0V9TZ6atnKgieG_BR2DpaBGlPHO1yU";
    }

    public String getUserName() {return userName;}


}
