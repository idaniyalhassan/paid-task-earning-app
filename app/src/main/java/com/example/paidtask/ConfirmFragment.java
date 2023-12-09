package com.example.paidtask;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;


public class ConfirmFragment extends BottomSheetDialogFragment {
    TextView textViewAm, textViewAcc;
    Button button_confirm;
    private static final String PREFS_NAME = "MyPrefsFile";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_confirm, container, false);
        textViewAm = view.findViewById(R.id.rupees);
        textViewAcc = view.findViewById(R.id.details);
        button_confirm = view.findViewById(R.id.confirm_button);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String Amount = sharedPreferences.getString("PKR", "");
        String Payment = sharedPreferences.getString("Payment", "");
        String AccNum= sharedPreferences.getString("Accnum", "");
        String AccNam= sharedPreferences.getString("Accname", "");
        String Points= sharedPreferences.getString("Points", "");

        if (Amount != null && Payment != null && AccNum != null && AccNam != null && Points != null) {
            textViewAm.setText(Amount);
            textViewAcc.setText(Payment + "\n" + AccNam + "\n" + AccNum);
        }
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              int  points = Integer.valueOf(Points);
              PointManager.minusToPoints(requireContext(), points);
                showSuccessDialog();

            }
        });
        return view;
    }
    public  void showSuccessDialog(){
        PopupDialog.getInstance(getActivity())
                .setStyle(Styles.SUCCESS)
                .setHeading("Well Done")
                .setDescription("You have successfully"+
                        " submitted the withdraw request!"+
                        " Check earn history for payment status.")
                .setCancelable(false)
                .setDismissButtonBackground(R.drawable.button_shape)
                .showDialog(new OnDialogButtonClickListener() {
                    @Override
                    public void onDismissClicked(Dialog dialog) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
    }
}