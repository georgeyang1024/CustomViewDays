package com.wxp.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * CustomView Day2
 * 自定义View实现图片与文字上下并排
 * 参考网址：http://blog.csdn.net/lmj623565791/article/details/24300125
 * Created by wxp on 2015/8/17.
 */
public class Day2View extends View {
    final int TYPE_FILLXY = 0;
    final int TYPE_CENTER = 1;
    Bitmap mBmp = null;
    int mImgScaleType = 0;
    String mTextContent = "hello";
    int mTextColor = Color.RED;
    int mTextSize = 16;
    Rect mTextBounds = null;
    Paint mPaint = null;

    Rect mImgBounds = null;
    int mWidth = 0;
    int mHeight = 0;

    public Day2View(Context context) {
        this(context, null, 0);
    }

    public Day2View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mImgBounds = new Rect();
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Day2View, defStyleAttr, 0);
        int length = array.length();
        for (int i = 0; i < length; i++) {
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

        array.recycle();

        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mTextBounds = new Rect();
        mPaint.getTextBounds(mTextContent, 0, mTextContent.length(), mTextBounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            int measureWidth = getMeasuredWidth();
            int measureHeight = getMeasuredHeight();

            int titleWidth = mTextBounds.width();
            int imgWidth = mBmp.getWidth();

            if (widthMode == MeasureSpec.AT_MOST) {
                int maxWidth = Math.max(titleWidth, imgWidth);
                mWidth = Math.min(maxWidth + getPaddingLeft() + getPaddingRight(), widthSize);
            }


        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            int height = mBmp.getHeight() + mTextBounds.height() + getPaddingTop() + getPaddingBottom();
            mHeight = Math.min(height, heightSize);

        }

        setMeasuredDimension(mWidth, mHeight);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("wxp", "wxp-onDraw" + mWidth + " | " + mHeight);

        mImgBounds.left = getPaddingLeft();
        mImgBounds.top = getPaddingTop();
        mImgBounds.right = mWidth - getPaddingRight();
        mImgBounds.bottom = mHeight - getPaddingBottom();

        if (mImgScaleType == TYPE_FILLXY) {
            canvas.drawBitmap(mBmp, null, mImgBounds, mPaint);
        } else {
            /**
             * 这里为什么可以直接用mWidth/2-mBmp.getWidth()/2？因为mWidth在之前已经measure过了，宽度已经包含了图片的宽度。
             */
            mImgBounds.left = mWidth / 2 - mBmp.getWidth() / 2;
            mImgBounds.right = mWidth / 2 + mBmp.getWidth() / 2;
            mImgBounds.top = (mHeight - mTextBounds.height()) / 2 - mBmp.getHeight() / 2;
            mImgBounds.bottom = (mHeight - mTextBounds.height()) / 2 + mBmp.getHeight() / 2;
            canvas.drawBitmap(mBmp, null, mImgBounds, mPaint);
        }

        /**
         * 当宽度设置为固定值时，文字宽度有可能会大于view 的宽度
         */
        if (mTextBounds.width() > mWidth) {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTextContent, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
        } else {
            canvas.drawText(mTextContent, mWidth*1.0f / 2 - mTextBounds.width()*1.0f / 2, mHeight - getPaddingBottom(), mPaint);
        }
    }
}
