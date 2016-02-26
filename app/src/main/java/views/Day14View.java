package views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * CustomView Day14
 * TagFlowLayoyt:实现一个针对tag标签的布局,继承自已有的Day6View，在Day6View的基础上再加上实现点击事件以及数据以adapter注入
 * 参考网址： http://blog.csdn.net/lmj623565791/article/details/48393217
 * Created by wxp on 15/9/26.
 */
public class Day14View extends Day6View implements Day14_TagAdapter.OnDataChangedListener {


    public Day14View(Context context) {
        this(context, null, 0);
    }

    public Day14View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day14View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
    }

    public Day14_TagAdapter mAdapter;

    public void setAdapter(Day14_TagAdapter adapter) {
        mAdapter = adapter;
        mAdapter.setOnDataChangedListener(this);
        changeAdapter();
    }

    @Override
    public void onDataChange() {
        changeAdapter();
    }

    public void changeAdapter() {

        removeAllViews();
        Day14_TagAdapter adapter = mAdapter;

        Day14_TagView tagViewContainer = null;

        for (int i = 0; i < adapter.getCount(); i++) {
            View tagView = adapter.getView(this, i, adapter.getItem(i));

            tagViewContainer = new Day14_TagView(getContext());
            /**
             * 使得父控件的状态变化能够向下传递
             */
            tagView.setDuplicateParentStateEnabled(true);
            if (tagView.getLayoutParams() != null) {

                tagViewContainer.setLayoutParams(tagView.getLayoutParams());
            }

            tagViewContainer.addView(tagView);

            addView(tagViewContainer);
        }
    }

    private MotionEvent mMotionEvent;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            mMotionEvent = MotionEvent.obtain(event);
            Log.d("onTouchEvent", " onTouchEvent --> up");
        } else {
            Log.d("onTouchEvent", " onTouchEvent --> other");
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        if (mMotionEvent == null) {
            return super.performClick();
        }

        int x = (int) mMotionEvent.getX();
        int y = (int) mMotionEvent.getY();

        /**
         * 注意这里要把mMotionEvent置空
         */
        mMotionEvent = null;

        Day14_TagView tagView = findChildView(x, y);

        int pos = findPosByView(tagView);

        if (tagView != null) {

            tagView.toggle();

            if (mOnTagClickListener != null) {
                /**
                 * 这里的clickTag应该是TagView里面包装的子View
                 */
                return mOnTagClickListener.clickTag(this, pos, tagView.getTagView());
            }
        }

        return super.performClick();
    }

    private int findPosByView(View child) {
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View v = getChildAt(i);
            if (v == child) return i;
        }
        return -1;
    }

    private OnTagClickListener mOnTagClickListener;

    public void setOnTagClickListener(OnTagClickListener listener) {
        this.mOnTagClickListener = listener;
    }

    public interface OnTagClickListener {
        boolean clickTag(View parent, int pos, View tagView);
    }

    public interface OnTagSelectedListener {

    }

    public Day14_TagView findChildView(int x, int y) {

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            Day14_TagView tagView = (Day14_TagView) getChildAt(i);

            Rect rect = new Rect();

            tagView.getHitRect(rect);

            if (rect.contains(x, y)) {
                return tagView;
            }
        }

        return null;


    }
}
