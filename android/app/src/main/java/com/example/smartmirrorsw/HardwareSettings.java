package com.example.smartmirrorsw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HardwareSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hardware_settings);
    }

    public void logout (View v){
        setContentView(R.layout.login_main);

    }
}
