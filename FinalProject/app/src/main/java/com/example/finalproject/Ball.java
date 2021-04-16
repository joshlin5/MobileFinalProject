package com.example.finalproject;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

public class Ball {
    public final int RADIUS = 100;
    private Paint mPaint;
    private PointF mCenter;

    private int mSurfaceWidth;
    private int mSurfaceHeight;

    public Ball(int surfaceWidth, int surfaceHeight) {

        mSurfaceWidth = surfaceWidth;
        mSurfaceHeight = surfaceHeight;

        // Set initial position and velocity
        mCenter = new PointF(RADIUS, RADIUS);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xffaaaaff);
    }

    public void setCenter(int x, int y) {
        mCenter.x = x;
        mCenter.y = y;
    }

    public void move(PointF velocity) {

        // Move ball's center
        mCenter.offset(-velocity.x * 1.5f, velocity.y * 1.5f);

        if (mCenter.y > mSurfaceHeight - RADIUS) {
            mCenter.y = mSurfaceHeight - RADIUS;
        }
        else if (mCenter.y < RADIUS) {
            mCenter.y = RADIUS;
        }

        if (mCenter.x > mSurfaceWidth - RADIUS) {
            mCenter.x = mSurfaceWidth - RADIUS;
        }
        else if (mCenter.x < RADIUS) {
            mCenter.x = RADIUS;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(mCenter.x, mCenter.y, RADIUS, mPaint);
    }

    public boolean intersects(Wall wall) {

        Rect rect = wall.getRect();
        // Find point on wall that is closest to ball center
        //Rect rect = wall.getRect();
        int nearestX = Math.max(rect.left, Math.min((int) mCenter.x, rect.right));
        int nearestY = Math.max(rect.top, Math.min((int) mCenter.y, rect.bottom));

        // Measure distance from nearest point to ball center
        int deltaX = (int) mCenter.x - nearestX;
        int deltaY = (int) mCenter.y - nearestY;

        // Return true if distance from ball center to nearest point is less than ball radius
        return (deltaX * deltaX + deltaY * deltaY) < (RADIUS * RADIUS);
    }

    public int getBottom() {
        return (int) mCenter.y + RADIUS;
    }
}