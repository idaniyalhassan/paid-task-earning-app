package com.example.paidtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mhadikz.toaster.Toaster;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    CardView cardView;
    TextView textFront, textBack, flips;
    Integer count = 2;
    AdView mAdview;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        cardView = findViewById(R.id.cardView);
        textFront= findViewById(R.id.textFront);
        textBack = findViewById(R.id.textBack);
        flips= findViewById(R.id.flipcount);

        AdView adView = new AdView(GameActivity.this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-1179961504182874/9183396465");
        mAdview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);

        flips.setText("Flips Left: "+count);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count>0){
                    flipCard();
                    count--;
                    flips.setText("Flips Left: "+count);
                }
                else {
                    new Toaster.Builder(GameActivity.this)
                            .setTitle("No Flips Left")
                            .setDescription("Come Tommorrow for more flips.")
                            .setDuration(Toaster.LENGTH_SHORT)
                            .setStatus(Toaster.Status.ERROR)
                            .show();                }
            }
        });
    }
    private void flipCard() {
        cardView.animate()
                .rotationYBy(180f)
                .setDuration(500)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        int randomNumber = new Random().nextInt(100);
                        textBack.setText(String.valueOf(randomNumber));
                        new Toaster.Builder(GameActivity.this)
                                .setTitle("Congrats!")
                                .setDescription(randomNumber+" Points added successfully.")
                                .setDuration(Toaster.LENGTH_SHORT)
                                .setStatus(Toaster.Status.SUCCESS)
                                .show();
                        textBack.setVisibility(View.VISIBLE);
                        int newPoints = randomNumber;
                        cardView.setCardBackgroundColor(getResources().getColor(R.color.green));
                        PointManager.addPoints(GameActivity.this, newPoints);
                        textFront.setVisibility(View.GONE);
                        cardView.setRotationY(0);
                    }
                })
                .start();
    }
}