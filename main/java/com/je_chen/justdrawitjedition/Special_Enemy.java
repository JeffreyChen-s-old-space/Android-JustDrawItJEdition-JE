package com.je_chen.justdrawitjedition;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.ArrayList;

public class Special_Enemy extends Boss_Enemy implements Runnable{

    protected Special_Enemy(Bitmap bitmap, ArrayList<Bitmap> Draw_Array, int Speed, int Run_Time, String Direction, int[] Array_Tag) {
        super(bitmap, Draw_Array, Speed, Run_Time, Direction, Array_Tag);
    }

    @Override
    protected void OnDraw(Canvas canvas) {
        canvas.drawBitmap(this.Character_Bitmap, this.Object_X, this.Object_Y, null);
        canvas.drawBitmap(this.Draw_Array.get(Draw_Count), this.Object_X,this.Object_Y-this.Draw_Array.get(Draw_Count).getHeight(), null);
    }

}

