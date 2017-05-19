package com.example.tony.firstapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Tony on 5/18/2017.
 */

public class G_ControlClient {
    private static final String api_key = "9482fbe6126e13d94a463153c62cd857";
    private static final String api_base = "http://api.openweathermap.org/data/2.5/weather?";
    private static Stack<G_DelegateClient> zip_controllers;

    public Stack<G_DelegateClient> delegates() {
        return zip_controllers;
    }

    public String apiBaseUrl() {
        return api_base;
    }

    public void addZipDelegate(int zipcode) {
        Log.i("INFO","fuck");
        G_DelegateClient newclient = new G_DelegateClient(zipcode);
        newclient.args().put("appid",api_key);
        zip_controllers.add(newclient);
    }

    private static G_ControlClient instance = null;

    public static G_ControlClient instance() {
        if (instance == null)
            instance = new G_ControlClient();
        return instance;
    }

    private G_ControlClient() {
        zip_controllers = new Stack<>();
    }
}
