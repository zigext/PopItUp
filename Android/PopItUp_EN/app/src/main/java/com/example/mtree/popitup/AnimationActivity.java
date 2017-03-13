package com.example.mtree.popitup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class AnimationActivity extends AppCompatActivity {

    private VideoView videoView;
    private String code;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        Intent intent = getIntent();
        code = intent.getStringExtra("code");

        getVideoPath();

        videoView = (VideoView) findViewById(R.id.videoView);

//        Uri video = Uri.parse("http://techslides.com/demos/samples/sample.mp4");
        videoView.setVideoURI(Uri.parse(path));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();
            }
        });

    }

    private void getVideoPath() {
        switch (code) {
            case ("animation1"):
                path = "android.resource://" + getPackageName() + "/" + R.raw.animate1;
                break;
            case ("character_hanuman"):
                path = "android.resource://" + getPackageName() + "/" + R.raw.animate1;
                break;
            case ("character_naga"):
                path = "android.resource://" + getPackageName() + "/" + R.raw.animate1;
                break;
            case ("character_nilaphat"):
                path = "android.resource://" + getPackageName() + "/" + R.raw.animate1;
                break;
            case ("character_ogre"):
                path = "android.resource://" + getPackageName() + "/" + R.raw.animate1;
                break;
            case ("character_sridaxrama"):
                path = "android.resource://" + getPackageName() + "/" + R.raw.animate1;
                break;
        }
    }
}
