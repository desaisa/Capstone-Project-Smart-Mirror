package com.example.smartmirrorsw.security;


import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class BluetoothConnection extends Thread{
    private static BluetoothConnection btConn;
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    public String TAG = "Bluetooth Connection ";

    public BluetoothConnection(BluetoothDevice device){
        BluetoothSocket tmp = null;
        mmDevice = device;

        try{
            Log.d(TAG,"Socket create attempt");
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("965401d5-b903-44cf-8dfb-4406a4290630"));
            Log.d(TAG,"Socket created");
        }catch(IOException e){
            Log.e(TAG,"Couldn't create socket",e);
        }
        mmSocket = tmp;
    }
    public static BluetoothConnection getInstance(){
        return btConn;
    }
    public void run(){//running this thread will have the phone connect to a bt socket from rpi, this may
        try{
            Log.d(TAG,"Create Connection");
            mmSocket.connect();
            Log.d(TAG,"Connection successful");
           BluetoothService.ConnectedThread connectedThread = new BluetoothService().new ConnectedThread(mmSocket);
            connectedThread.start();
            BluetoothService.setInstance(connectedThread);
            //connectedThread.write("this is a message from my phone to the mirror");

        }catch(IOException connectException){
            Log.e(TAG,"connect failed",connectException);
            try{
                mmSocket.close();
            } catch(IOException e){
                Log.e(TAG, "closing failed",e);
            }
            return;
        }
    }

    public void  cancel(){
        try{
            mmSocket.close();
        } catch(IOException e){
            Log.e(TAG, "could not close socket");
        }
    }
}
