package com.tzwm.deadalarm;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * Created by tzwm on 10/5/13.
 */
public class CountDownThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;

    public CountDownThread(SurfaceHolder _surfaceHolder) {
        surfaceHolder = _surfaceHolder;
    }

    @Override
    public void run() {
//        canvas = surfaceHolder.lockCanvas(null);
//        canvas.drawColor(Color.YELLOW);
//        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    public void drawPoint(float x, float y) {
        canvas = surfaceHolder.lockCanvas();
        Paint mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        canvas.drawPoint(x, y, mPaint);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }
}
