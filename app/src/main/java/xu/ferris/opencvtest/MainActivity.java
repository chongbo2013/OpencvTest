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
 * 获取bitmap 透明边界
 */
public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    byte[] bitmapArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        //9716  144x144
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        bitmapArray = baos.toByteArray();
        imageView.setImageBitmap(bitmap);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Rect rect = new Rect();
        boolean isBreak = false;
        for (int row = 0; row < height / 2; row++) {
            if (isBreak)
                break;
            for (int col = 0; col < width / 2; col++) {
                int left = col;
                int top = row;
                int right = width - left - 1;
                int bottom = height - top - 1;
                boolean leftTopPoint = isPointAlphaZero(bitmap, left, top);
                boolean righttopPoint = isPointAlphaZero(bitmap, right, top);
                boolean leftbottomPoint = isPointAlphaZero(bitmap, left, bottom);
                boolean rightbottomPoint = isPointAlphaZero(bitmap, right, bottom);
                if (leftTopPoint && righttopPoint && leftbottomPoint && rightbottomPoint) {
                    rect.set(left, top, right, bottom);
                } else {
                    isBreak = true;
                    break;
                }
            }
        }
        System.out.print("rect:" + rect.toString());

    }

    public boolean isPointAlphaZero(Bitmap bitmap, int col, int row) {
        int pixel = bitmap.getPixel(col, row);
        int alpha = Color.alpha(pixel);
        return alpha == 0;
    }
}
