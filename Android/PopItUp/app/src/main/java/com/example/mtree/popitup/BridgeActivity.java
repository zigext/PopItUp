package com.example.mtree.popitup;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class BridgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge);


    }

//    private boolean InstallmyAPK(){
//
//        AssetManager assetManager = getAssets();
//        InputStream in = null;
//        OutputStream out = null;
//        File myAPKFile = new File(Environment.getExternalStorageDirectory().getPath()+"/BridgeGame.apk");
//
//        try {
//
//            if(!myAPKFile.exists()){
//                in = assetManager.open("BridgeGame.apk");
//                out = new FileOutputStream(myAPKFile);
//
//                byte[] buffer = new byte[1024];
//                int read;
//                while((read = in.read(buffer)) != -1) {
//                    out.write(buffer, 0, read);
//                }
//
//                in.close();
//                in = null;
//
//                out.flush();
//                out.close();
//                out = null;
//            }
//
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(Uri.fromFile(myAPKFile),
//                    "application/vnd.android.package-archive");
//            startActivity(intent);
//            return true;
//
//        } catch(Exception e) {return false;}
//
//    }

}
