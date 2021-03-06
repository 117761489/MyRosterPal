package com.example.myrosterpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        //https://suragch.medium.com/how-to-add-a-bottom-navigation-bar-in-android-958ed728ef6c
        //Article sited above used as a source for bottom navigation code
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(RequestActivity.this, MainActivity.class));
                        break;

                    case R.id.action_staff:
                        startActivity(new Intent(RequestActivity.this, StaffActivity.class));
                        break;
                    case R.id.action_roster:
                        startActivity(new Intent(RequestActivity.this, RosterActivity.class));
                        break;
                    case R.id.action_request:
                        startActivity(new Intent(RequestActivity.this, RequestActivity.class));
                        break;
                    case R.id.action_settings:
                        startActivity(new Intent(RequestActivity.this, SettingsActivity.class));
                        break;
                }
                return true;
            }
        });
    }
}

