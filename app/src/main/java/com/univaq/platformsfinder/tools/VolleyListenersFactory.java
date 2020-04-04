package com.univaq.platformsfinder.tools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.google.android.gms.maps.model.LatLng;
import com.univaq.platformsfinder.model.PlatformsDB;
import com.univaq.platformsfinder.view.MapActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VolleyListenersFactory
{
    private static final String TAG = "LISTENER";


    public Response.Listener<String> geocodingListener(final Context context, final int distance)
    {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG, "Response Listener");
                    //JSON conversion and VM update
                    JSONObject obj = new JSONObject(response);
                    Log.d(TAG, "JSONObj = " + obj.toString());
                    JSONDecoder decoder = new JSONDecoder();
                    LatLng location = decoder.addressJSONDecoder(obj);
                    Log.d(TAG, "location = " + location.toString());

                    //MapActivity call
                    BundleFactory bundleFactory = new BundleFactory();
                    Intent intent = new Intent(context, MapActivity.class);
                    intent.putExtras(bundleFactory.addressBundle(location, distance));
                    context.startActivity(intent);

                } catch (JSONException j) {
                }
            }
        };
    }

    public Response.Listener<String> mapListener(final Context context)
    {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Log.d(TAG, "INSIDE MAP LISTENER");
                    Log.d(TAG, "JSONResponse = " + response);
                    final JSONArray array = new JSONArray(response);
                    final JSONDecoder decoder = new JSONDecoder();
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                PlatformsDB.getInstance(context).platformsDao().insertPlatforms(decoder.platformsJSONDecoder(array));
                            }catch (JSONException e) { Log.d(TAG, e.toString()); }
                        }
                    });
                    t.run();
                } catch (JSONException e) { Log.d(TAG, e.toString()); }
            }
        };
    }
}
