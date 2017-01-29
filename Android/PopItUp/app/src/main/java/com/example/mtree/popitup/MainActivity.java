//package com.example.mtree.popitup;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.hardware.Camera;
//import android.os.Handler;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.Html;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import com.google.zxing.Result;
//
//import me.dm7.barcodescanner.zxing.ZXingScannerView;
//
//public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
//
//    private ZXingScannerView mScannerView;
//    public String result;
//
//    private ImageButton btnStart;
//
//    private Intent intent;
//
//    boolean doubleBackToExitPressedOnce = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        btnStart = (ImageButton) findViewById(R.id.btnStart);
//
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mScannerView.stopCamera();           // Stop camera on pause
////        if (checkForCamera() != null){
////        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        setContentView(R.layout.activity_main);
//    }
//
//    public void resumeCamera() {
//        // If you would like to resume scanning, call this method below:
//        mScannerView.resumeCameraPreview(this);
//    }
//
//    public void QrScanner(View view) {
//        Log.d("press test", "pressed");
//        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
//        setContentView(mScannerView);
//
//        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
//        mScannerView.startCamera();         // Start camera
//    }
//
//    public String gameName(String code){
//        switch (code){
//            case ("code001"):   return "เกมตอบคำถาม";
//            case ("code002"):   return "เกมตอบคำถาม 2";
//            case ("code003"):   return "เกมหาของ";
//            default:            return "CODE FAILED";
//        }
//    }
//
//    @Override
//    public void handleResult(Result rawResult) {
//        result = rawResult.getText();
//
//        // Do something with the result here
////        Log.d("handler", rawResult.getText()); // Prints scan results
////        Log.d("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
//
//        // show the scanner result into dialog box.
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setTitle( Html.fromHtml("<font color='#FFFFFF' size=3>Want to Play?</font>"));
//        builder.setMessage(gameName(rawResult.getText())) //rawResult.getText()
//                .setCancelable(true);
//        builder.setPositiveButton("PLAY", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
////                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
////                        Intent intent = new Intent(MainActivity.this,
////                                QuestionActivity.class);
////                        intent.putExtra("code", result);
////                        startActivity(intent);
//                checkQrCode(result);
//            }
//        });
//
//        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                resumeCamera();
//            }
//        });
//
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
//
//    public void checkQrCode(String code) {
//        String strCode = code.toString();
////        Toast.makeText(MainActivity.this, strCode, Toast.LENGTH_LONG).show();
////        Log.d("qrtest", strCode);
//        switch (strCode) {
//
//            case ("code001"): //Question game
//                intent = new Intent(MainActivity.this, QuestionActivity.class);
//                intent.putExtra("code", strCode);
//                startActivity(intent);
//                break;
//
//            case ("code002"): //Question2 game
//                intent = new Intent(MainActivity.this, Question2Activity.class);
//                intent.putExtra("code", strCode);
//                startActivity(intent);
//                break;
//
//            case ("code003"): //Camera game
//                intent = new Intent(MainActivity.this, CameraActivity.class);
//                intent.putExtra("code", strCode);
//                startActivity(intent);
//                break;
//
//            default:
//                Toast.makeText(MainActivity.this, "CODE FAILED", Toast.LENGTH_LONG).show();
//        }
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);
//    }
//
//    @Override
//    public void onBackPressed()
//    {
//        // code here to show dialog
//        super.onBackPressed();  // optional depending on your needs
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)  {
//        if (keyCode == KeyEvent.KEYCODE_BACK ) {
//            intent = new Intent(MainActivity.this, MainActivity.class);
//            startActivity(intent);
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
//
//}
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