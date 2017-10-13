package xu.ferris.opencvtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 图标处理
 * Created by xff on 2017/9/6.
 */

public class ThemeConfig {
    //是否开启裁剪图片透明度区域
    public static boolean isCullAlphaRect=true;
    /**
     * 裁剪图标透明区域第二种方式，矩形裁剪或者正方形裁剪
     */
    public static boolean isUseCull2=true;
    private static final Canvas sCanvas = new Canvas();
    private static final Matrix mMatrix = new Matrix();
    private static final Paint mPaint = new Paint();
    static {
        sCanvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG,
                Paint.FILTER_BITMAP_FLAG));
    }

    public static Bitmap getIconFromPackageName(Context mContext, Bitmap bitmapAssets) {
            if (bitmapAssets != null && !bitmapAssets.isRecycled()) {
                return drawableToBitmap(cullBitmapAlphaRect(bitmapAssets));
            }

            return null;
    }

    public static Bitmap drawableToBitmap(Drawable icon) {
        if (icon instanceof BitmapDrawable) {
            // Ensure the bitmap has a density.
            BitmapDrawable bitmapDrawable = (BitmapDrawable) icon;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap.getDensity() == Bitmap.DENSITY_NONE) {
                bitmapDrawable.setTargetDensity(Launcher.getLauncher().getResources()
                        .getDisplayMetrics());
            }
        }
        final Canvas canvas = sCanvas;
        final Matrix mDrawMatrix = mMatrix;
        int dwidth = icon.getIntrinsicWidth();
        int dheight = icon.getIntrinsicHeight();
        int vwidth = (int) LauncherConfig.IconSize;
        int vheight = (int) LauncherConfig.IconSize;
        icon.setBounds(0, 0, dwidth, dheight);
        Bitmap bitmap = Bitmap.createBitmap(vwidth, vheight, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        canvas.save();
        float scale;
        float dx;
        float dy;
        if (dwidth <= vwidth && dheight <= vheight) {//也需要进行缩放
            //缩放
            float sx = vwidth / dwidth;
            float sy = vheight / dheight;
            scale = Math.min(sx, sy);
        }else {
            scale = Math.min((float) vwidth / (float) dwidth,
                    (float) vheight / (float) dheight);
        }
        dx = Math.round((vwidth - dwidth * scale) * 0.5f);
        dy = Math.round((vheight - dheight * scale) * 0.5f);
        mDrawMatrix.setScale(scale, scale);
        mDrawMatrix.postTranslate(dx, dy);
        if (mDrawMatrix != null) {
            canvas.concat(mDrawMatrix);
        }
        icon.draw(canvas);
        canvas.restore();
        canvas.setBitmap(null);
        canvas.concat(null);
        return bitmap;
    }

    public static Bitmap drawableToBitmap(Bitmap icon) {
        final Canvas canvas = sCanvas;
        final Matrix mDrawMatrix = mMatrix;
        int dwidth = icon.getWidth();
        int dheight = icon.getHeight();
        int vwidth = (int) LauncherConfig.IconSize;
        int vheight = (int) LauncherConfig.IconSize;

        Bitmap bitmap = Bitmap.createBitmap(vwidth, vheight, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        canvas.save();
        float scale;
        float dx;
        float dy;
        if (dwidth <= vwidth && dheight <= vheight) {//也需要进行缩放
            //缩放
            float sx = vwidth / dwidth;
            float sy = vheight / dheight;
            scale = Math.min(sx, sy);
        } else {
            scale = Math.min((float) vwidth / (float) dwidth,
                    (float) vheight / (float) dheight);
        }
        dx = Math.round((vwidth - dwidth * scale) * 0.5f);
        dy = Math.round((vheight - dheight * scale) * 0.5f);
        mDrawMatrix.setScale(scale, scale);
        mDrawMatrix.postTranslate(dx, dy);
        if (mDrawMatrix != null) {
            canvas.concat(mDrawMatrix);
        }
        canvas.drawBitmap(icon, 0, 0, mPaint);
        canvas.restore();
        canvas.setBitmap(null);
        canvas.concat(null);
        if (icon != null && !icon.isRecycled()) {
            icon.recycle();
            icon = null;
        }
        return bitmap;
    }


    /**
     * 裁剪图标透明边界
     * @param originBitmap
     * @return
     */
    public static Bitmap cullBitmapAlphaRect(Bitmap originBitmap){
        if(!isCullAlphaRect)
            return originBitmap;
        Rect rect=checkIconAlphaRect(originBitmap);
        if(rect==null)
            return originBitmap;
        Bitmap bitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = sCanvas;
        canvas.setBitmap(bitmap);
        canvas.save();
        canvas.translate(-rect.left,-rect.top);
        canvas.drawBitmap(originBitmap, 0, 0, mPaint);
        canvas.restore();
        canvas.setBitmap(null);
        canvas.concat(null);
        if (originBitmap != null && !originBitmap.isRecycled()) {
            originBitmap.recycle();
            originBitmap = null;
        }
        return bitmap;
    }



    /**
     * rect不为null代表找到边界
     * @param bitmap
     * @return
     */
    public static Rect checkIconAlphaRect(Bitmap bitmap){
        if(isUseCull2)
            return checkIconAlphaRect2(bitmap);
        //用正方形去裁剪图标的透明区域
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Rect rect = null;
        boolean isBreak = false;
        int col = 0;
        int row = 0;
        while (!isBreak){
            int left = Math.min(col,width-1);
            int top = Math.min(row,height-1);
            int right = width - left - 1;
            int bottom = height - top - 1;
            if(left==width/2-1||top==height/2-1)
                break;
            boolean leftTopPoint = isPointAlphaZero(bitmap, left, top);
            boolean righttopPoint = isPointAlphaZero(bitmap, right, top);
            boolean leftbottomPoint = isPointAlphaZero(bitmap, left, bottom);
            boolean rightbottomPoint = isPointAlphaZero(bitmap, right, bottom);
            if (leftTopPoint && righttopPoint && leftbottomPoint && rightbottomPoint) {
                //上面的线条
                boolean topLine=isHLineAlphaZero(bitmap,left,right,top);
                //下面的线条
                boolean bottomLine=isHLineAlphaZero(bitmap,left,right,bottom);
                //左面的线条
                boolean leftLine=isVLineAlphaZero(bitmap,left,top,bottom);
                //右面的线条
                boolean rightLine=isVLineAlphaZero(bitmap,right,top,bottom);
                if(topLine&&bottomLine&&leftLine&&rightLine){
                    if(rect==null)
                        rect= new Rect();
                    rect.set(left, top, right+1, bottom+1);
                }else{
                    isBreak=true;
                }
            }else{
                isBreak=true;
            }
            col++;
            row++;
        }
        return rect;
    }

    /**
     * 用区域去裁剪图标的透明区域
     * @param bitmap
     * @return
     */
    public static Rect checkIconAlphaRect2(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Rect rect = null;
        int point[]=new int[4];
        boolean[] scan_flag ={true,true,true,true};
        int col = 0;
        int row = 0;
        while(true){
            int left = Math.min(col,width-1);
            int top = Math.min(row,height-1);
            int right = width - left - 1;
            int bottom = height - top - 1;
            if(scan_flag[0])
            {
                if(isHLineAlphaZero(bitmap,0,width-1,top) == false)
                {
                    scan_flag[0] = false;
                    point[0] = top;
                }
            }
            if(scan_flag[1])
            {
                if(isHLineAlphaZero(bitmap,0,width-1,bottom)== false)
                {
                    scan_flag[1] = false;
                    point[1] = bottom;
                }
            }
            if(scan_flag[2])
            {
                if(isVLineAlphaZero(bitmap,left,0,height-1) == false)
                {
                    scan_flag[2] = false;
                    point[2] = left;
                }
            }
            if(scan_flag[3])
            {
                if(isVLineAlphaZero(bitmap,right,0,height-1) == false)
                {
                    scan_flag[3] = false;
                    point[3] = right;
                }
            }
            if(scan_flag[0] == false && scan_flag[1] == false && scan_flag[2] == false && scan_flag[3] == false) {
                if(rect==null)
                    rect= new Rect();
                rect.set(point[2], point[0], point[3]+1, point[1]+1);
                break;
            }
            col++;
            row++;
        }
        return rect;
    }

    /**
     * 横向监测透明度
     * @param bitmap
     * @param fromX
     * @param toX
     * @param Y
     * @return
     */
    public static boolean isHLineAlphaZero(Bitmap bitmap,int fromX,int toX,int Y){
        for(int i=fromX;i<=toX;i++){
            if(!isPointAlphaZero(bitmap,i,Y))
                return false;
        }
        return true;
    }

    /**
     * 竖向监测透明度
     * @param bitmap
     * @param X
     * @param fromY
     * @param toY
     * @return
     */
    public static boolean isVLineAlphaZero(Bitmap bitmap,int X,int fromY,int toY){
        for(int i=fromY;i<=toY;i++){
            if(!isPointAlphaZero(bitmap,X,i))
                return false;
        }
        return true;
    }
    public static boolean isPointAlphaZero(Bitmap bitmap, int col, int row) {
        int pixel = bitmap.getPixel(col, row);
        int alpha = Color.alpha(pixel);
        return alpha == 0;
    }
}
