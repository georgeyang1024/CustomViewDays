package views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxp.customview.R;

import java.util.List;

/**
 * 自定义View实现超时热门分类
 *
 * @author : wangxianpeng
 * @date : 2015/12/15
 * E-mail : wisimer@163.com
 */
public class Day17View extends LinearLayout {

    private int mChildWidth = 0;
    private int mChildHeight = 0;
    private int mWidth = 0;
    private int mHeight = 0;
    private Context mContext;
    private HotCategoryAdapter mHotCategoryAdapter;

    public Day17View(Context context) {
        this(context, null, 0);
    }

    public Day17View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day17View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void setAdapter(HotCategoryAdapter adapter) {
        mHotCategoryAdapter = adapter;
    }

    public void bindLayout() {
        int count = mHotCategoryAdapter.getCount();
        this.removeAllViews();
        for (int i = 0; i < count; i++) {
            View v = mHotCategoryAdapter.getView(i, null, this);
            //v.setOnClickListener(this. onClickListener );
            addView(v);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);


        mWidth = width;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, mChildHeight);
        }

        if (childCount > 0) {
            View childView = getChildAt(0);

            mChildWidth = childView.getMeasuredWidth();
            mChildHeight = childView.getMeasuredHeight();

            if (heightMode == MeasureSpec.AT_MOST) {
                mHeight = childView.getMeasuredHeight();

            } else {

                if (height < childView.getMeasuredHeight()) {
                    mHeight = childView.getMeasuredHeight();
                } else {
                    mHeight = height;
                }
            }
            Log.d("Day17onLayout", "mWidth = " + mWidth + " | mHeight =  " + mHeight + " | " + childView.getMeasuredHeight());
        }

        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        int leftPadding = getPaddingLeft();

        int rightPadding = getPaddingRight();
        int topPadding = getPaddingTop();
        int bottomPadding = getPaddingBottom();
        int horSpace = 0;
        int verSpace = 0;
        verSpace = (mHeight - mChildHeight - topPadding - bottomPadding) / 2;
        if (childCount > 1) {
            horSpace = (mWidth - leftPadding - rightPadding - mChildWidth * childCount) / (childCount - 1);

            Log.d("Day17onLayout", "horSpace = " + horSpace + " | mChildWidth = " + mChildWidth);
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);

                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();
                int left = leftPadding + childWidth * i + horSpace * i;
                int right = left + childWidth;
                int top = topPadding + verSpace;
                int bottom = top + childHeight;
                Log.d("Day17onLayout", left + " | " + top + " | " + right + " | " + bottom + " | ");
                childView.layout(left, top, right, bottom);
            }
        }
        if (childCount == 1) {
            View childView = getChildAt(0);
            horSpace = (mWidth - leftPadding - rightPadding - mChildWidth) / 2;
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            int left = leftPadding + horSpace;
            int right = left + childWidth;
            int top = topPadding + verSpace;
            int bottom = top + childHeight;
            Log.d("Day17onLayout", left + " | " + top + " | " + right + " | " + bottom + " | ");
            childView.layout(left, top, right, bottom);
        }


    }


    public static class InnerItem {
        public int index = 0;
        public String name = "清洁";
        public String url = "http://jd.com";
        public String imgUrl = "http://img11.360buyimg.com/n4/jfs/t1936/162/1274013976/110854/f2b30c64/5653db54N6f7bef81.jpg";

        public InnerItem(int index, String name, String url, String imgUrl) {
            this.index = index;
            this.name = name;
            this.url = url;
            this.imgUrl = imgUrl;
        }
    }

    public static class HotCategoryAdapter extends BaseAdapter {

        List<InnerItem> mData;
        private Context mContext;

        public HotCategoryAdapter(Context context, List<InnerItem> data) {
            mContext = context;
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public InnerItem getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return ((InnerItem) mData.get(position)).index;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.channel_hotcategory_item_layout, parent, false);
            Log.d("Day17onLayout", "convertView.getWidth = " + convertView.getMeasuredWidth() + " | getHeight =  " + convertView.getHeight());
            TextView name = (TextView) convertView.findViewById(R.id.channel_hotcategory_item_id_tv);
            name.setText(getItem(position).name);
            /**
             * 设置内容
             */
            return convertView;
        }
    }
}
