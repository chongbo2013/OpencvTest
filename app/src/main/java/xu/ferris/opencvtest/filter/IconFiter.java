package xu.ferris.opencvtest.filter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * 滤镜图标实现
 * Created by xff on 2017/10/13.
 */

public abstract class IconFiter {
    private final static ColorMatrix colorMatrix=new ColorMatrix();

    public void applyFiter(Paint paint){
        colorMatrix.reset();
        apply(colorMatrix);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(filter);
    }

    public void resetFiter(Paint paint){
        colorMatrix.reset();
        paint.setColorFilter(null);
    }

    protected abstract void apply(ColorMatrix mColorMatrix);
}
