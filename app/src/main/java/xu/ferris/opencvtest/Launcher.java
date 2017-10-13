package xu.ferris.opencvtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import static android.R.attr.bitmap;

/**
 * ICON透明度区域裁剪相关
 * by ferris.xu 2017-10-13 11:56:07
 */
public class Launcher extends AppCompatActivity {
    RectImageView imageView,imageView2;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.launcher=this;
        setContentView(R.layout.activity_main);
        imageView = (RectImageView) findViewById(R.id.imageView);
        imageView2= (RectImageView) findViewById(R.id.imageView2);
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorSelector(Launcher.this, Color.RED, new ColorSelector.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        imageView2.setFiter(color);
                        imageView.setFiter(color);
                    }
                }).show();
            }
        });
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.sms);
        LauncherConfig.IconSize=Math.max(bitmap.getWidth(),bitmap.getHeight());
        imageView.setImageBitmap(bitmap);
        Bitmap finalBitmap = ThemeConfig.getIconFromPackageName(this,BitmapFactory.decodeResource(getResources(), R.mipmap.sms));
        imageView2.setImageBitmap(finalBitmap);
    }
    static Launcher launcher;
    public static Launcher getLauncher() {
        return launcher;
    }
}
