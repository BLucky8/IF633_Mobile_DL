package umn.ac.id.uts_31943;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static umn.ac.id.uts_31943.MainActivity.song;

public class MPlayer extends AppCompatActivity {

    ImageView cardImage, previous, next;
    TextView songName, songArtis, durationPlayed, durationTotal;
    SeekBar seekBarDuration;
    FloatingActionButton playPause;
    int position = -1;
    private Handler handler = new Handler();
    private Thread playThread, prevThread, nextThread;
    static ArrayList<Song> songs = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_music);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Music Player");
        initSongDetailView();
        getIntentMethod();
        songName.setText(songs.get(position).getTitle());
        songArtis.setText(songs.get(position).getArtist());
        seekBarDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        MPlayer.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBarDuration.setProgress(mCurrentPosition);
                    durationPlayed.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    protected void onResume() {
        playThreadBtn();
        nextThreadBtn();
        prevThreadBtn();
        super.onResume();
    }

    private void playThreadBtn(){
        playThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                playPause.setOnClickListener(v -> playPauseClicked());
            }
        };
        playThread.start();
    }

    private void playPauseClicked(){
        if(mediaPlayer.isPlaying()){
            playPause.setImageResource(R.drawable.play);
            mediaPlayer.pause();
            seekBarDuration.setMax(mediaPlayer.getDuration() / 1000);
            MPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBarDuration.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }else{
            playPause.setImageResource(R.drawable.ic_baseline_pause_24);
            mediaPlayer.start();
            seekBarDuration.setMax(mediaPlayer.getDuration() / 1000);
            MPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBarDuration.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
    }

    private void nextThreadBtn() {
        nextThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                next.setOnClickListener(v -> nextClicked());
            }
        };
        nextThread.start();
    }

    private void nextClicked(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) % songs.size());
            uri = Uri.parse(songs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            songName.setText(songs.get(position).getTitle());
            songArtis.setText(songs.get(position).getArtist());
            MPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBarDuration.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            playPause.setImageResource(R.drawable.ic_baseline_pause_24);
            mediaPlayer.start();
        }else{
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) % songs.size());
            uri = Uri.parse(songs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            songName.setText(songs.get(position).getTitle());
            songArtis.setText(songs.get(position).getArtist());
            MPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBarDuration.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            playPause.setImageResource(R.drawable.play);
        }
    }

    private void prevThreadBtn(){
        prevThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                previous.setOnClickListener(v -> prevClicked());
            }
        };
        prevThread.start();
    }

    private void prevClicked(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? (songs.size() - 1) : (position - 1));
            uri = Uri.parse(songs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            songName.setText(songs.get(position).getTitle());
            songArtis.setText(songs.get(position).getArtist());
            MPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBarDuration.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            playPause.setImageResource(R.drawable.ic_baseline_pause_24);
            mediaPlayer.start();
        }else{
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? (songs.size() - 1) : (position - 1));
            uri = Uri.parse(songs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            songName.setText(songs.get(position).getTitle());
            songArtis.setText(songs.get(position).getArtist());
            MPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBarDuration.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            playPause.setImageResource(R.drawable.play);
        }
    }

    private String formattedTime(int mCurrentPosition){
        String totalDurationOut = "";
        String totalDurationNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalDurationOut = minutes + ":" + seconds;
        totalDurationNew = minutes + ":" + "0" + seconds;
        if(seconds.length() == 1){
            return totalDurationNew;
        }else{
            return totalDurationOut;
        }
    }

    private void getIntentMethod(){
        position = getIntent().getIntExtra("position", -1);
        songs = song;
        if(songs != null){
            playPause.setImageResource(R.drawable.ic_baseline_pause_24);
            uri = Uri.parse(songs.get(position).getPath());
        }
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }else{
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        seekBarDuration.setMax(mediaPlayer.getDuration() / 1000);
        metaData(uri);
    }

    private void initSongDetailView(){
        songName = findViewById(R.id.songName);
        songArtis = findViewById(R.id.songArtis);
        durationPlayed = findViewById(R.id.durationPlayed);
        durationTotal = findViewById(R.id.durationTotal);
        cardImage = findViewById(R.id.cardImage);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        playPause = findViewById(R.id.playPause);
        seekBarDuration = findViewById(R.id.seekBarDuration);
    }

    private void metaData(Uri uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int duration_total = Integer.parseInt(songs.get(position).getDuration()) / 1000;
        durationTotal.setText(formattedTime(duration_total));
        byte[] img = retriever.getEmbeddedPicture();
        if(img != null){
            Glide.with(this).asBitmap().load(img).into(cardImage);
        }else{
            Glide.with(this).asBitmap().load(R.drawable.cover_art).into(cardImage);
        }
    }
}