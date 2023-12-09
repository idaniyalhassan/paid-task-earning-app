package com.example.paidtask;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;



import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {
    SmoothBottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomBar = findViewById(R.id.bottombar);

        HomeFragment homeFragment= new HomeFragment();
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.empty_frame, homeFragment);
        fragmentTransaction.commit();

        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                if (i==0){
                    FragmentManager fragmentManager= getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.empty_frame, new HomeFragment());
                    fragmentTransaction.commit();
                }
                if (i==1){
                    FragmentManager fragmentManager= getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.empty_frame, new MoreFragment());
                    fragmentTransaction.commit();
                }
                return false;
            }
        });
    }
}