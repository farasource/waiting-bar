package com.farasource.component.wait.indicators;

import android.graphics.Canvas;

import com.farasource.component.wait.BaseAnimator;
import com.farasource.component.wait.BaseIndicator;

import java.util.ArrayList;

public class BallWave extends BaseIndicator {
    private final float[] scales = {1.0f, 1.0f, 1.0f};

    @Override
    public void createAnimators(ArrayList<BaseAnimator> animators) {
        for (int i = 0; i < 3; i++) {
            BaseAnimator scaleAnim = BaseAnimator.ofFloat(1.0f, 0.0f, 1.0f);
            scaleAnim.setStartDelay(i * 110);
            scaleAnim.setPosition(i);
            scaleAnim.addUpdateListener(animation -> {
                scales[scaleAnim.getPosition()] = (float) animation.getAnimatedValue();
                if (scaleAnim.getPosition() == 0) {
                    invalidateSelf();
                }
            });
            animators.add(scaleAnim);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int space = 5;
        float radius = (((float) width()) - (space * 2.0f)) / 6.0f;
        float x = (((float) width()) / 2.0f) - ((radius * 2.0f) + space);
        for (int i = 0; i < 3; i++) {
            canvas.save();
            canvas.translate((radius * 2.0f * ((float) i)) + x + (((float) i) * space), ((float) height()) / 2.0f);
            canvas.scale(scales[i], scales[i]);
            canvas.drawCircle(0, 0, radius, paint());
            canvas.restore();
        }
    }
}