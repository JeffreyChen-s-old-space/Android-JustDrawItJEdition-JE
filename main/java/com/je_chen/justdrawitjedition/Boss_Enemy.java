package com.je_chen.justdrawitjedition;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

import static com.je_chen.justdrawitjedition.Fight_Scene.Score;
import static com.je_chen.justdrawitjedition.GameView.EnemyList;
import static com.je_chen.justdrawitjedition.Start_Sence.Max_Width;

public class Boss_Enemy extends Enemy implements Runnable{

    protected int Draw_Count=0;

    protected Boss_Enemy(Bitmap bitmap, ArrayList<Bitmap> Draw_Array, int Speed, int Run_Time, String Direction, int[] Array_Tag) {
        super(bitmap,Draw_Array,Speed,Run_Time,Direction,Array_Tag);
        this.Draw_Count=0;
    }

    @Override
    protected void InitFactory() {
        //取得寬高
        this.Object_H = this.Character_Bitmap.getHeight();
        this.Object_W = this.Character_Bitmap.getWidth();

        //設定xy
        this.Object_Y=0-(this.Object_H);
        this.Object_X=(Max_Width-this.Character_Bitmap.getWidth())/2;
        Start_Move = new Thread(this);
        Start_Move.start();
    }

    @Override
    protected void Check_Tag(int Tag){
        if(Tag==this.Array_Tag[Draw_Count]){
            this.HP-=1;
            if(Draw_Count<Draw_Array.size())
                Draw_Count+=1;
            if(this.HP<=0) {
                this.Run = false;
                EnemyList.remove(this);
            }
            if(Score<Integer.MAX_VALUE)
                Score+=1;
        }
    }

    @Override
    protected void OnDraw(Canvas canvas) {
        canvas.drawBitmap(this.Character_Bitmap, this.Object_X, this.Object_Y, null);
        canvas.drawBitmap(this.Draw_Array.get(Draw_Count), (Max_Width-this.Object_X+this.Draw_Array.get(Draw_Count).getWidth())/2,this.Object_Y-this.Draw_Array.get(Draw_Count).getHeight(), null);
    }

}
