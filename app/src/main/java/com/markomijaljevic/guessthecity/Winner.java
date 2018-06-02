package com.markomijaljevic.guessthecity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Winner extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewScore;
    private TextView textViewRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        textViewScore = (TextView) findViewById(R.id.TextViewScore);
        textViewRetry = (TextView) findViewById(R.id.textViewRetry);

        textViewRetry.setOnClickListener(this);

        textViewScore.setText(getIntent().getStringExtra("Score"));


    }

    @Override
    public void onClick(View view) {

        if(view == textViewRetry){
            finish();
            startActivity(new Intent(this,StartGame.class));
        }

    }
}
