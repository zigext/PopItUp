package com.example.mtree.popitup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.mtree.popitup.R.id.mission1;
import static com.example.mtree.popitup.R.id.mission2;
import static com.example.mtree.popitup.R.id.mission3;
import static com.example.mtree.popitup.R.id.mission4;
import static com.example.mtree.popitup.R.id.mission5;
import static com.example.mtree.popitup.R.id.mission6;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnClickListener {

    private ZXingScannerView mScannerView;
    public String result;

    private ImageButton btnStart;
    private Button btnReset;
    private TextView txtMission;
    private MediaPlayer welcome;

    private List<Button> missions;
    private static final int[] missionId = {mission1, mission2, mission3, mission4, mission5, mission6};

    SharedPreferences prefs;
    private Boolean[] value = new Boolean[6];

    boolean doubleBackToExitPressedOnce = false;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMission = (TextView) findViewById(R.id.txtMission);

        welcome = MediaPlayer.create(this, R.raw.welcome);
        welcome.start();

        btnStart = (ImageButton) findViewById(R.id.btnStart);
        btnReset = (Button) findViewById(R.id.btnReset);
//        btnReset.setOnClickListener(this);

        prefs = this.getSharedPreferences("dummy", Context.MODE_PRIVATE);

//        resetPrefs();
        getPrefs();
        getMission();

        getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void resetPrefs() {
        for (int i = 0; i < value.length; i++) {
            value[i] = false;
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("mission1", false);
        editor.putBoolean("mission2", false);
        editor.putBoolean("mission3", false);
        editor.putBoolean("mission4", false);
        editor.putBoolean("mission5", false);
        editor.putBoolean("mission6", false);
        editor.apply();
    }

    private void getPrefs() {
        value[0] = prefs.getBoolean("mission1", false);
        value[1] = prefs.getBoolean("mission2", false);
        value[2] = prefs.getBoolean("mission3", false);
        value[3] = prefs.getBoolean("mission4", false);
        value[4] = prefs.getBoolean("mission5", false);
        value[5] = prefs.getBoolean("mission6", false);
    }

    private void getMission() {
        missions = new ArrayList<Button>(missionId.length);
        for (int id : missionId) {
            final Button mission = (Button) findViewById(id);
            mission.setOnClickListener(btnClick);
            missions.add(mission);
        }

        for (int i = 0; i < missionId.length; i++) {
            Log.d("value: ", value[i] + "");
            if (value[i]) {
                missions.get(i).setEnabled(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    missions.get(i).setBackground(getResources().getDrawable(R.drawable.rounded_corner_mission_yes));
                }
            }
        }

        getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
    }

    View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case (mission1):
                    Intent intent = new Intent(MainActivity.this, CatchActivity.class);
                    startActivity(intent); break;
                case (mission2):
                    intent = new Intent(MainActivity.this, QuestionActivity.class);
                    startActivity(intent); break;
                case (mission3):
                    intent = new Intent(MainActivity.this, Question2Activity.class);
                    startActivity(intent); break;
                case (mission4):
                    intent = new Intent(MainActivity.this, BridgeActivity.class);
                    startActivity(intent); break;
                case (mission5):
                    intent = new Intent(MainActivity.this, QuestionmathActivity.class);
                    startActivity(intent); break;
                case (mission6):
                    intent = new Intent(MainActivity.this, CameraActivity.class);
                    startActivity(intent); break;
                default: break;
            }
        }
    };

    @Override
    public void onClick(View v) {

    }

    public void rstPrefs(View view) {
        if (value[0] || value[1] || value[2] || value[3] || value[4] || value[5] ){
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("ต้องการรีเซ็ตมิสชั่นไหม?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            resetPrefs();
                            setContentView(R.layout.activity_main);
                            getPrefs();
                            getMission();

                            new AlertDialog.Builder(MainActivity.this)
                                    .setMessage("รีเซ็ตเรียบร้อยแล้ว")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScannerView != null) {
            mScannerView.stopCamera();           // Stop camera on pause
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
        getPrefs();
        getMission();
//        mScannerView.startCamera();
    }

    public void QrScanner(View view) {
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);

        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera
    }

    @Override
    public void handleResult(Result rawResult) {
        result = rawResult.getText();
        // Do something with the result here
//        Log.d("handler", rawResult.getText()); // Prints scan results
//        Log.d("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(Html.fromHtml("<font color='#FFFFFF' size=3>Want to Play?</font>"));
        builder.setMessage(gameName(rawResult.getText())) //rawResult.getText()
                .setCancelable(true);
        builder.setPositiveButton("PLAY", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(MainActivity.this,
//                                QuestionActivity.class);
//                        intent.putExtra("code", result);
//                        startActivity(intent);
                checkQrCode(result);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                onResume();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void changePrefs(String mission) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(mission, true);
        editor.apply();
    }

    public String gameName(String code) {
        switch (code) {
            case ("Pop It Up"): return "Welcome to Pop It Up!";
            case ("animation1"): return "ต้องการเข้าไปสู่โลกหนังสือนิทานหรือไม่?";
            case ("catch"): return "เกมรับหิน";
            case ("code001"): return "เกมตอบคำถาม";
            case ("code002"): return "เกมตอบคำถาม 2";
            case ("bridge"): return "เกมสร้างสะพาน";
            case ("code003"): return "เกมคิดเลข";
            case ("code004"): return "เกมหาของ";
            default: return "CODE FAILED";
        }
    }

    public void checkQrCode(String code) {
        String strCode = code.toString();
        Intent intent;
//        Toast.makeText(MainActivity.this, strCode, Toast.LENGTH_LONG).show();
//        Log.d("qrtest", strCode);
        switch (strCode) {
            case ("Pop It Up"):
                Toast.makeText(MainActivity.this, gameName("Pop It Up"), Toast.LENGTH_SHORT);
                break;

            case ("animation1"):
                intent = new Intent(MainActivity.this, AnimationActivity.class);
                intent.putExtra("code", strCode);
                startActivity(intent);
                break;

            case ("catch"): //catch game    mission1
                intent = new Intent(MainActivity.this, CatchActivity.class);
                intent.putExtra("code", strCode);
                startActivity(intent);
                break;

            case ("code001"): //question1    mission2
                if (value[0]) {
                    intent = new Intent(MainActivity.this, QuestionActivity.class);
                    intent.putExtra("code", strCode);
                    startActivity(intent);
                } else {showError();}
                break;

            case ("code002"): //question2    mission3
                if (value[0] && value[1]) {
                    intent = new Intent(MainActivity.this, Question2Activity.class);
                    intent.putExtra("code", strCode);
                    startActivity(intent);
                } else {showError();}
                break;

            case ("bridge"): //bridge game    mission4
                if (value[0] && value[1] && value[2]) {
                    intent = new Intent(MainActivity.this, BridgeActivity.class);
                    intent.putExtra("code", strCode);
                    startActivity(intent);
                } else {showError();}
                break;

            case ("code003"): //maths game    mission5
                if (value[0] && value[1] && value[2] && value[3]) {
                    intent = new Intent(MainActivity.this, QuestionmathActivity.class);
                    intent.putExtra("code", strCode);
                    startActivity(intent);
                } else {showError();}
                break;

            case ("code004"): //find things game    mission6
                if (value[0] && value[1] && value[2] && value[3] && value[4]) {
                    intent = new Intent(MainActivity.this, CameraActivity.class);
                    intent.putExtra("code", strCode);
                    startActivity(intent);
                } else {showError();}
                break;

            default:
                Toast.makeText(MainActivity.this, "CODE FAILED", Toast.LENGTH_LONG).show();
        }
    }

    private void showError() {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("ต้องผ่านด่านก่อนหน้าก่อนนะ")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }

        setContentView(R.layout.activity_main);
        getPrefs();
        getMission();

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to quit.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}