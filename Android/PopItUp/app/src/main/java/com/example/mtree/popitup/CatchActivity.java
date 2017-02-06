package com.example.mtree.popitup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class CatchActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    ImageButton btnStart;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch);

        btnStart = (ImageButton) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);

        SharedPreferences prefs = this.getSharedPreferences("dummy", Context.MODE_PRIVATE);;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("mission1", true);
        editor.apply();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnStart) {
            PackageManager managerclock = getPackageManager();
            intent = managerclock.getLaunchIntentForPackage("com.PopItUp.CatchGame");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(intent);
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