package views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.wxp.customview.R;

/**
 * CustomView Day13
 * 自定义ListView实现左滑出现删除按钮
 * 参考网址：http://blog.csdn.net/lmj623565791/article/details/22961279
 * Created by wxp on 2015/9/22.
 */
public class Day13View extends ListView {
    PopupWindow mPop = null;
    LayoutInflater mInflater = null;
    Button mDelBtn = null;
    int mPopWidth = 0, mPopHeight = 0;
    int mCurrentItemPos = 0;
    View mCurrentItemView = null;
    int mX = 0, mY = 0;

    int mTouchSlop = 0;
    boolean mIsSliding = false;
    OnDelClickListener mOnDelClickListener = null;

    public Day13View(Context context) {
        this(context, null, 0);
    }

    public Day13View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day13View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * Point1 ： 获取最小滑动距离
         */
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.day13_view_popview, null);
        mDelBtn = (Button) view.findViewById(R.id.id_day13_view_pop_btn);
        /**
         * Point2 ： 创建 PopupWindow
         */
        mPop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        /**
         * Point3 ： 计算PopupWindow的宽高，首先要measre一下，否则无法获取宽高
         */
        mPop.getContentView().measure(0, 0);
        mPopWidth = mPop.getContentView().getMeasuredWidth();
        mPopHeight = mPop.getContentView().getMeasuredHeight();
    }





    /**
     * Point4 ： 这里重写dispatchTouchEvent方法是为了判断是否需要显示PopupWindow
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();

        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                mX = x;
                mY = y;
                /**
                 * Point5 ： 对于ACTION_DOWN事件，如果PopupWindow已经显示了，则把它隐藏了，并且直接返回false，表示屏蔽listview的touch事件，下面的时间不做处理。
                 */
                if (mPop.isShowing()) {
                    hidePopView();
                    return false;
                }

                /**
                 * Point6 ： 如果PopupWindow未显示，首先计算出当前触摸的item在列表中所在的位子，然后通过这个位置的值获取这个item对应的View
                 */
                mCurrentItemPos = pointToPosition(mX, mY);
                mCurrentItemView = getChildAt(mCurrentItemPos - getFirstVisiblePosition());
                if (mCurrentItemView == null) {
                    return false;
                }
                Log.e("day13", "wxp-mCurrentItemPos : " + mCurrentItemPos + " | firstVisiblePos : " + getFirstVisiblePosition());
                break;

            case MotionEvent.ACTION_MOVE:

                int dx = x - mX;
                int dy = y - mY;

                /**
                 * Point7 ： 在dispatchTouchEvent的ACTION_MOVE事件中计算滑动的距离，以此来判断mIsSliding的值
                 */
                if (Math.abs(dx) > mTouchSlop && Math.abs(dy) < mTouchSlop && x < mX) {
                    mIsSliding = true;
                }
                break;
        }


        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();

        /**
         * Point8 ： 到了这一步就可以真正来显示PopupWindow，前提是mIsSliding为true，也就是判断手指是在某一个item上向左滑动
         */
        if (mIsSliding) {

            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    /**
                     * Point9 ： 如果PopupWindow已经显示，则直接返回true，屏蔽listview的滑动操作。如果没有这一步会导致左滑出现PopupWindow不松手继续上下滑动的时候，listview也会跟着上下滑动
                     */
                    if (mPop.isShowing()) {
                        return true;
                    }

                    /**
                     * Point10 ： 获取当前操作的这个item对应的View在屏幕上的位置
                     */
                    int[] location = new int[2];
                    mCurrentItemView.getLocationOnScreen(location);
                    /**
                     * Point11 ： 设置PopupWindow出现和隐藏的动画
                     */
                    mPop.setAnimationStyle(R.style.popwindow_delete_btn_anim_style);
                    mPop.update();
                    /**
                     * Point13 ： 设置PopupWindow显示的位置，是在当前item的右边。
                     */
                    mPop.showAtLocation(mCurrentItemView, Gravity.LEFT | Gravity.TOP, location[0] + mCurrentItemView.getWidth(), location[1] + mCurrentItemView.getHeight() / 2 - mPopHeight / 2);
                    /**
                     * Point14 ： 设置删除按钮的点击事件
                     */
                    mDelBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnDelClickListener != null) {

                                Animation.AnimationListener al = new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        mOnDelClickListener.deleteItem(mCurrentItemPos);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                };
                                /**
                                 * Point15 ： 添加item删除动画
                                 */
                                collapse(mCurrentItemView, al);

                            }
                            mPop.dismiss();
                        }
                    });

                    break;

                case MotionEvent.ACTION_UP:

                    /**
                     *  手指抬起更新滑动状态为false
                     */
                    mIsSliding = false;
                    break;
            }


            return super.onTouchEvent(ev);
        }


        return super.onTouchEvent(ev);
    }

    public void hidePopView() {
        if (mPop != null && mPop.isShowing()) {
            mPop.dismiss();
        }
    }

    public void setOnDelClickListener(OnDelClickListener listener) {
        this.mOnDelClickListener = listener;

    }

    public interface OnDelClickListener {
        boolean deleteItem(int pos);
    }

    private void collapse(final View view, Animation.AnimationListener al) {
        final int originHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                /**
                 *Point16 ： 注意此处判断的值需小于1.否则会界面上多删除一个item
                 */
                if (interpolatedTime == 0.9f) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = originHeight - (int) (originHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        if (al != null) {
            animation.setAnimationListener(al);
        }
        animation.setDuration(200);
        view.startAnimation(animation);
        Log.e("day13", "wxp-length startAnimation");
    }
}
