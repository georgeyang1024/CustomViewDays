package views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * CustomView Day10
 * <p/>
 * 参考网址：http://blog.csdn.net/lmj623565791/article/details/23692439
 * Created by wxp on 2015/9/2.
 */
public class Day10View extends ViewGroup {

    Scroller mScroller = null;
    int mHeight = 0;

    public Day10View(Context context) {
        this(context, null, 0);
    }

    public Day10View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day10View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("wxp", "wxp-onMeasure- widthMeasureSpec : " + widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightSize);
            Log.e("wxp", "wxp-onMeasure- childView -width  : " + childView.getMeasuredWidth() + " | height : " + childView.getMeasuredHeight());
        }
        mHeight = heightSize;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("wxp", "wxp-onLayout- changed : " + changed);
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) getLayoutParams();

        int childCount = getChildCount();

        marginLayoutParams.height = mHeight * childCount;
        setLayoutParams(marginLayoutParams);

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(l, i * mHeight, r, (i + 1) * mHeight);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {



        return super.onTouchEvent(event);
    }
}
