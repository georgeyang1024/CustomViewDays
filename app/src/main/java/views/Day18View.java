package views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxp.customview.R;

import utils.DPU;

/**
 * @author : wangxianpeng
 * @date : 2016/1/17
 * E-mail : wangxianpeng@jd.com
 * <p/>
 * 购物车图标
 */
public class Day18View extends FrameLayout {

    private TextView mCountView;
    public Day18View(Context context) {
        this(context, null, 0);
    }

    public Day18View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day18View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        RelativeLayout container = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DPU.dp(48), DPU.dp(48));
        container.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        imageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        ImageView cartImage = new ImageView(getContext());
        cartImage.setImageResource(R.drawable.always_purchase_cart_icon);
        cartImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        cartImage.setAdjustViewBounds(true);
        cartImage.setPadding(4, 4, 4, 4);
        cartImage.setLayoutParams(imageParams);
        container.addView(cartImage);

        RelativeLayout.LayoutParams countParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        countParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        countParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mCountView = new TextView(getContext());
        mCountView.setTextColor(0xffffffff);
        mCountView.setText(0 + "");
        mCountView.setGravity(Gravity.CENTER);
        mCountView.setTextSize(8);
        mCountView.setBackgroundResource(R.drawable.product_shopping_car_icon);
        mCountView.setPadding(4, 0, 4, 0);
        mCountView.setLayoutParams(countParams);
        container.addView(mCountView);

        this.addView(container);
    }

    public void setText(int count) {
        if (mCountView != null) {
            mCountView.setText(count+"");
        }
    }
}
