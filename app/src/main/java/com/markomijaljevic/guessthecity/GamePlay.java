package com.markomijaljevic.guessthecity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class GamePlay extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewCount;
    private TextView answer1;
    private TextView answer2;
    private TextView answer3;
    private ImageView image;
    private String CorrectAns;
    private CountDownTimer cdt;

    private Intent intent;

    private String sec;
    private int HighScore=0;
    private int counter=0;
    private Integer[] arr;


    private DataSnapshot ds;

    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        textViewCount = (TextView) findViewById(R.id.textViewCounter);
        answer1 = (TextView) findViewById(R.id.textViewAns1);
        answer2 = (TextView) findViewById(R.id.textViewAns2);
        answer3 = (TextView) findViewById(R.id.textViewAns3);

        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);

        image = (ImageView) findViewById(R.id.imageViewImage);

        dbRef = FirebaseDatabase.getInstance().getReference();

        intent = new Intent(getBaseContext(),GameOver.class);


        cdt = new CountDownTimer(11000, 1000) {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onTick(long millisUntilFinished) {

                sec = Objects.toString((millisUntilFinished / 1000),null);

                if(sec == "3" || sec == "2" || sec == "1"){
                    textViewCount.setTextColor(Color.RED);
                }
                else{
                    textViewCount.setTextColor(Color.WHITE);
                }


                textViewCount.setText(Objects.toString((millisUntilFinished / 1000),null));

            }

            public void onFinish() {
                cancel();
                finish();
                startActivity(new Intent(getApplicationContext(),GameOver.class).putExtra("Score",String.valueOf((HighScore))));
            }
        }.start();


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arr = new Integer[(int)dataSnapshot.getChildrenCount()];

                for (int i = 0; i < arr.length; i++) {
                    arr[i] = i+1;
                }
                Collections.shuffle(Arrays.asList(arr));

                Integer[] cpyArr = new Integer[(arr.length+1)];
                System.arraycopy(arr, 0, cpyArr, 0, arr.length);

                arr = cpyArr;
                arr[arr.length-1]=arr.length;

                ds=dataSnapshot;
                setQuestion(dataSnapshot,arr[counter]);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void setQuestion(DataSnapshot dataSnapshot,int counter){

        try{
            Picasso.with(getApplicationContext()).load(dataSnapshot.child("Question_"+counter).child("QuestionImg").getValue().toString()).into(image);

            answer1.setText(dataSnapshot.child("Question_"+counter).child("Ans1").getValue().toString());
            answer2.setText(dataSnapshot.child("Question_"+counter).child("Ans2").getValue().toString());
            answer3.setText(dataSnapshot.child("Question_"+counter).child("Ans3").getValue().toString());

            CorrectAns = dataSnapshot.child("Question_"+counter).child("CorrectAns").getValue().toString();

            cdt.start();

        }catch (NullPointerException e){

            cdt.cancel();
            finish();
            startActivity(new Intent(this,Winner.class).putExtra("Score",String.valueOf((HighScore)*2)));

        }


    }

    @Override
    public void onClick(View view) {

        intent.putExtra("Score",String.valueOf(HighScore));

        if(view == answer1){
            if(answer1.getText().toString().equals(CorrectAns)){

                HighScore += Integer.parseInt(sec);
                counter++;

                setQuestion(ds,arr[counter]);
            }
            else{

                cdt.cancel();
                finish();
                startActivity(intent);
            }

        }

        if(view == answer2){
            if(answer2.getText().toString().equals(CorrectAns)){

                HighScore += Integer.parseInt(sec);
                counter++;
                setQuestion(ds,arr[counter]);
            }
            else{
                cdt.cancel();
                finish();
                startActivity(intent);
            }

        }


        if(view == answer3){
            if(answer3.getText().toString().equals(CorrectAns)){

                HighScore += Integer.parseInt(sec);
                counter++;
                setQuestion(ds,arr[counter]);
            }
            else{
                cdt.cancel();
                finish();
                startActivity(intent);
            }

        }

    }
}
