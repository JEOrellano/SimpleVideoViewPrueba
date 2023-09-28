package com.example.simplevideoviewprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private static final String VIDEO_SAMPLE = "suvideo";
    private VideoView mVideoView;
    private int mCurrentPoint = 0;
    private static final String PLAYBACK_TIME = "play_time";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = findViewById(R.id.videoView);
        MediaController controlador = new MediaController(this);
        mVideoView.setMediaController(controlador);
        controlador.setMediaPlayer(mVideoView);
        if(savedInstanceState != null){
            mCurrentPoint = savedInstanceState.getInt(PLAYBACK_TIME);
        }
    }
    private Uri getMedia(String mediaName) {
        return Uri.parse("android.resource://"+getPackageName()+"/raw/"+mediaName);
    }
    private void initializePlayer(){
        Uri videoUri = getMedia(VIDEO_SAMPLE);
        mVideoView.setVideoURI(videoUri);
        if(mCurrentPoint>0){
            mVideoView.seekTo(mCurrentPoint);
        } else {
          //Saltar a 1 muestra el primer cuadro del video
          mVideoView.seekTo(1);
        }
        mVideoView.start();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mediaPlayer){
                //Implementation here
                Toast.makeText(MainActivity.this, "Playback completed", Toast.LENGTH_SHORT).show();
                mVideoView.seekTo(1);
            }
        });
    }
    private void releasePlayer(){
        mVideoView.stopPlayback();
    }
    @Override
    protected void onStart(){
        super.onStart();
        initializePlayer();
    }
    @Override
    protected void onStop(){
        super.onStop();
        releasePlayer();
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
            mVideoView.pause();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }
}
