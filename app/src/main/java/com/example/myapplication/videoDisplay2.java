package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class videoDisplay2 extends AppCompatActivity {

    private ArrayList<String> videoUrls;
    private int currentVideoIndex = 0;
    private VideoView videoView;
    private MediaController mediaController;
    url ob = new url();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_display2);

        videoUrls = getIntent().getStringArrayListExtra("videoUrls");

        videoView = findViewById(R.id.videoViewAll);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        if (videoUrls != null && videoUrls.size() > 0) {
            playVideo(currentVideoIndex);
        }
    }

    private void playVideo(int index) {
        if (index >= 0 && index < videoUrls.size()) {
            Uri videoUri = Uri.parse(videoUrls.get(index));
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(videoUri);
            videoView.requestFocus();
            videoView.start();

            // Set a completion listener to play the next video when the current one finishes
            videoView.setOnCompletionListener(mp -> {
                currentVideoIndex = (currentVideoIndex + 1) % videoUrls.size();
                playVideo(currentVideoIndex);
            });
        }
    }
}
