package com.example.batteryview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * create by libo
 * create on 2020/7/22
 * description
 */
public class BatteryView extends View {
    private Path path;
    /**
     * 边框绘制paint
     */
    private Paint borderPaint;
    /**
     * 电量矩形
     */
    private Paint batteryPaint;
    /** 显示电量值 */
    private Paint textPain;
    /** 低电量百分比 */
    private final float lowPower = 0.1f;
    /** 中电量百分比 */
    private final float mediumPower = 0.4f;
    /** 高电量百分比 */
    private final float highPower = 0.8f;
    private float curPower = lowPower;
    private int width, height;
    /** 电池边框宽度 */
    private int strokeWidth = 12;
    public static final int TYPE_LOW = 0;
    public static final int TYPE_MEDIUM = 1;
    public static final int TYPE_HIGH = 2;


    public BatteryView(Context context) {
        super(context);
        init();
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        path = new Path();
        borderPaint = new Paint();
        borderPaint.setColor(getResources().getColor(R.color.white));
        borderPaint.setAntiAlias(true);
        borderPaint.setStrokeWidth(strokeWidth);
        borderPaint.setStyle(Paint.Style.STROKE);

        batteryPaint = new Paint();

        textPain = new Paint();
        textPain.setColor(getResources().getColor(R.color.white));
        textPain.setTextSize(45);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    /**
     * 设置电量类型
     */
    public void setPowerLevel(int powerLevel) {
        switch (powerLevel) {
            case TYPE_LOW:
                curPower = lowPower;
                batteryPaint.setColor(getResources().getColor(R.color.red));
                break;
            case TYPE_MEDIUM:
                curPower = mediumPower;
                batteryPaint.setColor(getResources().getColor(R.color.yellow));
                break;
            case TYPE_HIGH:
                curPower = highPower;
                batteryPaint.setColor(getResources().getColor(R.color.green));
                break;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制电池边框，使用Path来做，从左上角逆时针将电池边框画完成
        drawBorder(canvas);

        //绘制当前矩形电量
        drawPower(canvas);

        //显示电量文字显示
        drawPowerText(canvas);
    }

    private void drawBorder(Canvas canvas) {
        int headHeight = dp2px(getContext(), 30);
        int headWidth = dp2px(getContext(), 60);
        int sideLength = (width - headWidth)/2;
        path.moveTo(0, headHeight);
        path.lineTo(0, height);
        path.lineTo(width, height);
        path.lineTo(width, headHeight);
        path.lineTo(sideLength+headWidth, headHeight);
        path.lineTo(sideLength+headWidth, 0);
        path.lineTo(sideLength, 0);
        path.lineTo(sideLength, headHeight);
        path.lineTo(0, headHeight);
        canvas.drawPath(path, borderPaint);
    }

    private void drawPower(Canvas canvas) {
        Rect rect = new Rect(strokeWidth, (int) (height*(1-curPower)), width-strokeWidth, height-strokeWidth);
        canvas.drawRect(rect, batteryPaint);
    }

    private void drawPowerText(Canvas canvas) {
        Rect rect = new Rect();
        String text = curPower*100 + "%";
        textPain.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, (width-rect.width())/2, height/2, textPain);
    }

    public static int dp2px(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}
