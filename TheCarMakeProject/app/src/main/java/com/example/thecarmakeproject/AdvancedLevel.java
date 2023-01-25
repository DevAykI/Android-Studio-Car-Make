package com.example.thecarmakeproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class AdvancedLevel extends AppCompatActivity {
    boolean message;
    private TypedArray cars;
    private ImageButton carimage1;
    private ImageButton carimage2;
    private ImageButton carimage3;
    private Button btnGen;
    int rand=0,rand2=0,rand3=0;
    int incorrectcounter = 0;
    int scorecounter = 0;
    CountDownTimer waitTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);
        //-- Getting image/button --//
        cars = getResources().obtainTypedArray(R.array.cars);
        carimage1 = (ImageButton)findViewById(R.id.imageButton);
        carimage2 = (ImageButton)findViewById(R.id.imageButton2);
        carimage3 = (ImageButton)findViewById(R.id.imageButton3);
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
                //Log.d("info", btntext);
                if (btntext == "next" ) {
                    btnGen.setText("submit");
                    RandomImage();
                    countdown();
                    ((ConstraintLayout) findViewById(R.id.popupcon)).setVisibility(View.INVISIBLE);
                }else{
                    Validate();
                }
            }
        });


    }

    //-- RandomImage function chooses a random car image! --//
    private void RandomImage(){
        // resetting everything
        ((TextView) findViewById(R.id.triesleft)).setText("You have 3 tries!");
        Random random = new Random();
        incorrectcounter=0;
        ((TextView) findViewById(R.id.cartext)).setText("");
        ((TextView) findViewById(R.id.cartext)).setEnabled(true);
        ((TextView) findViewById(R.id.cartext)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.cartext2)).setText("");
        ((TextView) findViewById(R.id.cartext2)).setEnabled(true);
        ((TextView) findViewById(R.id.cartext2)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.cartext3)).setText("");
        ((TextView) findViewById(R.id.cartext3)).setEnabled(true);
        ((TextView) findViewById(R.id.cartext3)).setTextColor(Color.BLACK);

        do{ // making sure the random picker does not pick the same car twice!
            rand = random.nextInt(30);
            rand2 = random.nextInt(30);
            rand3 = random.nextInt(30);
        }while (rand == rand2 || rand2 ==rand3 || rand3 == rand);
        carimage1.setBackgroundResource(getResources().getIdentifier(cars.getString(rand) , "drawable", getPackageName()));
        carimage1.setTag(cars.getString(rand));
        carimage2.setBackgroundResource(getResources().getIdentifier(cars.getString(rand2) , "drawable", getPackageName()));
        carimage2.setTag(cars.getString(rand2));
        carimage3.setBackgroundResource(getResources().getIdentifier(cars.getString(rand3) , "drawable", getPackageName()));
        carimage3.setTag(cars.getString(rand3));
        Log.d("answer", "Right Most:"+cars.getString(rand)+"| Middle:"+cars.getString(rand2)+"| Left Most:"+cars.getString(rand3));// answers for those like me who aren't very knowledgeable in cars.
    }

    //-- Validate function makes sure the answers selected are correct! --//
    public void Validate() {
        // -- getting the names of the car -- //
        String car1 = (String) carimage1.getTag().toString();
        String car2 = (String) carimage2.getTag().toString();
        String car3 = (String) carimage3.getTag().toString();
        // -- getting the text that were entered --//
        String cartext1 = ((TextView) findViewById(R.id.cartext)).getText().toString().toLowerCase();
        String cartext2 = ((TextView) findViewById(R.id.cartext2)).getText().toString().toLowerCase();
        String cartext3 = ((TextView) findViewById(R.id.cartext3)).getText().toString().toLowerCase();

        // -- checking if names match! -- //
        if (car1.equals(cartext1) && ((TextView) findViewById(R.id.cartext)).isEnabled()){
            scorecounter += 1;
            ((TextView) findViewById(R.id.cartext)).setEnabled(false);
            ((TextView) findViewById(R.id.cartext)).setTextColor(Color.GREEN);
        }else{
            if (((TextView) findViewById(R.id.cartext)).isEnabled()) { // making sure i'm not tagging the correct answer as incorrect
                ((TextView) findViewById(R.id.cartext)).setEnabled(true);
                ((TextView) findViewById(R.id.cartext)).setTextColor(Color.RED);
            }
        }
        if (car2.equals(cartext2) && ((TextView) findViewById(R.id.cartext2)).isEnabled()){
            scorecounter += 1;
            ((TextView) findViewById(R.id.cartext2)).setEnabled(false);
            ((TextView) findViewById(R.id.cartext2)).setTextColor(Color.GREEN);
        }else{
            if (((TextView) findViewById(R.id.cartext2)).isEnabled()) {
                ((TextView) findViewById(R.id.cartext2)).setEnabled(true);
                ((TextView) findViewById(R.id.cartext2)).setTextColor(Color.RED);
            }
        }
        if (car3.equals(cartext3) && ((TextView) findViewById(R.id.cartext3)).isEnabled()){
            scorecounter += 1;
            ((TextView) findViewById(R.id.cartext3)).setEnabled(false);
            ((TextView) findViewById(R.id.cartext3)).setTextColor(Color.GREEN);
        }else{
            if (((TextView) findViewById(R.id.cartext3)).isEnabled()) {
                ((TextView) findViewById(R.id.cartext3)).setEnabled(true);
                ((TextView) findViewById(R.id.cartext3)).setTextColor(Color.RED);
            }
        }
        ((TextView) findViewById(R.id.Score)).setText("Score: "+scorecounter);

        // -- now checking if all names match --//
        if (car1.equals(cartext1) && car2.equals(cartext2) && car3.equals(cartext3) ) {

            btnGen.setText("next");
            ((TextView) findViewById(R.id.result)).setText("Correct!");
            ((TextView) findViewById(R.id.result)).setTextColor(Color.parseColor("#00FF00"));
            ((TextView) findViewById(R.id.answer)).setText(""); // no text according to the cw if you got it correct.
            ((ConstraintLayout) findViewById(R.id.popupcon)).setVisibility(View.VISIBLE);
        }else{
            countdown();
            // another try or if out of tries display incorrect
            incorrectcounter+= 1;
            ((TextView) findViewById(R.id.triesleft)).setText((3-incorrectcounter) + " tries left!");

            if (incorrectcounter == 3){
                //-- giving the answers for all the incorrect cars --//
                ((TextView) findViewById(R.id.answer)).setText("");
                if (!car1.equals(cartext1)){// less work for same effect
                    ((TextView) findViewById(R.id.answer)).setText("["+car1+"] ");
                }
                if (!car2.equals(cartext2)){
                    ((TextView) findViewById(R.id.answer)).setText(((TextView) findViewById(R.id.answer)).getText().toString()+"["+car2+"] ");
                }
                if (!car3.equals(cartext3)){
                    ((TextView) findViewById(R.id.answer)).setText(((TextView) findViewById(R.id.answer)).getText().toString()+"["+car3+"] ");
                }

                btnGen.setText("next");
                ((TextView) findViewById(R.id.result)).setText("Wrong!");
                ((TextView) findViewById(R.id.result)).setTextColor(Color.parseColor("#FF0000"));
                ((TextView) findViewById(R.id.answer)).setText(((TextView) findViewById(R.id.answer)).getText().toString()+"were the correct answer(s)");
                ((ConstraintLayout) findViewById(R.id.popupcon)).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.triesleft)).setText("ran out!");
            }
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
                countdown.setText("" + (millisUntilFinished+999) / 1000); // ensuring the countdown starts from 20 and ends at 1 just like the coursework specifies! This does not effect the real countdown!
            }
            public void onFinish() {
                Validate();
            }

        }.start();
    }

}