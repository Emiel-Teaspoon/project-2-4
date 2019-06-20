package com.example.project24;

import android.app.Application;

public class EventmapApp extends Application {

    private String user_id;
    private String userName;
    private String ApiKey;

    public EventmapApp(){

    }

    public void setUser(String id, String name, String key) {
        user_id = id;
        userName = name;
        ApiKey = key;
    }

    public String getApiKey() {
        return "The Key";
    }

}
