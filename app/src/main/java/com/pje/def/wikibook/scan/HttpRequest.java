package com.pje.def.wikibook.scan;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Sim on 18/11/2015.
 */
public class HttpRequest {

    public JSONParser parser;
    public String test;

    public void doHttpRequest (String url, final Activity activity){


        //httpRequest
        RequestQueue queue = Volley.newRequestQueue(activity);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.v("HTTP onResponse", "Response is: " + response.substring(0, 500));
                        /*test = response;
                        parser = new JSONParser(response);
                        parser.parseJSON(activity);*/


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ERROR", "That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        queue.start();
        Log.v("TEST", stringRequest.toString());


    }
}
