package com.example.lab05_6215247003_ponggame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PongGame extends SurfaceView implements Runnable {

    private final boolean DEBUGGING = true;
    private SurfaceHolder mOurHolder;
    private Canvas mCanvas;
    private Paint mPaint;
    private long mFPS;
    private final int MILLIS_IN_SECOND = 1000;
    private int mScreenX;
    private int mScreenY;
    private int mFontSize;
    private int mFontMargin;
    private Bat mBat;
    private Ball mBall;
    private int mScore;
    private int mLives;
    private Thread mGameThread = null;
    private volatile boolean mPlaying;
    private boolean mPaused = true;


    public PongGame(Context context, int x, int y) {
        super(context);
        mScreenX = x;
        mScreenY = y;
        mFontSize = mScreenX / 20;
        mFontMargin = mScreenX / 75;

        mOurHolder = getHolder();
        mPaint = new Paint();
        mBall = new Ball(mScreenX);
        startNewGame();
    }
    //พลังชีวิต
    private void startNewGame() {
        mScore = 0;
        mLives = 3;
        mBall.reset(mScreenX, mScreenY);
    }
    private void draw() {
        if (mOurHolder.getSurface().isValid()) {
            mCanvas = mOurHolder.lockCanvas();
            mCanvas.drawColor(Color.argb(255, 26, 128, 182));
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint.setTextSize(mFontSize);
            mCanvas.drawRect(mBall.getRect(), mPaint); // add code
            mCanvas.drawText("Score: " + mScore + " Lives: " + mLives,

                    mFontMargin, mFontSize, mPaint);

            if (DEBUGGING) {
                printDebuggingText();
            }
            mOurHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    private void printDebuggingText() {
        int debugSize = mFontSize / 2;
        int debugStart = 150;
        mPaint.setTextSize(debugSize);
        mCanvas.drawText("FPS: " + mFPS ,

                10, debugStart + debugSize, mPaint);
    }

    @Override
    public void run() {
        while (mPlaying) {
            long frameStartTime = System.currentTimeMillis();
            if(!mPaused){
                update();
                detectCollisions();
            }
            draw();
            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            if (timeThisFrame > 0) {
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }
        }
    }
    private void update() {
        mBall.update(mFPS);
    }

    private void detectCollisions(){

    }

    public void pause() {

        mPlaying = false;
        try {

            mGameThread.join();

        } catch (InterruptedException e) {

            Log.e("Error:", "joining thread");

        }
    }
    public void resume() {
        mPlaying = true;
        mGameThread = new Thread(this);
        mGameThread.start();

    }
}
