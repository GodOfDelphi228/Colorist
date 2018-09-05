package com.katerina.colorist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView myImageView;
    private Button leftButton;
    private Button rightButton;
    private TextView countTextView;
    private RelativeLayout myLayout;
    private int trueButton;
    private int counter;
    private int colorTrue;
    private int colorFalse;
    private int randomButton;
    private boolean ifSaved;
    private static final String KEY_INDEX = "index";
    private static final String TAG = "MainActivity";
    private static final String COLOR_TRUE = "colorTrue";
    private static final String COLOR_FALSE = "colorFalse";
    private static final String RANDOM_BUTTON = "randomButton";
    private static final String APP_PREFERENCES = "mySettings";
    private static final String APP_PREFERENCES_COUNTER = "counter";
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        myImageView = findViewById(R.id.imageView);
        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);
        countTextView = findViewById(R.id.count_textView);
        myLayout = findViewById(R.id.linearLayout);
        if (savedInstanceState != null) {
            ifSaved = true;
            counter = savedInstanceState.getInt(KEY_INDEX, 0);
            colorTrue = savedInstanceState.getInt(COLOR_TRUE, 0);
            colorFalse = savedInstanceState.getInt(COLOR_FALSE, 0);
            randomButton = savedInstanceState.getInt(RANDOM_BUTTON, 0);
            changeColor(colorTrue);
            setColors(colorTrue, colorFalse);
        } else {
            ifSaved = false;
            initializeColor();
        }
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newState(0);
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newState(1);
            }
        });
        countTextView.setText(String.valueOf(counter));
        if (settings.contains(APP_PREFERENCES_COUNTER)) {
            countTextView.setText(settings.getString(APP_PREFERENCES_COUNTER, "0"));
            counter = Integer.valueOf(settings.getString(APP_PREFERENCES_COUNTER, "0"));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        String strCountTextView = String.valueOf(counter);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(APP_PREFERENCES_COUNTER, strCountTextView);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                counter = 0;
                countTextView.setText(String.valueOf(counter));
                return true;
            case R.id.about:
                startAboutActivity();
                return true;
            case R.id.settings:
                startSettingsActivity();
                return true;
        }
        return true;
    }

    @Override
    public void  onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, counter);
        savedInstanceState.putInt(COLOR_TRUE, colorTrue);
        savedInstanceState.putInt(COLOR_FALSE, colorFalse);
        savedInstanceState.putInt(RANDOM_BUTTON, randomButton);
    }

    @Override
    protected void onStop() {
        super.onStop();
        onSaveInstanceState(new Bundle());
    }

    private int getRandomButton() {
        Random myRandom = new Random();
        return myRandom.nextInt(2);
    }

    private void setColors(int colorTrue, int colorFalse) {
        if (!ifSaved) {
            randomButton = getRandomButton();
        }
        if (randomButton == 1) {
            trueButton = 0;
            leftButton.setText(String.format("#%06X", 0xFFFFFF & colorTrue));
            rightButton.setText(String.format("#%06X", 0xFFFFFF & colorFalse));
        } else {
            trueButton = 1;
            leftButton.setText(String.format("#%06X", 0xFFFFFF & colorFalse));
            rightButton.setText(String.format("#%06X", 0xFFFFFF & colorTrue));
        }
    }

    private int getRandomColor() {
        Random myRandom = new Random();
        return Color.argb(255, myRandom.nextInt(256), myRandom.nextInt(256),
                myRandom.nextInt(256));
    }

    private void changeColor(int color) {
        myImageView.setBackgroundColor(color);
    }

    private void initializeColor() {
        colorTrue = getRandomColor();
        colorFalse = getRandomColor();
        changeColor(colorTrue);
        setColors(colorTrue, colorFalse);
    }

    private boolean checkColor(int clickedButton) {
        return clickedButton == trueButton;
    }

    private void newState(int clickedButton) {
        if (checkColor(clickedButton)) {
            countTextView.setText(String.valueOf(++counter));
        } else {
            countTextView.setText(String.valueOf(--counter));
        }
        ifSaved = false;
        initializeColor();
    }

    private void startAboutActivity() {
        Intent i = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(i);
    }

    private void startSettingsActivity() {
        Intent i = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(i);
    }
}

