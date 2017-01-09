package me.rebi.contactsdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.rebi.contactsdemo.util.DisplayUtil;

/**
 * 作者: Warm.
 * 日期: 2017/1/8 17:45.
 * 联系: QQ-865741452.
 * 内容:
 */
public class QuickBar extends View {
    private static final String TAG = "QuickBar";
    private int mWidth;
    private int mHeight;
    private Context context;
    private List<String> strList = new ArrayList<>();
    private Map<String, Integer> map;
    private float barTextSize;
    private float barWidth;
    private Paint barPaint;
    private float textSize;
    private Paint textPaint;
    private OnTouchListener onTouchListener;
    private Paint.FontMetrics fm;

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public QuickBar(Context context) {
        this(context, null);
    }

    public QuickBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }


    private void init() {

        barTextSize = DisplayUtil.sp2px(context, 15);
        textSize = DisplayUtil.sp2px(context, 30);
        barWidth = DisplayUtil.dp2px(context, 30);

        barPaint = new Paint();
        barPaint.setTextSize(barTextSize);
        barPaint.setAntiAlias(true);
        barPaint.setTextAlign(Paint.Align.CENTER);
        barPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        fm=textPaint.getFontMetrics();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
        setMeasuredDimension(doMeasure(widthMeasureSpec), doMeasure(heightMeasureSpec));
    }

    private int doMeasure(int measureSpec) {
        int result = DisplayUtil.dp2px(context, 300);
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                result = Math.min(result, size);
                break;
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");
        drawBar(canvas);
        drawText(canvas);

    }

    private int position = -1;
    private float length;

    private void drawBar(Canvas canvas) {
        barPaint.setColor(Color.argb(255,179,179,179));
        RectF rectF=new RectF(mWidth-barWidth,0,mWidth,mHeight);
        canvas.drawRect(rectF, barPaint);
        barPaint.setColor(Color.argb(255,0,0,0));
        if (map != null&&strList!=null ) {
            length = mHeight / strList.size();
            for (int i = 0; i < strList.size(); i++) {
                canvas.drawText(strList.get(i), mWidth - barWidth / 2, length * (i + 0.5f), barPaint);
            }
        }
    }

    private void drawText(Canvas canvas) {
        if (can()) {

            float textCenterVerticalBaselineY = mHeight / 2 - fm.descent + (fm.bottom - fm.top) / 2;

            barPaint.setColor(Color.argb(255,179,179,179));
            RectF rectF=new RectF(mWidth / 2-100,mHeight / 2-100,mWidth / 2+100,mHeight / 2+100);
            canvas.drawRect(rectF, barPaint);
            canvas.drawText(strList.get(position), mWidth / 2, textCenterVerticalBaselineY, textPaint);
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate: ");
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: w=" + w);
        Log.d(TAG, "onSizeChanged: h=" + h);
        Log.d(TAG, "onSizeChanged: oldw=" + oldw);
        Log.d(TAG, "onSizeChanged: oldh=" + oldh);
        mWidth = w;
        mHeight = h;
    }

    private float downX;
    private float downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                Log.d(TAG, "onTouchEvent: downX=" + downX);
                Log.d(TAG, "onTouchEvent: mWidth-barWidth=" + (mWidth - barWidth));
                Log.d(TAG, "onTouchEvent: mWidth=" + mWidth);

                if (downX > (mWidth - barWidth) && downX < mWidth) {
                    position = getPos(downY);
                    Log.d(TAG, "onTouchEvent ACTION_DOWN: position=" + position);
                    invalidate();
                    if (can()) {
                        onTouchListener.touch(map.get(strList.get(position)));
                    }
                    return true;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (downX > (mWidth - barWidth) && downX < mWidth) {
                    position = getPos(event.getY());
                    Log.d(TAG, "onTouchEvent ACTION_MOVE: position=" + position);
                    invalidate();
                    if (can()) {
                        onTouchListener.touch(map.get(strList.get(position)));
                    }
                    return true;
                }

                break;

            case MotionEvent.ACTION_UP:
                position = -1;
                invalidate();
                break;
        }

        return super.onTouchEvent(event);
    }

    private boolean can(){


        return position < strList.size() && position >= 0;
    }

    private int getPos(float y) {

        return (int) (y / length);
    }


    public void setData( Map<String, Integer> map) {
        this.map = map;
        strList.addAll(map.keySet());
        invalidate();
    }

    public interface OnTouchListener{
        void touch(int position);
    }
}
