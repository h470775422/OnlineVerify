package com.my.udpclient;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.my.other.ServerIP;

/**
 * Created by ACE on 2015/11/3.
 */
public class ConnectManager implements Runnable{

    private UDPSocket socket = new UDPSocket();
    private String macAddr;
    public ConnectManager(String macAddr){
        this.macAddr = macAddr;
    }

    @Override
    public void run() {
        while(true){
            sendMacAddr();
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMacAddr(){
        UDPSocket socket = new UDPSocket();
        socket.sendPackge(macAddr.getBytes(), ServerIP.ip,ServerIP.udpPort);
    }


}
