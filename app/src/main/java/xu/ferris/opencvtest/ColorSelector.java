package xu.ferris.opencvtest;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.view.Window;

import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;

public class ColorSelector extends android.app.Dialog implements SeekBar.OnSeekBarChangeListener{

    int color = Color.BLACK;
    Context context;
    View colorView;
    View view;

    OnColorSelectedListener onColorSelectedListener;
    AppCompatSeekBar red, green, blue;


    public ColorSelector(Context context,Integer color, OnColorSelectedListener onColorSelectedListener) {
        super(context, android.R.style.Theme_Material_Light_Dialog);
        this.context = context;
        this.onColorSelectedListener = onColorSelectedListener;
        if(color != null)
            this.color = color;
        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if(ColorSelector.this.onColorSelectedListener != null)
                    ColorSelector.this.onColorSelectedListener.onColorSelected(ColorSelector.this.color);
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_selector);

        view = (LinearLayout)findViewById(R.id.contentSelector);


        colorView = findViewById(R.id.viewColor);
        colorView.setBackgroundColor(color);
        // Resize ColorView
        colorView.post(new Runnable() {

            @Override
            public void run() {
                LayoutParams params = (LayoutParams) colorView.getLayoutParams();
                params.height = colorView.getWidth();
                colorView.setLayoutParams(params);
            }
        });


        // Configure Sliders
        red = (AppCompatSeekBar) findViewById(R.id.red);
        green = (AppCompatSeekBar) findViewById(R.id.green);
        blue = (AppCompatSeekBar) findViewById(R.id.blue);

        int r = (this.color >> 16) & 0xFF;
        int g = (this.color >> 8) & 0xFF;
        int b = (this.color >> 0) & 0xFF;

        red.setProgress(r);
        green.setProgress(g);
        blue.setProgress(b);

        red.setOnSeekBarChangeListener(this);
        green.setOnSeekBarChangeListener(this);
        blue.setOnSeekBarChangeListener(this);
    }

    @Override
    public void show() {
        super.show();
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            color = Color.rgb(red.getProgress(), green.getProgress(), blue.getProgress());
            colorView.setBackgroundColor(color);

        if(ColorSelector.this.onColorSelectedListener != null)
            ColorSelector.this.onColorSelectedListener.onColorSelected(ColorSelector.this.color);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    // Event that execute when color selector is closed
    public interface OnColorSelectedListener{
        public void onColorSelected(int color);
    }



}