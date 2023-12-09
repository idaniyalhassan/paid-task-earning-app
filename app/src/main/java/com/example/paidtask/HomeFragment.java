package com.example.paidtask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.mhadikz.toaster.Toaster;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class HomeFragment extends Fragment implements View.OnClickListener {
    Button redeem;
    CardView cv1, cv2, cv3, cv4, cv5;
    SwipeRefreshLayout swipeRefreshLayout;
    private TextView pointsTextView;
    private  InterstitialAd mInterstitialAd;
    private AdView mAdview;
    private RewardedAd rewardedAd;
    private static final String PREF_NAME = "MyPrefs";
    private static final String LAST_CLICK_DATE_KEY_PREFIX = "lastClickDate_";
    private static final String LAST_CLICK_BUTTON_ID_KEY_PREFIX = "lastClickButtonId_";

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        redeem = view.findViewById(R.id.redeem);
        cv1 = view.findViewById(R.id.card1);
        cv2 = view.findViewById(R.id.card2);
        cv3 = view.findViewById(R.id.card3);
        cv4 = view.findViewById(R.id.card4);
        cv5 = view.findViewById(R.id.watchAds);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        pointsTextView = view.findViewById(R.id.totalpoints);

        loadint();
        loadrewarded();


        AdView adView = new AdView(getActivity());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-1179961504182874/9183396465");
        mAdview = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);

        int currentPoints = PointManager.loadPoints(requireContext());
        updatePointsDisplay(currentPoints);
        updatePointsDisplay(PointManager.loadPoints(requireContext()));

        cv1.setOnClickListener((View.OnClickListener) this);
        cv2.setOnClickListener((View.OnClickListener) this);
        cv3.setOnClickListener((View.OnClickListener) this);
        cv4.setOnClickListener((View.OnClickListener) this);
        cv5.setOnClickListener((View.OnClickListener) this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int currentPoints = PointManager.loadPoints(requireContext());
                updatePointsDisplay(currentPoints);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RedeemActivity.class);
                startActivity(intent);
            }
        });
                return view;
            }
            @Override
            public void onClick(@NonNull View view) {
                int buttonId = view.getId();

                if (isTodayFirstClick(buttonId)) {
                    if (buttonId == R.id.card1) {
                        task1(buttonId);
                    } else if (buttonId == R.id.card2) {
                        task2(buttonId);
                    } else if (buttonId == R.id.card3) {
                        task3(buttonId);
                    } else if (buttonId == R.id.card4) {
                        task4(buttonId);
                    } else if (buttonId == R.id.watchAds) {
                        task5(buttonId);
                    }
                } else {
                    new Toaster.Builder(getActivity())
                            .setTitle("Come Tommorrow")
                            .setDescription("You have already got your reward today!")
                            .setDuration(Toaster.LENGTH_SHORT)
                            .setStatus(Toaster.Status.INFO)
                            .show();
                }
            }

            private void task1(final int buttonId) {
                if (isTodayFirstClick(buttonId)) {
                    loadrewarded();
                    if (rewardedAd != null) {
                        rewardedAd.show(getActivity(), new OnUserEarnedRewardListener() {
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
                                new Toaster.Builder(getActivity())
                                        .setTitle("Congrats!")
                                        .setDescription("100 Points added successfully.")
                                        .setDuration(Toaster.LENGTH_SHORT)
                                        .setStatus(Toaster.Status.SUCCESS)
                                        .show();
                                PointManager.addPoints(requireContext(), 100);
                                updatePointsDisplay(PointManager.loadPoints(requireContext()));
                                saveAdDismissDate(buttonId);
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
                        new Toaster.Builder(getActivity())
                                .setTitle("Congrats!")
                                .setDescription("100 Points added successfully.")
                                .setDuration(Toaster.LENGTH_SHORT)
                                .setStatus(Toaster.Status.SUCCESS)
                                .show();
                        PointManager.addPoints(requireContext(), 100);
                        updatePointsDisplay(PointManager.loadPoints(requireContext()));
                        saveAdDismissDate(buttonId);
                    }
                } else {
                    new Toaster.Builder(getActivity())
                            .setTitle("Come Tommorrow")
                            .setDescription("You have already got your reward today!")
                            .setDuration(Toaster.LENGTH_SHORT)
                            .setStatus(Toaster.Status.INFO)
                            .show();
                }
            }

            private void task2(final int buttonId) {
                if (isTodayFirstClick(buttonId)) {
                    loadrewarded();
                    if (rewardedAd != null) {
                        rewardedAd.show(getActivity(), new OnUserEarnedRewardListener() {
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
                                new Toaster.Builder(getActivity())
                                        .setTitle("Congrats!")
                                        .setDescription("50 Points added successfully.")
                                        .setDuration(Toaster.LENGTH_SHORT)
                                        .setStatus(Toaster.Status.SUCCESS)
                                        .show();
                                PointManager.addPoints(requireContext(), 100);
                                updatePointsDisplay(PointManager.loadPoints(requireContext()));
                                saveAdDismissDate(buttonId);
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
                        new Toaster.Builder(getActivity())
                                .setTitle("Congrats!")
                                .setDescription("50 Points added successfully.")
                                .setDuration(Toaster.LENGTH_SHORT)
                                .setStatus(Toaster.Status.SUCCESS)
                                .show();
                        PointManager.addPoints(requireContext(), 100);
                        updatePointsDisplay(PointManager.loadPoints(requireContext()));
                        saveAdDismissDate(buttonId);
                    }
                } else {
                    new Toaster.Builder(getActivity())
                            .setTitle("Come Tommorrow")
                            .setDescription("You have already got your reward today!")
                            .setDuration(Toaster.LENGTH_SHORT)
                            .setStatus(Toaster.Status.INFO)
                            .show();
                }
            }

            private void task3(final int buttonId) {
                if (isTodayFirstClick(buttonId)) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(getActivity());

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {

                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Intent intent = new Intent(getActivity(), SpinActivity.class);
                                startActivity(intent);
                                loadint();
                                saveAdDismissDate(buttonId);
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {

                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.

                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.

                            }
                        });

                    } else {
                        Intent intent = new Intent(getActivity(), SpinActivity.class);
                        startActivity(intent);
                        loadint();
                        saveAdDismissDate(buttonId);
                    }
                } else {
                    new Toaster.Builder(getActivity())
                            .setTitle("Come Tommorrow")
                            .setDescription("You have already got your reward today!")
                            .setDuration(Toaster.LENGTH_SHORT)
                            .setStatus(Toaster.Status.INFO)
                            .show();
                }
            }
            private void task4(final int buttonId) {
                if (isTodayFirstClick(buttonId)) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(getActivity());

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {

                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Intent intent = new Intent(getActivity(), GameActivity.class);
                                startActivity(intent);
                                loadint();
                                saveAdDismissDate(buttonId);
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {

                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.

                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.

                            }
                        });

                    } else {
                        Intent intent = new Intent(getActivity(), GameActivity.class);
                        startActivity(intent);
                        loadint();
                        saveAdDismissDate(buttonId);
                    }
                } else {
                    new Toaster.Builder(getActivity())
                            .setTitle("Come Tommorrow")
                            .setDescription("You have already got your reward today!")
                            .setDuration(Toaster.LENGTH_SHORT)
                            .setStatus(Toaster.Status.INFO)
                            .show();
                }
            }
            private void task5(final int buttonId) {
                if (isTodayFirstClick(buttonId)) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(getActivity());

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {

                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Intent intent = new Intent(getActivity(), VideoActivity.class);
                                startActivity(intent);
                                loadint();
                                saveAdDismissDate(buttonId);
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {

                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.

                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.

                            }
                        });

                    } else {
                        Intent intent = new Intent(getActivity(), VideoActivity.class);
                        startActivity(intent);
                        loadint();
                        saveAdDismissDate(buttonId);
                    }
                } else {
                    new Toaster.Builder(getActivity())
                            .setTitle("Come Tommorrow")
                            .setDescription("You have already got your reward today!")
                            .setDuration(Toaster.LENGTH_SHORT)
                            .setStatus(Toaster.Status.INFO)
                            .show();
                }
            }
    private void loadrewarded(){
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(getActivity(), "ca-app-pub-1179961504182874/1374841598",
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
    public void loadint(){
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(getActivity(),"ca-app-pub-1179961504182874/6742734252", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                        mInterstitialAd = null;
                    }
                });
    }
    private boolean  isTodayFirstClick(int buttonId) {
        SharedPreferences preferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String lastClickDateKey = LAST_CLICK_DATE_KEY_PREFIX + buttonId;
        String lastClickDate = preferences.getString(lastClickDateKey, "");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        return !currentDate.equals(lastClickDate);
    }
    private void  saveAdDismissDate(int buttonId) {
        SharedPreferences preferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        String lastClickDateKey = LAST_CLICK_DATE_KEY_PREFIX + buttonId;
        String lastClickButtonIdKey = LAST_CLICK_BUTTON_ID_KEY_PREFIX + buttonId;

        editor.putString(lastClickDateKey, currentDate);
        editor.putInt(lastClickButtonIdKey, buttonId);
        editor.apply();
    }

    private void updatePointsDisplay(int points) {
        pointsTextView.setText("Points: "+points);
    }
}