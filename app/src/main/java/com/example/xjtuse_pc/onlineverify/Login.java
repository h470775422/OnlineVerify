package com.example.xjtuse_pc.onlineverify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.haoxiaole.Client;
import com.my.other.ServerIP;
import com.my.serializable.Student;

public class Login extends Activity {

    public  Handler myHandler = new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what == -1){
                Toast.makeText(Login.this,"网络连接失败",Toast.LENGTH_LONG).show();
                return;
            }
            Bundle bundle = msg.getData();
            String string = bundle.getString("recieve");
            if (string.equals("successed")) {
                Toast.makeText(Login.this,"签到成功",Toast.LENGTH_LONG).show();
                intoOnlinePage();
            } else {
                Toast.makeText(Login.this, "签到失败,请尝试注册", Toast.LENGTH_SHORT).show();

            }
            super.handleMessage(msg);
        }
    };

    public void intoOnlinePage(){
        Intent intent = new Intent(this,OnlineActivity.class);
        startActivity(intent);
    }

    public void set(View v){
        Intent intent = new Intent(this,SetActivity.class);
        startActivity(intent);
    }

    // get Mac Address
    public String getMacAddress() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String phoneMac = info.getMacAddress();
        return phoneMac;
    }


    public void register(View v){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    //onClick btlogin
    public void btlogin(View v) {

        Student student = new Student();
        student.type = 2;
        student.macAddr = getMacAddress();
        new Thread(new Client(student,myHandler)).start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ServerIP.ip = "192.168.1.107";
        ServerIP.udpPort = 8890;
        ServerIP.tcpPort = 8889;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
