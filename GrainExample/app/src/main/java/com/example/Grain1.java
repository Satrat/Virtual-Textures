package com.example;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


import com.senseg.haptics.Grain;

import com.example.sara.grainexample.R;

public class Grain1 extends AppCompatActivity {
    public static int slider = 0;
    public static Grain grain = new Grain(Grain.GRAIN_AREA_SMOOTH);
    public static float intensity = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grain1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SeekBar mySeekBar = (SeekBar) findViewById(R.id.seekBar);
        mySeekBar.setOnSeekBarChangeListener(customSeekBarListener);

        //TextView textView = (TextView) findViewById(R.id.intensityText);
        //textView.setText("Intensity: "+ Integer.toString(slider));
    }

    private SeekBar.OnSeekBarChangeListener customSeekBarListener =
            new OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    slider = progress;
                    TextView textView = (TextView) findViewById(R.id.intensityText);
                    textView.setText("Intensity: "+ Integer.toString(slider));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };

    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                intensity = slider/100.0f;
                grain.play(intensity);
                break;

            case MotionEvent.ACTION_MOVE:
                intensity = slider/100.0f;
                grain.play(intensity);
                break;

            case MotionEvent.ACTION_HOVER_MOVE:
                intensity = slider/100.0f;
                grain.play(intensity);
                break;

            default:
                return false;
        }
        return true;
    }

}
