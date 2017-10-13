package xu.ferris.opencvtest.filter;

import android.graphics.Color;
import android.graphics.ColorMatrix;

/**
 * 灰度图标
 * Created by xff on 2017/10/13.
 */

public class ColorEffectFiter extends IconFiter {
    int color=Color.WHITE;
    @Override
    protected void apply(ColorMatrix mColorMatrix) {
        mColorMatrix.setSaturation(0);
        setColorScale(color, mColorMatrix);
    }

    public static void setColorScale(int color, ColorMatrix target) {
        target.setScale(Color.red(color) / 255f, Color.green(color) / 255f,
                Color.blue(color) / 255f, Color.alpha(color) / 255f);
    }

    public void setColor(int color) {
        this.color=color;
    }
}
