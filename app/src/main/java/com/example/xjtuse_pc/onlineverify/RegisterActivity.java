package com.example.xjtuse_pc.onlineverify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.liyansheng.Client;
import com.my.function.TakePhoto;
import com.my.other.ServerIP;
import com.my.serializable.Student;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RegisterActivity extends Activity {
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    Student student = new Student();
    private TakePhoto takePhoto = new TakePhoto();
    private Uri imageUri;
    private Bitmap bitmap;

    public Handler myHandler = new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what == -1){
                Toast.makeText(RegisterActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
                return;
            }
            Bundle bundle = msg.getData();
            String string = bundle.getString("recieve");
            if (string.equals("successed")) {
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                finish();
            }else if(string.equals("existedMac")){
                Toast.makeText(RegisterActivity.this, "该手机已注册，请尝试联系管理员", Toast.LENGTH_LONG).show();
            }else if(string.equals("existedStudent")){
                Toast.makeText(RegisterActivity.this, "该学生已存在，请勿重复注册", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(RegisterActivity.this, "未知原因，注册失败，请尝试联系管理员", Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };

    //按钮：打开照相机
    public void takephoto(View v){
        File outputImage = new File(Environment.getExternalStorageDirectory(),"output_Image.jpg");
        if(outputImage.exists()){
            outputImage.delete();
            try {
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
        }
    }

    public void zhuce(View v){
        EditText etname = (EditText)findViewById(R.id.etname);
        student.name  = etname.getText().toString();
        EditText etid = (EditText)findViewById(R.id.etid);
        student.idNumber = etid.getText().toString();

        EditText etclass = (EditText)findViewById(R.id.etclass);
        student.studentClass=etclass.getText().toString();
        GetLocalMacAddress glm = new GetLocalMacAddress();
        student.macAddr=glm.macAddress;
        student.type=1;

        student.picName = student.idNumber+".jpg";
        student.picLength = bitmap.getByteCount();
        student.headPic = Bitmap2Bytes(bitmap);
        new Thread(){
            @Override
            public void run() {
                Message message = new Message();
                Client client = new Client();
                if(!client.connection(ServerIP.ip,ServerIP.tcpPort)){
                    message.what = -1;
                    myHandler.sendMessage(message);
                    return;
                }
                client.sendData(student);
                String s =(String)client.rcData();

                Bundle bundle = new Bundle();
                bundle.putString("recieve",s);
                message.setData(bundle);
                myHandler.sendMessage(message);
                client.disconnection();
            }

        }.start();

    };


    public byte[] Bitmap2Bytes(Bitmap bm)
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);

        return baos.toByteArray();

    }

    public class GetLocalMacAddress {

        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info =wifi.getConnectionInfo();
        String macAddress = (String)info.getMacAddress();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
