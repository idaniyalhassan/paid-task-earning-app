package com.example.paidtask;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class RedeemActivity extends AppCompatActivity {
    TextView totaltp;
    EditText et1, et2, et3;
    Spinner spinner;
    Button submit;
    private static final String PREFS_NAME = "MyPrefsFile";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);
        totaltp = findViewById(R.id.totaltp);
        et1= findViewById(R.id.points);
        et2= findViewById(R.id.acc_name);
        spinner= findViewById(R.id.spinner);
        et3= findViewById(R.id.acc_num);
        submit = findViewById(R.id.submit_bt);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        int currentPoints = PointManager.loadPoints(RedeemActivity.this);

        updatePointsDisplay(currentPoints);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pt = et1.getText().toString();
                String AccName = et2.getText().toString();
                String AccNum = et3.getText().toString();
                String Payment = spinner.getSelectedItem().toString();

                if (Pt.isEmpty()){
                et1.setError("Required");
                return;
                }
                Integer Points = Integer.valueOf(Pt);
                if (Points > currentPoints ){
                    et1.setError("Insufficient Points");
                    return;
                }
                if (Points == null ){
                    et1.setError("Required");
                    return;
                }
                if (Points < 10000) {
                    et1.setError("Minimum 10,000 Points");
                    return;
                }
                if (AccName.isEmpty()) {
                    et2.setError("Required");
                    return;
                }
                if (AccNum.isEmpty()) {
                    et3.setError("Required");
                    return;
                }

                double tp = Double.parseDouble(Points.toString());
                float pkr = (float) (tp / 275);
                String formattedPkr = String.format("%.2f", pkr);

                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
                editor.putString("PKR", String.valueOf(formattedPkr));
                editor.putString("Accname", AccName);
                editor.putString("Accnum", AccNum);
                editor.putString("Payment", Payment);
                editor.putString("Points", String.valueOf(Points));
                editor.apply();

                ConfirmFragment confirmFragment=new ConfirmFragment();
                confirmFragment.show(getSupportFragmentManager(), confirmFragment.getTag());

            }
        });
    }
    private void updatePointsDisplay(int points) {
        totaltp.setText("Points: "+ points);
    }
}