package com.univaq.platformsfinder.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.univaq.platformsfinder.R;
import com.univaq.platformsfinder.tools.BundleFactory;
import com.univaq.platformsfinder.tools.MyVolley;
import com.univaq.platformsfinder.tools.StringMaker;
import com.univaq.platformsfinder.tools.URLManager;
import com.univaq.platformsfinder.tools.VolleyListenersFactory;

/**
 * Main activity.
 */
public class MainActivity extends AppCompatActivity {

    private static int distance = 300;
    private static final String TAG = "MAINACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GoButton Creation
        Button goButton = findViewById(R.id.goButton);
        StringMaker maker = new StringMaker();
        goButton.setText(maker.mainButtonString(this));
        goButton.setTextSize(18);
        View.OnClickListener goButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BundleFactory bundleFactory = new BundleFactory();
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtras(bundleFactory.mainBundle(distance));
                startActivity(intent);
            }
        };
        goButton.setOnClickListener(goButtonListener);

        //Distance TextView & SeekBar Creation
        final TextView distanceTextView = findViewById(R.id.distanceTextView);
        distanceTextView.setText(distanceStringMaker(distance));

        SeekBar distanceSeekBar = findViewById(R.id.seekBar);
        SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "progress = " + progress);
                distance = progress;
                distanceTextView.setText(distanceStringMaker(distance));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        };
        distanceSeekBar.setOnSeekBarChangeListener(seekBarListener);

        //SelPos EditText and Button Creation
        final EditText selPosEditText = findViewById(R.id.addressTextView);
        Button selPosButton = findViewById(R.id.selPosButton);
        selPosButton.setText(maker.selPosButtonString(this));

        //SelPos Volley Listeners
        VolleyListenersFactory factory = new VolleyListenersFactory();
        final Response.Listener<String> listener = factory.geocodingListener(this, distance);
        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                selPosEditText.setHint(R.string.selPos_connectionFailed_string);
            }
        };

        //SelPosButton OnClickListener
        View.OnClickListener selPosListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selPosEditText.getText().toString().isEmpty())
                {
                    URLManager urlManager = new URLManager();
                    String address = selPosEditText.getText().toString();
                    String url = urlManager.getGeocodingURL(address, getApplicationContext());
                    StringRequest request = new StringRequest(url, listener, errorListener);
                    MyVolley.getInstance(getApplicationContext()).getQueue().add(request);
                }
            }
        };
        selPosButton.setOnClickListener(selPosListener);
    }

    private String distanceStringMaker(int distance)
    {
        Log.d(TAG, "distanceStringMaker distance = " + distance);
        return getApplicationContext().getString(R.string.distance_string) + distance +" km";
    }
}
