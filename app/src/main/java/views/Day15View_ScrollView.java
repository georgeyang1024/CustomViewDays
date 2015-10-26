package views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author : wangxianpeng
 * @date : 2015/10/26
 * E-mail : wisimer@163.com
 */
public class Day15View_ScrollView extends ScrollView {
    public static final String TAG = Day15View_ScrollView.class.getSimpleName() + "-->wxp-->";

    public Day15View_ScrollView(Context context) {
        super(context);
    }

    public Day15View_ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Day15View_ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, TAG + "dispatchTouchEvent");

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, TAG + "dispatchTouchEvent -> MotionEvent.ACTION_DOWN");

            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, TAG + "dispatchTouchEvent -> MotionEvent.ACTION_MOVE");


            case MotionEvent.ACTION_UP:
                Log.e(TAG, TAG + "dispatchTouchEvent -> MotionEvent.ACTION_UP ");


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
}
