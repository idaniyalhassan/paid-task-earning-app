package com.example.paidtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.jar.Attributes;

public class HistoryActivity extends AppCompatActivity {
    LinearLayout record;
    TextView data, pay, name, num, amount;
    private static final String PREFS_NAME = "MyPrefsFile";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        record= findViewById(R.id.card123);
        data= findViewById(R.id.historytext);
        pay= findViewById(R.id.method);
        name = findViewById(R.id.accname);
        amount= findViewById(R.id.amounttp1);
        num= findViewById(R.id.accnum);
        SharedPreferences sharedPreferences = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String Amount = readFromSharedPreferences("PKR");
        String Payment = readFromSharedPreferences("Payment");
        String AccNum = readFromSharedPreferences("Accnum");
        String AccNam = readFromSharedPreferences("Accname");
        String check = "PKR";
        if (sharedPreferences.contains(check)) {
            record.setVisibility(View.VISIBLE);
            amount.setText(Amount + " Rs");
            name.setText(AccNam);
            num.setText(AccNum);
            pay.setText(Payment);
            data.setVisibility(View.GONE);
        }
        else
        {
            record.setVisibility(View.GONE);
            data.setVisibility(View.VISIBLE);
        }
    }
    private String readFromSharedPreferences(String key) {
        Context context = this;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}