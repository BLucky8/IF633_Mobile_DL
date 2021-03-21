package umn.ac.id.uts_31943;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Button toProfile, toLogin;
    public static final int PERMISSION_CODES = 1;
    public static ArrayList<Song> song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toProfile = findViewById(R.id.toProfile);
        toLogin = findViewById(R.id.toLogin);
        permissionMusic();

        toLogin.setOnClickListener(v -> {//
            Intent intentKeLogin = new Intent(MainActivity.this, Login.class);
            startActivity(intentKeLogin);
        });

        toProfile.setOnClickListener(v -> {//
            Intent intentKeProfile = new Intent(MainActivity.this, Profile.class);
            startActivity(intentKeProfile);
        });
    }

    public static ArrayList<Song> getAllMusic(Context context){
        ArrayList<Song> tempListLagu = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projectionMusic = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST
        };
        Cursor cursor = context.getContentResolver().query(uri, projectionMusic, null, null, null);
        if(cursor != null){
            while(cursor.moveToNext()){
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String path = cursor.getString(3);
                String artist = cursor.getString(4);

                Song song = new Song(path, title, artist, album, duration);
                Log.e("Path : " + path, "Album : " + album);
                tempListLagu.add(song);
            }
            cursor.close();
        }
        return tempListLagu;
    }

    private void permissionMusic(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_CODES);
        }else{
            song = getAllMusic(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CODES){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                song = getAllMusic(this);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_CODES);
            }
        }
    }

}