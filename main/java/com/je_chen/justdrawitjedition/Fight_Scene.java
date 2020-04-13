package com.je_chen.justdrawitjedition;

import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import static com.je_chen.justdrawitjedition.GameView.EnemyList;
import static com.je_chen.justdrawitjedition.GameView.GameView_Running;
import static com.je_chen.justdrawitjedition.GameView.GameView_Thread;
import static com.je_chen.justdrawitjedition.GameView.Game_Over;

public class Fight_Scene extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener, GestureOverlayView.OnGesturingListener{

    protected FrameLayout frameLayout;

    protected  GestureOverlayView Fight_Darw_OverLayView;

    protected GameView Game_SurfaceView;

    protected  GestureLibrary GestureLib;

    public static int Score=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fight_Darw_OverLayView = new GestureOverlayView(this);
        Game_SurfaceView = new GameView(this);
        frameLayout=new FrameLayout(this);
        Fight_Darw_OverLayView.setGestureStrokeLengthThreshold(0.01f);
        Fight_Darw_OverLayView.setGestureStrokeSquarenessTreshold(0.01f);
        Fight_Darw_OverLayView.setGestureStrokeAngleThreshold(0.01f);
        Fight_Darw_OverLayView.addOnGesturePerformedListener(this);
        Fight_Darw_OverLayView.setVisibility(View.VISIBLE);
        Fight_Darw_OverLayView.setEnabled(true);
        GestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
        GestureLib.load();
        frameLayout.addView(Game_SurfaceView, 0);
        frameLayout.addView(Fight_Darw_OverLayView,1);
        setContentView(frameLayout);
        GameView_Running=false;
        Score=0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        onPause();
        Delay_Play(500);
        if(EnemyList.size()!=0 && GameView_Running==false){
            for(Enemy enemy: EnemyList){
                Thread thread = new Thread(enemy);
                thread.start();
            }
        }
        GameView_Running=true;
            if (GameView_Thread != null) {
                if (GameView_Thread.getState() == Thread.State.NEW) {
                    GameView_Thread.start();
                } else {
                    GameView_Thread = new Thread(GameView_Thread);
                    GameView_Thread.start();
                }
            }
        Game_SurfaceView.setVisibility(View.VISIBLE);
        Log.e("Fight_Sence_Acivity","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        GameView_Running=false;
        Singleton_MusicPlayer.instance().Stop_Background_Music();
        Log.e("Fight_Sence_Acivity","onPause");
        if(Game_Over==true) {
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Game_SurfaceView.setVisibility(View.GONE);
        Log.e("Fight_Sence_Acivity","onDestroy");
        this.finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {   //確定按下退出鍵
            onPause();
            new AlertDialog.Builder(Fight_Scene.this)
                    .setCancelable(false)
                    .setTitle(getString(R.string.WantToLeave))
                    .setMessage(getString(R.string.Chose))
                    .setPositiveButton(getString(R.string.Leave),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("onKeyDown", "Open Start_Sence");
                                    Fight_Scene.this.finish();
                                    Intent Start_Menu =new Intent(Fight_Scene.this,Start_Sence.class);
                                    Start_Menu.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    Fight_Scene.this.startActivity(Start_Menu);
                                    onResume();
                                }
                            })
                    .setNeutralButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("onKeyDown", "Cancel Open Start_Sence");
                            onResume();
                        }
                    })
                    .show();
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = GestureLib.recognize(gesture);
        Prediction prediction = predictions.get(0);
        if (prediction.score >= 5.0) {
            switch (prediction.name){

                case "magic_sr_x_01":
                    for(int i=0;i<EnemyList.size();i++){
                        EnemyList.get(i).Check_Tag(0);
                    }
                    break;
                case "magic_sr_x_03":
                    for(int i=0;i<EnemyList.size();i++){
                        EnemyList.get(i).Check_Tag(1);
                    }
                    break;
                case "magic_sr_x_05":
                    for(int i=0;i<EnemyList.size();i++){
                        EnemyList.get(i).Check_Tag(2);
                    }
                    break;
                case "magic_sr_x_07":
                    for(int i=0;i<EnemyList.size();i++){
                        EnemyList.get(i).Check_Tag(3);
                    }
                    break;
                case "magic_sr_x_c_1":
                    for(int i=0;i<EnemyList.size();i++){
                        EnemyList.get(i).Check_Tag(4);
                    }
                    break;
                case "magic_sr_x_d_1":
                    for(int i=0;i<EnemyList.size();i++){
                        EnemyList.get(i).Check_Tag(5);
                    }
                    break;
            }
        }
    }

    @Override
    public void onGesturingStarted(GestureOverlayView overlay) {

    }

    @Override
    public void onGesturingEnded(GestureOverlayView overlay) {

    }

    public void Delay_Play(final int time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                //延遲完後要幹嘛:
                if(GameView_Running==true)
                Singleton_MusicPlayer.instance().Start_Background_Music(getApplicationContext(),R.raw.encounter);

                if(Singleton_MusicPlayer.instance().Playing_Check()&& GameView_Running==true){
                    Singleton_MusicPlayer.instance().Start_Background_Music(getApplicationContext(),R.raw.encounter);
                    Delay_Play(100);
                }
                if(!Singleton_MusicPlayer.instance().Playing_Check() && GameView_Running==true){
                    Singleton_MusicPlayer.instance().Start_Background_Music(getApplicationContext(),R.raw.encounter);
                }
            }
        };
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                    Message msg = Message.obtain();
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }


}

