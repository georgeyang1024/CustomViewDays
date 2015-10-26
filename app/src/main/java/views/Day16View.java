package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

/**
 * 自定义View实现软键盘打开之后悬浮在键盘之上的一层键盘
 * @author : wangxianpeng
 * @date : 2015/10/25
 * E-mail : wisimer@163.com
 */
public class Day16View extends KeyboardView{
    public Day16View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Day16View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("HELLO",200,0,new Paint());
    }
}
