package views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * CustomView Day10
 * 自定义ViewGroup实现竖向引导界面Part1，首先来实现控制可以随着手指滑动上下滚动
 * 参考网址：http://blog.csdn.net/lmj623565791/article/details/23692439
 * Created by wxp on 2015/9/6.
 */
public class Day10View extends ViewGroup {
    int mScreenHeight = 0;
    Scroller mScroller = null;

    public Day10View(Context context) {
        this(context, null, 0);
    }

    public Day10View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day10View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * Point1 ：获取屏幕高度
         */
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenHeight = metrics.heightPixels;
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        /**
         * Point2 ： 根据高度计算每个子View布局
         */
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, mScreenHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        /**
         * Point3 ： 设置控件的高度为 屏幕的高度*子View的个数
         */
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) getLayoutParams();
        marginLayoutParams.height = mScreenHeight * childCount;
        setLayoutParams(marginLayoutParams);

        /**
         *Point4 ： 确定每个子View的位置
         */
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
        }
    }

    boolean mIsScrolling = false;
    /**
     * 移动时不断更新的Y值
     */
    int mLastY = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        /**
         * Point5：获取当前手指位置的Y坐标
         */
        int y = (int) event.getY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                /**
                 *Point6 ：手指按下时获取Y轴滚动的距离。
                 * getScrollY()是View的方法，如果为正表示内容向上滑动
                 */
                mLastY = y;
                break;

            case MotionEvent.ACTION_MOVE:

                /**
                 * Point7 : 计算Y轴滚动的差值
                 */
                int dy = mLastY - y;

                /**
                 * Point8 : 调用scrollBy执行滚动
                 */
                scrollBy(0, dy);

                /**
                 * Point9 ： 这一步很重要，在手指尚未抬起时，要将y赋值给mLastY，接着继续执行Point7计算差值
                 */
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

}
