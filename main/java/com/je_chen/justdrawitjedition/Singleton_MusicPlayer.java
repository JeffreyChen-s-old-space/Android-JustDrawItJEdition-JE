package com.je_chen.justdrawitjedition;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class Singleton_MusicPlayer {

    private static final String TAG = Singleton_MusicPlayer.class.getName();

    private static Singleton_MusicPlayer instance;
    public static Singleton_MusicPlayer instance() {
        if(instance==null) instance = new Singleton_MusicPlayer();
        return instance;
    }

    private MediaPlayer mediaPlayer;

    protected void Start_Background_Music(Context ctx, int res) {
        if(mediaPlayer==null) {
            mediaPlayer = MediaPlayer.create(ctx, res);
            mediaPlayer.setLooping(true);
        }
        if(mediaPlayer!=null&&!mediaPlayer.isPlaying())
            Log.i(TAG,"started");
        mediaPlayer.start();
    }

    protected void Stop_Background_Music() {
        if(mediaPlayer!=null) {
            Log.i(TAG, "stopped");
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    protected boolean Playing_Check(){
        if(mediaPlayer!=null)
        return mediaPlayer.isPlaying();
        return false;
    }
}
