package utils;

import android.util.TypedValue;

import com.wxp.customview.BaseApplication;

/**
 * @author : wangxianpeng
 * @date : 2016/2/26
 * E-mail : wangxianpeng@jd.com
 */
public class DPU {
    public static int dp(int x) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x, BaseApplication.getInstance().getResources().getDisplayMetrics());
    }
}
