package com.rizwan.fastestvideotrimmer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayer extends AppCompatActivity {


    VideoView videoPlayView;
    ImageView play;
    MediaController mediaController;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);


        videoPlayView = findViewById(R.id.video);
        play = findViewById(R.id.playbutton);
        mediaController = new MediaController(this);



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                play.setVisibility(View.GONE);
                String video = helper.Path;
                uri = Uri.parse(video);
                videoPlayView.setVideoURI(uri);

                videoPlayView.setMediaController(mediaController);
                mediaController.setAnchorView(videoPlayView);
                videoPlayView.start();


            }
        });

    }
}
