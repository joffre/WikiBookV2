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
 * Do the request to GoogleBook and fill the JSONParser
 * Created by Sim on 18/11/2015.
 */
public class HttpRequest {

    /**
     * Use to parse the JSON fil which contain the book information
     */
    public JSONParser parser;

    public void doHttpRequest (String url, final MainActivity activity, final BookCreatorFragment fragment){


        //httpRequest
        RequestQueue queue = Volley.newRequestQueue(activity);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parser = new JSONParser(response);

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
    }
}
