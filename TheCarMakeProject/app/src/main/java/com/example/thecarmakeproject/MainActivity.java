package com.example.thecarmakeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void launchIdentify(View v){
        // launch a new activity
        Intent i = new Intent(this, Identify_car.class);
        i.putExtra("countdown",((Switch) findViewById(R.id.switch1)).isChecked());
        startActivity(i);
    }
    public void launchHint(View v){
        // launch a new activity
        Intent i = new Intent(this, HintsActivity.class);
        i.putExtra("countdown",((Switch) findViewById(R.id.switch1)).isChecked());
        startActivity(i);
    }
    public void launchIdentifyImage(View v){
        // launch a new activity
        Intent i = new Intent(this, CarImageActivity.class);
        i.putExtra("countdown",((Switch) findViewById(R.id.switch1)).isChecked());
        startActivity(i);
    }
    public void launchAdvancedLevel(View v){
        Intent i = new Intent(this, AdvancedLevel.class);
        i.putExtra("countdown",((Switch) findViewById(R.id.switch1)).isChecked());
        startActivity(i);
    }
}