package com.wxp.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wxp on 15/8/30.
 */
public class Day8View extends View implements View.OnTouchListener {
    public Day8View(Context context) {
        this(context, null, 0);
    }

    public Day8View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    Bitmap mSwitchOff = null;
    Bitmap mSwitchOn = null;
    Bitmap mSwitchBtn = null;

    boolean mSwitch = false;
    Paint mPaint = null;

    public Day8View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwitch = !mSwitch;
                invalidate();
            }
        });
    }

    public void init() {
        mSwitchOff = BitmapFactory.decodeResource(getResources(), R.drawable.switch_off);
        mSwitchOn = BitmapFactory.decodeResource(getResources(), R.drawable.switch_on);
        mSwitchBtn = BitmapFactory.decodeResource(getResources(), R.drawable.switch_btn);
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0, height = 0;
        int bgWidth = mSwitchOff.getWidth();
        int bgHeight = mSwitchOff.getHeight();
        if (widthMode == MeasureSpec.EXACTLY) {
            /**
             * 如果宽度小于背景图片宽度则取背景图片宽度
             */
            width = widthSize < bgWidth ? bgWidth : widthSize;
        } else {
            width = bgWidth + getPaddingLeft() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize < bgHeight ? bgHeight : heightSize;
        } else {
            height = bgHeight + getPaddingLeft() + getPaddingRight();
        }

        setMeasuredDimension(width, height);

    }


    /**
     * 滑块左边X坐标
     */
    float mSwitchBtnX = 0;
    /**
     * 当前手指的X坐标
     */
    float mX = 0;
    /**
     * 是否正在滑动
     */
    boolean mOnSlip = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mSwitch) {
            canvas.drawBitmap(mSwitchOff, getPaddingLeft(), getPaddingTop(), mPaint);
        } else {
            canvas.drawBitmap(mSwitchOn, getPaddingLeft(), getPaddingTop(), mPaint);
        }

        if (mOnSlip) {
            if (mX < (getWidth() / 2 - mSwitchOff.getWidth() / 2)) {
                mSwitchBtnX = getWidth() / 2 - mSwitchOff.getWidth() / 2;
            } else if (mX > (getWidth() / 2 + mSwitchOn.getWidth() / 2 - mSwitchBtn.getWidth())) {
                mSwitchBtnX = mSwitchBtnX = getWidth() / 2 + mSwitchOn.getWidth() / 2 - mSwitchBtn.getWidth();
            }
        } else {
            if (mSwitchBtnX < (getWidth() / 2 - mSwitchBtn.getWidth() / 2)) {
                mSwitch = false;
            } else {
                mSwitch = true;
            }
            if (!mSwitch) {
                mSwitchBtnX = getWidth() / 2 - mSwitchOff.getWidth() / 2;
            } else {
                mSwitchBtnX = getWidth() / 2 + mSwitchOn.getWidth() / 2 - mSwitchBtn.getWidth();
            }
        }
        canvas.drawBitmap(mSwitchBtn, mSwitchBtnX, getHeight() / 2 - mSwitchBtn.getHeight() / 2, mPaint);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mOnSlip = true;
                mX = event.getX();
                mSwitchBtnX = mX;
                break;

            case MotionEvent.ACTION_MOVE:
                mSwitchBtnX = event.getX();
                break;

            case MotionEvent.ACTION_UP:
                mOnSlip = false;
                break;
        }
        invalidate();
        return true;
    }
}
