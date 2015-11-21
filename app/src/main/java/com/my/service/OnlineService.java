package com.my.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.my.udpclient.ConnectManager;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class OnlineService extends Service {


    private MyBinder mBinder = new MyBinder();
    private String macAddr;

//    public OnlineService() {
//        super("OnlineService");
//    }

    @Override
    public void onCreate(){
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){

        macAddr = intent.getStringExtra("mac");
        new Thread(new ConnectManager(macAddr)).start();
        Log.i("韩翔辉","onStartCommand");
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    public void ConnToServer(){

    }
    @Override
    public IBinder onBind(Intent intent){
        return mBinder;
    }

    public class MyBinder extends Binder {

        public void function(){
        }
    }

}
