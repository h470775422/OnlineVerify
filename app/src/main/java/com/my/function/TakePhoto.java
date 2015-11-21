package com.my.function;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by ACE on 2015/11/10.
 */
public class TakePhoto {




//    public TakePhoto(){
//
//    }
//    public void openCamera()
//    {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                Log.i("Caimogu", ".........takePhoto().........");
//                File outputImage = new File(Environment.getExternalStorageDirectory(),"output_Image.jpg");
//                if(outputImage.exists())
//                {
//                    outputImage.delete();
//                }
//                imageUri = Uri.fromFile(outputImage);
////                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
////                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
////                startActivityForResult(intent, TAKE_PHOTO);
//
//            }
//        }).start();
//
//    }
//
//    public Uri getImageUri() {
//        return imageUri;
//    }
//
//    public void setImageUri(Uri imageUri) {
//        this.imageUri = imageUri;
//    }
}
