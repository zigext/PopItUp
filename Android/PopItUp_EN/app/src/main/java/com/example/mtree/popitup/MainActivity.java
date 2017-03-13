package com.example.mtree.popitup;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import static com.example.mtree.popitup.R.id.split_action_bar;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnClickListener {

    private ZXingScannerView mScannerView;
    public String result;

    private ImageButton btnStart;
    private Button btnReset;
    private TextView txtMission;
    private MediaPlayer welcome;

    private MediaPlayer speak;

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
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                1);

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
                    .setMessage(R.string.mainActivity_reset)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            resetPrefs();
                            setContentView(R.layout.activity_main);
                            getPrefs();
                            getMission();

                            new AlertDialog.Builder(MainActivity.this)
                                    .setMessage(R.string.mainActivity_donereset)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
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
        Log.d("result___", result);

        switch (result){
            case ("sound_page_01"): soundDialog(result); break;
            case ("sound_page_02"): soundDialog(result); break;
            case ("sound_page_03"): soundDialog(result); break;
            case ("sound_page_04"): soundDialog(result); break;
            case ("sound_page_05"): soundDialog(result); break;
            case ("sound_page_06"): soundDialog(result); break;
            case ("sound_page_07"): soundDialog(result); break;
            case ("sound_page_08"): soundDialog(result); break;
            case ("sound_page_10"): soundDialog(result); break;

            default:
                // Do something with the result here
//        Log.d("handler", rawResult.getText()); // Prints scan results
//        Log.d("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

                // show the scanner result into dialog box.
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle(Html.fromHtml("<font color='#FFFFFF' size=3>ต้องการเล่นหรือไม่?</font>"));
                builder.setMessage(gameName(rawResult.getText())) //rawResult.getText()
                        .setCancelable(true);
                builder.setPositiveButton(R.string.play, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(MainActivity.this,
//                                QuestionActivity.class);
//                        intent.putExtra("code", result);
//                        startActivity(intent);
                        checkQrCode(result);
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onResume();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                break;
        }

    }

    private void changePrefs(String mission) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(mission, true);
        editor.apply();
    }

    public String gameName(String code) {
        switch (code) {
            case ("Pop It Up"): return getString(R.string.case_popitup);
            case ("animation1"): return getString(R.string.case_animation1);
            case ("character_hanuman"): return getString(R.string.case_character_hanuman);
            case ("character_naga"): return getString(R.string.character_naga);
            case ("character_nilaphat"): return getString(R.string.character_nilaphat);
            case ("character_ogre"): return getString(R.string.character_ogre);
            case ("character_sridaxrama"): return getString(R.string.character_sridaxrama);
            case ("catch"): return getString(R.string.activity_catch);
            case ("code001"): return getString(R.string.activity_question);
            case ("code002"): return getString(R.string.activity_question2);
            case ("bridge"): return getString(R.string.activity_bridge);
            case ("code003"): return getString(R.string.activity_maths);
            case ("code004"): return getString(R.string.activity_things);
            default: return getString(R.string.code_failed);
        }
    }

    public void checkQrCode(String code) {
        String strCode = code.toString();
        Intent intent;
//        Toast.makeText(MainActivity.this, strCode, Toast.LENGTH_LONG).show();
//        Log.d("qrtest", strCode);
        switch (strCode) {
            case ("Pop It Up"):
                Toast.makeText(MainActivity.this, gameName("Pop It Up"), Toast.LENGTH_SHORT); break;
            case ("animation1"):
                sendIntent(strCode, AnimationActivity.class); break;
            case ("character_hanuman"):
                sendIntent(strCode, AnimationActivity.class); break;
            case ("character_naga"):
                sendIntent(strCode, AnimationActivity.class); break;
            case ("character_nilaphat"):
                sendIntent(strCode, AnimationActivity.class); break;
            case ("character_ogre"):
                sendIntent(strCode, AnimationActivity.class); break;
            case ("character_sridaxrama"):
                sendIntent(strCode, AnimationActivity.class); break;
            case ("catch"): //catch game    mission1
                sendIntent(strCode, CatchActivity.class); break;

            case ("code001"): //question1    mission2
                if (value[0]) {
                    sendIntent(strCode, QuestionActivity.class);
                } else {showError();}
                break;

            case ("code002"): //question2    mission3
                if (value[0] && value[1]) {
                    sendIntent(strCode, Question2Activity.class);
                } else {showError();} break;

            case ("bridge"): //bridge game    mission4
                if (value[0] && value[1] && value[2]) {
                    sendIntent(strCode, BridgeActivity.class);
                } else {showError();} break;

            case ("code003"): //maths game    mission5
                if (value[0] && value[1] && value[2] && value[3]) {
                    sendIntent(strCode, QuestionmathActivity.class);
                } else {showError();} break;

            case ("code004"): //find things game    mission6
                if (value[0] && value[1] && value[2] && value[3] && value[4]) {
                    sendIntent(strCode, CameraActivity.class);
                } else {showError();} break;

            default: Toast.makeText(MainActivity.this, R.string.code_failed, Toast.LENGTH_LONG).show();
        }
    }

    private void sendIntent(String code, Class ActivityClass){
        Intent intent = new Intent(MainActivity.this, ActivityClass);
        intent.putExtra("code", code);
        startActivity(intent);
    }

    private void soundDialog(String result){

        switch (result){
            case ("sound_page_01"):
                speak = MediaPlayer.create(this, R.raw.sound_page_01);
                speak.start();
                break;
            case ("sound_page_02"):
                speak = MediaPlayer.create(this, R.raw.sound_page_02);
                speak.start();
                break;
            case ("sound_page_03"):
                speak = MediaPlayer.create(this, R.raw.sound_page_03);
                speak.start();
                break;
            case ("sound_page_04"):
                speak = MediaPlayer.create(this, R.raw.sound_page_04);
                speak.start();
                break;
            case ("sound_page_05"):
                speak = MediaPlayer.create(this, R.raw.sound_page_05);
                speak.start();
                break;
            case ("sound_page_06"):
                speak = MediaPlayer.create(this, R.raw.sound_page_06);
                speak.start();
                break;
            case ("sound_page_07"):
                speak = MediaPlayer.create(this, R.raw.sound_page_07);
                speak.start();
                break;
            case ("sound_page_08"):
                speak = MediaPlayer.create(this, R.raw.sound_page_08);
                speak.start();
                break;
            case ("sound_page_10"):
                speak = MediaPlayer.create(this, R.raw.sound_page_10);
                speak.start();
                break;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                speak.stop();
                onResume();
            }
        });

        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.activity_sound, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.show();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                ImageView image = (ImageView) dialog.findViewById(R.id.correct_ans);
                Bitmap icon = BitmapFactory.decodeResource(MainActivity.this.getResources(),
                        R.drawable.correct_ans);
                float imageWidthInPX = (float)image.getWidth();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                        Math.round(imageWidthInPX * (float)icon.getHeight() / (float)icon.getWidth()));
                image.setLayoutParams(layoutParams);
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                speak.stop();
                dialog.dismiss();
            }
        });
    }


    private void showError() {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(R.string.pass_before)
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
        Toast.makeText(this, R.string.exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}