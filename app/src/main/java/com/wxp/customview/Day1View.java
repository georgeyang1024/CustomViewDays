package com.wxp.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.Random;

/**
 * CustomView Day1
 * 自定义View实现点击数字加1
 * 参考网址：http://blog.csdn.net/lmj623565791/article/details/24252901
 * Created by wxp on 2015/8/17.
 */
public class Day1View extends View {
    private Paint mPaint = new Paint();
    ;

    private String mTitleText = "1111";
    private int mTitleColor = Color.RED;
    private int mTitleSize = 10;
    private int mTitleBackground = Color.YELLOW;

    private Rect mTextBounds = null;

    public Day1View(Context context) {
        this(context, null, 0);
    }

    public Day1View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day1View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         * Point1:获取自定义属性
         */
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Day1View, defStyleAttr, 0);
        int n = array.length();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.Day1View_titleText:
                    mTitleText = array.getString(attr);
                    break;

                case R.styleable.Day1View_titleColor:
                    mTitleColor = array.getColor(attr, Color.BLACK);
                    break;

                case R.styleable.Day1View_titleSize:
                    /**
                     *Point2：将dp转化为px
                     */
                    mTitleSize = array.getDimensionPixelSize(attr, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics()));
                    break;

                case R.styleable.Day1View_titleBackground:
                    mTitleBackground = array.getColor(attr, Color.YELLOW);
                    break;
            }
        }
        array.recycle();
        mPaint.setColor(mTitleColor);
        /**
         *Point3:得到绘制文本的宽高
         */
        mPaint.setTextSize(mTitleSize);
        mTextBounds = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTextBounds);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                mTitleText = randomText();
                postInvalidate();
            }
        });
    }

    public String randomText() {
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int n = random.nextInt(10);
            sb.append(n);
        }

        return sb.toString();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         *Point4：设置宽高为wrap_content时手动计算空间宽高
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mTextBounds = new Rect();
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTextBounds);
            /**
             * Point5：宽高包含padding值
             */
            width = mTextBounds.width()+getPaddingLeft()+getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mTextBounds = new Rect();
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTextBounds);
            height = mTextBounds.height()+getPaddingTop()+getPaddingBottom();
        }

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mTitleBackground);
//        Log.e("wxp", "wxp-left : " + mTextBounds.left);
//        Log.e("wxp", "wxp-right : " + mTextBounds.right);
//        Log.e("wxp", "wxp-top : " + mTextBounds.top);
//        Log.e("wxp", "wxp-bottom : " + mTextBounds.bottom);
//        Log.e("wxp", "wxp-height : " + mTextBounds.height());
//        Log.e("wxp", "wxp-width : " + mTextBounds.width());
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleColor);
        /**
         * Point6：在边框内部居中绘制文字
         */

        canvas.drawText(mTitleText, getMeasuredWidth()/2-mTextBounds.width()/2, getMeasuredHeight()/2+mTextBounds.height()/2, mPaint);

    }
}
