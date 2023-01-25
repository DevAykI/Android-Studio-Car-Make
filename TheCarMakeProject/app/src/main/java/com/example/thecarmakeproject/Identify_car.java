package com.example.thecarmakeproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;
//optimizing code time!

public class Identify_car extends AppCompatActivity{
    int rand=0,lastpicked=0;
    CountDownTimer waitTimer;
    private TypedArray cars;
    private ImageView iv;
    private Button btnGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car);

        //-- Getting image/button --//
        cars = getResources().obtainTypedArray(R.array.cars);
        iv = (ImageView)findViewById(R.id.iv);
        btnGen = (Button)findViewById(R.id.SubmitButton);
        btnGen.setText("identify");

        //-- Creating the DropDownList! --//
        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{cars.getString(0), cars.getString(1), cars.getString(2), cars.getString(3),cars.getString(4),cars.getString(5), cars.getString(6), cars.getString(7),cars.getString(8),cars.getString(9), cars.getString(10), cars.getString(11),cars.getString(12),cars.getString(13), cars.getString(14), cars.getString(15),cars.getString(16),cars.getString(17),cars.getString(18),cars.getString(19),cars.getString(20), cars.getString(21),cars.getString(22),cars.getString(23), cars.getString(24), cars.getString(25),cars.getString(26),cars.getString(27),cars.getString(28),cars.getString(29)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        RandomImage();// create a randomized image

        //-- Creating a countdown if turned on! --//
        Intent i = getIntent();
        Boolean message = i.getBooleanExtra("countdown",false); // this allows me to transport data
        if (message){
            countdown();
        }else{
            ConstraintLayout layout = findViewById(R.id.Container);
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.height =0;
            params.width = 0;
            layout.setLayoutParams(params);
        }

        //-- Creating the onclick event for identify button! --//
        btnGen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                String btntext = btnGen.getText().toString();
                //Log.d("info", btntext);
                if (btntext == "identify" ) {
                    Log.d("info", "has gone through");
                    btnGen.setText("next");
                    Validate();
                    // stopping timer if it is currently still counting down//
                    if(waitTimer != null) {
                        waitTimer.cancel();
                        waitTimer = null;
                    }
                }else if (btntext == "next"){
                    if (message){
                        countdown();
                    }
                    RandomImage();
                    btnGen.setText("identify");
                    ((ConstraintLayout)findViewById(R.id.popupcon)).setVisibility(View.INVISIBLE);
                }


            }
        });


    }

    //-- RandomImage function chooses a random car image! --//
    private void RandomImage(){
        Random random = new Random();

        do{ // making sure the random picker does not pick the same car twice!
            rand = random.nextInt(30);
        }while (rand == lastpicked);

        String mDrawableName = cars.getString(rand);
        int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        iv.setBackgroundResource(resID);
        iv.setTag(mDrawableName);
        lastpicked = rand;
        Log.d("answer", "Car Name: " + mDrawableName);// help for those who are not so educated in cars just like me XD

    }

    //-- Validate function makes sure the answers selected are correct! --//
    private void Validate() {
        String imageName = (String) iv.getTag(); // getting name of currently displayed image
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
        String text = mySpinner.getSelectedItem().toString();// getting the name user selected
        if (imageName == text) {
            ((TextView) findViewById(R.id.result)).setText("Correct!");
            ((TextView) findViewById(R.id.result)).setTextColor(Color.parseColor("#00FF00"));
            ((TextView) findViewById(R.id.answer)).setText(imageName + " was the correct answer!");
            ((ConstraintLayout) findViewById(R.id.popupcon)).setVisibility(View.VISIBLE);

        } else {
            ((TextView) findViewById(R.id.result)).setText("Wrong!");
            ((TextView) findViewById(R.id.result)).setTextColor(Color.parseColor("#FF0000"));
            ((TextView) findViewById(R.id.answer)).setText(imageName + " was the correct answer!");
            ((ConstraintLayout) findViewById(R.id.popupcon)).setVisibility(View.VISIBLE);
        }
    }

    //-- The countdown function creates a 20 second countdown --//
    private void countdown(){
        TextView countdown = (TextView)findViewById(R.id.CountDown);
        waitTimer = new CountDownTimer(20000, 1000) {// 20 second count down with 1 second countdown interval!

            public void onTick(long millisUntilFinished) {
                countdown.setText("" + (millisUntilFinished+999) / 1000); // ensuring the countdown starts from 20 and ends at 1 just like the coursework specifies! This does not effect the countdown!
            }

            public void onFinish() {
                countdown.setText("Times Up!");
                btnGen.setText("next");
                Validate();// validating as if user clicked the identify button!
            }

        }.start();
    }


}