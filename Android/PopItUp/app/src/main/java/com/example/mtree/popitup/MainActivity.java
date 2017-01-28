package com.example.mtree.popitup;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    public String result;

    private ImageButton btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (ImageButton) findViewById(R.id.btnStart);

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
    }

    public void resumeCamera() {
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }

    public void QrScanner(View view) {
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);

        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera
    }

    public String gameName(String code){
        switch (code){
            case ("code001"):   return "เกมตอบคำถาม";
            case ("code002"):   return "เกมตอบคำถาม 2";
            case ("code003"):   return "เกมหาของ";
            default:            return "CODE FAILED";
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        result = rawResult.getText();

        // Do something with the result here
//        Log.d("handler", rawResult.getText()); // Prints scan results
//        Log.d("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle( Html.fromHtml("<font color='#FFFFFF' size=3>Want to Play?</font>"));
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
                resumeCamera();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void checkQrCode(String code) {
        String strCode = code.toString();
        Intent intent;
//        Toast.makeText(MainActivity.this, strCode, Toast.LENGTH_LONG).show();
//        Log.d("qrtest", strCode);
        switch (strCode) {

            case ("code001"): //Question with choices
                intent = new Intent(MainActivity.this, QuestionActivity.class);
                intent.putExtra("code", strCode);
                startActivity(intent);
                break;

            case ("code002"): //Camera game
                intent = new Intent(MainActivity.this, Question2Activity.class);
                intent.putExtra("code", strCode);
                startActivity(intent);
                break;

            case ("code003"): //Camera game
                intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra("code", strCode);
                startActivity(intent);
                break;



            default:
                Toast.makeText(MainActivity.this, "CODE FAILED", Toast.LENGTH_LONG).show();
        }

    }

}
