package com.example.smartmirrorsw.security;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

//using ssh right now
public class Connection {
    private String hostname;
    private String username;
    private int port;
    private String password;
    private Session session;
    public Connection (String host, int port, String username, String password)
    {
        hostname = host;
        port = port;
        username = username;
        password = password;
    }
    public void establishConnection(){
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(username, hostname, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "password");
            session.connect(5000);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void executeCommand (String command){
        Channel channelssh = null;
        try {
            channelssh = session.openChannel("exec");
            ((ChannelExec)channelssh).setCommand(command);
            channelssh.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }




}
