package com.example.sara.motiontracking;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.senseg.haptics.Grain;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onTouchEvent(MotionEvent event) {
        Grain grain = new Grain(Grain.GRAIN_AREA_SMOOTH);
        InputDevice.MotionRange xRangeObj =
                InputDevice.getDevice(event.getDeviceId()).getMotionRange(event.AXIS_X);
        InputDevice.MotionRange yRangeObj =
                InputDevice.getDevice(event.getDeviceId()).getMotionRange(event.AXIS_X);

        float minX = xRangeObj.getMin();
        float maxX = xRangeObj.getMax();
        float xRange = xRangeObj.getRange();

        float minY = yRangeObj.getMin();
        float maxY = yRangeObj.getMax();
        float yRange = yRangeObj.getRange();

        switch(event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                float intensityX = x/xRange;
                float intensityY = 1 - (y/yRange);
                float intensity = (intensityX + intensityY)/2.0f;
                grain.play(intensity);
                break;

            default:
                return false;
        }

        return true;
    }
}
