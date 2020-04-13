package com.je_chen.justdrawitjedition;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import static com.je_chen.justdrawitjedition.Fight_Scene.Score;
import static com.je_chen.justdrawitjedition.GameView.Difficult;
import static com.je_chen.justdrawitjedition.GameView.GameView_Running;
import static com.je_chen.justdrawitjedition.GameView.Game_Over;

public class Start_Sence extends AppCompatActivity implements View.OnClickListener {

    public static int Max_Width,Max_Height;

    private byte Animation_State = 0;

    private Normal_Function NFuction = new Normal_Function();

    private Normal_Animation NAnimation = new Normal_Animation();

    private Button Play_Button,Difficult_Button;

    private ImageView startSence_BG1,startSence_BG2,startSence_BG3,startSence_BG4;

    private FileIO File_System;

    private AES_File AES = new AES_File();

    private String KeyAes="12345678876543219876543223456789";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //取得螢幕長寬
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics monitorsize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(monitorsize);
        Max_Width = monitorsize.widthPixels;
        Max_Height = monitorsize.heightPixels;

        //設置顯示的Layout
        setContentView(R.layout.activity_main);

        //設置物件
        Play_Button=findViewById(R.id.Play_Button);
        Difficult_Button=findViewById(R.id.Difficult_Button);
        startSence_BG1=findViewById(R.id.startSence_BG1);
        startSence_BG2=findViewById(R.id.startSence_BG2);
        startSence_BG3=findViewById(R.id.startSence_BG3);
        startSence_BG4=findViewById(R.id.startSence_BG4);
        //設置聆聽者
        Play_Button.setOnClickListener(this);
        Difficult_Button.setOnClickListener(this);

        //起始動畫
        NAnimation.Fade_View(startSence_BG1,250,0.1f,1.0f);
        NAnimation.Fade_View(startSence_BG2,500,0.1f,1.0f);
        NAnimation.Fade_View(startSence_BG3,750,0.1f,1.0f);
        NAnimation.Fade_View(startSence_BG4,1000,0.1f,1.0f);
        GameView_Running=false;

        File_System= new FileIO(getApplicationContext());

        try {
            Difficult = Integer.valueOf(Read_File("Difficult"));
        }catch (Exception e){
            e.printStackTrace();
            Difficult=1;
        }
        Log.w("Difficult",String.valueOf(Difficult));
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(Game_Over==true){
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle(getString(R.string.GameOver))
                    .setMessage("Your Score: "+String.valueOf(Score))
                    .setPositiveButton(getString(R.string.OK),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Game_Over=false;
                                }
                            })
                    .show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Singleton_MusicPlayer.instance().Start_Background_Music(getApplicationContext(), R.raw.es_stage2);
        Log.e("Start_Sence_Acivity","onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Singleton_MusicPlayer.instance().Stop_Background_Music();
        Log.e("Start_Sence_Acivity","onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Singleton_MusicPlayer.instance().Stop_Background_Music();
        Log.e("Start_Sence_Acivity","onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Singleton_MusicPlayer.instance().Stop_Background_Music();
        Log.e("Start_Sence_Acivity","onDestroy");
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {   //確定按下退出鍵
            onPause();
            new AlertDialog.Builder(Start_Sence.this)
                    .setCancelable(false)
                    .setTitle(getString(R.string.WantToLeave))
                    .setMessage(getString(R.string.Chose))
                    .setPositiveButton(getString(R.string.Leave),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("onKeyDown", "Close_the_app");
                                    Start_Sence.this.finish();
                                    System.exit(0);
                                }
                            })
                    .setNeutralButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("onKeyDown", "Close_the_app");
                            onResume();
                        }
                    })
                    .show();
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.Play_Button:
                NAnimation.CleanAm_Gone(new ImageView[]{startSence_BG1, startSence_BG2, startSence_BG3, startSence_BG4});
                Intent Start_Game = new Intent(Start_Sence.this, Fight_Scene.class);
                Singleton_MusicPlayer.instance().Start_Background_Music(getApplicationContext(),R.raw.encounter);
                Start_Game.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                GameView_Running=true;
                Game_Over=false;
                startActivity(Start_Game);
                this.finish();
                break;

            case R.id.Difficult_Button:
                onPause();
                new AlertDialog.Builder(Start_Sence.this)
                        .setCancelable(false)
                        .setTitle(getString(R.string.WantToLeave))
                        .setMessage(getString(R.string.Chose))
                        .setNeutralButton(getString(R.string.Easy), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("Difficult", "Choose Difficult");
                                onResume();
                                Difficult=1;
                                Save_File(String.valueOf(Difficult),"Difficult");
                                Log.w("Difficult",String.valueOf(Difficult));
                            }
                        })
                        .setNegativeButton(getString(R.string.Normal), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("Difficult", "Choose Difficult");
                                onResume();
                                Difficult=2;
                                Save_File(String.valueOf(Difficult),"Difficult");
                                Log.w("Difficult",String.valueOf(Difficult));
                            }
                        })
                        .setPositiveButton(getString(R.string.Hard), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("Difficult", "Choose Difficult");
                                onResume();
                                Difficult=3;
                                Save_File(String.valueOf(Difficult),"Difficult");
                                Log.w("Difficult",String.valueOf(Difficult));
                            }
                        })
                        .show();
                break;
        }
    }

    private String Aes_String(String SourceString){
        String Passward="";
        try {
             Passward=AES.encrypt256(SourceString,KeyAes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Passward;
    }

    private void Save_File(String Value,String File_Name){
        try {
            File_System.Save_File(Aes_String(Value),File_Name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String Read_File(String File_Name){
        String DePassward="";
        try {
             DePassward= AES.decrypt256(File_System.Read_File(File_Name),KeyAes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return DePassward;
    }


}
