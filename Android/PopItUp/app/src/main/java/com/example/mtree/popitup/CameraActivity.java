package com.example.mtree.popitup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    Intent cameraIntent;

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imgView;

    private ImageButton btnPhoto;
    private LinearLayout layerBtnPhoto;
    private LinearLayout layerPhoto;
    private LinearLayout layerButton;

    private LinearLayout layoutActivity;

    private Button btnDecline;
    private Button btnAccept;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initInstance();
    }

    private void initInstance() {

        SharedPreferences prefs = this.getSharedPreferences("dummy", Context.MODE_PRIVATE);;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("mission6", true);
        editor.apply();

        layerBtnPhoto = (LinearLayout) findViewById(R.id.layerBtnPhoto);
        layerPhoto = (LinearLayout) findViewById(R.id.layerPhoto);
        layerButton = (LinearLayout) findViewById(R.id.layerButton);
        layoutActivity = (LinearLayout) findViewById(R.id.layoutActivity);

        btnDecline = (Button) findViewById(R.id.btnDecline);
        btnDecline.setOnClickListener(this);

        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(this);

        imgView = (ImageView)this.findViewById(R.id.imgView);

        btnPhoto = (ImageButton) this.findViewById(R.id.btnPhoto);
        btnPhoto.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == btnPhoto) {
            cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }

        if (v == btnDecline){
            Toast.makeText(CameraActivity.this, "ว๊าาา แย่จังงงง ลองใหม่นะ", Toast.LENGTH_SHORT).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                    startActivity(getIntent());
                }
            }, 2000);
        }

        if (v == btnAccept){
            Toast.makeText(CameraActivity.this, "ดอกไม้นี่ใช้ได้เลยนี่นา!", Toast.LENGTH_SHORT).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                    startActivity(getIntent());
                }
            }, 2000);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED ){
            layoutActivity.setWeightSum((float) 10.0);

            layerBtnPhoto.setVisibility(View.GONE);
            layerPhoto.setVisibility(View.VISIBLE);
            layerButton.setVisibility(View.VISIBLE);

            getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
        }


        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgView.setImageBitmap(photo);
        }

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}