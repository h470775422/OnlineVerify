package com.example.xjtuse_pc.onlineverify;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.my.other.ServerIP;
import com.my.service.OnlineService;

public class OnlineActivity extends Activity {


    private OnlineService.MyBinder mBinder;
    private MyServiceConnecton mServiceConn = new MyServiceConnecton();


    protected String getMacAddr(){
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_verify);

        Intent intent = new Intent(this, OnlineService.class);
        intent.putExtra("mac", getMacAddr());
        startService(intent);
        bindService(intent, mServiceConn, BIND_AUTO_CREATE);
    }



    class MyServiceConnecton implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (OnlineService.MyBinder)service;
            mBinder.function();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_online_verify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
