package com.example.sara.alteredsizetest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.senseg.haptics.Grain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public float startIntensity;
    public float endIntensity;
    public boolean inc;
    public Button increase;
    public Button decrease;
    DecimalFormat df;
    public int testNum;
    public int grainCount = 0;

    public String[] grainList = {"GRAIN_AREA_GRAINY", "GRAIN_AREA_BUMPY"};
    public Tuple[] gratings = {new Tuple(0.2f, 0.6f,0,0), new Tuple(0.2f, 0.8f,0,0), new Tuple(0.2f, 1.0f,0,0)};
    public int grainIndex;
    public int gratingIndex = 0;
    public float windowSize = 600.0f;
    public String pixels = "";

    TextView textElement;
    ImageView greenL, greenR, blackL, blackR;
    File file;
    FileWriter fileWriter;
    int id;
    String age = "";
    String gender = "";
    String hand = "";
    int grain1 = 0;
    int grain2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        increase = (Button) findViewById(R.id.Increase);
        decrease = (Button) findViewById(R.id.Decrease);
        textElement = (TextView) findViewById(R.id.doneChecker);

        if(windowSize == 300.0f) {
            pixels = "150px";
        }
        else {
            pixels = "300px";
        }

        increase.setOnClickListener(this);
        decrease.setOnClickListener(this);

        Intent intent = getIntent();
        age = intent.getStringExtra(UserInputActivity.AGE_MESSAGE);
        gender = intent.getStringExtra(UserInputActivity.GENDER_MESSAGE);
        hand = intent.getStringExtra(UserInputActivity.HAND_MESSAGE);
        greenL = (ImageView)findViewById(R.id.greenL);
        greenR = (ImageView)findViewById(R.id.greenR);
        blackL = (ImageView)findViewById(R.id.blackL);
        blackR = (ImageView)findViewById(R.id.blackR);

        greenL.setVisibility(View.INVISIBLE);
        greenR.setVisibility(View.INVISIBLE);
        blackL.setVisibility(View.VISIBLE);
        blackR.setVisibility(View.VISIBLE);

        grainIndex = grain1;
        updateParameters();

        id = (int)((Math.random() * 5000) + 1);
        grainCount = 0;

        try {
            String name = "Test" + id +".txt";
            file = new File("/storage/emulated/0/Download/Gradient_Tests", name);

            fileWriter = new FileWriter(file,true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //writeToFile is a helper function at the end of the code
        //writeToFile("Test Trial Results for ID " + id + "\n\n");
        //writeToFile("Current Grain: " + grainList[grainIndex] + "\n");
        df = new DecimalFormat("##.##");

        //updateParameters();
        textElement.setText("Progress: " + testNum + "/120");

        //textElement.setText("" + age + " " + gender + " " + hand);

    }

    public boolean onTouchEvent(MotionEvent event) {
        InputDevice.MotionRange xRangeObj =
                InputDevice.getDevice(event.getDeviceId()).getMotionRange(event.AXIS_X);

        float maxX = xRangeObj.getMax();
        float minX = xRangeObj.getMin();
        float center = (maxX + minX) / 2;
        float begin = center - (windowSize / 2);
        float end = center + (windowSize / 2);
        float intensity;

        float startInt = startIntensity;
        float endInt = endIntensity;
        float delta = endInt - startInt;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Grain grain = new Grain(grainList[grainIndex]);
                float x = event.getX();
                float xPos = x - begin;
                if(x > end || x < begin) {
                    intensity = 0.0f;
                }

                else {
                    if(inc){
                        intensity = startInt + (delta * xPos) / windowSize;
                    }

                    else {
                        intensity = endInt - (delta * xPos) / windowSize;
                    }
                }
                if(testNum <= 120) textElement.setText("Progress: " +testNum +"/120");
                //textElement.setText("" + startIntensity + " " + endIntensity + " " + intensity + " " + inc);
                grain.play(intensity);
                break;

            default:
                return false;
        }

        return true;
    }

    public void onClick(View v){

        if(testNum < 120) {
            testNum++;
            textElement.setText("Progress: " + testNum + "/120");

            writeToFile("" + id + " ");
            writeToFile("" + age + " ");
            writeToFile("" + gender + " ");
            writeToFile("" + hand + " ");
            writeToFile("" + grainIndex + " ");
            writeToFile("" + gratingIndex + " ");
            writeToFile("" + (int)windowSize + " ");

            if (inc) {
                writeToFile("1 ");
            } else {
                writeToFile("0 ");
            }

            if (v == increase) {
                writeToFile("1 \n");
            } else if (v == decrease) {
                writeToFile("0 \n");
            }
        }

        updateParameters();
    }

    public boolean checkAllDone() {
        for(int i = 0; i < gratings.length; i++){
            if(!(gratings[i].checkDone()))
                return false;
        }

        return true;
    }

    public void resetAll() {
        for(int i = 0; i < gratings.length; i++){
            gratings[i].reset();
        }
    }
    public void updateParameters() {

        if(checkAllDone()) {
            if(windowSize == 600.0f) {
                windowSize = 300.0f;

                greenL.setVisibility(View.VISIBLE);
                greenR.setVisibility(View.VISIBLE);
                blackL.setVisibility(View.INVISIBLE);
                blackR.setVisibility(View.INVISIBLE);
            }
            else {
                grainIndex = grain2;
                grainCount++;
                windowSize = 600.0f;

                greenL.setVisibility(View.INVISIBLE);
                greenR.setVisibility(View.INVISIBLE);
                blackL.setVisibility(View.VISIBLE);
                blackR.setVisibility(View.VISIBLE);
            }
            if(grainCount < 2 ) {
                if(windowSize == 600.0f) {
                    showSimplePopUp();
                    testNum = 0;
                    textElement.setText("Progress: " + testNum + "/120");
                }

                resetAll();

                double gratingRand = Math.random() * 3;
                while(gratings[(int)gratingRand].checkDone()){
                    gratingRand = Math.random() * 3;
                }

                gratingIndex = (int)gratingRand;
                startIntensity = gratings[gratingIndex].x;
                endIntensity = gratings[gratingIndex].y;

                if(gratings[gratingIndex].numInc == 10) inc = false;
                else if (gratings[gratingIndex].numDec == 10) inc = true;
                else {
                    double incRand = Math.random();
                    if (incRand > 0.5) inc = true;
                    else inc = false;
                }

                gratings[gratingIndex].increment(inc);

            }

            else {
                textElement.setText("The test is complete!");
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                testNum = 121;
            }
        }

        else{
            double gratingRand = Math.random() * 3;
            while(gratings[(int)gratingRand].checkDone()){
                gratingRand = Math.random() * 3;
            }

            gratingIndex = (int)gratingRand;
            startIntensity = gratings[gratingIndex].x;
            endIntensity = gratings[gratingIndex].y;

            if(gratings[gratingIndex].numInc == 10) inc = false;
            else if (gratings[gratingIndex].numDec == 10) inc = true;
            else {
                double incRand = Math.random();
                if (incRand > 0.5) inc = true;
                else inc = false;
            }

            gratings[gratingIndex].increment(inc);
        }
    }

    //writes a string to an output file
    public void writeToFile(String content) {

        if (checkStorageState()) {
            try {
                fileWriter.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else {

        }
    }

    public boolean checkStorageState() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private void showSimplePopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Part 1 Complete!");
        helpBuilder.setMessage("Take a short break, then press continue to go on to part 2");
        helpBuilder.setPositiveButton("Continue",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }


}
