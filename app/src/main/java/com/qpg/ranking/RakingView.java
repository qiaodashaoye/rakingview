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
    private Paint top_point;
    private Paint left1_point;
    private Paint left2_point;
    private Paint moddle1_point;
    private Paint moddle2_point;
    private Paint right1_point;
    private Paint right2_point;

    private Paint leftLine;
    private Paint rightLine;
    private Paint center_Line;
    private Paint bottomLine1;
    private Paint bottomLine2;
    private Paint bottomLine3;

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
        canvas.drawCircle(width,height,3,top_point);
        canvas.drawLine(width,height,width,height+vLine,center_Line);
        canvas.drawLine(width,height,width-Float.valueOf(x+""),height+vLine,leftLine);
        canvas.drawLine(width,height,width+Float.valueOf(x+""),height+vLine,rightLine);

        canvas.drawLine(width-Float.valueOf(x+""),height+vLine,width-Float.valueOf(x+""),height+vLine*2,bottomLine1);
        canvas.drawLine(width,height+vLine,width,height+vLine*2,bottomLine2);
        canvas.drawLine(width+Float.valueOf(x+""),height+vLine,width+Float.valueOf(x+""),height+vLine*2,bottomLine3);

        canvas.drawCircle(width-Float.valueOf(x+""),height+vLine,3,left1_point);
        canvas.drawCircle(width,height+vLine,3,moddle1_point);
        canvas.drawCircle(width+Float.valueOf(x+""),height+vLine,3,right1_point);

        canvas.drawCircle(width-Float.valueOf(x+""),height+vLine*2,3,left2_point);
        canvas.drawCircle(width,height+vLine*2,3,moddle2_point);
        canvas.drawCircle(width+Float.valueOf(x+""),height+vLine*2,3,right2_point);

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
        top_point = new Paint();
        top_point.setAntiAlias(true);
        top_point.setColor(Color.WHITE);

        left1_point = new Paint();
        left1_point.setAntiAlias(true);
        left1_point.setColor(Color.WHITE);

        left2_point = new Paint();
        left2_point.setAntiAlias(true);
        left2_point.setColor(Color.WHITE);

        top_point = new Paint();
        top_point.setAntiAlias(true);
        top_point.setColor(Color.WHITE);

        moddle1_point = new Paint();
        moddle1_point.setAntiAlias(true);
        moddle1_point.setColor(Color.WHITE);

        moddle2_point = new Paint();
        moddle2_point.setAntiAlias(true);
        moddle2_point.setColor(Color.WHITE);

        right1_point = new Paint();
        right1_point.setAntiAlias(true);
        right1_point.setColor(Color.WHITE);

        right2_point = new Paint();
        right2_point.setAntiAlias(true);
        right2_point.setColor(Color.WHITE);
    }

    private void initLine(){
        leftLine=new Paint();
        leftLine=new Paint();
        leftLine.setAntiAlias(true);
        leftLine.setColor(Color.WHITE);

        rightLine=new Paint();
        rightLine=new Paint();
        rightLine.setAntiAlias(true);
        rightLine.setColor(Color.WHITE);

        center_Line=new Paint();
        center_Line=new Paint();
        center_Line.setAntiAlias(true);
        center_Line.setColor(Color.WHITE);

        bottomLine1=new Paint();
        bottomLine1=new Paint();
        bottomLine1.setAntiAlias(true);
        bottomLine1.setColor(Color.WHITE);

        bottomLine1=new Paint();
        bottomLine1=new Paint();
        bottomLine1.setAntiAlias(true);
        bottomLine1.setColor(Color.WHITE);

        bottomLine2=new Paint();
        bottomLine2=new Paint();
        bottomLine2.setAntiAlias(true);
        bottomLine2.setColor(Color.WHITE);

        bottomLine3=new Paint();
        bottomLine3=new Paint();
        bottomLine3.setAntiAlias(true);
        bottomLine3.setColor(Color.WHITE);

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
