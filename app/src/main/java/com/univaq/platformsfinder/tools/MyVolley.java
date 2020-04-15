package com.univaq.platformsfinder.tools;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * The type My volley.
 */
public class MyVolley
{
    private RequestQueue queue;

    private static MyVolley instance = null;

    /**
     * returns MyVolley instance
     *
     * @param context the context
     * @return the my volley
     */
    public static MyVolley getInstance(Context context){
        return instance == null ? instance = new MyVolley(context) : instance;
    }

    private MyVolley(Context context) {

        queue = Volley.newRequestQueue(context);
    }

    /**
     * Returns Volley queue
     *
     * @return the request queue
     */
    public RequestQueue getQueue(){
        return queue;
    }
}