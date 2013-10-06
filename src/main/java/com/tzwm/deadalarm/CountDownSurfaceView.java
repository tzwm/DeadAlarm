package com.tzwm.deadalarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Chronometer;

import java.util.Calendar;

/**
 * Created by tzwm on 10/5/13.
 */
public class CountDownSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private CountDownActivity countDownActivity;
    private SurfaceHolder countDownholder;
    private MediaController mediaController;
    private Thread drawThread;
    private int xCanvas, yCanvas, rCenterCircle, rFringeCircle;
    private int ccColor, currentColor, arcColor, arcAngle, currentArcAngle;
    private boolean isMove, isRecording;
    private int secondRemain;

    public CountDownSurfaceView(Context context) {
        super(context);
        this.init(context);
    }

    public CountDownSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public CountDownSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        xCanvas = getWidth();
        yCanvas = getHeight();
        rCenterCircle = getWidth() / 5;
        rFringeCircle = getWidth() / 3;

        drawThread = new Thread(this);
        drawThread.start();
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

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if(!(Math.abs(x - xCanvas / 2) < rCenterCircle && Math.abs(y - yCanvas / 2) < rCenterCircle)){
                    countDownActivity.mCountDownTextView.setBase(secondRemain);
                    break;
                }

                mediaController.startRecording();
                isRecording = true;
                ccColor = Color.RED;

                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                if(isRecording)
                    centerTouchUp();
                else
                    fringeTouchUp();

                break;

            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true;
    }



    private void sendAlarm() {
        Intent intent = new Intent("android.tzwm.hello");
        PendingIntent pi = PendingIntent.getBroadcast(countDownActivity,
                                                        1, intent,
                                                        PendingIntent.FLAG_ONE_SHOT);
        AlarmManager arm = (AlarmManager)countDownActivity.getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, secondRemain);
        arm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
    }

    private void centerTouchUp() {
        mediaController.stopRecording();
        isRecording = false;
        ccColor = Color.TRANSPARENT;
        countDownActivity.runOnUiThread(countDownActivity.mCountDownTextView);
        sendAlarm();
    }

    private void fringeTouchUp() {
        ccColor = Color.GREEN;
        try {
            drawThread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ccColor = Color.TRANSPARENT;
        countDownActivity.runOnUiThread(countDownActivity.mCountDownTextView);
        sendAlarm();
    }

    private void init(Context context) {
        countDownActivity = (CountDownActivity)context;
        mediaController = new MediaController();

        countDownholder = this.getHolder();
        countDownholder.addCallback(this);
        countDownholder.setFormat(PixelFormat.TRANSPARENT);
        setZOrderOnTop(true);
        setFocusableInTouchMode(true);

        ccColor = Color.TRANSPARENT;
        currentColor = -1;
        arcColor = Color.WHITE;
        arcAngle = 300;
        currentArcAngle = -1;

        isRecording = false;
        isMove = false;

        secondRemain = 10;
    }

    @Override
    public void run() {
        while(true){
            if(ccColor != currentColor){
                Canvas canvas = countDownholder.lockCanvas(new Rect(xCanvas/2-rCenterCircle-1,
                                                                    yCanvas/2-rCenterCircle-1,
                                                                    xCanvas/2+rCenterCircle+1,
                                                                    yCanvas/2+rCenterCircle+1));
                Paint mPaint = new Paint();
                mPaint.setAntiAlias(true);
                if(ccColor == Color.TRANSPARENT)
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                else{
                    mPaint.setColor(ccColor);
                    canvas.drawCircle(xCanvas/2, yCanvas/2, rCenterCircle, mPaint);
                }
                countDownholder.unlockCanvasAndPost(canvas);

                currentColor = ccColor;
            }

            if(arcAngle != currentArcAngle) {
                Canvas canvas = countDownholder.lockCanvas(new Rect(xCanvas/2-rFringeCircle-1,
                                                                    yCanvas/2-rFringeCircle-1,
                                                                    xCanvas/2+rFringeCircle+1,
                                                                    yCanvas/2+rFringeCircle+1));
                Paint mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setStyle(Paint.Style.STROKE);
                if(arcColor == Color.TRANSPARENT)
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                else {
                    mPaint.setColor(arcColor);
                    canvas.drawArc(new RectF(xCanvas/2-rFringeCircle, yCanvas/2-rFringeCircle,
                                             xCanvas/2+rFringeCircle, yCanvas/2+rFringeCircle),
                                   0, arcAngle, false, mPaint);
                }
                countDownholder.unlockCanvasAndPost(canvas);

                currentArcAngle = arcAngle;
            }

        }
    }
}
