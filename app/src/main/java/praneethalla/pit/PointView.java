package praneethalla.pit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * PointView class which is responsible for creating the pit points
 **/

public class PointView extends View implements Comparable<PointView> {

    private static final String TAG = "PointView";

    Paint mPointPaint;

    Canvas mCanvas;

    Context mContext;

    float mX;
    float mY;

    /**
     * init method called by constructor which is responsible for initializing and
     * setting the paint to point
     */
    private void init() {

        Log.d(TAG, "PointView initialized");

        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setDither(true);
        mPointPaint.setColor(Color.BLUE);
        mPointPaint.setStyle(Paint.Style.STROKE);
        mPointPaint.setStrokeJoin(Paint.Join.ROUND);
        mPointPaint.setStrokeCap(Paint.Cap.ROUND);
        mPointPaint.setStrokeWidth(18f);
    }

    /**
     * setter method for setting the point coordinates
     *
     * @param context - context of the activity
     * @param x       - x coordinate of point
     * @param y       - y coordinate of point
     */
    public void setPoint(Context context, float x, float y) {
        mContext = context;
        mX = x;
        mY = y;
    }

    /**
     * getter method for getting the point x
     */
    public float getPointX() {
        return mX;
    }

    /**
     * getter method for getting the point y
     */
    public float getPointY() {
        return mY;
    }

    public PointView(Context context) {
        super(context);
        init();
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * compare method for comparing two point objects
     *
     * @param p1 - First PointView
     * @param p2 - Second PointView
     */
    public int compare(PointView p1, PointView p2) {

        int result = Double.compare(p1.getPointX(), p2.getPointX());
        if (result == 0) {
            result = Double.compare(p1.getPointY(), p2.getPointY());
        }
        return result;
    }

    @Override
    public int compareTo(PointView o) {
        return compare(this, o);
    }

    @Override
    public void onDraw(Canvas canvas) {

        mCanvas = canvas;

        Log.d(TAG, "Drawing Point");

        mPointPaint.setAntiAlias(true);
        mPointPaint.setDither(true);
        mPointPaint.setColor(Color.BLUE);
        mPointPaint.setStyle(Paint.Style.STROKE);
        mPointPaint.setStrokeJoin(Paint.Join.ROUND);
        mPointPaint.setStrokeCap(Paint.Cap.ROUND);
        mPointPaint.setStrokeWidth(22f);

        mCanvas.drawPoint(mX, mY, mPointPaint);
    }
}
