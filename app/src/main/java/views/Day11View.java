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
 * CustomView Day11
 * 自定义ViewGroup实现竖向引导界面Part2，实现自动滚动到上一屏或者下一屏
 * 参考网址：http://blog.csdn.net/lmj623565791/article/details/23692439
 * Created by wxp on 2015/9/7.
 */
public class Day11View extends ViewGroup {
    int mScreenHeight = 0;
    Scroller mScroller = null;
    boolean mIsScrolling = false;
    /**
     * 移动时不断更新的Y值
     */
    int mLastY = 0;
    int mScrollStartY = 0;
    int mScrollEndY = 0;

    public Day11View(Context context) {
        this(context, null, 0);
    }

    public Day11View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public Day11View(Context context, AttributeSet attrs, int defStyleAttr) {
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        /**
         * Point5 ：如果正在滚动则直接返回父类的onTouchEvent方法
         */
        if (mIsScrolling) {
            return super.onTouchEvent(event);
        }

        /**
         * Point6  ： 获取当前手指位置的Y坐标,这个值在滑动时是不断更新的
         */
        int y = (int) event.getY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                /**
                 *Point7 ：手指按下时获取Y轴滚动的距离。
                 * getScrollY()是View的方法，如果为正表示内容向上滑动
                 */
                mLastY = y;
                mScrollStartY = getScrollY();
                break;

            case MotionEvent.ACTION_MOVE:

                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                /**
                 * Point8 : 实时计算Y轴滚动的差值
                 */
                int dy = mLastY - y;

                /**
                 *还要做边界检查，滑到最顶端或者最底端的时候不能再滑动了
                 */


                /**
                 * Point9 : 调用scrollBy执行滚动
                 */
                scrollBy(0, dy);
                Log.e("wxp", "wxp-MotionEvent.ACTION_MOVE");
                /**
                 * Point10 ： 这一步很重要，在手指尚未抬起时，要将y赋值给mLastY，接着继续执行Point7计算差值
                 */
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:

                mScrollEndY = getScrollY();

                /**
                 * Point11 ： 计算抬起时和按下时的差值
                 */
                int dScrollY = mScrollEndY - mScrollStartY;

                if (wantScrollNext()) {
                    if (ableScroll()) {
                        /**
                         * Point12 ： 判断用户想要滑到下一屏并且已经滑动的距离超过屏幕高度的1/2，则松开手指后控件自动继续向上滚动，从而滑动到下一屏
                         */
                        mScroller.startScroll(0, mScrollEndY, 0, mScreenHeight - dScrollY);
                    } else {
                        /**
                         * Point13 ：否则，则松开手指后控件自动滚会原来的位置
                         */
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

                /**
                 * 下面这一行很重要，表示抬起后，继续滑动。
                 * 如果没有这一行，松开手指后，自动滑动过程中点击控件会停止滑动
                 */
                mIsScrolling = true;
                postInvalidate();
                break;
        }
        return true;
    }

    /**
     * Point14 ：如果抬起时Y值大于按下时Y值，表示用户想要滑动到下一屏
     *
     * @return
     */
    public boolean wantScrollNext() {

        return mScrollEndY > mScrollStartY;
    }

    /**
     * 如果抬起时Y值小于按下时Y值，表示用户想要滑动到上一屏
     *
     * @return
     */
    public boolean wantScrollPre() {

        return mScrollEndY < mScrollStartY;
    }

    /**
     * Point15 ：如果差值大于屏幕的1/2则可以滑动
     * @return
     */
    public boolean ableScroll() {
        return Math.abs(mScrollEndY - mScrollStartY) > mScreenHeight / 2;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        /**
         * Point16 ： 判断是否滚动结束
         */
        Log.e("wxp", "wxp-mScroller.computeScrollOffset()-mScroller.getCurrY() 1 : " + mScroller.getCurrY());
        if (mScroller.computeScrollOffset()) {

            scrollTo(0, mScroller.getCurrY());
            Log.e("wxp", "wxp-mScroller.computeScrollOffset()-mScroller.getCurrY() 2 : " + mScroller.getCurrY());
            postInvalidate();
        } else {
            Log.e("wxp", "wxp-mScroller.computeScrollOffset()-else : " + mScroller.getCurrY());
            int position = getScrollY();
            mIsScrolling = false;
        }
    }
}
