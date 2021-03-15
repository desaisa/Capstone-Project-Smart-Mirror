package com.example.smartmirrorsw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmirrorsw.security.BluetoothService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LogInPage extends AppCompatActivity {

    private static final String FILE_NAME = "example.txt";
    private static final String TAG = "LogInPageLog";

    boolean flag = false;



    static EditText username_in;
    static EditText password_in;


    static boolean [] account_verification = new boolean[2];


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        username_in = findViewById(R.id.user_name);
        password_in = findViewById(R.id.password);

        create_shell_file();

    }

    public void create_shell_file() {
        FileOutputStream fos = null;
        String text = "Admin" + "," + "Password" + ","+ "sarthak-desai@live.com" +"\n" + "Sarthak" + "," + "Desai" + "," + "desaisa03@gmail.com" + "\n";

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void login(View v){

        String username = username_in.getText().toString();
        String password = password_in.getText().toString();

        if(username.equals("admin") && password.equals("admin")) {
            Toast.makeText(this, "Successful Login", Toast.LENGTH_LONG).show();
            open_home_screen();
        }else {

            //Write to rpi
            try {
                BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
                connectedThread.write("LOGIN:" + username + ":" + password);
                //connectedThread.write("LOGIN:rvQ9DjJNal0");
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Read from rpi
            try {
                String login_result = null;
                while (login_result == null) {
                    login_result = BluetoothService.getInput();
                }
                Log.d(TAG, login_result);
                if (login_result.equals("0")) {
                    Toast.makeText(this, "Successful Login", Toast.LENGTH_LONG).show();
                    open_home_screen();
                } else if (login_result.equals("1"))
                    Toast.makeText(this, "Incorrect Password", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "User Does Not Exist", Toast.LENGTH_LONG).show();

                Log.d(TAG, login_result.getClass().getName());
                BluetoothService.setInputToNull();
            } catch (Exception e) {
                Log.e(TAG, "Unable to read response" + e.getMessage());
            }
        }
        //Prev code uncomment if need to
        /*
        read_accounts(username,password);

        if (account_verification[0]==true){
            if(account_verification[1]==true){
                Toast.makeText(this,"Successful Login",Toast.LENGTH_LONG).show();
                open_home_screen();
                username_in.getText().clear();
                password_in.getText().clear();


            }
            else{
                Toast.makeText(this,"Incorrect Password",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this,"User Does Not Exist",Toast.LENGTH_LONG).show();
        }



        account_verification[0]=false;
        account_verification[1]=false;*/

    }



    public void open_create_account(View v){

        Intent intent = new Intent(this,CreateAccount.class);
        startActivity(intent);

    }




    public void read_accounts(String username, String password){
        account_verification[0]=false;
        account_verification[1]=false;

        FileInputStream fis = null;

        try {
            fis=openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            String [] splitline;

            while((text=br.readLine())!=null){

                splitline = text.split(",");

                // here call verification method
                user_verification(username,password,splitline);
                // here check for flag
                if (flag==true){
                    break;
                }
                account_verification[0]=false;
                account_verification[1]=false;
                splitline = null;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        // now check to see if this account exists

    }



    public void user_verification(String username, String password, String [] splitline){
        flag=false;
        account_verification[0]=false;
        account_verification[1]=false;
        if(splitline[0].equals(username)){
            //username exists
            //Toast.makeText(this,splitline[0],Toast.LENGTH_LONG).show();
            account_verification[0]= true;
            flag=true;
            if(splitline[1].equals(password)){
                //password is correct
                account_verification[1]= true;

            }

        }

    }

    public void open_home_screen(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public static String get_username(){
        return username_in.getText().toString();
    }


}
