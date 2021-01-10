package com.example.lab05_6215247003_ponggame;

import android.graphics.RectF;

public class Bat {
    final int STOPPED = 0;
    final int LEFT = 1;
    final int RIGHT = 2;


    private final RectF mRect;
    private final float mLength;
    private final float mBatSpeed;
    private final int mScreenX;
    private float mXCooed;
    private int mBatMoving = STOPPED;

    Bat(int sx, int sy) {
        mScreenX = sx;
        mLength = mScreenX / 8;
        float height = sy / 40;
        mXCooed = mScreenX / 2;
        float mYCooed = sy - height;
        mRect = new RectF(mXCooed, mYCooed,
                mXCooed + mLength,
                mYCooed + height);
        mBatSpeed = mScreenX;
    }
    RectF getRect() {
        return mRect;

    }
    void setMovementState(int state) {

        mBatMoving = state;

    }
    void update(long fps) {

        if (mBatMoving == LEFT) {
            mXCooed = mXCooed - mBatSpeed / fps;
        }
        if (mBatMoving == RIGHT) {
            mXCooed = mXCooed + mBatSpeed / fps;
        }

        if (mXCooed < 0) {
            mXCooed = 0;
        } else if (mXCooed + mLength > mScreenX) {
            mXCooed = mScreenX - mLength;
        }

        mRect.left = mXCooed;
        mRect.right = mXCooed + mLength;
    }

}
