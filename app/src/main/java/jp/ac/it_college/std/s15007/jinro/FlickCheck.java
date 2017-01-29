package jp.ac.it_college.std.s15007.jinro;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by samuel on 17/01/26.
 */

public abstract class FlickCheck {
    public static final int FLICK_LEFT = 0;
    public static final int FLICK_RIGHT = 1;
    public static final int FLICK_UP = 2;
    public static final int FLICK_DOWN = 3;

    private float adjustX = 150.0f;
    private float adjustY = 150.0f;
    private float touchX;
    private float touchY;
    private float nowTouchX;
    private float nowTouchY;

    public FlickCheck(View flickView,float adjustX, float adjustY) {
        this.adjustX = adjustX;
        this.adjustY = adjustY;

        flickView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchX = event.getX();
                        touchY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        nowTouchX = event.getX();
                        nowTouchY = event.getY();
                        check();
                        v.performClick();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return true;
            }
        });
    }

    private void check() {
        Log.d("FlickPoint", "startX:" + touchX + " endX:" + nowTouchX
                + " startY:" + touchY + " endY:" + nowTouchY);

        // 左フリック
        if(touchX > nowTouchX)
        {
            if(touchX - nowTouchX > adjustX)
            {
                getFlick(FLICK_LEFT);
                return;
            }
        }
        // 右フリック
        if(nowTouchX > touchX)
        {
            if(nowTouchX - touchX > adjustX)
            {
                getFlick(FLICK_RIGHT);
                return;
            }
        }
        // 上フリック
        if(touchY > nowTouchY)
        {
            if(touchY - nowTouchY > adjustY)
            {
                getFlick(FLICK_UP);
                return;
            }
        }
        // 下フリック
        if(nowTouchY > touchY)
        {
            if(nowTouchY - touchY > adjustY)
            {
                getFlick(FLICK_DOWN);
                return;
            }
        }
    }

    public abstract void getFlick(int swipe);
}