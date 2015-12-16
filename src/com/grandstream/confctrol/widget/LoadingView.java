package com.grandstream.confctrol.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import com.grandstream.confctrol.R;

/**
 * Created by zhyjiang on 12/16/15.
 */
public class LoadingView extends View {

    //界面需要的图片

    private Bitmap panpic;

    private Bitmap panhandpic;

    //旋转矩阵
    private Matrix panRotate = new Matrix();

    //平移矩阵
    private Matrix panhandTrans=new Matrix();

    private int x = 0;

    private int x0;
    private int y0;

    private boolean ifRotate = true;
    private boolean ifFirst = true;

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
        init(context);
    }

    private void init(Context context){
        Resources r = context.getResources();

        //设置指针平移矩阵为按向量(160,160-指针的高度)平移

//        panhandTrans.setTranslate(160, 160 - 76);

        //生成图片
        panpic = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
        y0 = panpic.getHeight();
        x0 = panpic.getWidth();
//        panhandpic = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
//        panpic = BitmapFactory.decodeStream(r.openRawResource(R.drawable.ic_launcher));
//        panhandpic=BitmapFactory.decodeStream(r.openRawResource(R.drawable.panhand));

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


//        canvas.concat(panRotate);
        panRotate.setRotate(x, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.concat(panRotate);
        panRotate.setTranslate(canvas.getWidth() / 2 - x0 / 2, canvas.getHeight() / 2 - y0 / 2);
//        canvas.drawBitmap(panpic, panRotate, null);
        canvas.drawBitmap(panpic, panRotate, null);

        if (ifRotate){
            Message message = handler.obtainMessage();
            message.what = 1;
            handler.sendMessageDelayed(message, 50);
        }

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
