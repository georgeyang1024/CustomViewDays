package com.wxp.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * CustomView Day6
 * 自定义View实现FlowLayout
 * 参考网址：http://blog.csdn.net/lmj623565791/article/details/38352503
 * Created by wxp on 2015/8/25.
 */
public class Day6View extends ViewGroup {
    public Day6View(Context context) {
        super(context);
    }

    public Day6View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Day6View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        MarginLayoutParams params = null;
        View childView = null;
        int childWidth = 0, childHeight = 0;

        int width = widthSize, height = 0;
        int lineWidth = 0, lineHeight = 0;

        /**
         * Point1 ： 计算控件的宽高，宽度要么指定具体数值，要么match_parent，不可能wrap_content，高度可以wrap_content
         */
        for (int i = 0; i < childCount; i++) {
            childView = getChildAt(i);
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            params = (MarginLayoutParams) childView.getLayoutParams();

            if ((lineWidth + (params.leftMargin + childWidth + params.rightMargin)) < width) {
                lineHeight = Math.max(childHeight, childHeight + params.topMargin + params.bottomMargin);
                lineWidth += (params.leftMargin + childWidth + params.rightMargin);
            } else {
                /**
                 * Point2 ： height作为最终高度，是每一行的高度的累加
                 */
                height += lineHeight;
                lineWidth = 0;
                lineWidth += (params.leftMargin + childWidth + params.rightMargin);

            }

        }
        height += lineHeight;

        if (heightMode == MeasureSpec.EXACTLY) {

            height = heightSize;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("wxp", "wxp-onLayout ");
        View childView = null;
        MarginLayoutParams params = null;
        int childWidth = 0, childHeight = 0;
        int childCount = getChildCount();
        int left = 0, top = 0, right = 0, bottom = 0;

        int lineWidth = 0;
        int lineHeight = 0;
        int mHeight = 0;

        /**
         * Point3 ： 计算每个子View的具体位置
         */
        for (int i = 0; i < childCount; i++) {
            childView = getChildAt(i);
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            params = (MarginLayoutParams) childView.getLayoutParams();

            if ((lineWidth + (params.leftMargin + childWidth + params.rightMargin)) < getWidth()) {

                left = lineWidth + params.leftMargin;
                right = left + childWidth;
                /**
                 * Point4 ： top值是当前总高度加上topMargin值
                 */
                top = mHeight + params.topMargin;
                bottom = top + childHeight;
                lineWidth += (params.leftMargin + childWidth + params.rightMargin);
                /**
                 * Point5 ： lineHeight值是当前bottom值加上bottomMargin值
                 */
                lineHeight = bottom + params.bottomMargin;

            } else {
                /**
                 * Point6 ： 换行后，将lineHeight赋值给mHeight
                 */
                mHeight = lineHeight;
                /**
                 * Point7 ： 当lineWidth加上当前子view宽度超过控件宽度则换行排列，换行时首先将lineWidth置0，再加上当前子View的宽度和margin值
                 */
                lineWidth = 0;
                lineWidth += (params.leftMargin + childWidth + params.rightMargin);
                left = params.leftMargin;
                right = left + childWidth;
                top = mHeight + params.topMargin ;
                bottom = top + childHeight;

            }

            childView.layout(left, top, right, bottom);
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
