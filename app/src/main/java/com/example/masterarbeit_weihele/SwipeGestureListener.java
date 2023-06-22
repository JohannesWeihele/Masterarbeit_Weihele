package com.example.masterarbeit_weihele;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        float diffX = event2.getX() - event1.getX();
        float diffY = event2.getY() - event1.getY();

        if (Math.abs(diffX) > Math.abs(diffY)
                && Math.abs(diffX) > SWIPE_THRESHOLD
                && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
            if (diffX > 0) {
                System.out.println("Hallo");
                return true;
            }
        }

        return false;
    }
}