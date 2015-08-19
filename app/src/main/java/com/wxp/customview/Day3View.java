package com.wxp.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * CustomView Day3
 * 自定义View实现进度圈
 * 参考网址：http://blog.csdn.net/lmj623565791/article/details/24500107
 * Created by wxp on 2015/8/19.
 */
public class Day3View extends View {
    public int mFirstColor = Color.RED;
    public int mSecondColor = Color.BLUE;
    public int mInnerWidth = 64;
    public int mProgressWidth = 24;
    public int mDuration = 10;
    /**
     * Point1：设置时间以及刷新频率
     */
    public float mSpeed = 10;//默认每秒走10度。可以通过360/mDuration得到
    public int mTimes = 100;//每秒刷新100次
    public RectF mBounds = null;
    public int mWidth = 0;
    public int mHeight = 0;
    public float mDegree = 0;
    Paint mPaint = null;

    public Day3View(Context context) {
        this(context, null, 0);
    }
    public Day3View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day3View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Day3View, defStyleAttr, 0);
        for (int i = 0; i < array.length(); i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.Day3View_firstColor:
                    mFirstColor = array.getColor(attr, Color.RED);
                    break;

                case R.styleable.Day3View_secondColor:
                    mSecondColor = array.getColor(attr, Color.BLUE);
                    break;

                case R.styleable.Day3View_innerWidth:
                    mInnerWidth = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, getResources().getDisplayMetrics()));
                    break;

                case R.styleable.Day3View_progressWidth:
                    mProgressWidth = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()));
                    break;

                case R.styleable.Day3View_duration:
                    mDuration = array.getInteger(attr, 10);
                    break;
            }
        }
        array.recycle();

        mPaint = new Paint();
        mPaint.setColor(mFirstColor);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mBounds = new RectF();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //mDegree += 10;
               // postInvalidate();
            }
        });

        /**
         * Point2：计算每次旋转的度数
         */
        mSpeed = (float) (360*1.0/mDuration/mTimes);
        /**
         * Point3：开启刷新线程
         */
        post(new Runnable() {
            @Override
            public void run() {

                if (mDegree >= 360) {
                    int temp = mSecondColor;
                    mSecondColor = mFirstColor;
                    mFirstColor = temp;
                    mDegree = 0;
                }
                mDegree+= mSpeed;
                postDelayed(this, 1000/mTimes);
                postInvalidate();

            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            /**
             * Point4：取自定义属性内宽加外宽与view的宽度的较大值
             */
            if (mInnerWidth + mProgressWidth > widthSize) {
                mWidth = mInnerWidth + mProgressWidth;
            } else {
                mWidth = widthSize;
            }

        } else {

            mWidth = (mInnerWidth+mProgressWidth)*2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            if (mInnerWidth + mProgressWidth > heightSize) {
                mWidth = mInnerWidth + mProgressWidth;
            } else {
                mHeight = heightSize;
            }

        } else {
            mHeight = (mInnerWidth+mProgressWidth)*2;
        }

        if (mWidth != mHeight) {
            mWidth = Math.max(mWidth, mHeight);
            mHeight = mWidth;
        }

        Log.e("wxp", "wxp-mWidth : " + mWidth);
        Log.e("wxp", "wxp-mInnerWidth : " + mInnerWidth);
        Log.e("wxp", "wxp-mProgressWidth : " + mProgressWidth);
        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * Point5：绘制内弧
         */
        mPaint.setColor(mFirstColor);
        mBounds.left = 0;
        mBounds.right = mWidth;
        mBounds.top = 0;
        mBounds.bottom = mHeight;
        canvas.drawArc(mBounds, 0, mDegree, true, mPaint);

        /**
         * Point6：绘制外弧
         */
        mPaint.setColor(mSecondColor);
        mBounds.left = mWidth / 2 - mInnerWidth;
        mBounds.right = mWidth / 2 + mInnerWidth;
        mBounds.top = mHeight/2 - mInnerWidth;
        mBounds.bottom = mHeight/2 + mInnerWidth;
        canvas.drawArc(mBounds, 0, mDegree, true, mPaint);

    }
}
