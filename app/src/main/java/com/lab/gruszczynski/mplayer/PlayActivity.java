package com.lab.gruszczynski.mplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

/**
 * Created by maciej on 05.06.17.
 */
public class PlayActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private ImageButton rewind;
    private ImageButton forward;
    private ImageButton playPause;
    private ImageView cover;
    private TextView album;
    private TextView performer;
    private TextView title;
    private Context context;
    private MediaPlayer mediaPlayer;
    private int sound;
    private Handler mHandler;
    private Activity mainActivity;
    private  Runnable mediaPlayerRunnable;
    private Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        this.intent = intent;
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        rewind = (ImageButton) findViewById(R.id.rewindBtn);
        forward = (ImageButton) findViewById(R.id.forwardBtn);
        playPause = (ImageButton) findViewById(R.id.playPauseBtn);
        cover = (ImageView) findViewById(R.id.playerCoverIV);
        album = (TextView) findViewById(R.id.playerAlbumTV);
        performer = (TextView) findViewById(R.id.playerPerformerTV);
        title = (TextView) findViewById(R.id.playerTitleTV);
        context = this;

        intent = getIntent();
        rewind.setImageDrawable(getDrawable(R.drawable.ic_fast_rewind_black_24dp));
        rewind.setOnClickListener(new RewindClickListener(this));
        forward.setImageDrawable(getDrawable(R.drawable.ic_fast_forward_black_24dp));
        forward.setOnClickListener(new ForwardClickListener(this));
        playPause.setImageDrawable(getDrawable(R.drawable.ic_play_arrow_black_24dp));
        playPause.setOnClickListener(new PlayPauseClickListener(this));
        cover.setImageDrawable(getDrawable(intent.getIntExtra("cover", 0)));
        album.setText(intent.getStringExtra("album"));
        performer.setText(intent.getStringExtra("performer"));
        title.setText(intent.getStringExtra("title"));
        sound = intent.getIntExtra("sound", 0);
        seekBar.setOnSeekBarChangeListener(new SeekBarListener());
        mHandler = new Handler();
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            Drawable coverDrawable = getDrawable(intent.getIntExtra("cover", 0));
            coverDrawable.setAlpha(100);
            cover.setImageDrawable(coverDrawable);
        }
        else{
            Drawable coverDrawable = getDrawable(intent.getIntExtra("cover", 0));
            coverDrawable.setAlpha(255);
            cover.setImageDrawable(coverDrawable);
        }
        mediaPlayer = MediaPlayer.create(this, sound);
        seekBar.setMax(mediaPlayer.getDuration());
        mediaPlayerRunnable = new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
                if(mediaPlayer != null && !mediaPlayer.isPlaying()){
                    playPause.setImageDrawable(getDrawable(R.drawable.ic_play_arrow_black_24dp));
                }
                mHandler.postDelayed(this, 100);
            }
        };

        runOnUiThread(mediaPlayerRunnable);

    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(mediaPlayerRunnable);
        mediaPlayer.release();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mediaPlayer.release();
        super.onStop();
    }

    @Override
    public void onResume(){
        mediaPlayer = MediaPlayer.create(this, sound);
        runOnUiThread(mediaPlayerRunnable);
        super.onResume();
    }

    private class PlayPauseClickListener implements View.OnClickListener {
        private Context context;

        public PlayPauseClickListener(Context context){
        this.context = context;
        }

        @Override
        public void onClick(View v) {
            Log.d("@@@@@@@@@", mediaPlayer.toString());
            if(mediaPlayer==null){
                return ;
            }
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                ((ImageButton)v).setImageDrawable(context.getDrawable(R.drawable.ic_play_arrow_black_24dp));
            }
            else{
                mediaPlayer.start();
                ((ImageButton)v).setImageDrawable(context.getDrawable(R.drawable.ic_pause_black_24dp));
            }
        }
    }

    private class RewindClickListener implements View.OnClickListener {
        private Context context;

        public RewindClickListener(Context context){
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            if(mediaPlayer==null){
                return ;
            }
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 5000);
            }
        }
    }

    private class ForwardClickListener implements View.OnClickListener {
        private Context context;

        public ForwardClickListener(Context context){
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            if(mediaPlayer==null){
                return ;
            }
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000);
            }
        }
    }

    private class SeekBarListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mHandler.removeCallbacks(mediaPlayerRunnable);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mediaPlayer.seekTo(seekBar.getProgress());
            runOnUiThread(mediaPlayerRunnable);
        }
    }


}
