package com.example.tony.firstapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Tony on 5/18/2017.
 */

public class G_NetUtils {

    public static String getRequest(String endpoint_url) {
        try {
            URL url = new URL(endpoint_url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    public static String buildRequest(String base_url, HashMap<String, String> arg_dict) {
        String endpoint_string = base_url;

        Iterator it = arg_dict.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry pair = (Map.Entry) it.next();

            String key = (String) pair.getKey();
            String value = (String) pair.getValue();

            if (key.equalsIgnoreCase("zip"))
                endpoint_string += "&";

            endpoint_string += (key + "=" + value);

            it.remove();
        }
        return endpoint_string;
    }
}
