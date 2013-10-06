package com.tzwm.deadalarm;

import android.app.Notification;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by tzwm on 10/5/13.
 */
public class CountDownSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder countDownholder;
    private int xCanvas, yCanvas;
    private int ccColor;
    private int rCenterCircle;
    private boolean isMove, isRecording;
    private int secondRemain;

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
        xCanvas = getWidth();
        yCanvas = getHeight();
        rCenterCircle = getWidth() / 5;

        Thread thread = new Thread(this);
        thread.start();
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

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isMove = false;

                if(!(Math.abs(x - xCanvas / 2) < rCenterCircle && Math.abs(y - yCanvas / 2) < rCenterCircle))
                    break;

                drawRedCircle();

            case MotionEvent.ACTION_MOVE:
                isMove = true;
                if(!(Math.abs(x - xCanvas / 2) < rCenterCircle && Math.abs(y - yCanvas / 2) < rCenterCircle))
                    drawTransCircle();

                break;

            case MotionEvent.ACTION_UP:
                drawTransCircle();

                break;
            case MotionEvent.ACTION_CANCEL:
                isMove = true;

                drawTransCircle();
                break;
        }


        return true;
    }

    private void drawRedCircle() {
        Canvas canvas = countDownholder.lockCanvas(new Rect(xCanvas/2-rCenterCircle, yCanvas/2-rCenterCircle,
                                                            xCanvas/2+rCenterCircle, yCanvas/2+rCenterCircle));

        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(xCanvas/2, yCanvas/2, rCenterCircle, mPaint);

        countDownholder.unlockCanvasAndPost(canvas);
    }

    private void drawTransCircle() {
        Canvas canvas = countDownholder.lockCanvas(new Rect(xCanvas/2-rCenterCircle-1, yCanvas/2-rCenterCircle-1,
                xCanvas/2+rCenterCircle+1, yCanvas/2+rCenterCircle+1));

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        countDownholder.unlockCanvasAndPost(canvas);
    }

    private void init() {
        countDownholder = this.getHolder();
        countDownholder.addCallback(this);
        countDownholder.setFormat(PixelFormat.TRANSPARENT);
        setZOrderOnTop(true);

        setFocusableInTouchMode(true);

        ccColor = Color.WHITE;

        secondRemain = 0;
    }

    @Override
    public void run() {
        Canvas canvas = countDownholder.lockCanvas();

        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ccColor);
        canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, rCenterCircle, mPaint);
        canvas.drawArc(new RectF(xCanvas / 2 - rCenterCircle, yCanvas / 2 - rCenterCircle, xCanvas / 2 + rCenterCircle, yCanvas / 2 + rCenterCircle), 0, 360, true, mPaint);

        countDownholder.unlockCanvasAndPost(canvas);
    }
}
