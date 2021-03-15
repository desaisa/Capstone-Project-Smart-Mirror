package com.example.smartmirrorsw;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmirrorsw.security.BluetoothService;

public class Weather extends AppCompatActivity {
    private final String TAG = "WEATHER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //calling parent class to recall the app's last state
        super.onCreate(savedInstanceState);

        //method to fill the activity that is launched with  the activity_main.xml layout file
        setContentView(R.layout.fragment_weather);
    }

    public void setLocation(View view) {
        EditText location;
        location = (EditText) findViewById(R.id.location);
        CharSequence location_text = location.getText();
        String text = location_text.toString();
        System.out.println(text);

        try {
            BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
            connectedThread.write("WEATHER:" +text);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Read from rpi
        try {
            String weather_result = null;
            while (weather_result == null) {
                weather_result = BluetoothService.getInput();
            }
            Log.d(TAG, weather_result);
            BluetoothService.setInputToNull();
        } catch (Exception e) {
            Log.e(TAG, "Unable to read response" + e.getMessage());
        }
    }
}
