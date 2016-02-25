package views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxp.customview.R;

/**
 * @author : wangxianpeng
 * @date : 2016/1/17
 * E-mail : wangxianpeng@jd.com
 */
public class Day18View extends RelativeLayout {
    private Resources mResources;

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
        mResources = getResources();

        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        imageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        ImageView cartImage = new ImageView(getContext());
        cartImage.setImageResource(R.drawable.always_purchase_cart_icon);
        cartImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        cartImage.setAdjustViewBounds(true);
        cartImage.setPadding(8, 8, 8, 8);
        cartImage.setLayoutParams(imageParams);
        addView(cartImage);

        RelativeLayout.LayoutParams countParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 16);
        countParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        countParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        TextView countText = new TextView(getContext());
        countText.setTextColor(0xffffff);
        countText.setText(0+"");
        countText.setGravity(Gravity.CENTER);
        countText.setTextSize(8);
        countText.setBackgroundResource(R.drawable.product_shopping_car_icon);
        countText.setPadding(4, 0, 4, 0);
        countText.setLayoutParams(countParams);
        addView(countText);
    }

    public void setText(int count) {
        if (getChildCount() == 2) {
            TextView countText = (TextView) getChildAt(1);
            if (countText != null) {
                countText.setText(count);
            }
        }

    }
}
