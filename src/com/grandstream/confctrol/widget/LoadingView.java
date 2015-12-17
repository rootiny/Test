package com.grandstream.confctrol.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import com.grandstream.confctrol.R;

/**
 * Created by zhyjiang on 12/16/15.
 */
public class LoadingView extends View {

    private Bitmap pic;

    private Bitmap disablePic;

    private Matrix potate = new Matrix();

    private Matrix trans =new Matrix();

    private int x = 0;

    private int x0;
    private int y0;

    private boolean ifRotate = true;
    private boolean ifEnable = true;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            x += 5;
            postInvalidate();
        }
    };

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingViewAttrs);
        Drawable src = a.getDrawable(R.styleable.LoadingViewAttrs_attrsrc);
        Drawable disbalesrc = a.getDrawable(R.styleable.LoadingViewAttrs_attrdisablesrc);
        ifEnable = a.getBoolean(R.styleable.LoadingViewAttrs_attrenable, true);
        a.recycle();
        if (src == null){
            pic = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
        } else {
            pic = ((BitmapDrawable)src).getBitmap();
        }

        if (disbalesrc == null){
            disablePic = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
        } else {
            disablePic = ((BitmapDrawable)disbalesrc).getBitmap();
        }

        y0 = pic.getHeight();
        x0 = pic.getWidth();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        potate.setRotate(x, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.concat(potate);
        potate.setTranslate(canvas.getWidth() / 2 - x0 / 2, canvas.getHeight() / 2 - y0 / 2);
//        canvas.drawBitmap(pic, potate, null);
        Bitmap bitmap = ifEnable ? pic : disablePic;
        canvas.drawBitmap(bitmap, potate, null);

        if (ifRotate && ifEnable) {
            Message message = handler.obtainMessage();
            message.what = 1;
            handler.sendMessageDelayed(message, 50);
        }

    }

    public void setEnable(Boolean enable){
        ifEnable = enable;
    }

    public void setSrc(int id){
        pic = ((BitmapDrawable)getResources().getDrawable(id)).getBitmap();
        postInvalidate();
    }

    public void setDisableSrc(int id){
        disablePic = ((BitmapDrawable)getResources().getDrawable(id)).getBitmap();
        postInvalidate();
    }

    public void startRate(){
        ifRotate = true;
        postInvalidate();
    }

    public void stopRate(){
        ifRotate = false;
        postInvalidate();
    }


}
