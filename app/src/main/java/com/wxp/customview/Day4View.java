package com.wxp.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * CustomView Day4
 * 自定义View实现音量圈以及加减效果
 * 参考网址：http://blog.csdn.net/lmj623565791/article/details/24529807
 * Created by wxp on 2015/8/19.
 */
public class Day4View extends View {

    public int mFirstColor = Color.RED;
    public int mSecondColor = Color.BLUE;
    public int mProgressWidth = 20;
    public int mDotCount = 10;
    public float mDotDegree = 0;
    public int mSplitSize = 10;
    public Bitmap mBg = null;

    public Paint mFirstPaint = null;
    public Paint mSecondPaint = null;
    public Rect mBgBounds = null;
    public int mPaintWidth = 15;
    RectF mVolumBounds = new RectF();

    private int mCurrentCount = 3;
    public Day4View(Context context) {
        this(context, null, 0);
    }

    public Day4View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day4View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Day4View, defStyleAttr, 0);

        for (int i = 0; i < array.length(); i++) {
            int attr = array.getIndex(i);

            switch (attr) {
                case R.styleable.Day4View_day4FirstColor:
                    mFirstColor = array.getColor(attr, Color.RED);
                    break;

                case R.styleable.Day4View_day4SecondColor:
                    mSecondColor = array.getColor(attr, Color.BLUE);
                    break;

                case R.styleable.Day4View_day4CircleWidth:
                    mProgressWidth = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
                    break;

                case R.styleable.Day4View_dotCount:
                    mDotCount = array.getInt(attr, 10);
                    break;

                case R.styleable.Day4View_splitSize:
                    mSplitSize = array.getInt(attr, 10);
                    break;

                case R.styleable.Day4View_day4Bg:
                    mBg = BitmapFactory.decodeResource(getResources(), array.getResourceId(attr, R.drawable.whisper));
                    break;

            }
        }

        array.recycle();

        mDotDegree = (360 * 1.0f - mDotCount * mSplitSize) / mDotCount;

        /**
         * Point1：设置画笔的属性
         */
        mFirstPaint = new Paint();
        mFirstPaint.setStrokeWidth(mPaintWidth);//画笔宽度
        mFirstPaint.setStrokeCap(Paint.Cap.ROUND);//边缘处为圆形
        mFirstPaint.setStyle(Paint.Style.STROKE);//画出的图形是空心的仅描边
        mFirstPaint.setColor(mFirstColor);
        mFirstPaint.setAntiAlias(true);

        mSecondPaint = new Paint();
        mSecondPaint.setStrokeWidth(mPaintWidth);
        mSecondPaint.setStrokeCap(Paint.Cap.ROUND);
        mSecondPaint.setStyle(Paint.Style.STROKE);
        mSecondPaint.setColor(mSecondColor);
        mSecondPaint.setAntiAlias(true);

        mBgBounds = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = Math.max(mProgressWidth * 2, widthSize);
        } else {
            width = mProgressWidth * 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = Math.max(mProgressWidth * 2, height);
        } else {
            height = mProgressWidth * 2;
        }

        width = Math.max(width, height);
        height = width;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float start = 0;
        float end = 0;
        Log.e("wxp", "wxp-getMeasuredWidth() : " + getMeasuredWidth());

        int centerX = getWidth() / 2;
        /**
         * Point2：计算出音量圈所在圆的半径
         */
        int radius = centerX - mProgressWidth / 2;

        mVolumBounds.left = centerX - radius;
        mVolumBounds.right = centerX + radius;
        mVolumBounds.top = centerX - radius;
        mVolumBounds.bottom = centerX + radius;

        /**
         * Point3：绘制出基本的音量圈
         */
        for (int i = 0; i < mDotCount; i++) {
            end = start + mDotDegree;
            canvas.drawArc(mVolumBounds, start, mDotDegree, false, mFirstPaint);
            start = end + mSplitSize;
        }

        /**
         * Point4：绘制出当前音量的音量圈
         */
        start = 0;
        end = 0;
        for (int i = 0; i < mCurrentCount; i++) {
            end = start + mDotDegree;
            canvas.drawArc(mVolumBounds, start, mDotDegree, false, mSecondPaint);
            start = end + mSplitSize;
        }

        /**
         * Point5：绘制出内部的图标
         */
        mBgBounds.left = (int) (centerX - mVolumBounds.width() / 2 / (Math.sqrt(2)) + mProgressWidth / 2);
        mBgBounds.right = (int) (centerX + mVolumBounds.width() / 2 / (Math.sqrt(2)) - mProgressWidth / 2);
        mBgBounds.top = (int) (centerX - mVolumBounds.width() / 2 / (Math.sqrt(2)) + mProgressWidth / 2);
        mBgBounds.bottom = (int) (centerX + mVolumBounds.width() / 2 / (Math.sqrt(2)) - mProgressWidth / 2);
        Log.e("wxp", "wxp-mBgBounds.left : " + mBgBounds.left + " | right : " + mBgBounds.right);
        canvas.drawBitmap(mBg, null, mBgBounds, mSecondPaint);

    }

    /**
     * Point6：上下滑动增减音量
     */
    float startY = 0 ;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                Log.e("wxp", "wxp-start : " + startY);
                break;

            case MotionEvent.ACTION_UP:
                float endY = event.getY();

                Log.e("wxp", "wxp-end : " + endY);
                if (startY > endY) {
                    mCurrentCount++;
                } else {
                    mCurrentCount--;
                }
                postInvalidate();
                break;
        }
        return true;
    }
}
