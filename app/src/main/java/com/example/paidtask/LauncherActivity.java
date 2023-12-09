package com.example.paidtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LauncherActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        setContentView(R.layout.activity_launcher);
        firebaseAuth= FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();
        Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user != null) {
                    Intent i = new Intent(LauncherActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(LauncherActivity.this, WelcomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        },2000);
    }
}