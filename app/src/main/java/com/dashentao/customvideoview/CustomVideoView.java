package com.dashentao.customvideoview;

/**
 * Created by Administrator on 2015/9/22.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author dashentao
 * @date 2015 9-22
 * @since V 1.0
 */
public class CustomVideoView extends View {
    // 半径
    float r1 = 0;
    float r2 = 0;
    float r3 = 0;
    // 外圆宽度
    float w1 = 10;
    // 内圆宽度
    float w2 = 20;
    // 画笔
    Paint paint;
    // 进度
    float progress = 0;
    // 位图
    Bitmap bitmap;
    // 位图限制区域
    RectF oval;

    public CustomVideoView(Context context) {
        super(context);
        init(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ring);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float cx = getMeasuredWidth() / 2;
        float cy = getMeasuredHeight() / 2;
        r1 = cx - w1 / 2;
        r2 = cx - w1 / 2 - w2 / 2;
        r3 = cx - w1 / 2 - w2;

        // 绘制外圆
        paint.setAntiAlias(true);
        paint.setStrokeWidth(w1);
        paint.setColor(Color.parseColor("#454547"));
        canvas.drawCircle(cx, cy, r1, paint);

        // 绘制中间圆环
        paint.setColor(Color.parseColor("#747476"));
        paint.setStrokeWidth(w2);
        canvas.drawCircle(cx, cy, r2, paint);

        // 绘制内圆
        paint.setColor(Color.parseColor("#464648"));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, r3, paint);
        // 绘制中间的图片
        canvas.drawBitmap(bitmap, cx - bitmap.getWidth() / 2,
                cx - bitmap.getHeight() / 2, paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(w2);
        if (oval == null) {
            // 用于定义的圆弧的形状和大小的界限
            oval = new RectF(cx - r2, cy - r2, cx + r2, cy + r2);
        }
        canvas.drawArc(oval, 270, 360 * progress / 100, false, paint);
        super.onDraw(canvas);
    }

    /**
     * 设置进度
     *
     * @param progress 范围(0-100)
     */
    public void setProgress(float progress) {
        this.progress = progress;
        if (this.progress >= 100)
            this.progress = 100;
        if (this.progress <= 0)
            this.progress = 0;
        postInvalidate();
    }
}
