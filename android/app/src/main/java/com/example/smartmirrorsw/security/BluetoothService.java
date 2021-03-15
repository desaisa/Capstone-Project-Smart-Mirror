package com.example.smartmirrorsw.security;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class BluetoothService {
    private static final String TAG = "bluetoothService";
    private static ConnectedThread connectedThread;
    private static String inputString;
    public static ConnectedThread setInstance(ConnectedThread connectedThread1)
    {
        if(connectedThread == null)
        {
            connectedThread = connectedThread1;
        }
        return connectedThread;
    }

    public static ConnectedThread getInstance()
    {
        return connectedThread;
    }

    public static String getInput(){
        return inputString;
    }

    public static void setInputToNull(){
        inputString = null;
        return;
    }

    public class ConnectedThread extends Thread{

        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer;

        public ConnectedThread(BluetoothSocket socket){
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            Log.d(TAG,"create service for bt");
            try{
                tmpIn = socket.getInputStream();
            } catch(IOException e){
                Log.e(TAG,"Input stream error.");
            }

            try{
                tmpOut = socket.getOutputStream();
            }catch(IOException e){
                Log.e(TAG, "Output Stream error");
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run(){
            mmBuffer = new byte[1];
            int numBytes;
            Log.d(TAG,  "I am reading");
            while(true){
                try{
                    numBytes = mmInStream.read(mmBuffer);
                    inputString = new String(mmBuffer);
                    Log.d(TAG,  new String(mmBuffer));
                } catch (IOException e){
                    Log.e(TAG, "Input stream disconnected");
                    break;
                }
            }
        }

        public void  write(String text){
            byte[] bytes = new byte[0];
            Log.d(TAG,"writing");
            try {
                bytes = text.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.e(TAG,"write byte error",e);
            }
            try{
                mmOutStream.write(bytes);
            }catch(IOException e){
                Log.e(TAG,"write error",e);
            }
        }

    }
}
