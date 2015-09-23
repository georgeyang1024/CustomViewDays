package views;


import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * CustomView Day9
 * 自定义ScrollView实现下拉选择
 * Created by wxp on 2015/8/30.
 */

public class Day9View extends ScrollView {
    /**
     * 包含菜单和内容的布局
     */
    LinearLayout mWrapper;
    /**
     * 包含四个选项的菜单布局
     */
    LinearLayout mMenuView;
    /**
     * 菜单下面的内容区域
     */
    LinearLayout mContentView;

    /**
     * 单位：dp
     * 菜单距离底部的距离
     */
    int mMenuBottomPadding = 50;

    /**
     * 菜单视图的高度
     */
    int mMenuHeight;
    /**
     * 1/2的高度
     */
    int mHalfMenuHeight;
    /**
     * 1/4的高度
     */
    int mQuaMenuHeight;

    /**
     * 下拉选择了哪个选项
     */
    int belongToChosen = 0;

    int mScreenWidth;
    int mScreenHeight;
    boolean once = false;
    private OnChosenListener onChosenListener;

    public Day9View(Context context) {
        this(context, null);
    }

    public Day9View(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
         *  将dp转化为px
         */
        mMenuBottomPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());

        /**
         * 获取屏幕宽高
         */
        WindowManager wManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wManager.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
        setVerticalScrollBarEnabled(false);

    }

    /**
     * Point1　： 设置子menu部分和Content部分的宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        once = true;
        mWrapper = (LinearLayout) getChildAt(0);
        mMenuView = (LinearLayout) mWrapper.getChildAt(0);
        mContentView = (LinearLayout) mWrapper.getChildAt(1);
        /**
         * Point2　： 设置menu区域的高度
         */
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mMenuHeight = mScreenWidth - 8 * mMenuBottomPadding;
            mMenuView.getLayoutParams().width = mScreenWidth;
        } else {
            mMenuHeight = mMenuView.getLayoutParams().height = mScreenHeight - 8 * mMenuBottomPadding;
        }

        mHalfMenuHeight = mMenuHeight / 2;
        mQuaMenuHeight = mMenuHeight / 4;

        /**
         * Point3　： 设置内容区域的高度
         */
        mContentView.getLayoutParams().height = heightSize;

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        super.onLayout(changed, l, t, r, b);
        /**
         * Point4　： 设置偏移量将menu隐藏
         */

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.scrollTo(0, mMenuHeight);
        } else {
            this.scrollTo(0, mMenuHeight);
        }


    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                /**
                 * Point5　：  判断滑动到那个menu item
                 */
                int scrollY = getScrollY();

                if (scrollY > 5 && scrollY <= mQuaMenuHeight) {
                    belongToChosen = 0;
                }
                if (scrollY > mQuaMenuHeight && scrollY <= mHalfMenuHeight) {
                    belongToChosen = 1;
                }
                if (scrollY > mHalfMenuHeight && scrollY < (3 * mQuaMenuHeight)) {
                    belongToChosen = 2;
                } else if (scrollY > (3 * mQuaMenuHeight) && scrollY < (mMenuHeight - 5)) {
                    belongToChosen = 3;
                }
                if (onChosenListener != null) {
                    onChosenListener.changChosen(belongToChosen);
                }


                /**
                 * Point6　：  缓慢滑动到初始位置
                 */
                this.smoothScrollTo(0, mMenuHeight);

                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 加入这一段代码即可实现抽屉世下拉
     *
      @Override protected void onScrollChanged(int l, int t, int oldl, int oldt) {
      super.onScrollChanged(l, t, oldl, oldt);
      float scale = t * 1.0f / mMenuHeight;
      ViewHelper.setTranslationY(mMenuView, mMenuHeight * scale);
      }
     */

    public void setOnChosenListener(OnChosenListener listener) {
        this.onChosenListener = listener;
    }

    public int getChosen() {
        return belongToChosen;
    }

    public interface OnChosenListener {
        public void changChosen(int choose);
    }
}
