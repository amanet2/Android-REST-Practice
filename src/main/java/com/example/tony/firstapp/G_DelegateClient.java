package com.example.tony.firstapp;

import java.util.HashMap;

/**
 * Created by Tony on 5/18/2017.
 */

public class G_DelegateClient {
    private int zip_code;
    private HashMap<String,String> argument_map;

    public HashMap<String,String> args() {
        return argument_map;
    }

    public G_DelegateClient(int zip) {
        zip_code = zip;
        argument_map = new HashMap<String, String>();
        argument_map.put("zip",String.valueOf(zip_code)+",us");
    }
}
