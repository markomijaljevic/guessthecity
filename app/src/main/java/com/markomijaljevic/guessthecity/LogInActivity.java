package com.markomijaljevic.guessthecity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextPass;
    private Button buttonLogInUser;
    private TextView textViewSingup;

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this,StartGame.class));
        }

        editTextEmail = (EditText)findViewById(R.id.EditTextEmail);
        editTextPass = (EditText)findViewById(R.id.EditTextPassword);
        buttonLogInUser = (Button)findViewById(R.id.ButtonLogInUser);
        textViewSingup = (TextView)findViewById(R.id.TextViewSignUp);

        buttonLogInUser.setOnClickListener(this);
        textViewSingup.setOnClickListener(this);


    }


    public void userLogin(){

        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(),StartGame.class));
                }
                else{
                    Toast.makeText(getApplicationContext(),"User does not exist",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    @Override
    public void onClick(View view) {

        if(view == buttonLogInUser){
            userLogin();
        }
        if(view == textViewSingup){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

    }
}
