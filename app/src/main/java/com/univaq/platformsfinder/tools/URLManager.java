package com.univaq.platformsfinder.tools;

import android.content.Context;

import com.univaq.platformsfinder.R;

/**
 * The Url manager.
 */
public class URLManager
{
    /**
     * Gets url for google maps' geocoding service
     *
     * @param address the address
     * @param context the context
     * @return the geocoding url
     */
    public String getGeocodingURL(String address, Context context)
    {
        //replacing spaces with +s in address
        String formattedAddress = address.replaceAll(" ", "+");
        String geocodingBasicURL = context.getResources().getString(R.string.geocodingBasicURL_string);
        String apiKey = context.getResources().getString(R.string.google_maps_api_key);
        return geocodingBasicURL + formattedAddress + "&key=" + apiKey;
    }
}
