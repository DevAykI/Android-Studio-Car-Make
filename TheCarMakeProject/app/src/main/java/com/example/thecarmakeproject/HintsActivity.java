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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class HintsActivity extends AppCompatActivity {
    private TypedArray cars;
    private ImageView iv;
    private Button btnGen;
    boolean message;
    int rand=0,lastpicked=0;
    int incorrectcounter = 0;
    CountDownTimer waitTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

        //-- Getting image/button --//
        cars = getResources().obtainTypedArray(R.array.cars);
        iv = (ImageView)findViewById(R.id.iv);
        btnGen = (Button)findViewById(R.id.SubmitButton);
        btnGen.setText("submit");

        RandomImage();
        //-- Creating a countdown if turned on! --//
        Intent i = getIntent();
        message = i.getBooleanExtra("countdown",false); // this allows me to transport data
        if (message){
            countdown();
        }else{
            ConstraintLayout layout = findViewById(R.id.Container);
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.height =0;
            params.width = 0;
            layout.setLayoutParams(params);
        }

        //-- Creating the onclick event for submit button! --//
        btnGen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String btntext = btnGen.getText().toString();
                if (btntext == "submit" ) {
                    Log.d("info", "has gone through");

                    Validate();

                }else if (btntext == "next"){

                    if (message){
                        countdown();
                    }
                    RandomImage();
                    btnGen.setText("submit");
                    ((ConstraintLayout)findViewById(R.id.popupcon)).setVisibility(View.INVISIBLE);
                }


            }
        });


    }

    //-- RandomImage function chooses a random car image! --//
    private void RandomImage(){
        Random random = new Random();
        incorrectcounter=0;
        ((TextView) findViewById(R.id.Triesleft)).setText("You have 3 tries!");
        do{ // making sure the random picker does not pick the same car twice!
            rand = random.nextInt(30);
        }while (rand == lastpicked);

        String mDrawableName = cars.getString(rand);
        int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        iv.setBackgroundResource(resID);
        iv.setTag(mDrawableName);

        // Creating The hint game mode! //
        String displaySecret = "";
        for (int i = 0; i < mDrawableName.length(); i++)
            displaySecret += "-";
        ((TextView)findViewById(R.id.hintview)).setText(displaySecret);

        lastpicked = rand;
        Log.d("answer", "Car Name: " + mDrawableName);// help for those who are not so educated in cars just like me XD

    }

    //-- Validate function makes sure the answers selected are correct! --//
    private void Validate() {

        String imageName = (String) iv.getTag(); // getting name of currently displayed image
        int position = imageName.indexOf(((TextView) findViewById(R.id.Guess)).getText().toString());

        if (position == -1 || ((TextView) findViewById(R.id.Guess)).getText().toString().equals("")){//-1 means incorrect
            incorrectcounter+= 1;
            ((TextView) findViewById(R.id.Triesleft)).setText((3-incorrectcounter) + " tries left!");

            if (incorrectcounter == 3){
                btnGen.setText("next");
                ((TextView) findViewById(R.id.result)).setText("Wrong!");
                ((TextView) findViewById(R.id.result)).setTextColor(Color.parseColor("#FF0000"));
                ((TextView) findViewById(R.id.answer)).setText(imageName + " was the correct answer!");
                ((ConstraintLayout) findViewById(R.id.popupcon)).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.Triesleft)).setText("ran out!");

            }
        }

        String newDisplaySecret = "";
        for (int i = 0; i < imageName.length(); i++)
            if ((imageName.substring(i, i+1).equals(((TextView) findViewById(R.id.Guess)).getText().toString().toLowerCase())))
                newDisplaySecret += imageName.charAt(i); //newly guessed character
            else
                newDisplaySecret += ((TextView)findViewById(R.id.hintview)).getText().charAt(i); //old state
        ((TextView)findViewById(R.id.hintview)).setText(newDisplaySecret);
        ((TextView) findViewById(R.id.Guess)).setText("");
        Log.d("image", (((TextView)findViewById(R.id.hintview)).getText().toString().toLowerCase() +" and "+imageName.toLowerCase()));
        if (message && incorrectcounter <3 && !newDisplaySecret.equals(imageName.toLowerCase())){
            countdown();
        }
        if (newDisplaySecret.equals(imageName.toLowerCase())){
            btnGen.setText("next");
            ((TextView) findViewById(R.id.result)).setText("Correct!");
            ((TextView) findViewById(R.id.result)).setTextColor(Color.parseColor("#00FF00"));
            ((TextView) findViewById(R.id.answer)).setText(imageName + " was the correct answer!");
            ((ConstraintLayout) findViewById(R.id.popupcon)).setVisibility(View.VISIBLE);
        }


    }

    //-- The countdown function creates a 20 second countdown --//
    private void countdown(){
        if(waitTimer != null) {
            waitTimer.cancel();
            waitTimer = null;
        }
        TextView countdown = (TextView)findViewById(R.id.CountDown);
        waitTimer = new CountDownTimer(20000, 1000) {// 20 second count down with 1 second countdown interval!

            public void onTick(long millisUntilFinished) {
                countdown.setText("" + (millisUntilFinished+999) / 1000); // ensuring the countdown starts from 20 and ends at 1 just like the coursework specifies! This does not effect the countdown!
            }
            public void onFinish() {
                countdown.setText("Times Up!");
                btnGen.setText("next");
                Validate();// validating as if user clicked the submit button!
            }

        }.start();
    }


}