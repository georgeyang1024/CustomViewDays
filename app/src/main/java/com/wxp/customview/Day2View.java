package com.wxp.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * CustomView Day2
 * 自定义View实现图片与文字上下并排
 * 参考网址：http://blog.csdn.net/lmj623565791/article/details/24300125
 * Created by wxp on 2015/8/17.
 */
public class Day2View extends View{
    public Day2View(Context context) {
        this(context, null,0);
    }

    public Day2View(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    Bitmap mBmp = null;
    int mImgScaleType = 0;
    String mTextContent = "hello";
    int mTextColor = Color.RED;
    int mTextSize = 16;

    Paint mPaint = null;
    public Day2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Day2View, defStyleAttr, 0);
        int length = array.length();
        for (int i=0;i<length;i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.Day2View_imgSrc:
                    mBmp = BitmapFactory.decodeResource(getResources(), array.getResourceId(attr, R.drawable.whisper));
                    break;

                case R.styleable.Day2View_imgScleType:
                    mImgScaleType = array.getInt(attr, 0);
                    break;
                case R.styleable.Day2View_textContent:
                    mTextContent = array.getString(attr);
                    break;
                case R.styleable.Day2View_textColor:
                    mTextColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.Day2View_textSize:
                    mTextSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }

        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBmp,100,100,mPaint);
        canvas.drawText(mTextContent,100,100,mPaint);
    }
}
