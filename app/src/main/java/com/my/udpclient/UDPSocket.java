package com.my.udpclient;


import android.util.Log;
import android.widget.Toast;

import com.example.xjtuse_pc.onlineverify.MainActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by XJTUSE-PC on 2015/11/1.
 */
public class UDPSocket
{
    DatagramSocket socket = null;
    DatagramPacket packet = null;

    public UDPSocket()
    {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendPackge(byte[] bytes,String addr,int port)
    {
        try {
            packet = new DatagramPacket(bytes,0,bytes.length, InetAddress.getByName(addr),port);
            socket.send(packet);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitForRecv()
    {
        byte[] bytes = new byte[32];
        packet = new DatagramPacket(bytes,32);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = new String(packet.getData(),0,packet.getLength());
        Log.i("return",str);
    }
}
