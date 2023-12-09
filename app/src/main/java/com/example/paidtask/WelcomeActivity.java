package com.example.paidtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    Button login_bt, signup_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        login_bt = findViewById(R.id.loginpage);
        signup_bt = findViewById(R.id.signuppage);
        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        signup_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(WelcomeActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}