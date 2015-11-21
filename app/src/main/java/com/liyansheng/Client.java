package com.liyansheng;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * Created by XJTUSE-PC on 2015/11/2.
 */
public class Client {


    private Socket socket=null;
    private ObjectOutputStream objectos=null;
    private ObjectInputStream objectis =null;


    public boolean connection( String addr, int port){
        boolean res = true;
      try {
          socket=  new Socket(addr,port);
      } catch (IOException e) {
          res = false;
          e.printStackTrace();
      }finally {
          return res;
      }
  };
  public void sendData(Object object){
      if(object==null){
          return;
      }
      else{
      try {
          objectos = new ObjectOutputStream(socket.getOutputStream());
          objectos.writeObject(object);
          objectos.flush();
      } catch (IOException e) {
          e.printStackTrace();
      }}


  };
    public Object rcData() {
        Object object = new Object();
        try {
            objectis=new ObjectInputStream(socket.getInputStream());
            try {
           object = objectis.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
      return object;
    }
    public void disconnection(){

        try {
            socket.close();
            objectis.close();
            objectos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    };



}
