package com.tobincorporated.mynewdrawingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class CanvasView extends View {

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private ArrayList<Path> myPaths;
    private ArrayList<Integer> myColors;
    private int pathNum;
    Context context;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        myPaths = new ArrayList<Path>();
        myColors = new ArrayList<Integer>();
        pathNum = 0;
        myPaths.add(new Path());
        myColors.add( Color.rgb(0,0,0));

        context = c;
        // we set a new Path
        mPath = new Path();

        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the mPath with the mPaint on the canvas when onDraw
        for(int i = 0; i<=pathNum; i++) {
            Path myPath = myPaths.get(i);
            mPaint.setColor(myColors.get(i));
            canvas.drawPath(myPath, mPaint);
        }
    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {

        myPaths.get(pathNum).moveTo(x, y);
        mX = x;
        mY = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            myPaths.get(pathNum).quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public void clearCanvas() {
        myPaths = new ArrayList<Path>();
        myColors = new ArrayList<Integer>();
        pathNum = 0;
        myPaths.add(new Path());
        myColors.add( Color.rgb(0,0,0));

        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        myPaths.get(pathNum).lineTo(mX, mY);

        myColors.add(myColors.get(pathNum));

        pathNum++;
        myPaths.add(new Path());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }

    public void setDrawColor(int[] colorRGB){

        int rv = colorRGB[0];
        int gv = colorRGB[1];
        int bv = colorRGB[2];
        myColors.set(pathNum,Color.rgb(rv,gv,bv));
    }
}
