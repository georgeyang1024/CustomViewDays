package views;

import android.content.Context;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * CustomView Day14
 * TagFlowLayoyt:实现一个针对tag标签的布局
 * 参考网址： http://blog.csdn.net/lmj623565791/article/details/48393217
 * Created by wxp on 15/9/26.
 */
public class Day14View extends FrameLayout implements Checkable {
    boolean mIsChecked = false;
    int[] CHECK_STATE = new int[]{android.R.attr.state_checked};
    public Day14View(Context context) {
        super(context);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace);
        if (mIsChecked) {
            mergeDrawableStates(states, CHECK_STATE);
        }
        return states;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mIsChecked != checked) {
            mIsChecked = checked;
            refreshDrawableState();
        }

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
