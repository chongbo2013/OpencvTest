package xu.ferris.opencvtest;

/**
 * Created by x002 on 2017/10/13.
 */


import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class ColorMatrixActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    ImageView imageView;
    SeekBar seekBarR, seekBarG, seekBarB, seekBarA;
    ColorMatrix colorMatrix;
    View colorView;
    TextView colorText;
    ColorMatrix mHueMatrix = new ColorMatrix();
    ColorMatrix mSaturationMatrix = new ColorMatrix();
    ColorMatrix mLightnessMatrix = new ColorMatrix();
    SeekBar seekBarHue, seekBarSaturation, seekBarLightness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix);
        colorMatrix = new ColorMatrix();
        colorMatrix.setScale(caculate(128), caculate(128), caculate(128), caculate(128));
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        seekBarR = (SeekBar) findViewById(R.id.bar_R);
        seekBarG = (SeekBar) findViewById(R.id.bar_G);
        seekBarB = (SeekBar) findViewById(R.id.bar_B);
        seekBarA = (SeekBar) findViewById(R.id.bar_A);

        colorView = findViewById(R.id.color_view);
        colorText = (TextView) findViewById(R.id.color_text);

        seekBarR.setOnSeekBarChangeListener(this);
        seekBarG.setOnSeekBarChangeListener(this);
        seekBarB.setOnSeekBarChangeListener(this);
        seekBarA.setOnSeekBarChangeListener(this);


        seekBarHue = (SeekBar) findViewById(R.id.bar_hue);
        seekBarSaturation = (SeekBar) findViewById(R.id.bar_saturation);
        seekBarLightness = (SeekBar) findViewById(R.id.bar_lightness);

        seekBarHue.setOnSeekBarChangeListener(this);
        seekBarSaturation.setOnSeekBarChangeListener(this);
        seekBarLightness.setOnSeekBarChangeListener(this);
    }

    protected float caculate(int progress) {
        float scale = progress / 128f;
        return scale;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


        colorText.setText("颜色值：#" + Integer.toHexString(seekBarA.getProgress())
                + Integer.toHexString(seekBarR.getProgress())
                + Integer.toHexString(seekBarG.getProgress())
                + Integer.toHexString(seekBarB.getProgress()));

        colorView.setBackgroundColor(Color.argb(seekBarA.getProgress(),
                seekBarR.getProgress(),
                seekBarG.getProgress(),
                seekBarB.getProgress()));



        float mHueValue = (seekBarHue.getProgress() - 128f) * 1.0f / 128f * 180;
        float mSaturationValue = seekBarSaturation.getProgress() / 128f;
        float mLightnessValue = seekBarLightness.getProgress() / 128f;
        //设置色相
        mHueMatrix.reset();
        mHueMatrix.setRotate(0, mHueValue);
        mHueMatrix.setRotate(1, mHueValue);
        mHueMatrix.setRotate(2, mHueValue);

        //设置饱和度
        mSaturationMatrix.reset();
        mSaturationMatrix.setSaturation(mSaturationValue);

        //亮度
        mLightnessMatrix.reset();
        mLightnessMatrix.setScale(mLightnessValue, mLightnessValue, mLightnessValue, 1);


        colorMatrix.reset();// 效果叠加
        colorMatrix.setScale(caculate(seekBarR.getProgress()), caculate(seekBarG.getProgress()),
                caculate(seekBarB.getProgress()), caculate(seekBarA.getProgress()));
        colorMatrix.postConcat(mLightnessMatrix);
        colorMatrix.postConcat(mSaturationMatrix);
        colorMatrix.postConcat(mHueMatrix);

        imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}