package com.farasource.component.wait;

import android.content.Context;

import com.farasource.component.wait.indicators.BallWave;
import com.farasource.component.wait.indicators.CircleWave;
import com.farasource.component.wait.indicators.LineWave;
import com.farasource.component.wait.indicators.ProgressBar;

public class Indicators {

    public static BaseIndicator indicator(Context context, int type) {
        switch (type) {
            case 1:
                return BallWave();
            case 2:
                return LineWave();
            case 3:
                return CircleWave();
            default:
                return ProgressBar(context);
        }
    }

    public static ProgressBar ProgressBar(Context context) {
        return new ProgressBar(context);
    }

    public static BallWave BallWave() {
        return new BallWave();
    }

    public static LineWave LineWave() {
        return new LineWave();
    }

    public static CircleWave CircleWave() {
        return new CircleWave();
    }
}
