package views;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : wangxianpeng
 * @date : 2016/2/26
 * E-mail : wangxianpeng@jd.com
 */
public abstract class Day14_TagAdapter<T> {

    private List<T> mData;
    private int mCount = 0;

    public Day14_TagAdapter(List<T> data) {
        this.mData = data;
    }

    public Day14_TagAdapter(T[] data) {
        this.mData = new ArrayList<>(Arrays.asList(data));
    }

    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    public abstract View getView(Day6View parent, int pos, T t);

    private OnDataChangedListener mListener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.mListener = listener;
    }

    public void notifyDataChanged() {
        mListener.onDataChange();
    }

    public T getItem(int pos) {
        return mData.get(pos);
    }

    static interface OnDataChangedListener {
        void onDataChange();
    }
}
