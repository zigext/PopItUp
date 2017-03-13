package com.example.mtree.popitup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.VideoView;

public class CharacterActivity extends AppCompatActivity {

    private ImageView imageView;
    private String code;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        Intent intent = getIntent();
        code = intent.getStringExtra("code");

        imageView = (ImageView) findViewById(R.id.imageView);
        setCharacter();

    }

    private void setCharacter() {
        switch (code) {
            case ("character_naga"):
                imageView.setImageResource(R.drawable.seamonster);
                break;
            case ("character_nilaphat"):
                imageView.setImageResource(R.drawable.nilaphat);
                break;
            case ("character_ogre"):
                imageView.setImageResource(R.drawable.giant);
                break;
            case ("character_sridaxrama"):
                imageView.setImageResource(R.drawable.love);
                break;
        }
    }
}
