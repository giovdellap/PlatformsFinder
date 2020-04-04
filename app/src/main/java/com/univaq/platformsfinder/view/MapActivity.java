package com.univaq.platformsfinder.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.univaq.platformsfinder.R;
import com.univaq.platformsfinder.model.PlatformTable;
import com.univaq.platformsfinder.model.PlatformsDB;
import com.univaq.platformsfinder.tools.BundleFactory;
import com.univaq.platformsfinder.tools.MyVolley;
import com.univaq.platformsfinder.tools.VolleyListenersFactory;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener
{
    private static final String TAG = "MAPACTIVITY";
    private GoogleMap myMap;
    private FusedLocationProviderClient myProvider;
    private Location myLocation;
    private int distance;
    private String mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Gets Settings from Intent
        mode = getIntent().getExtras().getString("MODE");
        distance = Integer.parseInt(getIntent().getExtras().getString("DISTANCE"));
        Log.d(TAG, "distance = " + distance);
        if(mode.equals("ADDRESS"))
        {
            double latitude = Double.parseDouble(getIntent().getExtras().getString("LAT"));
            double longitude = Double.parseDouble(getIntent().getExtras().getString("LON"));
            myLocation = new Location("");
            myLocation.setLatitude(latitude);
            myLocation.setLongitude(longitude);
            Log.d(TAG, "lat = " + latitude);
            Log.d(TAG, "lon = " + longitude);
        }
        else {
            myProvider = LocationServices.getFusedLocationProviderClient(this);
        }

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Populates DB if first time
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        //if(pref.getBoolean("first_time", true))
        if(true)
        {
            Log.d(TAG, "IS FIRST TIME");
            populateDB();
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("first_time", false);
            editor.apply();
        }

        Button listViewButton = findViewById(R.id.listViewButton);
        listViewButton.setOnClickListener(listButtonListener());
    }

    private View.OnClickListener listButtonListener()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BundleFactory factory = new BundleFactory();
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtras(factory.listBundle(myLocation, distance));
                startActivity(intent);
            }
        };
    }

    private void populateDB()
    {
        Log.d(TAG, "POPULATING DB");
        VolleyListenersFactory factory = new VolleyListenersFactory();
        Response.Listener<String> listener = factory.mapListener(getApplicationContext());
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "ERRORE!!!!!");
                Log.d(TAG, error.toString());
            }
        };
        String url = getString(R.string.db_url);
        StringRequest request = new StringRequest(url, listener, errorListener);
        Log.d(TAG, "URL = " + url);
        MyVolley.getInstance(getApplicationContext()).getQueue().add(request);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        if(mode.equals("LOCATION"))
        {
            myProvider.getLastLocation().addOnSuccessListener(MapActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.d(TAG, "location = " + location.toString());
                    myMap = myMapMarkerCreator(myMap, location, getApplicationContext());
                    myLocation = location;
                }
            });
        }
        else
        {
            myMap = myMapMarkerCreator(myMap, this.myLocation, getApplicationContext());
        }
    }

    private GoogleMap myMapMarkerCreator(GoogleMap map, Location location, final Context context)
    {
        GoogleMap toReturn = map;

        //adding current position marker to map
        MarkerOptions posMarker = new MarkerOptions();
        LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        posMarker.position(myLatLng);
        posMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        posMarker.title(getString(R.string.myPosition_marker));
        toReturn.addMarker(posMarker);

        //selecting valid platforms and adding markers
        final ArrayList<PlatformTable> tables = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                tables.addAll(PlatformsDB.getInstance(context).platformsDao().getPLatforms());
            }
        });
        t.run();

        for (int i = 0; i < tables.size(); i++)
        {
            Location platformLocation = new Location("");
            platformLocation.setLongitude(tables.get(i).longitudine);
            platformLocation.setLatitude(tables.get(i).latitudine);
            float currentDistance = platformLocation.distanceTo(location);
            if ((currentDistance*1000) <= distance)
            {
                MarkerOptions platformMarker = new MarkerOptions();
                platformMarker.position(new LatLng(platformLocation.getLatitude(), platformLocation.getLongitude()));
                platformMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                platformMarker.title(tables.get(i).denominazione);
                toReturn.addMarker(platformMarker);
            }
        }
        return toReturn;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        BundleFactory factory = new BundleFactory();
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtras(factory.detailsBundle(marker, getApplicationContext()));
        startActivity(intent);
        return true;
    }
}
