package com.farasource.component.wait.indicators;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.farasource.component.wait.BaseAnimator;
import com.farasource.component.wait.BaseIndicator;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class LineWave extends BaseIndicator {

    float[] scales = new float[]{1f, 1f, 1f, 1f, 1f};

    public void createAnimators(ArrayList<BaseAnimator> animators) {
        long[] delays = new long[]{400, 200, 0, 200, 400};
        for (int i = 0; i < 5; i++) {
            BaseAnimator scaleAnim = BaseAnimator.ofFloat(1, 0.4f, 1);
            scaleAnim.setPosition(i);
            scaleAnim.setStartDelay(delays[i]);
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
    public void draw(@NonNull Canvas canvas) {
        float x = width() / 11f;
        float y = height() / 2f;
        for (int i = 0; i < 5; i++) {
            canvas.save();
            canvas.translate((2 + i * 2) * x - x / 2, y);
            canvas.scale(1, scales[i]);
            RectF rectF = new RectF(-x / 2, -height() / 2.5f, x / 2, height() / 2.5f);
            canvas.drawRoundRect(rectF, 5, 5, paint());
            canvas.restore();
        }
    }
}
