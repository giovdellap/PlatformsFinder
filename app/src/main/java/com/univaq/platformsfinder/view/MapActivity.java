package com.univaq.platformsfinder.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
import com.univaq.platformsfinder.tools.DBHandler;
import com.univaq.platformsfinder.tools.MyVolley;
import com.univaq.platformsfinder.tools.VolleyListenersFactory;

import org.json.JSONArray;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback
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
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
            {
                String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(this, permissions, 0);
            }
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
        Response.Listener<JSONArray> listener = factory.mapListener(getApplicationContext());
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "ERRORE!!!!!");
                Log.d(TAG, error.toString());
            }
        };
        String url = getString(R.string.db_url);
        JsonArrayRequest request = new JsonArrayRequest(url, listener, errorListener);
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
        DBHandler handler = new DBHandler();
        final ArrayList<PlatformTable> tables = handler.getAllPlatforms(getApplicationContext());

        Log.d(TAG, "Tables size = " + tables.size());
        for (int i = 0; i < tables.size(); i++)
        {
            Location platformLocation = new Location("");
            platformLocation.setLongitude(tables.get(i).longitudine);
            platformLocation.setLatitude(tables.get(i).latitudine);
            float currentDistance = platformLocation.distanceTo(location)/1000;
            Log.d(TAG, "currentDistance = " + currentDistance);
            if ((currentDistance) <= distance)
            {
                MarkerOptions platformMarker = new MarkerOptions();
                platformMarker.position(new LatLng(platformLocation.getLatitude(), platformLocation.getLongitude()));
                platformMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                platformMarker.title(tables.get(i).denominazione);
                toReturn.addMarker(platformMarker);
            }
        }
        toReturn.setOnMarkerClickListener(markerListener());
        return toReturn;
    }

    private GoogleMap.OnMarkerClickListener markerListener() {
        return new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d(TAG, "OnMarkerClick");
                BundleFactory factory = new BundleFactory();
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtras(factory.detailsBundle(marker, getApplicationContext()));
                startActivity(intent);
                return true;
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0)
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                finish();

    }
}
