package com.je_chen.justdrawitjedition;

import android.graphics.Bitmap;


public class Normal_Enemy extends Enemy implements Runnable{

    protected Normal_Enemy(Bitmap bitmap, Bitmap Draw_Thing, int Speed, int Run_Time, String Direction, int Tag) {
        super(bitmap, Draw_Thing, Speed, Run_Time, Direction, Tag);
    }
}
