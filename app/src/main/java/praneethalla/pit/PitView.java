package praneethalla.pit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * PitView class which is responsible for initializing the pit points, drawing the lines between points,
 * reordering the points
 * Uses PointView for creating the points {@link praneethalla.pit.PointView}
 **/

public class PitView extends ViewGroup {

    private static final String TAG = "PitView";

    private List<PointView> mPointsViewList = new ArrayList<>();

    private Boolean isPointTouchedDown = false;

    private PointView mCurrentPointView = null;

    private Context mContext;

    private Paint mLinePaint;

    PointView mPointView1;
    PointView mPointView2;
    PointView mPointView3;
    PointView mPointView4;
    PointView mPointView5;

    private Point mScreenPoint;

    /**
     * init method called by constructor which is responsible for initializing the points,
     * paint, adding the child views to parent pit view.
     */
    private void init() {

        Log.d(TAG, "PitView initialized");

        mLinePaint = new Paint();
        mScreenPoint = new Point();

        Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        display.getSize(mScreenPoint);

        mPointView1 = new PointView(mContext);
        mPointView2 = new PointView(mContext);
        mPointView3 = new PointView(mContext);
        mPointView4 = new PointView(mContext);
        mPointView5 = new PointView(mContext);

        mPointView1.setPoint(mContext, 200, 200);
        mPointView2.setPoint(mContext, 300, 350);
        mPointView3.setPoint(mContext, 400, 400);
        mPointView4.setPoint(mContext, 600, 550);
        mPointView5.setPoint(mContext, 850, 1000);

        addView(mPointView1);
        addView(mPointView2);
        addView(mPointView3);
        addView(mPointView4);
        addView(mPointView5);

        mPointsViewList.add(mPointView1);
        mPointsViewList.add(mPointView2);
        mPointsViewList.add(mPointView3);
        mPointsViewList.add(mPointView4);
        mPointsViewList.add(mPointView5);
    }

    public PitView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public PitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    public void onDraw(Canvas canvas) {

        Log.d(TAG, "Drawing PitView");

        int maxX = mScreenPoint.x;
        int maxY = mScreenPoint.y;

        mLinePaint.setColor(Color.BLUE);
        mLinePaint.setStrokeWidth(4f);

        canvas.drawLine(0, maxY / 2, (2 * maxX) / 2, maxY / 2, mLinePaint);
        canvas.drawLine(maxX / 2, 0, maxX / 2, (2 * maxY) / 2, mLinePaint);

        for (PointView pointView : mPointsViewList) {
            pointView.draw(canvas);
        }

        for (int i = 0; i < mPointsViewList.size(); i++) {
            if (i + 1 < mPointsViewList.size()) {
                PointView p1 = mPointsViewList.get(i);
                PointView p2 = mPointsViewList.get(i + 1);
                canvas.drawLine(p1.getPointX(), p1.getPointY(), p2.getPointX(), p2.getPointY(), mLinePaint);
            }
        }
    }

    /**
     * addPoint method which is responsible for adding new point at the origin
     */
    public void addPoint() {

        Log.d(TAG, "Adding point at origin");

        int maxX = mScreenPoint.x;
        int maxY = mScreenPoint.y;

        PointView originPointView = new PointView(mContext);
        originPointView.setPoint(mContext, maxX / 2, maxY / 2);

        mPointsViewList.add(originPointView);

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP) {
            mCurrentPointView = null;

            Collections.sort(mPointsViewList, new Comparator<PointView>() {
                @Override
                public int compare(PointView o1, PointView o2) {
                    return o1.compareTo(o2);
                }
            });

            invalidate();
            isPointTouchedDown = false;
        }
        if (action == MotionEvent.ACTION_DOWN) {
            isPointTouchedDown = false;
        } else if (action == MotionEvent.ACTION_MOVE) {
            invalidate();
        }
        if (isPointTouchedDown && mCurrentPointView != null) {
            mCurrentPointView.dispatchTouchEvent(event);
            isPointTouchedDown = false;
            return true;
        }

        float x = event.getX();
        float y = event.getY();

        mCurrentPointView = getCurrentPointView(x, y);

        if (mCurrentPointView != null) {
            isPointTouchedDown = true;
            mCurrentPointView.setPoint(mContext, x, y);
            mCurrentPointView.dispatchTouchEvent(event);
        }

        return true;
    }

    /**
     * method to identify the current point that user has clicked on
     *
     * @param x - x coordinate of user touched area
     * @param y - y coordinate of user touched area
     */
    private PointView getCurrentPointView(float x, float y) {

        int offSet = 50;
        float minSlope = Float.MAX_VALUE;

        for (PointView pv : mPointsViewList) {
            float x1 = pv.getPointX();
            float y1 = pv.getPointY();
            float slope;
            if ((y <= y1 + offSet && y >= y1 - offSet) &&
                    (x <= x1 + offSet && x >= x1 - offSet)) {
                slope = (Math.abs(y - y1) / (Math.abs(x - x1)));
                if (slope < minSlope) {
                    minSlope = slope;
                    mCurrentPointView = pv;
                }
            }
        }

        return mCurrentPointView;
    }
}

