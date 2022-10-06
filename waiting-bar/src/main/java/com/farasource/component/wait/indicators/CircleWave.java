package com.farasource.component.wait.indicators;

import android.graphics.Canvas;
import android.view.animation.LinearInterpolator;

import com.farasource.component.wait.BaseAnimator;
import com.farasource.component.wait.BaseIndicator;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class CircleWave extends BaseIndicator {

    float[] scales = new float[]{1, 1, 1};
    int[] alphas = new int[]{255, 255, 255};

    @Override
    protected void createAnimators(ArrayList<BaseAnimator> animators) {
        long[] delays = new long[]{0, 200, 400};
        for (int i = 0; i < 3; i++) {
            BaseAnimator scaleAnim = BaseAnimator.ofFloat(0.1f, 0.9f);
            scaleAnim.setInterpolator(new LinearInterpolator());
            scaleAnim.setPosition(i);
            scaleAnim.addUpdateListener(animation -> {
                scales[scaleAnim.getPosition()] = (float) animation.getAnimatedValue();
                if (scaleAnim.getPosition() == 0) {
                    invalidateSelf();
                }
            });
            scaleAnim.setStartDelay(delays[i]);

            BaseAnimator alphaAnim = BaseAnimator.ofInt(255, 0);
            alphaAnim.setInterpolator(new LinearInterpolator());
            alphaAnim.setPosition(i);
            alphaAnim.addUpdateListener(animation -> {
                alphas[alphaAnim.getPosition()] = (int) animation.getAnimatedValue();
            });
            scaleAnim.setStartDelay(delays[i]);

            animators.add(scaleAnim);
            animators.add(alphaAnim);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        float space = 4;
        for (int i = 0; i < 3; i++) {
            paint().setAlpha(alphas[i]);
            canvas.scale(scales[i], scales[i], width() / 2f, height() / 2f);
            canvas.drawCircle(width() / 2f, height() / 2f, width() / 2f - space, paint());
        }
    }
}
