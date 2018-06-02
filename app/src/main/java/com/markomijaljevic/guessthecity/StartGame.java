package com.markomijaljevic.guessthecity;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import java.util.Objects;


public class StartGame extends AppCompatActivity {

    private TextView textViewCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        textViewCount = (TextView)findViewById(R.id.textViewCount);

        new CountDownTimer(6000, 1000) {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onTick(long millisUntilFinished) {
                textViewCount.setText(Objects.toString((millisUntilFinished / 1000),null));
            }

            public void onFinish() {
                finish();
                startActivity(new Intent(getApplicationContext(),GamePlay.class));
            }
        }.start();



    }


}
