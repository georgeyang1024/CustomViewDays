package com.wxp.customview;

import android.app.Application;
import android.content.Context;

/**
 * @author : wangxianpeng
 * @date : 2016/2/26
 * E-mail : wangxianpeng@jd.com
 */
public class BaseApplication extends Application {
    private static BaseApplication mInstance;

    public static BaseApplication getInstance() {

        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
