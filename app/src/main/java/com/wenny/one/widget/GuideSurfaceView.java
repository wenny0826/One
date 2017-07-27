package com.wenny.one.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

/**
 * Created by wenny on 2016/10/24.
 */

public class GuideSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private int[] ids;

    private SurfaceHolder surfaceHolder;
    private int index = 0;
    private int time;
    private boolean isFirst = true;//默认播放一次
    private boolean isRollBack = false;//是否反转播放

    private boolean isStop = true;//是否停止播放
    private Bitmap bitmap;

    public GuideSurfaceView(Context context) {
        this(context, null);
    }

    public GuideSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setListIds(int[] ids) {
        this.ids = ids;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private void init() {
        //1、从SurfaceView中获得SurfaceHolder
        surfaceHolder = this.getHolder();
        //2、给SurfaceHolder添加回调
        surfaceHolder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Surface视图创建完成，进行绘制的操作
//        draw();
        new MyThread().start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        index = -1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            while (isStop) {
                if(isFirst && !isRollBack && index < ids.length && index >= 0){
                    firstPlay();
                }
                //无限循环播放
                if(!isFirst && !isRollBack){
                    loopPlay();
                }
                //无限循环反转播放
                if (!isFirst && isRollBack){
                    loopRollBack();
                }
            }
        }
    }

    private void draw() {
        bitmap = BitmapFactory.decodeResource(getResources(), ids[index]);
        int imgWidth = bitmap.getWidth();
        int imgHeight = bitmap.getHeight();
        int width = getWidth();
        int heigth = getHeight();
        //3、通过SurfaceHolder锁定画布
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            //绘制底色，覆盖原来的绘制内容
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap, width/2 - imgHeight/2, heigth/2 - imgWidth/2, null);

            //4、绘制完成以后，解锁并且提交画布
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * 只顺序播放一次
     */
    private void firstPlay(){
        draw();
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        index++;
    }

    /**
     * 无限顺序播放
     */
    private void loopPlay(){
        draw();
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        index++;
        if(index == ids.length){
            index = 0;
        }
    }

    /**
     * 无限循环反转播放
     */
    private void loopRollBack(){

        boolean startRollBack = true;//是否开始反转
        draw();
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(index == ids.length -1){
            startRollBack = true;
        }
        if(index == 0){
            startRollBack = false;
        }
        if (startRollBack){
            index --;
        } else {
            index ++;
        }
    }
    public void isFirst(boolean first) {
        isFirst = first;
    }

    public void isRollBack(boolean rollBack) {
        isRollBack = rollBack;
    }

    public void Stop(boolean stop) {
        isStop = stop;
    }
}
