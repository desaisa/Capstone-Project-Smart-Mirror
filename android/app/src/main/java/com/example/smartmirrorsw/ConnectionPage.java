package com.example.smartmirrorsw;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.bluetooth.BluetoothAdapter;


import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmirrorsw.security.BluetoothConnection;


public class ConnectionPage extends AppCompatActivity {
    private final static int REQUEST_ENABLE_BT = 1;
    private final static String TAG = "BT Adapter ";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_main);


    }

    public void connect(View v){

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//get bt device hardware
        Log.d(TAG,"created");

        //if bluetooth on the device is disabled, shows a prompt that asks to enable bluetooth
        if(!bluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        while(!bluetoothAdapter.isEnabled()){}//this stops the code until there is an answer to the popup activated above, this stops the app from crashing

        //the device below is the bluetooth component of the rpi
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice("B8:27:EB:56:4A:A4");//the string is the mac address of the rpi
        Log.d(TAG,device.getName()+" found.");

        BluetoothConnection bluetoothConnection = new BluetoothConnection(device);
        bluetoothConnection.start();

        Intent intent = new Intent(this,LogInPage.class);
        startActivity(intent);
    }

}
