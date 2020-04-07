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

import java.util.ArrayList;

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

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ListAdapter adapter = new ListAdapter(platformTables);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<PlatformTable> getPlatforms (final Location location, final int distance)
    {
        final ArrayList<PlatformTable> goodTables = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<PlatformTable> array =new ArrayList<PlatformTable>();
                array.addAll(PlatformsDB.getInstance(getApplicationContext()).platformsDao().getPLatforms());
                for (int i = 0; i < array.size(); i++)
                {
                    Location platformLocation = new Location("");
                    platformLocation.setLatitude(array.get(i).latitudine);
                    platformLocation.setLongitude(array.get(i).longitudine);
                    if (location.distanceTo(platformLocation)*1000 < distance)
                        goodTables.add(array.get(i));
                }
            }
        });
        t.start();
        return goodTables;
    }
}
