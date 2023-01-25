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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class CarImageActivity extends AppCompatActivity {
    boolean message;
    private TypedArray cars;
    private ImageButton carimage1;
    private ImageButton carimage2;
    private ImageButton carimage3;
    private Button btnGen;
    int rand=0,rand2=0,rand3=0;
    CountDownTimer waitTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_image);
        //-- Getting image/button --//
        cars = getResources().obtainTypedArray(R.array.cars);
        carimage1 = (ImageButton)findViewById(R.id.imageButton);
        carimage2 = (ImageButton)findViewById(R.id.imageButton2);
        carimage3 = (ImageButton)findViewById(R.id.imageButton3);
        btnGen = (Button)findViewById(R.id.SubmitButton);
        btnGen.setText("next");

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
                    Log.d("info", "has gone through");
                    btnGen.setEnabled(false);
                    RandomImage();
                    countdown();
                    ((ConstraintLayout) findViewById(R.id.popupcon)).setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    //-- RandomImage function chooses a random car image! --//
    private void RandomImage(){
        Random random = new Random();
        do{ // making sure the random picker does not pick the same car twice!
            rand = random.nextInt(30);
            rand2 = random.nextInt(30);
            rand3 = random.nextInt(30);
            Log.d("random numbers:", "RandomImage: "+rand+" "+rand2+" "+rand3); // seeing where the dice roll
        }while (rand == rand2 || rand2 ==rand3 || rand3 == rand);
        int Chosen = DecideCar(rand,rand2,rand3);
        ((TextView) findViewById(R.id.CarName)).setText(cars.getString(Chosen));
        carimage1.setBackgroundResource(getResources().getIdentifier(cars.getString(rand) , "drawable", getPackageName()));
        carimage1.setTag(cars.getString(rand));
        carimage2.setBackgroundResource(getResources().getIdentifier(cars.getString(rand2) , "drawable", getPackageName()));
        carimage2.setTag(cars.getString(rand2));
        carimage3.setBackgroundResource(getResources().getIdentifier(cars.getString(rand3) , "drawable", getPackageName()));
        carimage3.setTag(cars.getString(rand3));

    }

    //-- Validate function makes sure the answers selected are correct! --//
    public void Validate(View v) {
        if (((ConstraintLayout) findViewById(R.id.popupcon)).getVisibility() == View.INVISIBLE) {
            if (v == null){
                btnGen.setEnabled(true);
                ((TextView) findViewById(R.id.result)).setText("Wrong!");
                ((TextView) findViewById(R.id.result)).setTextColor(Color.parseColor("#FF0000"));
                ((ConstraintLayout) findViewById(R.id.popupcon)).setVisibility(View.VISIBLE);
            }else{
                String imageName = (String) v.getTag(); // getting name of currently displayed image
                btnGen.setEnabled(true);
                if (((TextView) findViewById(R.id.CarName)).getText().toString().equals(imageName.toLowerCase())){
                    ((TextView) findViewById(R.id.result)).setText("Correct!");
                    ((TextView) findViewById(R.id.result)).setTextColor(Color.parseColor("#00FF00"));
                    ((ConstraintLayout) findViewById(R.id.popupcon)).setVisibility(View.VISIBLE);
                }else{
                    ((TextView) findViewById(R.id.result)).setText("Wrong!");
                    ((TextView) findViewById(R.id.result)).setTextColor(Color.parseColor("#FF0000"));
                    ((ConstraintLayout) findViewById(R.id.popupcon)).setVisibility(View.VISIBLE);
                }
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
                countdown.setText("Times Up!");
                btnGen.setText("next");
                Validate(null);
            }

        }.start();
    }
    public int DecideCar(int one,int two,int three){
        Random r = new Random();

        int[] disc = { one,two,three};
        return disc[r.nextInt(disc.length)];

    }

}