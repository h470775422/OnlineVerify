package com.my.tcpclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by XJTUSE-PC on 2015/11/1.
 */


//TCP 连接类
public class TCPSocket {
    private Socket socket = null;
    ObjectOutputStream objectOS = null;
    ObjectInputStream objectIS = null;

    //连接服务器
    public void connect(String addr, int port) {
        try {
            socket = new Socket(addr, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //发送包体
    public void sendData(Object object)
    {
        if(object == null)
            return;
        try {
            objectOS = new ObjectOutputStream(socket.getOutputStream());
            objectOS.writeObject(object);
            objectOS.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(objectOS != null)
            try {
            objectOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //接收包体
    public Object recvData()
    {
        Object object = null;
        try {
            objectIS = new ObjectInputStream(socket.getInputStream());
            object = objectIS.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(objectIS != null)
            try {
            objectIS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }
    //断开连接
    public void disConnect()
    {
        try {
            if(socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
