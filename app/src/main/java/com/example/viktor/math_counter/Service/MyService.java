package com.example.viktor.math_counter.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.viktor.math_counter.R;

public class MyService extends Service {

    MediaPlayer player;




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //Toast.makeText(this, "MyService run", Toast.LENGTH_SHORT).show();
        player = MediaPlayer.create(this, R.raw.fon);
        player.start();




    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}
