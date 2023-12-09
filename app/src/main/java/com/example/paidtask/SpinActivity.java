package com.example.paidtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mhadikz.toaster.Toaster;

import java.util.Random;

public class SpinActivity extends AppCompatActivity {

    public static final String[] sectors = {"0", "5", "10", "15", "20", "25","30","35","40","45","50","100"};
    public static final int [] sectorDegrees = new int [sectors.length];
    public static final Random random= new Random();
    private int degree = 0;
    private boolean isSpinning = false;
    private  int spinCount = 5;
    ImageView wheel;
    CardView spin_bt;
    TextView spinc;
    AdView mAdview;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);
         wheel = findViewById(R.id.wheel);
        spin_bt = findViewById(R.id.spinbutton);
        spinc = findViewById(R.id.spinscount);

        AdView adView = new AdView(SpinActivity.this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-1179961504182874/9183396465");
        mAdview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);

        spinc.setText("Spins Left: "+spinCount);

        getDegrees();
        spin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinCount >0 && !isSpinning){
                    spin();
                    isSpinning=true;
                    spinCount = spinCount-1;
                    spinc.setText("Spins Left: "+spinCount);
                }
                else {
                    new Toaster.Builder(SpinActivity.this)
                            .setTitle("No Spins Left")
                            .setDescription("Come Tommorrow for more spins")
                            .setDuration(Toaster.LENGTH_SHORT)
                            .setStatus(Toaster.Status.ERROR)
                            .show();
                }
            }
        });



    }
    private void spin(){
        degree = random.nextInt(sectors.length-1);
        RotateAnimation animation= new RotateAnimation(0, (360* sectors.length)+sectorDegrees[degree],
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Toaster.Builder(SpinActivity.this)
                        .setTitle("Congrats!")
                        .setDescription(sectors[sectors.length-(degree+1)]+" Points added successfully.")
                        .setDuration(Toaster.LENGTH_SHORT)
                        .setStatus(Toaster.Status.SUCCESS)
                        .show();
                isSpinning = false;
                showDialogBox();
                int newPoints = Integer.parseInt(sectors[sectors.length-(degree+1)]);
                PointManager.addPoints(SpinActivity.this, newPoints);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        wheel.startAnimation(animation);
    }
    private void getDegrees(){
        int sectorDegree = 360/ sectors.length;
        for (int i=0; i < sectors.length; i++){
            sectorDegrees[i] = (i+1)* sectorDegree;
        }
    }
    public void showDialogBox(){

    }
}