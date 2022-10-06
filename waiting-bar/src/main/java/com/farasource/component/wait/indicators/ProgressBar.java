package com.farasource.component.wait.indicators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.TypedValue;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.farasource.component.wait.BaseAnimator;
import com.farasource.component.wait.BaseIndicator;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ProgressBar extends BaseIndicator {

    private int startAngle;
    private float angleLength;
    private boolean risingCircleLength;
    private long lastUpdateTime;
    private final float risingTime = 500, rotationTime = 2000;
    private float currentProgressTime;
    private final RectF rectF;
    private final DecelerateInterpolator decelerateInterpolator;
    private final AccelerateInterpolator accelerateInterpolator;

    public ProgressBar(Context context) {
        paint().setStyle(Paint.Style.STROKE);
        paint().setStrokeCap(Paint.Cap.ROUND);
        paint().setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3.5f, context.getResources().getDisplayMetrics()));
        rectF = new RectF();
        decelerateInterpolator = new DecelerateInterpolator();
        accelerateInterpolator = new AccelerateInterpolator();
    }

    @Override
    protected void createAnimators(ArrayList<BaseAnimator> animators) {
        BaseAnimator animator = BaseAnimator.ofFloat(0, 1);
        animator.addUpdateListener(animation -> {
            long newTime = System.currentTimeMillis();
            long dt = newTime - lastUpdateTime;
            if (dt > 17) {
                dt = 17;
            }
            lastUpdateTime = newTime;
            startAngle += 360 * dt / rotationTime;
            int count = (startAngle / 360);
            startAngle -= count * 360;
            currentProgressTime += dt;
            if (currentProgressTime >= risingTime) {
                currentProgressTime = risingTime;
            }
            if (risingCircleLength) {
                angleLength = 4 + 266 * accelerateInterpolator.getInterpolation(currentProgressTime / risingTime);
            } else {
                angleLength = 4 - 270 * (1.0f - decelerateInterpolator.getInterpolation(currentProgressTime / risingTime));
            }
            if (currentProgressTime == risingTime) {
                if (risingCircleLength) {
                    startAngle += 270f;
                    angleLength = -266f;
                }
                risingCircleLength = !risingCircleLength;
                currentProgressTime = 0f;
            }
            invalidateSelf();
        });
        animators.add(animator);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        float x = width() / 2f;
        float y = height() / 2f;
        float space = 12;
        rectF.set(-x + space, -y + space, x - space, y - space);
        canvas.translate(x, y);
        canvas.drawArc(rectF, startAngle, angleLength, false, paint());
    }
}
