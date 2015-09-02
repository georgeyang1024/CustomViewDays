package views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.wxp.customview.R;

/**
 * CustomView Day7
 * 自定义View实现圆角矩形图像以及圆形图像
 * 参考网址：http://blog.csdn.net/lmj623565791/article/details/24555655
 * Created by wxp on 2015/8/27.
 */
public class Day7View extends View {
    Bitmap mSrcBmp = null;
    int mSrcType = 0;
    int mCornerRadius = 10;
    Paint mPaint = null;

    public Day7View(Context context) {
        this(context, null, 0);
    }

    public Day7View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day7View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Day7View, defStyleAttr, 0);
        int length = array.length();
        for (int i = 0; i < length; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.Day7View_src:
                    mSrcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.whisper);
                    break;

                case R.styleable.Day7View_type:
                    mSrcType = array.getInt(attr, 0);
                    break;

                case R.styleable.Day7View_cornerRadius:
                    mCornerRadius = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

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
            width = widthSize;
        } else {
            /**
             * Point1　：　注意计算宽度时要包含padding值
             */
            width = mSrcBmp.getWidth() + getPaddingLeft() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = mSrcBmp.getHeight() + getPaddingTop() + getPaddingTop();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mSrcBmp = Bitmap.createScaledBitmap(mSrcBmp, getWidth(), getHeight(), false);
        /**
         * Point２　：　根据不同类型绘制不同图像
         */
        if (mSrcType == 0) {
            canvas.drawBitmap(createCircleBitmap(mSrcBmp, Math.min(getWidth(),getHeight())), 0, 0, mPaint);
        } else {
            canvas.drawBitmap(createRoundCornerBitmap(mSrcBmp, getWidth(), getHeight()), 0, 0, mPaint);
        }
    }

    /**
     * Point３　：　创建圆形图像
     * @param src
     * @param width
     * @return
     */
    public Bitmap createCircleBitmap(Bitmap src, int width) {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        Bitmap resBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resBitmap);
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src, 0, 0, paint);
        return resBitmap;
    }

    /**
     * Point４　：　创建圆角矩形图像
     * @param src
     * @param width
     * @param height
     * @return
     */
    public Bitmap createRoundCornerBitmap(Bitmap src, int width,int height) {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        Bitmap resBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resBitmap);
        RectF rectF = new RectF(0,0,width,height);
        canvas.drawRoundRect(rectF,mCornerRadius,mCornerRadius,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src, 0, 0, paint);
        return resBitmap;
    }
}
