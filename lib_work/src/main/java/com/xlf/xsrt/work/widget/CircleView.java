package com.xlf.xsrt.work.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cfwu on 2018/10/19.
 */

public class CircleView extends View {
    private Paint mPaint;
    private String mPaintColor = "#FF00FF";

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor(mPaintColor));
    }

    public void setPaintColor(String color) {
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public void setPaintColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2;
        canvas.drawCircle(radius, radius, radius, mPaint);
    }
}
