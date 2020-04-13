/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */

package com.je_chen.justdrawitjedition;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import java.io.IOException;

public class Music_Service extends Service {

    private String Raw_Name;
    private int Raw_int;
    private MediaPlayer mp = new MediaPlayer();

    public boolean GetState(){
        return mp.isPlaying();
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.Raw_Name=intent.getStringExtra("Music_File");
        this.Raw_int=getResources().getIdentifier(Raw_Name, "raw", getPackageName());
        Log.e("Music_File",this.Raw_Name);
        AssetFileDescriptor file = getResources().openRawResourceFd(Raw_int);
        try {
            this.mp.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                    file.getLength());
            this.mp.prepare();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mp.setLooping(true);
        this.mp.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate(){
        super.onCreate();

    }

   @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e("MediaPlay Dead",this.Raw_Name);
       this.mp.stop();
       this.mp.release();
   }

}

