package com.katerina.colorist;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {


    private RadioGroup radioGroup;
    private static final String SELECTED_INDEX = "selectedIndex";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        radioGroup = findViewById(R.id.radio_group);
        restoreState();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.button_easy:
                        saveSelectedIndex(MainActivity.EASY);
                        break;
                    case R.id.button_average:
                        saveSelectedIndex(MainActivity.AVERAGE);
                        break;
                    case R.id.button_difficult:
                        saveSelectedIndex(MainActivity.DIFFICULT);
                        break;
                }
            }
        });
        RadioButton radioButton = (radioGroup.findViewById(getSelectedValue()));
        if (radioButton != null) {
            radioButton.setChecked(true);
        }
    }

    private void restoreState() {
        switch (getSelectedValue()) {
            case MainActivity.EASY:
                radioGroup.check(R.id.button_easy);
                break;
            case MainActivity.AVERAGE:
                radioGroup.check(R.id.button_average);
                break;
            case MainActivity.DIFFICULT:
                radioGroup.check(R.id.button_difficult);
                break;
        }

    }

    private int getSelectedValue() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getInt(SELECTED_INDEX, 0);
    }

    private void saveSelectedIndex(int value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putInt(SELECTED_INDEX, value);
        editor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
