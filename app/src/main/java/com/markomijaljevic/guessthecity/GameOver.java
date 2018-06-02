package com.markomijaljevic.guessthecity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GameOver extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewScore;
    private TextView textViewRetry;
    private String score;
    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        textViewScore = (TextView) findViewById(R.id.TextViewScore);
        textViewRetry = (TextView) findViewById(R.id.textViewRetry);

        dbRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        textViewRetry.setOnClickListener(this);

        score = getIntent().getStringExtra("Score");
        textViewScore.setText(score);

        HighScore high = new HighScore(mAuth.getCurrentUser().getEmail(),Integer.parseInt(score));
        dbRef.child("Users").push().setValue(high);

    }

    @Override
    public void onClick(View view) {

        if(view == textViewRetry){
            finish();
            startActivity(new Intent(this,StartGame.class));
        }

    }
}
