package views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * CustomView Day15
 * 自定义ListView是的ListView嵌套在ScrollView中时，解决ListView与ScrollView的冲突
 * 滑动到ListView顶部后继续滑动会触发ScrollView继续滑动；
 * 滑动到ListView底部后继续滑动会触发ScrollView继续滑动。
 * @author : wangxianpeng
 * @date : 2015/10/23
 * E-mail : wisimer@163.com
 */
public class Day15View extends ListView {
    public static final String TAG = Day15View.class.getSimpleName() + "-->wxp-->";

    float downY = 0;
    float y = 0;

    private int mTouchSlop = 0;

    public Day15View(Context context) {
        this(context, null, 0);
    }

    public Day15View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day15View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * ListView的父布局是ScrollView
     * requestDisallowInterceptTouchEvent方法的作用是通知父布局是否需要拦截事件。
     * 虽然GroupView默认是不拦截事件的。默认也是不处理事件的。
     * 但是ScrollView继承Framelayout时重写了onInterceptTouchEvent方法，使得ScrollView可以拦截滑动事件
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, TAG + "dispatchTouchEvent");

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, TAG + "dispatchTouchEvent -> MotionEvent.ACTION_DOWN");
                downY = ev.getRawY();
                y = downY;
                getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, TAG + "dispatchTouchEvent -> MotionEvent.ACTION_MOVE");
                y = ev.getRawY();
                if (scrollToTop()) {
                    Log.e(TAG, TAG + "scrollToTop -> y = " + y + " | downY = " + downY);

                    if (y - downY > mTouchSlop) {
                        /**
                         * Point 1 : 如果滑动到顶部，并且手指还想向下滑动，则事件交还给父控件，要求父控件可以拦截事件
                         */
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    } else if (y - downY < -mTouchSlop) {
                        /**
                         * Point 2 : 如果滑动到顶部，并且手指正常向上滑动，则事件由自己处理，要求父控件不许拦截事件
                         */
                        getParent().requestDisallowInterceptTouchEvent(true);

                    }

                }

                if (scrollToBottom()) {
                    Log.e(TAG, TAG + "scrollToBottom -> y = " + y + " | downY = " + downY);

                    if (y - downY < -mTouchSlop) {
                        /**
                         * Point 3 : 如果滑动到底部，并且手指还想向上滑动，则事件交还给父控件，要求父控件可以拦截事件
                         */
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    } else if (y - downY > mTouchSlop) {
                        /**
                         * Point 4 : 如果滑动到底部，并且手指正常向下滑动，则事件由自己处理，要求父控件不许拦截事件
                         */
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }

                break;

            case MotionEvent.ACTION_UP:
                Log.e(TAG, TAG + "dispatchTouchEvent -> MotionEvent.ACTION_UP ");
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, TAG + "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e(TAG, TAG + "onTouchEvent");
        return super.onTouchEvent(ev);
    }

    public boolean scrollToBottom() {
        int first = getFirstVisiblePosition();
        int last = getLastVisiblePosition();
        int visibleCoutn = getChildCount();
        int count = getCount();
        if ((first + visibleCoutn) == count) {
            return true;
        }
        return false;
    }

    public boolean scrollToTop() {
        int first = getFirstVisiblePosition();
        int last = getLastVisiblePosition();
        int visibleCoutn = getChildCount();
        int count = getCount();

        if (first == 0) {
            return true;
        }
        return false;
    }
}
