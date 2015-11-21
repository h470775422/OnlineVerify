package com.haoxiaole;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.my.other.ServerIP;
import com.my.serializable.Student;

/**
 * Created by XJTUSE-PC on 2015/11/1.
 */
public class Client implements Runnable{

    private Socket socket = null;
    Student student;
    Handler handler;
    public Client(Student student,Handler handler){
        this.student = student;
        this.handler = handler;
    }

    @Override
    public void run() {
        if(!connect()){
            Message m = new Message();
            m.what = -1;
            handler.sendMessage(m);
            return;
        }
        sendToServer(student);
        recieveFromSever();
        closeSocket();

    }

    //连接服务器
    public boolean connect()
    {
        boolean res = true;
        //IP地址和端口号（对应服务端）
        try {
            socket = new Socket(ServerIP.ip, ServerIP.tcpPort);
        } catch (IOException e) {
            res = false;
            e.printStackTrace();
        }finally {
            return res;
        }
    }

    //向服务器发送消息
    public void sendToServer(Object object){

        System.out.println("Client Sending: ..." );
        ObjectOutputStream os = null;

        try {
            os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(student);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //接收服务器消息
    public String recieveFromSever(){
        ObjectInputStream objectIS = null;
        String str = null;
        try {
            objectIS = new ObjectInputStream(socket.getInputStream());
            try {
                str = (String)objectIS.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("recieve",str);
        message.setData(bundle);
        handler.sendMessage(message);
       return str;
    }


    //close socket
    public void closeSocket(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client:Socket closed");
    }
}



