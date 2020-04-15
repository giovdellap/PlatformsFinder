package com.univaq.platformsfinder.tools;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;
import com.google.android.gms.maps.model.LatLng;
import com.univaq.platformsfinder.model.PlatformTable;
import com.univaq.platformsfinder.view.MapActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Makes Volley Listeners
 */
public class VolleyListenersFactory
{
    private static final String TAG = "LISTENER";


    /**
     * gets JSON response from geocoding request and starts MapActivity
     *
     * @param context  the context
     * @param distance the distance
     * @return the response listener
     */
    public Response.Listener<String> geocodingListener(final Context context, final int distance)
    {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG, "Response Listener");
                    //JSON conversion and VM update
                    JSONObject obj = new JSONObject(response);
                    JSONDecoder decoder = new JSONDecoder();
                    LatLng location = decoder.addressJSONDecoder(obj);
                    Log.d(TAG, "location = " + location.toString());

                    //MapActivity call
                    BundleFactory bundleFactory = new BundleFactory();
                    Intent intent = new Intent(context, MapActivity.class);
                    intent.putExtras(bundleFactory.addressBundle(location, distance));
                    context.startActivity(intent);

                } catch (JSONException j) {
                    Log.d(TAG, j.toString());
                }
            }
        };
    }

    /**
     * Gets JSONArray response from APIs and populates DB
     *
     * @param context the context
     * @return the response listener
     */
    public Response.Listener<JSONArray> mapListener(final Context context)
    {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                Log.d(TAG, "MAP LISTENER");
                final JSONArray array = response;
                final JSONDecoder decoder = new JSONDecoder();
                ArrayList<PlatformTable> tables = new ArrayList<>();
                try {
                     tables = decoder.platformsJSONDecoder(array);
                }catch (JSONException e)
                {
                    Log.d(TAG, "JSONException Handled");
                    Log.d(TAG, e.toString());
                }
                DBHandler handler = new DBHandler();
                handler.insertTables(tables, context);
            }

        };
    }
}
