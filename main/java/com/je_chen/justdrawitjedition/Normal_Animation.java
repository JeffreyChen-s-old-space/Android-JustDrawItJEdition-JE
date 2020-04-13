package com.je_chen.justdrawitjedition;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by JE on 2017/7/12.
 * Edit by JE 2020/1/1
 */

public class Normal_Animation {

    //延遲並旋轉動畫
    public void Delay_Rotate(final View view,final long time,final long Rotate_Time,final short FromDegrees,final short ToDegrees) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //延遲完後要幹嘛:
                Animation am_right = new RotateAnimation(FromDegrees, ToDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                am_right.setDuration(Rotate_Time);
                am_right.setRepeatCount(Animation.INFINITE);
                view.startAnimation(am_right);
                am_right.startNow();
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

    //延遲並旋轉動畫 []
    public void Delay_Rotate(final View[] view,final long time,final long Rotate_Time,final short FromDegrees,final short ToDegrees) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //延遲完後要幹嘛:
                Animation am_right = new RotateAnimation(FromDegrees, ToDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                am_right.setDuration(Rotate_Time);
                am_right.setRepeatCount(Animation.INFINITE);
                for(View v : view ){
                    v.startAnimation(am_right);
                }
                am_right.startNow();
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

    //延遲並清除動畫
    public void Delay_CleanImageViewAm(final View view,final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:

                view.clearAnimation();
                view.setVisibility(View.GONE);
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

    //延遲並清除動畫
    public void Delay_CleanImageViewAm(final View[] view,final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //延遲完後要幹嘛:
                for(View v : view ){
                    v.clearAnimation();
                    v.setVisibility(View.GONE);
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

    //延遲並Fade
    public void Delay_Fade(final View view,final long time,final  long Fade_DoneTime,final float LowAlpha,final float HighAlpha) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //延遲完後要幹嘛:

                Animation am_fade = new AlphaAnimation(LowAlpha, HighAlpha);
                am_fade.setDuration(Fade_DoneTime);
                am_fade.setRepeatCount(Animation.INFINITE);
                am_fade.setRepeatMode(Animation.REVERSE);
                view.setAnimation(am_fade);
                am_fade.start();
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

    //延遲並Fade[]
    public void Delay_Fade(final View[] view,final long time,final  long Fade_DoneTime,final float LowAlpha,final float HighAlpha) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //延遲完後要幹嘛:

                Animation am_fade = new AlphaAnimation(LowAlpha, HighAlpha);
                am_fade.setDuration(Fade_DoneTime);
                am_fade.setRepeatCount(Animation.INFINITE);
                am_fade.setRepeatMode(Animation.REVERSE);
                for(View v : view){
                    v.setAnimation(am_fade);
                }
                am_fade.start();
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

    //轉動動畫
    public void Rotate(View view,int Rotate_Time,short FromDegegrees ,short ToDegrees){
        Animation am_left = new RotateAnimation(FromDegegrees,ToDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        am_left.setDuration(Rotate_Time);
        am_left.setRepeatCount(Animation.INFINITE);
        view.startAnimation(am_left);
        am_left.startNow();
    }

    //轉動動畫[]
    public void Rotate(View[] view,int Rotate_Time,short FromDegegrees ,short ToDegrees){
        Animation am_left = new RotateAnimation(FromDegegrees,ToDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        am_left.setDuration(Rotate_Time);
        am_left.setRepeatCount(Animation.INFINITE);
        for(View v : view){
            v.startAnimation(am_left);
        }
        am_left.startNow();
    }

    //閃爍動畫
    public void Fade_View(View view, int Fade_DoneTime,float LowAlpha,float HighAlpha){
        Animation am_fade = new AlphaAnimation(LowAlpha, HighAlpha);
        am_fade.setDuration(Fade_DoneTime);
        am_fade.setRepeatCount(Animation.INFINITE);
        am_fade.setRepeatMode(Animation.REVERSE);
        view.startAnimation(am_fade);
        am_fade.start();
    }

    //閃爍動畫[]
    public void Fade_View(View[] view, int Fade_DoneTime,float LowAlpha,float HighAlpha){
        Animation am_fade = new AlphaAnimation(LowAlpha, HighAlpha);
        am_fade.setDuration(Fade_DoneTime);
        am_fade.setRepeatCount(Animation.INFINITE);
        am_fade.setRepeatMode(Animation.REVERSE);
        for(View v : view){
            v.startAnimation(am_fade);
        }
        am_fade.start();
    }

    //停止並隱藏View
    public void CleanAm_INVISIBLE(View view){
        view.clearAnimation();
        view.setVisibility(View.INVISIBLE);
    }

    //停止並隱藏View[]
    public void CleanAm_INVISIBLE(View[] view){
        for(View v : view){
            v.clearAnimation();
            v.setVisibility(View.INVISIBLE);
        }
    }

    //停止動畫且清掉View
    public void CleanAm_Gone(View view ){
            view.clearAnimation();
            view.setVisibility(View.GONE);
    }

    //停止動畫且清掉View[]
    public void CleanAm_Gone(View[] view ){
        for(View v : view){
            v.clearAnimation();
            v.setVisibility(View.GONE);
        }
    }

    //停止動畫
    public void StopAm(View view){
        view.clearAnimation();
    }

    //停止動畫[]
    public void StopAm(View []view){
        for(View v : view){
            v.clearAnimation();
        }
    }

}


