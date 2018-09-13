package com.katerina.colorist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView myImageView;
    private Button leftButton1;
    private Button rightButton1;
    private Button leftButton2;
    private Button rightButton2;
    private Button leftButton3;
    private Button rightButton3;
    private TextView countTextView;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private int trueButton;
    private int counter;
    private int colorTrue;
    private int colorFalse;
    private int colorFalse2;
    private int colorFalse3;
    private int colorFalse4;
    private int colorFalse5;
    private int randomButton;
    private boolean ifSaved;
    private static final String KEY_INDEX = "index";
    private static final String TAG = "MainActivity";
    private static final String COLOR_TRUE = "colorTrue";
    private static final String COLOR_FALSE = "colorFalse";
    private static final String RANDOM_BUTTON = "randomButton";
    private static final String APP_PREFERENCES = "mySettings";
    private static final String APP_PREFERENCES_COUNTER = "counter";
    private static final String SELECTED_INDEX = "selectedIndex";
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        findViewById();
        initializeColor();
        checkingInstanceStatus(savedInstanceState);
        clickedButton();
        countTextView.setText(String.valueOf(counter));
        if (settings.contains(APP_PREFERENCES_COUNTER)) {
            countTextView.setText(settings.getString(APP_PREFERENCES_COUNTER, "0"));
            counter = Integer.valueOf(settings.getString(APP_PREFERENCES_COUNTER, "0"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        switch (getSelectedValue()) {
            case R.id.button_easy:
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.GONE);
                linearLayout3.setVisibility(View.GONE);
                break;
            case R.id.button_average:
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
                linearLayout3.setVisibility(View.GONE);
                break;
            case R.id.button_difficult:
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
                linearLayout3.setVisibility(View.VISIBLE);
                break;
        }
    }

    private int getSelectedValue() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getInt(SELECTED_INDEX, -1);
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
        switch (getSelectedValue()) {
            case R.id.button_easy:
                savedInstanceState.putInt(KEY_INDEX, counter);
                savedInstanceState.putInt(COLOR_TRUE, colorTrue);
                savedInstanceState.putInt(COLOR_FALSE, colorFalse);
                savedInstanceState.putInt(RANDOM_BUTTON, randomButton);
                break;
            case R.id.button_average:
                savedInstanceState.putInt(KEY_INDEX, counter);
                savedInstanceState.putInt(COLOR_TRUE, colorTrue);
                savedInstanceState.putInt(COLOR_FALSE, colorFalse);
                savedInstanceState.putInt(COLOR_FALSE, colorFalse2);
                savedInstanceState.putInt(COLOR_FALSE, colorFalse3);
                savedInstanceState.putInt(RANDOM_BUTTON, randomButton);
                break;
            case R.id.button_difficult:
                savedInstanceState.putInt(KEY_INDEX, counter);
                savedInstanceState.putInt(COLOR_TRUE, colorTrue);
                savedInstanceState.putInt(COLOR_FALSE, colorFalse);
                savedInstanceState.putInt(COLOR_FALSE, colorFalse2);
                savedInstanceState.putInt(COLOR_FALSE, colorFalse3);
                savedInstanceState.putInt(COLOR_FALSE, colorFalse4);
                savedInstanceState.putInt(COLOR_FALSE, colorFalse5);
                savedInstanceState.putInt(RANDOM_BUTTON, randomButton);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        onSaveInstanceState(new Bundle());
        initializeColor();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initializeColor();
    }

    private int getRandomButton() {
        Random myRandom = new Random();
        switch (getSelectedValue()) {
            case R.id.button_easy:
                return myRandom.nextInt(2);
            case R.id.button_average:
                return myRandom.nextInt(4);
            case R.id.button_difficult:
                return myRandom.nextInt(6);
            default: return 6;
        }
    }

    private void setColors(int colorTrue, int colorFalse, int colorFalse2, int colorFalse3, int
                           colorFalse4, int colorFalse5) {
        if (!ifSaved) {
            randomButton = getRandomButton();
        }
        switch (getSelectedValue()) {
            case R.id.button_easy:
                switch (randomButton) {
                    case 0:
                        trueButton = 0;
                        leftButton1.setText(String.format("#%06X", 0xFFFFFF & colorTrue));
                        rightButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse));
                        break;
                    case 1:
                        trueButton = 1;
                        rightButton1.setText(String.format("#%06X", 0xFFFFFF & colorTrue));
                        leftButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse));
                        break;
                }
                break;
            case R.id.button_average:
                switch (randomButton) {
                    case 0:
                        trueButton = 0;
                        leftButton1.setText(String.format("#%06X", 0xFFFFFF & colorTrue));
                        rightButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse));
                        leftButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse2));
                        rightButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse3));
                        break;
                    case 1:
                        trueButton = 1;
                        rightButton1.setText(String.format("#%06X", 0xFFFFFF & colorTrue));
                        leftButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse));
                        leftButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse2));
                        rightButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse3));
                        break;
                    case 2:
                        trueButton = 2;
                        leftButton2.setText(String.format("#%06X", 0xFFFFFF & colorTrue));
                        rightButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse));
                        leftButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse2));
                        rightButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse3));
                        break;
                    case 3:
                        trueButton = 3;
                        rightButton2.setText(String.format("#%06X", 0xFFFFFF & colorTrue));
                        rightButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse));
                        leftButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse2));
                        leftButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse3));
                        break;
                }
                break;
            case R.id.button_difficult:
                switch (randomButton) {
                    case 0:
                        trueButton = 0;
                        leftButton1.setText(String.format("#%06X", 0xFFFFFF & colorTrue));
                        rightButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse));
                        leftButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse2));
                        rightButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse3));
                        leftButton3.setText(String.format("#%06X", 0xFFFFFF & colorFalse4));
                        rightButton3.setText(String.format("#%06X", 0xFFFFFF & colorFalse5));
                        break;
                    case 1:
                        trueButton = 1;
                        rightButton1.setText(String.format("#%06X", 0xFFFFFF & colorTrue));
                        leftButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse));
                        leftButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse2));
                        rightButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse3));
                        leftButton3.setText(String.format("#%06X", 0xFFFFFF & colorFalse4));
                        rightButton3.setText(String.format("#%06X", 0xFFFFFF & colorFalse5));
                        break;
                    case 2:
                        trueButton = 2;
                        leftButton2.setText(String.format("#%06X", 0xFFFFFF & colorTrue));
                        rightButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse));
                        leftButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse2));
                        rightButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse3));
                        leftButton3.setText(String.format("#%06X", 0xFFFFFF & colorFalse4));
                        rightButton3.setText(String.format("#%06X", 0xFFFFFF & colorFalse5));
                        break;
                    case 3:
                        trueButton = 3;
                        rightButton2.setText(String.format("#%06X", 0xFFFFFF & colorTrue));
                        rightButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse));
                        leftButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse2));
                        leftButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse3));
                        leftButton3.setText(String.format("#%06X", 0xFFFFFF & colorFalse4));
                        rightButton3.setText(String.format("#%06X", 0xFFFFFF & colorFalse5));
                        break;
                    case 4:
                        trueButton = 4;
                        leftButton3.setText(String.format("#%06X", 0xFFFFFF & colorTrue));
                        rightButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse));
                        leftButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse2));
                        rightButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse3));
                        leftButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse4));
                        rightButton3.setText(String.format("#%06X", 0xFFFFFF & colorFalse5));
                        break;
                    case 5:
                        trueButton = 5;
                        rightButton3.setText(String.format("#%06X", 0xFFFFFF & colorTrue));
                        rightButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse));
                        leftButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse2));
                        rightButton2.setText(String.format("#%06X", 0xFFFFFF & colorFalse3));
                        leftButton3.setText(String.format("#%06X", 0xFFFFFF & colorFalse4));
                        leftButton1.setText(String.format("#%06X", 0xFFFFFF & colorFalse5));
                        break;
                }
                break;
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
        switch (getSelectedValue()) {
            case R.id.button_easy:
                colorTrue = getRandomColor();
                colorFalse = getRandomColor();
                break;
            case R.id.button_average:
                colorTrue = getRandomColor();
                colorFalse = getRandomColor();
                colorFalse2 = getRandomColor();
                colorFalse3 = getRandomColor();
                break;
            case R.id.button_difficult:
                colorTrue = getRandomColor();
                colorFalse = getRandomColor();
                colorFalse2 = getRandomColor();
                colorFalse3 = getRandomColor();
                colorFalse4 = getRandomColor();
                colorFalse5 = getRandomColor();
                break;
        }
        changeColor(colorTrue);
        setColors(colorTrue, colorFalse, colorFalse2, colorFalse3, colorFalse4, colorFalse5);
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

    private void checkingInstanceStatus(Bundle savedInstanceState) {
        if (savedInstanceState != null)  {
            ifSaved = true;
            counter = savedInstanceState.getInt(KEY_INDEX, 0);
            randomButton = savedInstanceState.getInt(RANDOM_BUTTON, 0);
            changeColor(colorTrue);
            setColors(colorTrue, colorFalse, colorFalse2, colorFalse3, colorFalse4, colorFalse5);
            colorTrue = savedInstanceState.getInt(COLOR_TRUE, 0);
            colorFalse = savedInstanceState.getInt(COLOR_FALSE, 0);
            colorFalse2 = savedInstanceState.getInt(COLOR_FALSE, 0);
            colorFalse3 = savedInstanceState.getInt(COLOR_FALSE, 0);
            colorFalse4 = savedInstanceState.getInt(COLOR_FALSE, 0);
            colorFalse5 = savedInstanceState.getInt(COLOR_FALSE, 0);
        } else {
            ifSaved = false;
            initializeColor();
        }
    }

    private void clickedButton() {
        leftButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newState(0);
            }
        });
        rightButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newState(1);
            }
        });
        leftButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newState(2);
            }
        });
        rightButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newState(3);
            }
        });
        leftButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newState(4);
            }
        });
        rightButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newState(5);
            }
        });
    }

    private void findViewById() {
        linearLayout = findViewById(R.id.buttonPanel);
        linearLayout2 = findViewById(R.id.buttonPanel2);
        linearLayout3 = findViewById(R.id.buttonPanel3);
        myImageView = findViewById(R.id.imageView);
        leftButton1 = findViewById(R.id.left_button);
        rightButton1 = findViewById(R.id.right_button);
        leftButton2 = findViewById(R.id.left_button2);
        rightButton2 = findViewById(R.id.right_button2);
        leftButton3 = findViewById(R.id.left_button3);
        rightButton3 = findViewById(R.id.right_button3);
        countTextView = findViewById(R.id.count_textView);
    }
}

