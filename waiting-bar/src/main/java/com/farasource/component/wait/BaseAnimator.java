package com.farasource.component.wait;

import android.animation.ValueAnimator;

public class BaseAnimator extends ValueAnimator {
    private AnimatorUpdateListener listener = null;
    private int position;

    public static BaseAnimator ofFloat(float... values) {
        BaseAnimator baseAnimator = new BaseAnimator();
        baseAnimator.setFloatValues(values);
        baseAnimator.setDuration(1000);
        baseAnimator.setRepeatCount(ValueAnimator.INFINITE);
        return baseAnimator;
    }

    public static BaseAnimator ofInt(int... values) {
        BaseAnimator baseAnimator = new BaseAnimator();
        baseAnimator.setIntValues(values);
        baseAnimator.setDuration(1000);
        baseAnimator.setRepeatCount(ValueAnimator.INFINITE);
        return baseAnimator;
    }

    public void addUpdateListener(AnimatorUpdateListener listener2) {
        this.listener = listener2;
    }

    public void removeAllUpdateListeners() {
        this.listener = null;
        stop();
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position2) {
        this.position = position2;
    }

    public void start() {
        if (!isStarted()) {
            super.addUpdateListener(this.listener);
            super.start();
        }
    }

    public void stop() {
        if (isStarted()) {
            super.removeAllUpdateListeners();
            end();
        }
    }
}
