package com.example.smartmirrorsw;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.smartmirrorsw.security.BluetoothService;

public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    public Switch weather_switch;
    public Switch news_switch;
    public Switch youtube_switch;
    public Switch monitor_switch;
    private final String TAG = "Setting frag";
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*weather_switch.setChecked(true);
        try {
            BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
            connectedThread.write("SETWEATHER");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String weather_result = null;
            while (weather_result == null) {
                weather_result = BluetoothService.getInput();
            }
            if(weather_result.equals("1"))
                weather_switch.setChecked(true);
            Log.d(TAG, weather_result);
            BluetoothService.setInputToNull();
        } catch (Exception e) {
            Log.e(TAG, "Unable to read response" + e.getMessage());
        }*/
        return inflater.inflate(R.layout.fragment_settings, container, false);



    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (weather_switch.isChecked())
            {

                System.out.println("it works");
            }
            else{ //remove it}

    }
    }


}
