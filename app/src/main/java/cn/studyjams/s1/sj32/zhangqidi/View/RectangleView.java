package cn.studyjams.s1.sj32.zhangqidi.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 *
 * Created by AddiCheung on 2016/4/19 0019.
 */
public class RectangleView extends View {

    private int mWidth;
    private int mHeight;
    private Paint rangePaint;
    private Paint textPaint;
    private Paint bkPaint;
    public int range = 0;
    public int percent = 0;
    private int current = 0;


    public RectangleView(Context context) {
        this(context,null);
    }

    public RectangleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RectangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rangePaint = new Paint();
        rangePaint.setAntiAlias(true);
        rangePaint.setStyle(Paint.Style.FILL);
        rangePaint.setColor(Color.parseColor("#FFC107"));
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40);
        textPaint.setColor(Color.parseColor("#FFFFFF"));
        textPaint.setStyle(Paint.Style.STROKE);
        bkPaint = new Paint();
        bkPaint.setAntiAlias(true);
        bkPaint.setStyle(Paint.Style.FILL);
        bkPaint.setColor(Color.parseColor("#888888"));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF r = new RectF(0,mHeight/4,mWidth,mHeight*3/4);
           canvas.drawRect(r,bkPaint);
            drawRange(canvas);
            drawRangeText(canvas);
    }

    private void drawRangeText(Canvas canvas) {
            canvas.drawText(""+current,10,mHeight*1f/2+15,textPaint);
    }

    private void drawRange(Canvas canvas) {
        int c = (int) ((((float)current)/percent)*mWidth);
        RectF r = new RectF(0,mHeight/4,c,mHeight*3/4);
        canvas.drawRect(r,rangePaint);
    }


    public void setProgressRange(int range,int percent){
        this.range = range;
        this.percent = percent;
        startAnimations();
    }

    public void setProgressRange(int range,int color,int percent){
        this.range = range;
        startAnimations();
    }


    private void startAnimations() {
        ValueAnimator anim = ValueAnimator.ofInt(0,range);
        anim.setDuration(1500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                current = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }

    public interface updateView{
         void setRange(int range);
    }

}
