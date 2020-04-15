package com.univaq.platformsfinder.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.univaq.platformsfinder.R;
import com.univaq.platformsfinder.model.PlatformTable;
import com.univaq.platformsfinder.model.PlatformsDB;
import com.univaq.platformsfinder.tools.DBHandler;

import java.util.ArrayList;

/**
 * List activity.
 */
public class ListActivity extends AppCompatActivity {

    private static final String TAG = "LISTACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Getting platforms from Bundle and DB
        Double latitude = Double.parseDouble(getIntent().getExtras().getString("LAT"));
        Double longitude = Double.parseDouble(getIntent().getExtras().getString("LON"));
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        int distance = Integer.parseInt(getIntent().getExtras().getString("DISTANCE"));
        Log.d(TAG, "lat = " + latitude);
        Log.d(TAG, "lon = " + longitude);
        Log.d(TAG, "distance = " + distance);

        ArrayList<PlatformTable> platformTables = getPlatforms(location, distance);
        Log.d(TAG, "platforms number = " + platformTables.size());

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ListAdapter adapter = new ListAdapter(platformTables);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<PlatformTable> getPlatforms (Location location, int distance)
    {
        ArrayList<PlatformTable> goodTables = new ArrayList<>();
        DBHandler handler = new DBHandler();
        ArrayList<PlatformTable> allTables = handler.getAllPlatforms(getApplicationContext());
        for (int i = 0; i < allTables.size(); i++)
        {
            Location tableLocation = new Location("");
            tableLocation.setLatitude(allTables.get(i).latitudine);
            tableLocation.setLongitude(allTables.get(i).longitudine);
            float currentDistance = tableLocation.distanceTo(location)/1000;
            if(currentDistance<distance)
                goodTables.add(allTables.get(i));
        }
        return goodTables;
    }
}
