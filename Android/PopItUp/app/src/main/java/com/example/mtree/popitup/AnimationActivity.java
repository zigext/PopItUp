package com.example.mtree.popitup;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class AnimationActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        String path = "android.resource://" + getPackageName() + "/" + R.raw.animate1;

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
}
