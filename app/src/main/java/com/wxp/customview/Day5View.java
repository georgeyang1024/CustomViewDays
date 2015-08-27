package com.wxp.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * CustomView Day5
 * 自定义ViewGroup实现四个子view分别位于四个角落
 * 参考网址：http://blog.csdn.net/lmj623565791/article/details/38339817
 * Created by wxp on 2015/8/25.
 */
public class Day5View extends ViewGroup {
    public Day5View(Context context) {
        super(context);
    }

    public Day5View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Day5View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

        /**
         * Point1：计算所有子View的宽高
         */
        measureChildren(widthMode, heightMode);

        int childCount = getChildCount();

        MarginLayoutParams marginLayoutParams = null;

        View childView = null;
        int childWidth = 0;
        int childHeight = 0;

        int topWidth = 0;
        int bottomWidth = 0;
        int leftHeight = 0;
        int rightHeight = 0;
        for (int i = 0; i < childCount; i++) {
            childView = getChildAt(i);
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            /**
             * Point2：获取每个子view的layoutparams
             */
            marginLayoutParams = (MarginLayoutParams) childView.getLayoutParams();

            /**
             * Point3：计算上面两个子View所占的宽度
             */
            if (i == 0 || i == 1) {
                topWidth += marginLayoutParams.leftMargin + childWidth + marginLayoutParams.rightMargin;
            }
            /**
             * Point4：计算下面两个子View所占的宽度
             */
            if (i == 2 || i == 3) {
                bottomWidth += marginLayoutParams.leftMargin + childWidth + marginLayoutParams.rightMargin;
            }
            /**
             * Point5：计算左边两个子View所占的高度
             */
            if (i == 0 || i == 2) {
                leftHeight += marginLayoutParams.topMargin + childHeight + marginLayoutParams.bottomMargin;
            }
            /**
             * Point6：计算右边两个子View所占的高度
             */
            if (i == 1 || i == 3) {
                rightHeight += marginLayoutParams.topMargin + childHeight + marginLayoutParams.bottomMargin;
            }
        }

        /**
         * Point7：取两者的较大值
         */
        width = Math.max(topWidth, bottomWidth);
        height = Math.max(leftHeight, rightHeight);


        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }
        setMeasuredDimension(width, height);
    }

    /**
     * Point8：四个子view的排布方式：
     * 0 1
     * 2 3
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();

        MarginLayoutParams marginLayoutParams = null;
        View childView = null;
        int childWidth = 0;
        int childHeight = 0;

        int left = 0, right = 0, top = 0, bottom = 0;
        for (int i = 0; i < childCount; i++) {
            childView = getChildAt(i);
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            marginLayoutParams = (MarginLayoutParams) childView.getLayoutParams();

            /**
             * Point9：计算每个角落子View的l,t,r,b值
             */
            switch (i) {
                case 0:
                    left = marginLayoutParams.leftMargin;
                    top = marginLayoutParams.topMargin;
                    right = left + childWidth;
                    bottom = top + childHeight;
                    break;

                case 1:
                    top = marginLayoutParams.topMargin;
                    left = getWidth() - marginLayoutParams.rightMargin - childWidth;
                    right = left + childWidth;
                    bottom = top + childHeight;
                    break;

                case 2:
                    left = marginLayoutParams.leftMargin;
                    top = getHeight() - childHeight - marginLayoutParams.bottomMargin;
                    right = left + childWidth;
                    bottom = top + childHeight;
                    break;

                case 3:
                    left = getWidth() - marginLayoutParams.rightMargin - childWidth;
                    top = getHeight() - childHeight - marginLayoutParams.bottomMargin;
                    right = left + childWidth;
                    bottom = top + childHeight;
                    break;
            }

            /**
             * Point10：设置子view的位置
             */
            childView.layout(left, top, right, bottom);
        }
    }

    /**
     * Point11：重写generateLayoutParams方法，使得ViewGroup支持MarginLayoutParams
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
