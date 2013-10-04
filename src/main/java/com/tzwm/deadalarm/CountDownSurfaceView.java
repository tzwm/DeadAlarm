package com.tzwm.deadalarm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by tzwm on 10/5/13.
 */
public class CountDownSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder countDownholder;
    private CountDownThread countDownThread;

    public CountDownSurfaceView(Context context) {
        super(context);
        this.init();
    }

    public CountDownSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public CountDownSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        countDownThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

//        countDownThread.drawPoint(x, y);

        Canvas canvas = countDownholder.lockCanvas();
        Paint mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        canvas.drawPoint(x, y, mPaint);
        countDownholder.unlockCanvasAndPost(canvas);


        return true;
    }

    private void init() {
        countDownholder = this.getHolder();
        countDownholder.addCallback(this);

        setFocusableInTouchMode(true);

        countDownThread = new CountDownThread(countDownholder);
    }
}
