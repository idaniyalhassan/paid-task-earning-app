package com.example.paidtask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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


public class LoginActivity extends AppCompatActivity {

    TextView textView1, textView2;
    EditText editText1, editText2;
    Button login;
    ImageView imageView;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imageView = findViewById(R.id.back_bt);
        textView1 = findViewById(R.id.pswd_fgt);
        textView2 = findViewById(R.id.tosignup);
        editText1 = findViewById(R.id.email_login);
        editText2 = findViewById(R.id.pswd_login);
        login = findViewById(R.id.login_bt);
        mAuth = FirebaseAuth.getInstance();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = editText1.getText().toString().trim();
                String Password = editText2.getText().toString().trim();
                if (Email.isEmpty()) {
                    editText1.setError("Required");
                    return;
                }
                if (Password.isEmpty()) {
                    editText2.setError("Required");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    editText1.setError("Invalid Email Format");
                    return;
                }
                progressLoad();
                mAuth.signInWithEmailAndPassword(Email,Password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            dismissLoad();
                                            Toast.makeText(LoginActivity.this, "Email or Password is Incorrect", Toast.LENGTH_SHORT).show();
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