

package com.je_chen.justdrawitjedition;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by JE on 2017/8/2.
 * Edit by JE on 2020/1/1.
 */

public class Normal_Function {
    private static Toast toast;
    private static long lastClickTime;

    //延遲並改變圖像 ImageView
    public void Delay_Change_Image_ImageView(final ImageView image1, final long time,final int drawable) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                image1.setImageResource(drawable);
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

    //延遲並改變圖像 ImageButton
    public void Delay_Change_Image_ImageButton(final ImageButton imagebutton, final long time, final int drawable) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                imagebutton.setImageResource(drawable);
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

    //延遲毫秒 Gone
    public void Delay_Gone_View(final View view, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
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

    //延遲毫秒 並顯示View
    public void JExDelay_show_Edittext(final View view, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                view.setVisibility(View.VISIBLE);
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

    //延遲毫秒 並取消顯示View
    public void Delay_View_Invisible(final View view, final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                view.setVisibility(View.INVISIBLE);
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


 //延遲並讓View可按
    public void Delay_Enable_View(final View view,final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:

                view.setEnabled(true);

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

    public void Delay_Change_Text(final TextView view, final long time, final String change_text) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:

                view.setText(change_text);

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

    public void Get_Now_Time(final long time,final TextView text,final String stext) {

        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//延遲完後要幹嘛:
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String date = sDateFormat.format(new java.util.Date());
                text.setText(date+stext);
                text.setVisibility(View.VISIBLE);

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

    //延遲毫秒 並顯示Toast
    public void ShowToast(final Toast t1,final long time) {
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //延遲完後要幹嘛:
                t1.show();
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

    //Show出可決定方向跟偏移的Toast
    public  void ShowToast(Context context,
                                 String content,int gravity,int xOffset,int yOffset) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.setGravity(gravity,xOffset,yOffset);
        toast.show();

    }

    //判斷是否太快點擊
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 300) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    //重製ScrollView scroll bar至起始點
    public void scroll_reset(ScrollView scrollView,long scroll_y){
        int scroll_weight = scrollView.getHeight();
        scrollView.scrollTo(0,scroll_weight);
        scrollView.scrollTo(0,0);
        scroll_y=0;
    }





}











