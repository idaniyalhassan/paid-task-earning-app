package com.example.paidtask;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.mhadikz.toaster.Toaster;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;

public class VideoActivity extends AppCompatActivity {
    Button button1, button2, button3, button4, button5;
    private boolean bt1= true;
    private boolean bt2= true;
    private boolean bt3= true;
    private boolean bt4= true;
    private boolean bt5= true;
    private RewardedAd rewardedAd;
    AdView mAdview;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        button1= findViewById(R.id.taskButton1);
        button2= findViewById(R.id.taskButton2);
        button3= findViewById(R.id.taskButton3);
        button4= findViewById(R.id.taskButton4);
        button5= findViewById(R.id.taskButton5);

        AdView adView = new AdView(VideoActivity.this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-1179961504182874/9183396465");
        mAdview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);

        loadrewarded();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt1) {
                    loadrewarded();
                    if (rewardedAd != null) {
                        rewardedAd.show(VideoActivity.this, new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                            }
                        });
                        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                rewardedAd = null;
                                new Toaster.Builder(VideoActivity.this)
                                        .setTitle("Congrats!")
                                        .setDescription("75 Points added successfully.")
                                        .setDuration(Toaster.LENGTH_SHORT)
                                        .setStatus(Toaster.Status.SUCCESS)
                                        .show();
                                button1.setBackgroundColor(getResources().getColor(R.color.red));
                                bt1 = false;
                                int newPoints = 75;
                                PointManager.addPoints(VideoActivity.this, newPoints);
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                rewardedAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {

                            }
                        });
                    } else {
                        new Toaster.Builder(VideoActivity.this)
                                .setTitle("Congrats!")
                                .setDescription("75 Points added successfully.")
                                .setDuration(Toaster.LENGTH_SHORT)
                                .setStatus(Toaster.Status.SUCCESS)
                                .show();
                        button1.setBackgroundColor(getResources().getColor(R.color.red));
                        bt1 = false;
                        int newPoints = 75;
                        PointManager.addPoints(VideoActivity.this, newPoints);
                    }
                }
                else {
                    new Toaster.Builder(VideoActivity.this)
                            .setTitle("Come Tommorrow")
                            .setDescription("You have already got your reward today!")
                            .setDuration(Toaster.LENGTH_SHORT)
                            .setStatus(Toaster.Status.INFO)
                            .show();                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt2){

                        loadrewarded();
                        if (rewardedAd != null) {
                            rewardedAd.show(VideoActivity.this, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                                }
                            });
                            rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdClicked() {
                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    rewardedAd = null;
                                    new Toaster.Builder(VideoActivity.this)
                                            .setTitle("Congrats!")
                                            .setDescription("75 Points added successfully.")
                                            .setDuration(Toaster.LENGTH_SHORT)
                                            .setStatus(Toaster.Status.SUCCESS)
                                            .show();
                                    button2.setBackgroundColor(getResources().getColor(R.color.red));
                                    bt2 = false;
                                    int newPoints = 75;
                                    PointManager.addPoints(VideoActivity.this, newPoints);
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    rewardedAd = null;
                                }

                                @Override
                                public void onAdImpression() {
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {

                                }
                            });
                        } else {
                            new Toaster.Builder(VideoActivity.this)
                                    .setTitle("Congrats!")
                                    .setDescription("75 Points added successfully.")
                                    .setDuration(Toaster.LENGTH_SHORT)
                                    .setStatus(Toaster.Status.SUCCESS)
                                    .show();
                            button2.setBackgroundColor(getResources().getColor(R.color.red));
                            bt2 = false;
                            int newPoints = 75;
                            PointManager.addPoints(VideoActivity.this, newPoints);                        }
                    }
                    else {
                    new Toaster.Builder(VideoActivity.this)
                            .setTitle("Come Tommorrow")
                            .setDescription("You have already got your reward today!")
                            .setDuration(Toaster.LENGTH_SHORT)
                            .setStatus(Toaster.Status.INFO)
                            .show();                    }

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt3){
                        loadrewarded();
                        if (rewardedAd != null) {
                            rewardedAd.show(VideoActivity.this, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                                }
                            });
                            rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdClicked() {
                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    rewardedAd = null;
                                    new Toaster.Builder(VideoActivity.this)
                                            .setTitle("Congrats!")
                                            .setDescription("75 Points added successfully.")
                                            .setDuration(Toaster.LENGTH_SHORT)
                                            .setStatus(Toaster.Status.SUCCESS)
                                            .show();
                                    button3.setBackgroundColor(getResources().getColor(R.color.red));
                                    bt3 = false;
                                    int newPoints = 75;
                                    PointManager.addPoints(VideoActivity.this, newPoints);
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    rewardedAd = null;
                                }

                                @Override
                                public void onAdImpression() {
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {

                                }
                            });
                        } else {
                            new Toaster.Builder(VideoActivity.this)
                                    .setTitle("Congrats!")
                                    .setDescription("75 Points added successfully.")
                                    .setDuration(Toaster.LENGTH_SHORT)
                                    .setStatus(Toaster.Status.SUCCESS)
                                    .show();
                            button3.setBackgroundColor(getResources().getColor(R.color.red));
                            bt3 = false;
                            int newPoints = 75;
                            PointManager.addPoints(VideoActivity.this, newPoints);                        }
                    }
                    else {
                    new Toaster.Builder(VideoActivity.this)
                            .setTitle("Come Tommorrow")
                            .setDescription("You have already got your reward today!")
                            .setDuration(Toaster.LENGTH_SHORT)
                            .setStatus(Toaster.Status.INFO)
                            .show();                    }
                }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt4){
                        loadrewarded();
                        if (rewardedAd != null) {
                            rewardedAd.show(VideoActivity.this, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                                }
                            });
                            rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdClicked() {
                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    rewardedAd = null;
                                    new Toaster.Builder(VideoActivity.this)
                                            .setTitle("Congrats!")
                                            .setDescription("75 Points added successfully.")
                                            .setDuration(Toaster.LENGTH_SHORT)
                                            .setStatus(Toaster.Status.SUCCESS)
                                            .show();
                                    button4.setBackgroundColor(getResources().getColor(R.color.red));
                                    bt4 = false;
                                    int newPoints = 75;
                                    PointManager.addPoints(VideoActivity.this, newPoints);
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    rewardedAd = null;
                                }

                                @Override
                                public void onAdImpression() {
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {

                                }
                            });
                        } else {
                            new Toaster.Builder(VideoActivity.this)
                                    .setTitle("Congrats!")
                                    .setDescription("75 Points added successfully.")
                                    .setDuration(Toaster.LENGTH_SHORT)
                                    .setStatus(Toaster.Status.SUCCESS)
                                    .show();
                            button4.setBackgroundColor(getResources().getColor(R.color.red));
                            bt4 = false;
                            int newPoints = 75;
                            PointManager.addPoints(VideoActivity.this, newPoints);                        }
                    }
                    else {
                    new Toaster.Builder(VideoActivity.this)
                            .setTitle("Come Tommorrow")
                            .setDescription("You have already got your reward today!")
                            .setDuration(Toaster.LENGTH_SHORT)
                            .setStatus(Toaster.Status.INFO)
                            .show();                    }
                }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt5){
                        loadrewarded();
                        if (rewardedAd != null) {
                            rewardedAd.show(VideoActivity.this, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                                }
                            });
                            rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdClicked() {
                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    rewardedAd = null;
                                    new Toaster.Builder(VideoActivity.this)
                                            .setTitle("Congrats!")
                                            .setDescription("75 Points added successfully.")
                                            .setDuration(Toaster.LENGTH_SHORT)
                                            .setStatus(Toaster.Status.SUCCESS)
                                            .show();
                                    button5.setBackgroundColor(getResources().getColor(R.color.red));
                                    bt5 = false;
                                    int newPoints = 75;
                                    PointManager.addPoints(VideoActivity.this, newPoints);
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    rewardedAd = null;
                                }

                                @Override
                                public void onAdImpression() {
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {

                                }
                            });
                        } else {
                            new Toaster.Builder(VideoActivity.this)
                                    .setTitle("Congrats!")
                                    .setDescription("75 Points added successfully.")
                                    .setDuration(Toaster.LENGTH_SHORT)
                                    .setStatus(Toaster.Status.SUCCESS)
                                    .show();
                            button5.setBackgroundColor(getResources().getColor(R.color.red));
                            bt5 = false;
                            int newPoints = 75;
                            PointManager.addPoints(VideoActivity.this, newPoints);
                        }
                    }
                    else {
                    new Toaster.Builder(VideoActivity.this)
                            .setTitle("Come Tommorrow")
                            .setDescription("You have already got your reward today!")
                            .setDuration(Toaster.LENGTH_SHORT)
                            .setStatus(Toaster.Status.INFO)
                            .show();
                    }
                }
        });
    }
    private void loadrewarded() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-1179961504182874/1374841598",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                        rewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                    }
                });
    }
}