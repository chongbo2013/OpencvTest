package xu.ferris.opencvtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import xu.ferris.opencvtest.filter.GrayEffectFiter;

/**
 * Created by x002 on 2017/10/12.
 */

public class RectImageView extends ImageView {
    GrayEffectFiter effectFiter=new GrayEffectFiter();
    public RectImageView(Context context) {
        super(context);
    }

    public RectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    Rect rect=new Rect();
    public void setRect(Rect rect) {
        if(rect==null)
            return;
        this.rect.set(rect);
        invalidate();
    }
    Paint paint=new Paint();
    @Override
    public void draw(Canvas canvas) {
        if(getDrawable()!=null&&getDrawable() instanceof BitmapDrawable) {
            effectFiter.applyFiter(((BitmapDrawable)getDrawable()).getPaint());
        }
        super.draw(canvas);
        if(getDrawable()!=null&&getDrawable() instanceof BitmapDrawable) {
            effectFiter.resetFiter(((BitmapDrawable)getDrawable()).getPaint());
        }
        paint.setColor(Color.RED);
        paint.setAlpha(100);
        canvas.drawRect(rect,paint);
    }

    public void setFiter(int color){
        effectFiter.setColor(color);
        postInvalidate();
    }
}
