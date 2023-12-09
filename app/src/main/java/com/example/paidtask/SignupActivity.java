package com.example.paidtask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;

public class SignupActivity extends AppCompatActivity {
    ImageView iv;
    EditText text1, text2;
    TextView textView;
    Button bt1;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        iv= findViewById(R.id.back_hh);
        text1= findViewById(R.id.emailsignup);
        text2= findViewById(R.id.pswdsignup);
        textView= findViewById(R.id.tologin);
        bt1= findViewById(R.id.create);
        auth= FirebaseAuth.getInstance();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = text1.getText().toString().trim();;
                String Password = text2.getText().toString().trim();

                if (Email.isEmpty()) {
                    text1.setError("Required");
                    return;
                }
                if (Password.isEmpty()) {
                    text2.setError("Required");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    text1.setError("Invalid Email Format");
                    return;
                }
                progressLoad();
                auth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent ic = new Intent(SignupActivity.this, MainActivity.class);
                                    startActivity(ic);
                                    dismissLoad();
                                    finish();
                                    Toast.makeText(SignupActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    dismissLoad();
                                    Toast.makeText(SignupActivity.this, "Ops! Something went wrong.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    public void progressLoad(){
        PopupDialog.getInstance(this)
                .setStyle(Styles.LOTTIE_ANIMATION)
                .setLottieRawRes(R.raw.loadvip)
                .setCancelable(false)
                .showDialog();
    }
    public void dismissLoad(){
        PopupDialog.getInstance(this).dismissDialog();
    }
}