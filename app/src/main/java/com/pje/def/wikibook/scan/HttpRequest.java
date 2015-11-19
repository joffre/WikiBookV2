package com.pje.def.wikibook.scan;

import android.app.Activity;
import com.pje.def.wikibook.MainActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pje.def.wikibook.fragment.BookCreatorFragment;

/**
 * Created by Sim on 18/11/2015.
 */
public class HttpRequest {

    public JSONParser parser;
    public String test;

    public void doHttpRequest (String url, final MainActivity activity, final BookCreatorFragment fragment){


        //httpRequest
        RequestQueue queue = Volley.newRequestQueue(activity);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.v("HTTP onResponse", "Response is: " + response);
                        test = response;
                        parser = new JSONParser(response);
                        Log.v("Parser State", "State is: " + parser);
                       parser.parseJSON();
                        fragment.remplirChamps(parser);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ERROR", "That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        Log.v("TEST", stringRequest.toString());
    }
}
