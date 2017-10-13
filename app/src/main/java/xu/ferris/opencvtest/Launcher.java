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
 * ICON透明度区域裁剪相关
 * by ferris.xu 2017-10-13 11:56:07
 */
public class Launcher extends AppCompatActivity {
    RectImageView imageView,imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.launcher=this;
        setContentView(R.layout.activity_main);
        imageView = (RectImageView) findViewById(R.id.imageView);
        imageView2= (RectImageView) findViewById(R.id.imageView2);
        //9716  144x144
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        LauncherConfig.IconSize=Math.max(bitmap.getWidth(),bitmap.getHeight());
        imageView.setImageBitmap(bitmap);
        imageView.setRect(ThemeConfig.checkIconAlphaRect(bitmap));
        Bitmap finalBitmap = ThemeConfig.getIconFromPackageName(this,BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        imageView2.setImageBitmap(finalBitmap);
    }
    static Launcher launcher;
    public static Launcher getLauncher() {
        return launcher;
    }
}
