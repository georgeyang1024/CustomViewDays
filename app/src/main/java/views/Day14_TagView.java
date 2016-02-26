package views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * @author : wangxianpeng
 * @date : 2016/2/26
 * E-mail : wangxianpeng@jd.com
 */
public class Day14_TagView extends FrameLayout implements Checkable {
    boolean mIsChecked = false;
    int[] CHECK_STATE = new int[]{android.R.attr.state_checked};
    public Day14_TagView(Context context) {
        this(context, null, 0);
    }

    public Day14_TagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day14_TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] status = super.onCreateDrawableState(extraSpace+1);
        if (isChecked()) {
            mergeDrawableStates(status, CHECK_STATE);
        }
        return status;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mIsChecked != checked) {
            mIsChecked = checked;
            refreshDrawableState();
        }
    }

    public View getTagView() {
        return getChildAt(0);
    }
    @Override
    public boolean isChecked() {
        return mIsChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mIsChecked);

    }


}