package com.example.smartmirrorsw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmirrorsw.security.BluetoothService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class CreateAccount extends AppCompatActivity{

    private static final String FILE_NAME = "example.txt";
    private static final String TAG = "CreateAccountLog";
    boolean flag_username = false;
    boolean flag_email = false;
    boolean [] account_verification = new boolean[2];

    EditText username_in;
    EditText password_in;
    EditText email_in;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_acc_main);

        username_in = findViewById(R.id.user_name);
        password_in = findViewById(R.id.password);
        email_in = findViewById(R.id.email);


    }

    public void create_account(View v) {


        String username = username_in.getText().toString();
        String password = password_in.getText().toString();
        String email = email_in.getText().toString();

        //Write to pi in the form of CREATE:username:password:email
        try {
            BluetoothService.ConnectedThread connectedThread = BluetoothService.getInstance();
            connectedThread.write("CREATE:" + username + ":" + password+":"+email);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Read from rpi
        try {
            String create_result = null;
            Log.d(TAG, "hello");
            while (create_result == null) {
                create_result = BluetoothService.getInput();
            }
            Log.d(TAG, "yo");
            Log.d(TAG, create_result);

            if (create_result.equals("0")) {
                Toast.makeText(this,"New Account Created",Toast.LENGTH_LONG).show();
                finish();
            } else if (create_result.equals("1")) {
                Toast.makeText(this, "Username is Taken", Toast.LENGTH_LONG).show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "E-mail is Taken", Toast.LENGTH_LONG).show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

            Log.d(TAG, create_result.getClass().getName());
            BluetoothService.setInputToNull();
        } catch (Exception e) {
            Log.e(TAG, "Unable to read response" + e.getMessage());
        }

        /*read_accounts(username,email);

        if (account_verification[0]==true && account_verification[1]==true){
            Toast.makeText(this,"Username and E-mail are Taken",Toast.LENGTH_LONG).show();
            finish();
        }

        else if (account_verification[0]==true){
            Toast.makeText(this,"Username is Taken",Toast.LENGTH_LONG).show();
        }

        else if (account_verification[1]==true){
            Toast.makeText(this,"E-mail is Taken",Toast.LENGTH_LONG).show();
        }

        else{
            Toast.makeText(this,"New Account Created",Toast.LENGTH_LONG).show();

            // write new user to file
            write_accounts(username,password,email);
            finish();

        }*/
    }


    public void read_accounts(String username, String email){
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
                user_verification(username,email,splitline);
                // here check for flag
                if (flag_username==true||flag_email==true){
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

    public void user_verification(String username, String email, String [] splitline){
        flag_username=false;
        flag_email=false;
        account_verification[0]=false;
        account_verification[1]=false;
        if(splitline[0].equals(username)){
            //username exists
            //Toast.makeText(this,splitline[0],Toast.LENGTH_LONG).show();
            account_verification[0]= true;
            flag_username=true;

        }

        if (splitline[2].equals(email)){
            account_verification[1]= true;
            flag_email=true;
        }

    }

    public void write_accounts(String username, String password, String email){
        String text = username+","+password+","+email+"\n";
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_APPEND);
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






}

