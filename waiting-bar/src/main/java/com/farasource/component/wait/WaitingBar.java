package com.farasource.component.wait;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;

public class WaitingBar extends View {

    private BaseIndicator baseIndicator;

    public WaitingBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public WaitingBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaitingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.WaitingBar, defStyleAttr, 0);
        setIndicator(Indicators.indicator(context, typed.getInteger(R.styleable.WaitingBar_indicator, 0)));
        setIndicatorColor(typed.getColor(R.styleable.WaitingBar_indicatorColor, Color.WHITE));
        typed.recycle();
    }

    public void setIndicator(BaseIndicator indicator) {
        if (indicator != null) {
            if (baseIndicator != null) {
                baseIndicator.setCallback(null);
                baseIndicator.stop();
            }
            baseIndicator = indicator;
            baseIndicator.setCallback(this);
            baseIndicator.start();
        }
    }

    public void setIndicatorColor(@ColorInt int color) {
        baseIndicator.paint().setColor(color);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveCount = canvas.save();
        canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
        baseIndicator.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = Math.min(96, baseIndicator.getIntrinsicWidth());
        int h = Math.min(96, baseIndicator.getIntrinsicHeight());
        int[] state = getDrawableState();
        if (baseIndicator.isStateful()) {
            baseIndicator.setState(state);
        }
        setMeasuredDimension(resolveSizeAndState(w + getPaddingLeft() + getPaddingRight(), widthMeasureSpec, 0), resolveSizeAndState(h + getPaddingTop() + getPaddingBottom(), heightMeasureSpec, 0));
    }

    @Override
    public void invalidateDrawable(Drawable dr) {
        if (verifyDrawable(dr)) {
            Rect rect = dr.getBounds();
            int scrollX = getScrollX() + getPaddingLeft();
            int scrollY = getScrollY() + getPaddingTop();
            invalidate(rect.left + scrollX, rect.top + scrollY, rect.right + scrollX, rect.bottom + scrollY);
            return;
        }
        super.invalidateDrawable(dr);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        int w2 = w - (getPaddingRight() + getPaddingLeft());
        int h2 = h - (getPaddingTop() + getPaddingBottom());
        int right = w2;
        int bottom = h2;
        int top = 0;
        int left = 0;
        float intrinsicAspect = ((float) baseIndicator.getIntrinsicWidth()) / ((float) baseIndicator.getIntrinsicHeight());
        float boundAspect = ((float) w2) / ((float) h2);
        if (intrinsicAspect != boundAspect) {
            if (boundAspect <= intrinsicAspect) {
                int height = (int) (((float) w2) * (1.0f / intrinsicAspect));
                int top2 = (h2 - height) / 2;
                bottom = top2 + height;
                top = top2;
            } else {
                int width = (int) (((float) h2) * intrinsicAspect);
                int left2 = (w2 - width) / 2;
                right = width + left2;
                left = left2;
            }
        }
        baseIndicator.setBounds(left, top, right, bottom);
    }

    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        baseIndicator.setHotspot(x, y);
    }

    @Override
    public boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.baseIndicator;
    }

    @Override
    public void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE) {
            start();
        } else {
            stop();
        }
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            start();
        } else {
            stop();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    public void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }

    private void stop() {
        this.baseIndicator.stop();
        postInvalidate();
    }

    private void start() {
        this.baseIndicator.start();
        postInvalidate();
    }
}