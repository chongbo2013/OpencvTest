package xu.ferris.opencvtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import static android.R.attr.bitmap;

/**
 * 获取bitmap 透明边界区域
 */
public class MainActivity extends AppCompatActivity {
    RectImageView imageView;
    byte[] bitmapArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (RectImageView) findViewById(R.id.imageView);
        //9716  144x144
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        bitmapArray = baos.toByteArray();
        imageView.setImageBitmap(bitmap);
        imageView.getLayoutParams().width=bitmap.getWidth();
        imageView.getLayoutParams().height=bitmap.getHeight();
        imageView.requestLayout();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Rect rect = new Rect();
        boolean isBreak = false;
        int col = 0;
        int row = 0;
        while (!isBreak){
            int left = col;
            int top = row;
            int right = width - left - 1;
            int bottom = height - top - 1;
            boolean leftTopPoint = isPointAlphaZero(bitmap, left, top);
            boolean righttopPoint = isPointAlphaZero(bitmap, right, top);
            boolean leftbottomPoint = isPointAlphaZero(bitmap, left, bottom);
            boolean rightbottomPoint = isPointAlphaZero(bitmap, right, bottom);
            if (leftTopPoint && righttopPoint && leftbottomPoint && rightbottomPoint) {
                rect.set(left, top, right+1, bottom+1);
            }else{
                isBreak=true;
            }
            col++;
            row++;
        }
        System.out.print("rect:" + rect.toString());
        imageView.setRect(rect);
    }

    public boolean isPointAlphaZero(Bitmap bitmap, int col, int row) {
        int pixel = bitmap.getPixel(col, row);
        int alpha = Color.alpha(pixel);
        return alpha == 0;
    }
}
