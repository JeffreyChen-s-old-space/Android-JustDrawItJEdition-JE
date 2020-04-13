package com.je_chen.justdrawitjedition;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import static com.je_chen.justdrawitjedition.GameView.EnemyList;
import static com.je_chen.justdrawitjedition.GameView.GameView_Running;
import static com.je_chen.justdrawitjedition.GameView.Game_Over;
import static com.je_chen.justdrawitjedition.Start_Sence.Max_Height;
import static com.je_chen.justdrawitjedition.Start_Sence.Max_Width;
import static com.je_chen.justdrawitjedition.Fight_Scene.Score;

public class Enemy implements Runnable{
    protected boolean Run=true;
    protected Thread Start_Move;
    protected Bitmap Character_Bitmap,Draw_Thing;
    protected ArrayList<Bitmap> Draw_Array;
    protected int Speed;
    protected  int Tag;
    protected  int[] Array_Tag;
    protected String Direction;
    protected int HP=1;
    protected int Object_H,Object_W,Object_Y,Object_X;
    protected int Run_Time=5;

    protected Enemy(Bitmap bitmap,Bitmap Draw_Thing,int Speed,int Run_Time,String Direction,int Tag){
        this.Character_Bitmap=bitmap;
        this.Draw_Thing=Draw_Thing;
        this.Speed=Speed;
        this.Tag=Tag;
        this.Run_Time=Run_Time;
        this.Direction=Direction;
        this.InitFactory();
        Log.e("Tag_In_Enemy",String.valueOf(Tag));
    }

    protected Enemy(Bitmap bitmap, ArrayList<Bitmap> Draw_Array, int Speed, int Run_Time, String Direction, int[] Array_Tag){
        this.Character_Bitmap=bitmap;
        this.Speed=Speed;
        this.Array_Tag=Array_Tag;
        this.Run_Time=Run_Time;
        this.Direction=Direction;
        this.Draw_Array=new ArrayList<Bitmap>();
        this.Draw_Array.clear();
        this.Draw_Array=Draw_Array;
        this.HP=Draw_Array.size();
        for (int i=0;i<Draw_Array.size();i++)
            Log.e("Draw_Array_Tag",String.valueOf(this.Draw_Array.get(i)));
        this.InitFactory();
    }


    protected void InitFactory() {
        //取得寬高
        this.Object_H = this.Character_Bitmap.getHeight();
        this.Object_W = this.Character_Bitmap.getWidth();

        //設定xy
        this.Object_Y=0-(this.Object_H);
        this.Object_X=(int)(Math.random()*(Max_Width-this.Object_W));
        Start_Move = new Thread(this);
        Start_Move.start();
    }

    protected boolean Is_Alive() {
        return this.Run;
    }

    protected void Position_Change() {
        //按照移動的方向來改變物件的座標位置
        if (this.Direction.equals("Down")) {
                // y 值增加
                this.Object_Y+= this.Speed;
                if (this.Object_Y >= Max_Height) {
                    this.Run=false;
                    EnemyList.remove(this);
                    Game_Over=true;
        }
    }
}

    protected void Check_Tag(int Tag){
        if(Tag==this.Tag){
            this.HP-=1;
            if(this.HP==0) {
                this.Run = false;
                EnemyList.remove(this);
            }
            if(Score<Integer.MAX_VALUE)
                Score+=1;
        }
    }

    protected void OnDraw(Canvas canvas) {
        canvas.drawBitmap(this.Character_Bitmap, this.Object_X, this.Object_Y, null);
        canvas.drawBitmap(this.Draw_Thing, this.Object_X,this.Object_Y-this.Object_H, null);
    }

    @Override
    public void run() {

        while (Run==true && GameView_Running==true){
            this.Position_Change();
            try {
                Thread.sleep(Run_Time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

