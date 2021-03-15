package com.example.smartmirrorsw;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmirrorsw.security.BluetoothService;

public class Settings extends AppCompatActivity  {

    public Switch weather_switch;
    public Switch news_switch;
    public Switch youtube_switch;
    public Switch monitor_switch;
    public SeekBar volume_seekbar;
    public SeekBar light_seekbar;
    private final String TAG = "Settings";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //weather_switch.setChecked(true);
        //calling parent class to recall the app's last state
        super.onCreate(savedInstanceState);


        //method to fill the activity that is launched with  the activity_main.xml layout file
        setContentView(R.layout.fragment_settings);

        weather_switch = findViewById(R.id.weather_switch);
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
        }
        weather_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
                        connectedThread.write("ONWEATHER");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    //close weather
                    try {
                        BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
                        connectedThread.write("NOWEATHER");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        youtube_switch = findViewById(R.id.youtube_switch);
        try {
            BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
            connectedThread.write("SETCALENDAR");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String calendar_result = null;
            while (calendar_result == null) {
                calendar_result = BluetoothService.getInput();
            }
            if(calendar_result.equals("1"))
                youtube_switch.setChecked(true);
            Log.d(TAG, calendar_result);
            BluetoothService.setInputToNull();
        } catch (Exception e) {
            Log.e(TAG, "Unable to read response" + e.getMessage());
        }
        youtube_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
                        connectedThread.write("CALENDAR");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    //close youtube
                    try {
                        BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
                        connectedThread.write("NOCALENDAR");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        news_switch = findViewById(R.id.news_switch);
        try {
            BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
            connectedThread.write("SETNEWS");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String news_result = null;
            while (news_result == null) {
                news_result = BluetoothService.getInput();
            }
            if(news_result.equals("1"))
                news_switch.setChecked(true);
            Log.d(TAG, news_result);
            BluetoothService.setInputToNull();
        } catch (Exception e) {
            Log.e(TAG, "Unable to read response" + e.getMessage());
        }
        news_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Write to rpi
                    try {
                        BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
                        connectedThread.write("NEWS");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //Read from rpi
                    /*try {
                        String settings_result = null;
                        while (settings_result == null) {
                            settings_result = BluetoothService.getInput();
                        }
                        Log.d(TAG, settings_result);
                        BluetoothService.setInputToNull();
                    } catch (Exception e) {
                        Log.e(TAG, "Unable to read response" + e.getMessage());
                    }*/
                }
                else{
                    try {
                        BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
                        connectedThread.write("NONEWS");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        monitor_switch = findViewById(R.id.switch4);
        monitor_switch.setChecked(true);
        monitor_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Write to rpi

                }
                else{
                    try {
                        BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
                        connectedThread.write("NOTUBE");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        //sound volume
        volume_seekbar=(SeekBar) findViewById(R.id.speaker_seekbar);
        volume_seekbar.setOnSeekBarChangeListener(new volumeSeekBarListener());

        light_seekbar=(SeekBar) findViewById(R.id.light_seekbar);
        light_seekbar.setOnSeekBarChangeListener(new lightSeekBarListener());
    }


    private class volumeSeekBarListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int valueTobeUsed=progress;
            //send this value via bluetooth
            try {
                BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
                connectedThread.write("SOUND:"+Integer.toString(valueTobeUsed));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    public class lightSeekBarListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int valueToBeIsed=progress;
            //send this value via blutooth
            try {
                BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
                connectedThread.write("LIGHT:"+Integer.toString(valueToBeIsed));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
