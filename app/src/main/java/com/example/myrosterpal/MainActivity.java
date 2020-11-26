package com.example.myrosterpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://suragch.medium.com/how-to-add-a-bottom-navigation-bar-in-android-958ed728ef6c
        //Article sited above used as a source for bottom navigation code
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    //Each case runs depending on which option is pressed on the nav bar
                    case R.id.action_home:
                        //Intents switch to the next activity, this one will just reload the Main Activity
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        break;
                    case R.id.action_staff:
                        startActivity(new Intent(MainActivity.this, StaffActivity.class));
                        break;
                    case R.id.action_roster:
                        startActivity(new Intent(MainActivity.this, RosterActivity.class));
                        break;
                    case R.id.action_request:
                        startActivity(new Intent(MainActivity.this, RequestActivity.class));
                        break;
                    case R.id.action_settings:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;
                }
                return true;
            }
        });
    }
}