package xu.ferris.opencvtest.filter;

import android.graphics.Color;
import android.graphics.ColorMatrix;

/**
 * 灰度图标
 * Created by xff on 2017/10/13.
 */

public class GrayEffectFiter extends IconFiter {
    @Override
    protected void apply(ColorMatrix mColorMatrix) {
        mColorMatrix.setSaturation(0);
    }
}
