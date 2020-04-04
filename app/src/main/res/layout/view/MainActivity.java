package com.univaq.offshoreplatforms.offshoreplatforms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.univaq.offshoreplatforms.R;
import com.univaq.offshoreplatforms.view.MapActivity;

public class MainActivity extends AppCompatActivity {

    static int distance = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goButton = findViewById(R.id.goButton);
        View.OnClickListener goButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("MODE", "ADDRESS");
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
        goButton.setOnClickListener(goButtonListener);

        final TextView distanceTextView = findViewById(R.id.distanceTextView);
        distanceTextView.setText(distanceStringMaker(distance));

        SeekBar distanceSeekBar = findViewById(R.id.seekBar);
        SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance = progress;
                distanceTextView.setText(distanceStringMaker(distance));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        };
        distanceSeekBar.setOnSeekBarChangeListener(seekBarListener);

        EditText selPosEditText = findViewById(R.id.addressTextView);
        Button selPosButton = findViewById(R.id.selPosButton);
        View.OnClickListener selPosListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

    }

    private String distanceStringMaker(int distance)
    {
        return R.string.distance_string + distance +" km";
    }
}
