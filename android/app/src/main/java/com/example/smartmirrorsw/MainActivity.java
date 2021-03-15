package com.example.smartmirrorsw;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);


    }




    public void openMenuItem(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mediaFragment)
        {
            Intent intent = new Intent(this,YouTube.class);
            startActivity(intent);

        }

        else if (id == R.id.weatherFragment)
        {
            Intent intent = new Intent(this, Weather.class);
            startActivity(intent);

        }

        else if (id == R.id.settingsFragment)
        {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }

    }

    public void setLocation(View view) {
    }

    public void hhhh(View view) {
    }
}


