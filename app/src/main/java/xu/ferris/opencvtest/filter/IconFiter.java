package xu.ferris.opencvtest.filter;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * 滤镜图标实现
 * Created by xff on 2017/10/13.
 * 颜色矩阵M是以一维数组m=[
 * a,b,c,d,e,
 * f,g,h,i,j,
 * k,l,m,n,o,
 * p,q,r,s,t
 * ]的方式进行存储的。
 *
 *      * [ 1 0 0 0 0   - red vector
 *   0 1 0 0 0   - green vector
 *   0 0 1 0 0   - blue vector
 *   0 0 0 1 0 ] - alpha vector
 *第一行参数abcde决定了图像的红色成分，
 * 第二行参数fghij决定了图像的绿色成分，
 第三行参数klmno决定了图像的蓝色成分，
 第四行参数pqrst决定了图像的透明度，
 第五列参数ejot是颜色的偏移量。
 */

public abstract class IconFiter {
    private final static ColorMatrix colorMatrix=new ColorMatrix();

    // 黑白
    public static final float colormatrix_heibai[] = {0.8f, 1.6f, 0.2f, 0,
            -163.9f, 0.8f, 1.6f, 0.2f, 0, -163.9f, 0.8f, 1.6f, 0.2f, 0,
            -163.9f, 0, 0, 0, 1.0f, 0};
    // 怀旧
    public static final float colormatrix_huajiu[] = {0.2f, 0.5f, 0.1f, 0,
            40.8f, 0.2f, 0.5f, 0.1f, 0, 40.8f, 0.2f, 0.5f, 0.1f, 0, 40.8f, 0,
            0, 0, 1, 0};
    // 哥特
    public static final float colormatrix_gete[] = {1.9f, -0.3f, -0.2f, 0,
            -87.0f, -0.2f, 1.7f, -0.1f, 0, -87.0f, -0.1f, -0.6f, 2.0f, 0,
            -87.0f, 0, 0, 0, 1.0f, 0};
    // 淡雅
    public static final float colormatrix_danya[] = {0.6f, 0.3f, 0.1f, 0,
            73.3f, 0.2f, 0.7f, 0.1f, 0, 73.3f, 0.2f, 0.3f, 0.4f, 0, 73.3f, 0,
            0, 0, 1.0f, 0};
    // 蓝调
    public static final float colormatrix_landiao[] = {2.1f, -1.4f, 0.6f,
            0.0f, -71.0f, -0.3f, 2.0f, -0.3f, 0.0f, -71.0f, -1.1f, -0.2f, 2.6f,
            0.0f, -71.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 光晕
    public static final float colormatrix_guangyun[] = {0.9f, 0, 0, 0, 64.9f,
            0, 0.9f, 0, 0, 64.9f, 0, 0, 0.9f, 0, 64.9f, 0, 0, 0, 1.0f, 0};

    // 梦幻
    public static final float colormatrix_menghuan[] = {0.8f, 0.3f, 0.1f,
            0.0f, 46.5f, 0.1f, 0.9f, 0.0f, 0.0f, 46.5f, 0.1f, 0.3f, 0.7f, 0.0f,
            46.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 酒红
    public static final float colormatrix_jiuhong[] = {1.2f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f,
            0, 0, 0, 1.0f, 0};
    // 胶片
    public static final float colormatrix_fanse[] = {-1.0f, 0.0f, 0.0f, 0.0f,
            255.0f, 0.0f, -1.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, -1.0f, 0.0f,
            255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 湖光掠影
    public static final float colormatrix_huguang[] = {0.8f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.9f, 0.0f, 0.0f,
            0, 0, 0, 1.0f, 0};
    // 褐片
    public static final float colormatrix_hepian[] = {1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f,
            0, 0, 0, 1.0f, 0};
    // 复古
    public static final float colormatrix_fugu[] = {0.9f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f,
            0, 0, 0, 1.0f, 0};
    // 泛黄
    public static final float colormatrix_huan_huang[] = {1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f,
            0.0f, 0, 0, 0, 1.0f, 0};
    // 传统
    public static final float colormatrix_chuan_tong[] = {1.0f, 0.0f, 0.0f, 0,
            -10f, 0.0f, 1.0f, 0.0f, 0, -10f, 0.0f, 0.0f, 1.0f, 0, -10f, 0, 0,
            0, 1, 0};
    // 胶片2
    public static final float colormatrix_jiao_pian[] = {0.71f, 0.2f, 0.0f,
            0.0f, 60.0f, 0.0f, 0.94f, 0.0f, 0.0f, 60.0f, 0.0f, 0.0f, 0.62f,
            0.0f, 60.0f, 0, 0, 0, 1.0f, 0};

    // 锐色
    public static final float colormatrix_ruise[] = {4.8f, -1.0f, -0.1f, 0,
            -388.4f, -0.5f, 4.4f, -0.1f, 0, -388.4f, -0.5f, -1.0f, 5.2f, 0,
            -388.4f, 0, 0, 0, 1.0f, 0};
    // 清宁
    public static final float colormatrix_qingning[] = {0.9f, 0, 0, 0, 0, 0,
            1.1f, 0, 0, 0, 0, 0, 0.9f, 0, 0, 0, 0, 0, 1.0f, 0};
    // 浪漫
    public static final float colormatrix_langman[] = {0.9f, 0, 0, 0, 63.0f,
            0, 0.9f, 0, 0, 63.0f, 0, 0, 0.9f, 0, 63.0f, 0, 0, 0, 1.0f, 0};
    // 夜色
    public static final float colormatrix_yese[] = {1.0f, 0.0f, 0.0f, 0.0f,
            -66.6f, 0.0f, 1.1f, 0.0f, 0.0f, -66.6f, 0.0f, 0.0f, 1.0f, 0.0f,
            -66.6f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};

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
    protected int color= Color.WHITE;
    public void setColor(int color) {
        this.color=color;
    }
}
