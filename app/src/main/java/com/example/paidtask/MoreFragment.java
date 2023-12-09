package com.example.paidtask;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;


public class MoreFragment extends Fragment {
    CardView historyCard, logout, privacy, invite, rate ;
    FirebaseAuth firebaseAuth;
    AdView mAdview;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View view= inflater.inflate(R.layout.fragment_more, container, false);
     historyCard = view.findViewById(R.id.history);
     logout= view.findViewById(R.id.logout_cv);
     privacy= view.findViewById(R.id.privacy);
     invite= view.findViewById(R.id.invite);
     rate= view.findViewById(R.id.rate);
     firebaseAuth= FirebaseAuth.getInstance();

        AdView adView = new AdView(getActivity());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-1179961504182874/9183396465");
        mAdview = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);


     historyCard.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getActivity(), HistoryActivity.class);
             startActivity(intent);
         }
     });
     logout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             showDialogBox();
         }
     });
     privacy.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(getActivity(), PrivacyActivity.class);
             startActivity(intent);
         }
     });
     invite.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(getActivity(), InviteActivity.class);
             startActivity(intent);

         }
     });
     rate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(getActivity(), RateActivity.class);
             startActivity(intent);
         }
     });
        return view;
    }
    public void showDialogBox(){
        if (isAdded() && getActivity() != null && !getActivity().isFinishing()) {
            PopupDialog.getInstance(getActivity())
                    .setStyle(Styles.STANDARD)
                    .setHeading("Logout")
                    .setDescription("Are you sure you want to logout?" +
                            " This action cannot be undone")
                    .setPositiveButtonBackground(R.drawable.button_shape)
                    .setPopupDialogIcon(R.drawable.logout)
                    .setCancelable(false)
                    .showDialog(new OnDialogButtonClickListener() {
                        @Override
                        public void onPositiveClicked(Dialog dialog) {
                            super.onPositiveClicked(dialog);
                            progressLoad();
                            firebaseAuth.signOut();
                            dismissLoad();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onNegativeClicked(Dialog dialog) {
                            super.onNegativeClicked(dialog);
                        }
                    });
        }
        else {
            Toast.makeText(getActivity(), "Please Try Later", Toast.LENGTH_SHORT).show();
        }
    }
    public void progressLoad(){
        PopupDialog.getInstance(getActivity())
                .setStyle(Styles.LOTTIE_ANIMATION)
                .setLottieRawRes(R.raw.loadvip)
                .setCancelable(false)
                .showDialog();
    }
    public void dismissLoad(){
        PopupDialog.getInstance(getActivity()).dismissDialog();
    }

}