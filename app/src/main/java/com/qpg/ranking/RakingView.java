package com.qpg.ranking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/6/28.
 */

public class RakingView extends View{
    private Paint mPoint;
    private Paint mLines;


    private Paint str_paint;//文字画笔
    private Paint str_paint_count;//数量画笔
    private Paint img;
    private String[] str = {"好评数", "订单数", "总收入"};
    private int[] str_count = {1111, 5555, 3333};
    private Rect str_rect;//字体矩形
    private Rect str_rect_count;//数量矩形
    private int defalutSize = 300;//默认大小
    private int height,width;
    private Bitmap bitmap = null;   //  图片位图
    private Bitmap bitmapDisplay = null;
    private int nBitmapWidth = 0;   //  图片的宽度
    private int nBitmapHeight = 0;  //  图片的高度
    private int nPosX = 120;    //  图片所在的位置X
    private int nPosY = 10; //  图片所在的位置Y

    public RakingView(Context context) {
        super(context);

    }

    public RakingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPoint();
        initLine();

        init();
    }

    public RakingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int vLine=50;
        double ragle=15*(Math.PI/180);
        double x=vLine/(Float.valueOf(Math.tan(ragle)+""));//利用正切值，对边比邻边

        float[] pointSite=new float[]{
                //顶部点
                width,height,
                //第一行的点（从左到右）
                width-Float.valueOf(x+""),height+vLine,
                width,height+vLine,
                width+Float.valueOf(x+""),height+vLine,
                //第二行的点（从左到右）
                width-Float.valueOf(x+""),height+vLine*2,
                width,height+vLine*2,
                width+Float.valueOf(x+""),height+vLine*2
        };

        float[] linesSite=new float[]{
                width,height,width,height+vLine,
                width,height,width-Float.valueOf(x+""),height+vLine,
                width,height,width+Float.valueOf(x+""),height+vLine,

                width-Float.valueOf(x+""),height+vLine,width-Float.valueOf(x+""),height+vLine*2,
                width,height+vLine,width,height+vLine*2,
                width+Float.valueOf(x+""),height+vLine,width+Float.valueOf(x+""),height+vLine*2

        };

        canvas.drawLines(linesSite,mLines);
        canvas.drawPoints(pointSite,mPoint);

        canvas.drawText(str_count[0]+"",width-Float.valueOf(x+"")-str_rect_count.width()/2,height+vLine*2+str_rect_count.height(),str_paint_count);
        canvas.drawText(str_count[1]+"",width-str_rect_count.width()/2,height+vLine*2+str_rect_count.height(),str_paint_count);
        canvas.drawText(str_count[2]+"",width+Float.valueOf(x+"")-str_rect_count.width()/2,height+vLine*2+str_rect_count.height(),str_paint_count);

        canvas.drawText(str[0],width-Float.valueOf(x+"")-str_rect.width()/2,height+vLine*2+str_rect_count.height()+str_rect.height(),str_paint);
        canvas.drawText(str[1],width-str_rect.width()/2,height+vLine*2+str_rect_count.height()+str_rect.height(),str_paint);
        canvas.drawText(str[2],width+Float.valueOf(x+"")-str_rect.width()/2,height+vLine*2+str_rect_count.height()+str_rect.height(),str_paint);

        nPosX=width-nBitmapWidth/2;
        nPosY=height-nBitmapHeight/2;
        canvas.drawBitmap(bitmapDisplay,nPosX, nPosY,img);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        if (wMode == MeasureSpec.EXACTLY) {
            width = wSize;
        } else {
            width = Math.min(wSize, defalutSize);
        }

        if (hMode == MeasureSpec.EXACTLY) {
            height = hSize;
        } else {
            height = Math.min(hSize, defalutSize);
        }
        this.height=height/4;
        this.width=width/2;
    }

    private void init(){
        //初始化字体画笔
        str_paint = new Paint();
        str_paint.setAntiAlias(true);
        str_paint.setColor(Color.WHITE);
        str_paint.setTextSize(16);
        str_rect = new Rect();
        str_paint.getTextBounds(str[0], 0, str[0].length(), str_rect);
//初始化数量画笔
        str_paint_count = new Paint();
        str_paint_count.setAntiAlias(true);
        str_paint_count.setColor(Color.WHITE);
        str_paint_count.setTextSize(16);

        Paint.FontMetrics fontMetrics = str_paint_count.getFontMetrics();
// 计算文字高度
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
// 计算文字baseline
        float textBaseY = height - (height - fontHeight) / 2 - fontMetrics.bottom;
        //  canvas.drawText(text, width / 2, textBaseY, paint);

        str_rect_count = new Rect();
        str_paint_count.getTextBounds(str[0], 0, str[0].length(), str_rect_count);

        img = new Paint();
        img.setAntiAlias(true);
        img.setColor(Color.WHITE);
        img.setTextSize(16);

        //  加载需要操作的图片
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.gold);
        bitmapDisplay = bitmap;

        //  获取图片高度和宽度
        nBitmapWidth = bitmap.getWidth();
        nBitmapHeight = bitmap.getHeight();

    }

    private void initPoint(){
        mPoint = new Paint();
        mPoint.setAntiAlias(true);
        mPoint.setColor(Color.WHITE);
        mPoint.setStrokeCap(Paint.Cap.ROUND);
        mPoint.setStyle(Paint.Style.FILL);
        mPoint.setStrokeWidth(5f);         //设置画笔宽度

    }

    private void initLine(){

        mLines=new Paint();
        mLines.setAntiAlias(true);
        mLines.setColor(Color.WHITE);


    }

    public void setCount(int[] temp){
        str_count[0]=temp[0];
        str_count[1]=temp[1];
        str_count[2]=temp[2];
        invalidate();
    }

    public void setText(String[] temp){
        str[0]=temp[0];
        str[1]=temp[1];
        str[2]=temp[2];
        invalidate();
    }
}
