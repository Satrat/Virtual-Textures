package com.example.sara.alteredsizetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UserInputActivity extends AppCompatActivity {
    public final static String AGE_MESSAGE = "com.example.sara.alteredsizetest.AGE";
    public final static String GENDER_MESSAGE = "com.example.sara.alteredsizetest.GENDER";
    public final static String HAND_MESSAGE = "com.example.sara.alteredsizetest.HAND";

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
        String ageMessage = age.getText().toString();
        intent.putExtra(AGE_MESSAGE, ageMessage);
        String genderMessage = gender.getText().toString();
        intent.putExtra(GENDER_MESSAGE, genderMessage);
        String handMessage = hand.getText().toString();
        intent.putExtra(HAND_MESSAGE, handMessage);
        startActivity(intent);
    }
}
