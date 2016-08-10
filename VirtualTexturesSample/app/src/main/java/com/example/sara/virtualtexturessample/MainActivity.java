package com.example.sara.virtualtexturessample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.senseg.haptics.Grain;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public float startIntensity;
    public float endIntensity;
    public boolean inc;
    public Button increase;
    public Button decrease;
    public int testNum;

    public String[] grainList = {"GRAIN_AREA_SMOOTH", "GRAIN_AREA_EVEN", "GRAIN_AREA_GRAINY", "GRAIN_AREA_BUMPY"};
    public Tuple[] gratings = {new Tuple(0.4f, 0.8f, 0, 0), new Tuple(0.2f, 1.0f, 0, 0)};
    public int grainIndex;
    public int gratingIndex = 0;
    public float windowSize = 600.0f;

    TextView textElement;
    TextView correctness;
    ImageView greenL, greenR, blackL, blackR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        increase = (Button) findViewById(R.id.Increase);
        decrease = (Button) findViewById(R.id.Decrease);
        textElement = (TextView) findViewById(R.id.doneChecker);
        correctness = (TextView) findViewById(R.id.correctness);

        increase.setOnClickListener(this);
        decrease.setOnClickListener(this);

        greenL = (ImageView)findViewById(R.id.greenL);
        greenR = (ImageView)findViewById(R.id.greenR);
        blackL = (ImageView)findViewById(R.id.blackL);
        blackR = (ImageView)findViewById(R.id.blackR);

        greenL.setVisibility(View.INVISIBLE);
        greenR.setVisibility(View.INVISIBLE);
        blackL.setVisibility(View.VISIBLE);
        blackR.setVisibility(View.VISIBLE);

        grainIndex = 0;
        gratingIndex = 0;
        startIntensity = gratings[gratingIndex].x;
        endIntensity = gratings[gratingIndex].y;
        textElement.setText("Progress: " + testNum + "/8");

        double incRand = Math.random();
        if (incRand > 0.5) inc = true;
        else inc = false;

    }

    public void switchVis(int check) {
        if(check % 2 == 0) {
            greenL.setVisibility(View.INVISIBLE);
            greenR.setVisibility(View.INVISIBLE);
            blackL.setVisibility(View.VISIBLE);
            blackR.setVisibility(View.VISIBLE);
            windowSize = 600.0f;
        }

        else {
            greenL.setVisibility(View.VISIBLE);
            greenR.setVisibility(View.VISIBLE);
            blackL.setVisibility(View.INVISIBLE);
            blackR.setVisibility(View.INVISIBLE);
            windowSize = 300.0f;
        }

    }

    public boolean onTouchEvent(MotionEvent event) {
        InputDevice.MotionRange xRangeObj =
                InputDevice.getDevice(event.getDeviceId()).getMotionRange(event.AXIS_X);

        float maxX = xRangeObj.getMax();
        float minX = xRangeObj.getMin();
        float center = (maxX + minX) / 2;
        float begin = center - (windowSize/2);
        float end = center + (windowSize/2);
        float intensity;

        float startInt = startIntensity;
        float endInt = endIntensity;
        float delta = endInt - startInt;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Grain grain = new Grain(grainList[grainIndex]);
                float x = event.getX();
                float xPos = x - begin;
                if (x > end || x < begin) {
                    intensity = 0.0f;
                } else {
                    if (inc) {
                        intensity = startInt + (delta * xPos) / windowSize;
                    } else {
                        intensity = endInt - (delta * xPos) / windowSize;
                    }
                }
                if (testNum < 8) textElement.setText("Progress: " + testNum + "/8");
                grain.play(intensity);
                break;

            default:
                return false;
        }

        return true;
    }

    public void onClick(View v) {

        if (testNum < 8) {
            testNum++;
            textElement.setText("Progress: " + testNum + "/8");

            if(inc) {
                if(v == increase) {
                    correctness.setTextColor(Color.GREEN);
                    correctness.setText("Correct");
                }

                else {
                    correctness.setTextColor(Color.RED);
                    correctness.setText("Incorrect");
                }
            }

            else{
                if(v != increase) {
                    correctness.setTextColor(Color.GREEN);
                    correctness.setText("Correct");
                }

                else {
                    correctness.setTextColor(Color.RED);
                    correctness.setText("Incorrect");
                }
            }

            switchVis(testNum);
        }

        updateParameters();
    }

    public void updateParameters() {
        if(gratingIndex == 1 && grainIndex == 3) {
            textElement.setText("The sample test is complete!");
        }

        else {
            if (gratingIndex == 1) {
                grainIndex++;
                gratingIndex = 0;
            } else {
                gratingIndex++;
            }

            startIntensity = gratings[gratingIndex].x;
            endIntensity = gratings[gratingIndex].y;

            double incRand = Math.random();
            if (incRand > 0.5) inc = true;
            else inc = false;
        }
    }
}
