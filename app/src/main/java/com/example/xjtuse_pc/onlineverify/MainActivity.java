package com.example.xjtuse_pc.onlineverify;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.my.other.ServerIP;
import com.my.tcpclient.TCPSocket;
import com.my.udpclient.UDPSocket;
import com.my.serializable.Student;
import com.my.other.Type;

public class MainActivity extends Activity {
    public void sendUdp(View v)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mac = "dd-ff-ee-qq-ff";
                UDPSocket socket = new UDPSocket();
                socket.sendPackge(mac.getBytes(),ServerIP.ip,ServerIP.udpPort);
                socket.waitForRecv();
            }
        }).start();
    }
    public void send(View v)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Student stu = new Student();
                stu.type = Type.register;
                stu.name = "卓别";
                stu.idNumber = "3115393020";
                stu.macAddr = "22-55-96-84-85-65";
                stu.studentClass = "5128班";
                TCPSocket socket = new TCPSocket();
                socket.connect(ServerIP.ip, ServerIP.tcpPort);
                socket.sendData(stu)    ;
                //等待服务器
                socket.disConnect();
            }
        }).start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
