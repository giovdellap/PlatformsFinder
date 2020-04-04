package com.univaq.platformsfinder.tools;

import android.content.Context;

import com.univaq.platformsfinder.R;

public class URLManager
{
    public String getGeocodingURL(String address, Context context)
    {
        //replacing spaces with +s in address
        String formattedAddress = address.replaceAll(" ", "+");
        String geocodingBasicURL = context.getResources().getString(R.string.geocodingBasicURL_string);
        String apiKey = context.getResources().getString(R.string.google_maps_api_key);
        return geocodingBasicURL + formattedAddress + "&key=" + apiKey;
    }
}
