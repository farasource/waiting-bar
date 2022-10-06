package com.farasource.component.wait;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public abstract class BaseIndicator extends Drawable implements Animatable {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int alpha = 255;
    private ArrayList<BaseAnimator> animators = new ArrayList<>();

    public BaseIndicator() {
        paint.setStyle(Paint.Style.FILL);
        createAnimators(animators);
        if (animators.isEmpty()) {
            animators = null;
        }
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setAlpha(int i) {
        this.alpha = i;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
    }

    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    public void start() {
        if (animators != null) {
            for (BaseAnimator baseAnimator : animators) {
                baseAnimator.start();
            }
        }
    }

    public void stop() {
        if (animators != null) {
            for (BaseAnimator baseAnimator : animators) {
                baseAnimator.stop();
            }
        }
    }

    public boolean isRunning() {
        if (animators != null) {
            return animators.get(0).isRunning();
        }
        return false;
    }

    protected void createAnimators(ArrayList<BaseAnimator> animators) {
    }

    public Paint paint() {
        return paint;
    }

    public int width() {
        return getBounds().width();
    }

    public int height() {
        return getBounds().height();
    }

}