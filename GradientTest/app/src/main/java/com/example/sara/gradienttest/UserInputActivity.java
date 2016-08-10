package com.example.sara.gradienttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UserInputActivity extends AppCompatActivity {
    public final static String AGE_MESSAGE = "com.example.sara.gradienttest.AGE";
    public final static String GENDER_MESSAGE = "com.example.sara.gradienttest.GENDER";
    public final static String HAND_MESSAGE = "com.example.sara.gradienttest.HAND";
    public final static String GRAIN1_MESSAGE = "con.example.sara.gradienttest.GRAIN1";
    public final static String GRAIN2_MESSAGE = "con.example.sara.gradienttest.GRAIN2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);
    }

    public void sendInfo(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText age = (EditText) findViewById(R.id.editText);
        EditText gender = (EditText) findViewById(R.id.editText2);
        EditText hand = (EditText) findViewById(R.id.editText3);
        EditText grain1 = (EditText) findViewById(R.id.editText4);
        EditText grain2 = (EditText) findViewById(R.id.editText5);
        String ageMessage = age.getText().toString();
        intent.putExtra(AGE_MESSAGE, ageMessage);
        String genderMessage = gender.getText().toString();
        intent.putExtra(GENDER_MESSAGE, genderMessage);
        String handMessage = hand.getText().toString();
        intent.putExtra(HAND_MESSAGE, handMessage);
        String grain1Message = grain1.getText().toString();
        intent.putExtra(GRAIN1_MESSAGE, grain1Message);
        String grain2Message = grain2.getText().toString();
        intent.putExtra(GRAIN2_MESSAGE, grain2Message);
        startActivity(intent);
    }
}
