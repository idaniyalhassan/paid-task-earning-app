package com.example.paidtask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;

public class ForgetActivity extends AppCompatActivity {
    EditText reset_et;
    Button reset_bt;
    FirebaseAuth authen;
    String Resetmail;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        reset_bt= findViewById(R.id.forgetbt);
        reset_et= findViewById(R.id.forgetet);
        authen= FirebaseAuth.getInstance();
        reset_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resetmail = reset_et.getText().toString().trim();
                if (Resetmail.isEmpty()){
                    reset_et.setError("Enter your Email");
                    return;
                }
                progressLoad();
                authen.sendPasswordResetEmail(Resetmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    dismissLoad();
                                    Toast.makeText(ForgetActivity.this, "Reset Password Link has been sent to Registered Email.", Toast.LENGTH_LONG).show();
                                    reset_et.setText(null);

                                }
                                else {
                                    dismissLoad();
                                    Toast.makeText(ForgetActivity.this, "Opps! Something went wrong", Toast.LENGTH_SHORT).show();
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