package com.example.neera.bamboozled.activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.neera.bamboozled.R;

public class Activity_Media extends AppCompatActivity {
    Button btnSkip;
    VideoView videoView;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        btnSkip = (Button) findViewById(R.id.btnSkip);
        videoView = (VideoView) findViewById(R.id.videoView);

        videoView.setVideoURI(Uri.parse(
                "android.resource://" + getPackageName() + "/" + R.raw.video)
        );
        videoView.start();

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 8000);


    }


}
