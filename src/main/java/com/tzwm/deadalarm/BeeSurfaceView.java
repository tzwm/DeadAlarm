package com.tzwm.deadalarm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by tzwm on 10/8/13.
 */
public class BeeSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private Context mContext;
    private SurfaceHolder mHolder;
    private Thread mThread;
    private MediaController mMediaController;
    static boolean isQuitted=true;

    private int xCanvas, yCanvas;
    private int xCurrent, yCurrent, xTo, yTo;
    private int step, deviation;
    private int rCircle, rLast;
    private int cColor, ccColor;
    private Random random;

    static private int[] d = {-1, 0, 1};

    public BeeSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public BeeSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BeeSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if(Math.abs(x-xCurrent)<deviation && Math.abs(y-yCurrent)<deviation)
                    quit();
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public void run() {
        while(!isQuitted){
            Canvas canvas = mHolder.lockCanvas(new Rect(xCurrent-4*rCircle, yCurrent-4*rCircle,
                                                        xCurrent+4*rCircle, yCurrent+4*rCircle));
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            Paint mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(cColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(3);

            float xTmp, yTmp;
            float tmp = (float)Math.sqrt((xCurrent-xTo)*(xCurrent-xTo) +
                                            (yCurrent-yTo)*(yCurrent-yTo));
            xTmp = xCurrent + step*(xTo-xCurrent)/tmp;
            yTmp = yCurrent - step*(yCurrent-yTo)/tmp;
            xCurrent = (int)xTmp;
            yCurrent = (int)yTmp;
            canvas.drawCircle(xCurrent, yCurrent, rCircle, mPaint);
            mPaint.setColor(ccColor);
            mPaint.setStrokeWidth(3);
            if(rLast <= 0)
                rLast = rCircle;
            if(rLast-- % 2 == 0)
//                canvas.drawCircle(xCurrent, yCurrent, rLast, mPaint);

            if(Math.sqrt((xCurrent-xTo)*(xCurrent-xTo)
                    + (yCurrent-yTo)*(yCurrent-yTo)) <= rCircle){
                generateNext();
            }

            mHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isQuitted = false;

        xCanvas = getWidth();
        yCanvas = getHeight();

        xCurrent = random.nextInt(1<<30) % xCanvas;
        while(xCurrent<rCircle || xCurrent>xCanvas-rCircle)
            xCurrent = random.nextInt(1<<30) % xCanvas;
        yCurrent = random.nextInt(1<<30) % yCanvas;
        while(yCurrent<rCircle || yCurrent>yCanvas-rCircle)
            yCurrent = random.nextInt(1<<30) % yCanvas;

        generateNext();

        mMediaController.repeatNotification();

        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isQuitted = true;
    }

    private void quit() {
        isQuitted = true;
        mMediaController.stop();

        ((BeeActivity)mContext).finish();
    }

    public void init(Context _context) {
        mContext = _context;
        mHolder = getHolder();
        mMediaController = new MediaController(_context);
        mHolder.addCallback(this);
        setZOrderOnTop(true);
        setFocusableInTouchMode(true);

        rCircle = 35;
        rLast = 35;
        random = new Random();
        step = 11;
        deviation = 30;

        cColor = Color.WHITE;
        ccColor = Color.GREEN;

        isQuitted = true;
    }

    private void generateNext() {
        xTo = random.nextInt(1<<30) % xCanvas;
        while(xTo<=rCircle || xTo>=xCanvas-rCircle)
            xTo = random.nextInt(1<<30) % xCanvas;
        yTo = random.nextInt(1<<30) % yCanvas;
        while(yTo<=rCircle || yTo>=yCanvas-rCircle)
            yTo = random.nextInt(1<<30) % yCanvas;
    }

}
