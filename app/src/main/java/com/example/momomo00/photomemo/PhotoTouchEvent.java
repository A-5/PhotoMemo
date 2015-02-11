package com.example.momomo00.photomemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by momomo00 on 14/09/24.
 */
public class PhotoTouchEvent implements
        View.OnTouchListener,
        GestureDetector.OnDoubleTapListener,
        GestureDetector.OnGestureListener {

    private static final PhotoTouchEvent instance = new PhotoTouchEvent();

    private GestureDetector gestureDetector;

    private Activity activity;
    private PhotoMemoView photoMemoView;

    private PhotoTouchEvent() {}
    public static PhotoTouchEvent getInstance() {
        return instance;
    }

    /**
     * 
     * @param activity
     * @param photoMemoView
     * @return
     */
    public PhotoTouchEvent init(Activity act, PhotoMemoView photoMemoView) {
        this.gestureDetector = new GestureDetector(activity, this);

        this.activity = activity;
        this.photoMemoView = photoMemoView;

        return instance;
    }


    /**
     * 画面タッチを検出した場合のイベントの振り分けを行う
     *
     * @param view タッチされたビュー
     * @param motionEvent イベント
     * @return TODO
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        this.gestureDetector.onTouchEvent(motionEvent);

        return true;
    }


    @Override
    public boolean onDown(MotionEvent arg0) {
        this.photoMemoView.setLineFrom(arg0.getX(), arg0.getY());

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        this.photoMemoView.drawLineTo(e2.getX(), e2.getY());

        return false;
    }

    /**
     * 長押しイベント
     */
    @Override
    public void onLongPress(MotionEvent e) {
        this.photoMemoView.setTextPosition(e.getX(), e.getY());
        MenuInputString.getInstance().init(this.activity, this.photoMemoView).show();
    }

    /**
     * ダブルタップイベント
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d("TEST", "PhotoTouchEvent#onDoubleTap");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d("TEST", "PhotoTouchEvent#onDoubleTapEvent");
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("TEST", "PhotoTouchEvent#onFling");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("TEST", "PhotoTouchEvent#onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("TEST", "PhotoTouchEvent#onSingleTapUp");
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d("TEST", "PhotoTouchEvent#onSingleTapConfirmed");
        return false;
    }
}
