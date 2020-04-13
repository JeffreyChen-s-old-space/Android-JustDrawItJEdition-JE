package com.je_chen.justdrawitjedition;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

import static com.je_chen.justdrawitjedition.Fight_Scene.Score;


public class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private Surface GameView_holder;
    private Context GameContext;
    public static Thread GameView_Thread;
    public static boolean GameView_Running = true;
    private Canvas canvas = null;

    private Resources res;
    private  int Rand;
    private int[] Rand_Array;
    private Paint paint;

    public static boolean Game_Over=false;
    public static  int Difficult=3;

    private Bitmap_Factory BFactory;

    private int Dp_H,Dp_W,Dp_H_Boss,Dp_W_Boss;

    private Bitmap BackGround, NormalEnemyBitmap,BossEnemyBitmap,SpecialEnemyBitmap;

    private boolean Can_See_Gameview=false;

    private int Speed=5,RunTime=50;

    private Bitmap magic_sr_x_01;
    private Bitmap magic_sr_x_03;
    private Bitmap magic_sr_x_05;
    private Bitmap magic_sr_x_07;
    private Bitmap magic_sr_x_c_1;
    private Bitmap magic_sr_x_d_1;

    public static ArrayList<Enemy> EnemyList;

    public GameView(Context context) {
        super(context);
        this.BFactory = new Bitmap_Factory(context);
        this.res = getResources();
        getHolder().addCallback(this);
        this.holder = getHolder();
        this.GameView_holder = holder.getSurface();
        this.GameView_Thread = new Thread(this);
        this.GameContext = context;
        this.Inin_Game(context);
    }

    protected void Inin_Game(Context context) {

        Dp_H =(int)BFactory.pxFromDp(context,75);
        Dp_W =(int)BFactory.pxFromDp(context,75);

        Dp_H_Boss =(int)BFactory.pxFromDp(context,125);
        Dp_W_Boss =(int)BFactory.pxFromDp(context,125);

        this.BackGround = BitmapFactory.decodeResource(res, R.drawable.back_ground);

        this.NormalEnemyBitmap = BitmapFactory.decodeResource(res, R.drawable.enemy_type_1);
        NormalEnemyBitmap=BFactory.Re_Scale_Bitmap(NormalEnemyBitmap,Dp_W,Dp_H);

        this.BossEnemyBitmap = BitmapFactory.decodeResource(res, R.drawable.enemy_boss_01);
        BossEnemyBitmap=BFactory.Re_Scale_Bitmap(BossEnemyBitmap,Dp_W_Boss,Dp_H_Boss);

        this.SpecialEnemyBitmap = BitmapFactory.decodeResource(res, R.drawable.enemy_02);
        SpecialEnemyBitmap=BFactory.Re_Scale_Bitmap(SpecialEnemyBitmap,Dp_W,Dp_H);

        this.magic_sr_x_01 = BitmapFactory.decodeResource(res, R.drawable.magic_sr_x_01);
        magic_sr_x_01=BFactory.Re_Scale_Bitmap(magic_sr_x_01,Dp_W,Dp_H);

        this.magic_sr_x_03 = BitmapFactory.decodeResource(res, R.drawable.magic_sr_x_03);
        magic_sr_x_03=BFactory.Re_Scale_Bitmap(magic_sr_x_03,Dp_W,Dp_H);

        this.magic_sr_x_05 = BitmapFactory.decodeResource(res, R.drawable.magic_sr_x_05);
        magic_sr_x_05=BFactory.Re_Scale_Bitmap(magic_sr_x_05,Dp_W,Dp_H);

        this.magic_sr_x_07 = BitmapFactory.decodeResource(res, R.drawable.magic_sr_x_07);
        magic_sr_x_07=BFactory.Re_Scale_Bitmap(magic_sr_x_07,Dp_W,Dp_H);

        this.magic_sr_x_c_1 = BitmapFactory.decodeResource(res, R.drawable.magic_sr_x_c_1);
        magic_sr_x_c_1=BFactory.Re_Scale_Bitmap(magic_sr_x_c_1,Dp_W,Dp_H);

        this.magic_sr_x_d_1 = BitmapFactory.decodeResource(res, R.drawable.magic_sr_x_d_1);
        magic_sr_x_d_1=BFactory.Re_Scale_Bitmap(magic_sr_x_d_1,Dp_W,Dp_H);

        this.EnemyList = new ArrayList<Enemy>();
        this.EnemyList.clear();

        paint=new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth((int)BFactory.pxFromDp(context,12));
        paint.setTextSize((int)BFactory.pxFromDp(context,30));

        if(Difficult<0 || Difficult>3)
            Difficult=1;

        switch (Difficult){
            case 1:
                Speed=(int)BFactory.pxFromDp(context,2);;RunTime=50;
                break;
            case 2:
                Speed=(int)BFactory.pxFromDp(context,3);RunTime=50;
                break;
            case 3:
                Speed=(int)BFactory.pxFromDp(context,4);RunTime=50;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("GameView", "surfaceCreated");
            GameView_Thread = new Thread(this);
            GameView_Thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("GameView", "surfaceDestroyed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("GameView", "surfaceDestroyed");
    }


    @Override
    public void onVisibilityChanged(View view, int visibility) {
        super.onVisibilityChanged(view, visibility);
        if(visibility == VISIBLE){
            Can_See_Gameview=true;
            if(Singleton_MusicPlayer.instance().Playing_Check()) {
                Singleton_MusicPlayer.instance().Start_Background_Music(GameContext, R.raw.encounter);
                Log.w("surfaceDestroyed", String.valueOf(Singleton_MusicPlayer.instance().Playing_Check()));
            }
        }
        else if (visibility == INVISIBLE){
            Can_See_Gameview=false;
        }else if(visibility==GONE){
            Can_See_Gameview=false;
        }
    }



    @Override
    public void run() {
        Log.d("GameView", "run");

        while (GameView_Running && Can_See_Gameview) {

            try {

                if(Game_Over){
                    Intent Start_Menu =new Intent(GameContext,Start_Sence.class);
                    Start_Menu.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    GameContext.startActivity(Start_Menu);
                }
                //取得並鎖住畫布(canvas)
                canvas = holder.lockCanvas();
                //清除畫面
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                //畫出背景
                canvas.drawBitmap(BackGround, 0f, 0f, null);

                canvas.drawText( GameContext.getResources().getString(R.string.Score)+String.valueOf(Score),BFactory.pxFromDp(GameContext,1),BFactory.pxFromDp(GameContext,30),paint);

                if(EnemyList.isEmpty() && Score %20 == 0 && Score!=0){
                    EnemyList.add(new Boss_Enemy(BossEnemyBitmap,this.Get_Array_Bitmap("Boss"),Speed, RunTime,"Down",Rand_Array));
                   switch (Difficult){
                       case 1:
                           Speed+=(int)BFactory.pxFromDp(GameContext,1);
                           break;
                       case 2:
                           Speed+=(int)BFactory.pxFromDp(GameContext,2);
                           break;
                       case 3:
                           Speed+=(int)BFactory.pxFromDp(GameContext,3);
                           break;
                   }
                }else if(EnemyList.isEmpty() && Score %5 == 0 && Score!=0 && Difficult>1){
                    EnemyList.add(new Special_Enemy(SpecialEnemyBitmap,this.Get_Array_Bitmap("Special"),Speed, RunTime,"Down",Rand_Array));
                }else if(EnemyList.isEmpty()){
                    EnemyList.add(new Normal_Enemy(NormalEnemyBitmap,this.Get_Bitmap(),Speed, RunTime,"Down",Rand));
                }

                for(Enemy enemy : EnemyList){
                    enemy.OnDraw(canvas);
                    System.gc();
                }


                Thread.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    //解鎖畫布(canvas)並顯示到螢幕上
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        } //while
    }

    private Bitmap Get_Bitmap() {

        Bitmap Get = magic_sr_x_01;

        Rand = (int) (Math.random() * 5);
        Log.e("Rand_Gesture",String.valueOf(Rand));
        switch (Rand) {
            case 0:
                Get = magic_sr_x_01;
                break;
            case 1:
                Get = magic_sr_x_03;
                break;
            case 2:
                Get = magic_sr_x_05;
                break;
            case 3:
                Get = magic_sr_x_07;
                break;
            case 4:
                Get = magic_sr_x_c_1;
                break;
            case 5:
                Get = magic_sr_x_d_1;
                break;
        }
        return Get;
    }

    private ArrayList<Bitmap> Get_Array_Bitmap(String Type) {

        Bitmap Get = magic_sr_x_01;
        ArrayList<Bitmap> Return_Array_Bitmap;
        Return_Array_Bitmap=new ArrayList<Bitmap>();
        Return_Array_Bitmap.clear();
        int range=2;

        switch (Difficult) {
            case 1:
                if(Type.equals("Boss")) {
                    Rand_Array = new int[3];
                }else{
                    Rand_Array = new int[2];
                    range=1;
                }
                break;
            case 2:
                if(Type.equals("Boss")) {
                    Rand_Array = new int[5];
                    range=4;
                }else{
                    Rand_Array = new int[3];
                    range=2;
                }
                break;
            case 3:
                if(Type.equals("Boss")) {
                    Rand_Array = new int[7];
                    range=6;
                }else{
                    Rand_Array = new int[4];
                    range=3;
                }
                break;
        }
        for(int i=0;i<=range;i++) {
            Rand = (int) (Math.random() * 5);
            Log.e("Rand_Gesture_Array", String.valueOf(Rand));
            switch (Rand) {
                case 0:
                    Get = magic_sr_x_01;
                    Return_Array_Bitmap.add(Get);
                    Rand_Array[i]=Rand;
                    break;
                case 1:
                    Get = magic_sr_x_03;
                    Return_Array_Bitmap.add(Get);
                    Rand_Array[i]=Rand;
                    break;
                case 2:
                    Get = magic_sr_x_05;
                    Return_Array_Bitmap.add(Get);
                    Rand_Array[i]=Rand;
                    break;
                case 3:
                    Get = magic_sr_x_07;
                    Return_Array_Bitmap.add(Get);
                    Rand_Array[i]=Rand;
                    break;
                case 4:
                    Get = magic_sr_x_c_1;
                    Return_Array_Bitmap.add(Get);
                    Rand_Array[i]=Rand;
                    break;
                case 5:
                    Get = magic_sr_x_d_1;
                    Return_Array_Bitmap.add(Get);
                    Rand_Array[i]=Rand;
                    break;
            }
        }
        for (int i=0;i<Return_Array_Bitmap.size();i++)
            Log.w("Rand_Gesture_Array",String.valueOf(Return_Array_Bitmap.get(i)));
        for (int i=0;i<Rand_Array.length;i++)
            Log.w("Rand_Array",String.valueOf(Rand_Array[i]));
        return Return_Array_Bitmap;
    }

}

