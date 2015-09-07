package views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * CustomView Day12
 * 自定义ViewGroup实现竖向引导界面Part3，实现检测加速度自动滚动到上一屏或者下一屏，以及边界检测
 * 参考网址：http://blog.csdn.net/lmj623565791/article/details/23692439
 * Created by wxp on 2015/9/7.
 */
public class Day12View extends ViewGroup {
    int mScreenHeight = 0;
    Scroller mScroller = null;

    int mLastY = 0;
    int mScrollStartY = 0;
    int mScrollEndY = 0;
    boolean mIsScrolling = false;

    /**
     * 加速度检测
     */
    private VelocityTracker mVelocityTracker;

    public Day12View(Context context) {
        this(context, null, 0);
    }

    public Day12View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day12View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, mScreenHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) getLayoutParams();
        marginLayoutParams.height = childCount * mScreenHeight;
        setLayoutParams(marginLayoutParams);

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mIsScrolling) {
            return super.onTouchEvent(event);
        }
        int y = (int) event.getY();
        obtainVelocity(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mScrollStartY = getScrollY();
                break;

            case MotionEvent.ACTION_MOVE:

                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;

                /**
                 * Point1 ：
                 * (getScrollY() + dy) < 0表示已经到达控件顶部
                 * dy < 0表示还想要向下滑动
                 */
                if ((getScrollY() + dy) < 0 && dy < 0) {
                    return super.onTouchEvent(event);
                }

                /**
                 * Point2 ：
                 * (getScrollY() + dy) > (getHeight() - mScreenHeight)表示已经滑动到达控件底部了（控件的总的高度减去最后一个子View的高度则是最后一个控件的顶部位置）
                 * dy > 0表示还想要向上滑动
                 */
                if ((getScrollY() + dy) > (getHeight() - mScreenHeight) && dy > 0) {
                    return super.onTouchEvent(event);
                }

                scrollBy(0, dy);
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:

                mScrollEndY = getScrollY();
                int dScrollY = mScrollEndY - mScrollStartY;
                if (wantScrollNext()) {
                    if (ableScroll()) {
                        mScroller.startScroll(0, mScrollEndY, 0, mScreenHeight - dScrollY);
                    } else {
                        mScroller.startScroll(0, mScrollEndY, 0, -dScrollY);
                    }
                }

                if (wantScrollPre()) {
                    if (ableScroll()) {
                        mScroller.startScroll(0, mScrollEndY, 0, -mScreenHeight - dScrollY);
                    } else {
                        mScroller.startScroll(0, mScrollEndY, 0, -dScrollY);
                    }
                }
                mIsScrolling = true;
                postInvalidate();
                recycleVelocity();
                break;
        }

        return true;
    }

    /**
     * Point3 ：初始化加速度检测器
     *
     * @param event
     */
    public void obtainVelocity(MotionEvent event) {

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        mVelocityTracker.addMovement(event);
    }

    /**
     * Point4 ：释放加速度检测器
     */
    public void recycleVelocity() {

        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * Point5 ：获取Y轴方向加速度
     *
     * @return
     */
    public int getYVelocity() {

        mVelocityTracker.computeCurrentVelocity(1000);
        return (int) mVelocityTracker.getYVelocity();
    }

    public boolean wantScrollNext() {

        return mScrollEndY > mScrollStartY;
    }

    public boolean wantScrollPre() {

        return mScrollEndY < mScrollStartY;
    }

    /**
     * Point6 ： 当加速度大于600时，也判定可以滚动
     *
     * @return
     */
    public boolean ableScroll() {
        return Math.abs(mScrollEndY - mScrollStartY) > mScreenHeight / 2 || Math.abs(getYVelocity()) > 600;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        } else {
            mIsScrolling = false;
        }

    }
}
