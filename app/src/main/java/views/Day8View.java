package views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wxp.customview.R;

/**
 * CustomView Day8
 * 自定义View实现滑动switch
 * 参考网址 ：http://blog.csdn.net/xiaanming/article/details/8842453
 * Created by wxp on 2015/8/30.
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

        /**
         * Point1 ：设置OnTouchListener监听器
         */
        setOnTouchListener(this);

    }

    /**
     * Point2 ：初始化资源图片
     */
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
             * Point3 ：如果宽度小于背景图片宽度则取背景图片宽度
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
     * Point4 ：滑块左边X坐标
     */
    float mSwitchBtnX = 0;
    /**
     * Point5 ：当前手指的X坐标
     */
    float mX = 0;
    /**
     * Point6 ：是否正在滑动
     */
    boolean mOnSlip = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * Point7 ：判断滑块位置以及开关状态
         */
        if (mOnSlip) {
            if (mX < (getWidth() / 2 - mSwitchOff.getWidth() / 2)) {
                mSwitchBtnX = getWidth() / 2 - mSwitchOff.getWidth() / 2;
            } else if (mX > (getWidth() / 2 + mSwitchOn.getWidth() / 2 - mSwitchBtn.getWidth())) {
                mSwitchBtnX = getWidth() / 2 + mSwitchOn.getWidth() / 2 - mSwitchBtn.getWidth();
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

        /**
         * Point8 ：绘制不同状态下的背景
         */
        if (!mSwitch) {
            canvas.drawBitmap(mSwitchOff, getPaddingLeft(), getPaddingTop(), mPaint);
        } else {
            canvas.drawBitmap(mSwitchOn, getPaddingLeft(), getPaddingTop(), mPaint);
        }

        Log.e("wxp", "wxp-mSwitch : " + mSwitch);
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

            /**
             * Point9 ：滑动时不断改变滑动的位置
             */
            case MotionEvent.ACTION_MOVE:
                mX = event.getX();
                mSwitchBtnX = mX;

                break;

            case MotionEvent.ACTION_UP:
                mOnSlip = false;
                mX = event.getX();
                mSwitchBtnX = mX;
                break;
        }
        invalidate();
        return true;
    }
}
