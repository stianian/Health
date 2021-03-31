package com.example.healthemanager1.ui.dashboard.Drink.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.healthemanager1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created
 * 横向的柱形图
 *
 * 1：绘制XY轴
 * 2：绘制XY轴刻度和旁边的文本
 * 3：绘制圆角矩形（六个月份的柱子）
 * 4：绘制和背景颜色一样的矩形（遮挡圆角矩形左边的圆角）
 *
 */
public class HPillarView extends View {

    private Paint mPaint;
    //线的颜色，柱子右边显示**人的颜色
    private int lineColor, dataFontColor;
    //X轴距离左边和右边的距离，Y轴距离上边和下边的距离
    private float xLeftSpace, xRightSpace,
            yTopSpace, yBottomSpace;
    //XY轴刻度的大小：X轴刻度的高，Y轴刻度的宽
    private float xDividerHeight, yDividerWidth;
    //XY轴刻度颜色
    private int xDividerColor, yDividerColor;
    //x轴下面的字体距离X轴的距离，Y轴左边的字体距离Y轴的距离
    private float txtXSpace, txtYSpace;

    //每个柱子的高度
    private float pillarHeight;
    //柱子距离Y轴刻度的距离
    private float pillarMarginY = 10;

    private final float FULL_AMOUNT = 100;

    private List<Float> dataList = new ArrayList<>();

    private List<String> drinkNameList = new ArrayList<>();

    private List<String> dataList1 = new ArrayList<>();

    private float xyFontSize;

    public HPillarView(Context context) {
        this(context, null);
    }

    public HPillarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HPillarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initXmlAttrs(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
    }





    private void initXmlAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.hpillar);
        if (typedArray == null) return;
        lineColor = typedArray.getColor(R.styleable.hpillar_line_color_h, Color.BLACK);
        dataFontColor = typedArray.getColor(R.styleable.hpillar_data_font_color_h, Color.BLACK);
        xLeftSpace = typedArray.getDimension(R.styleable.hpillar_x_left_space_h, 50);
        xRightSpace = typedArray.getDimension(R.styleable.hpillar_x_right_space_h, 80);
        yTopSpace = typedArray.getDimension(R.styleable.hpillar_y_top_space_h, 30);
        yBottomSpace = typedArray.getDimension(R.styleable.hpillar_y_bottom_space_h, 100);
        yDividerWidth = typedArray.getDimension(R.styleable.hpillar_y_divider_width, 14);
        xDividerHeight = typedArray.getDimension(R.styleable.hpillar_x_divider_height, 14);
        xDividerColor = typedArray.getColor(R.styleable.hpillar_x_divider_color, Color.BLACK);
        yDividerColor = typedArray.getColor(R.styleable.hpillar_y_divider_color, Color.BLACK);
        xyFontSize = typedArray.getDimension(R.styleable.hpillar_xy_font_size_h, 5);
        txtXSpace = typedArray.getDimension(R.styleable.hpillar_txt_x_space, 20);
        txtYSpace = typedArray.getDimension(R.styleable.hpillar_txt_y_space, 15);
        pillarHeight = typedArray.getDimension(R.styleable.hpillar_pillar_height, 30);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dataList.size() == 0) return;

        /**
         * 绘制ＸＹ轴
         */
        initPaintColor(lineColor);

        /**
         * 绘制Y轴刻度 和 左边的文字
         */
//        //Y轴每个刻度的高度：获取Y轴真是高度，然后除以6获取每个刻度的高度
        float spaceVertical = (getHeight() - yTopSpace - yBottomSpace) / drinkNameList.size();


        //绘制Y轴刻度左边文字l
        Rect yTxtRect;
        mPaint.setTextSize(xyFontSize);
        for (int i = 0; i < drinkNameList.size(); i++) {
            String number = drinkNameList.get((drinkNameList.size() - 1 - i));
            yTxtRect = new Rect();
            //为了让六个月份居中，测量最大数
            mPaint.getTextBounds("苹果", 0, 2, yTxtRect);
            //设置居中
            mPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(
                    number,
                    xLeftSpace - yTxtRect.width() / 2 - txtYSpace,
                    yTopSpace + (spaceVertical * i) + yTxtRect.height() / 2,
                    mPaint
            );
        }



        /**
         * 绘制六组数据的柱形图
         */
        //获取X轴的宽度
        float fullWidth = getWidth() - xLeftSpace - xRightSpace - yDividerWidth / 2 - pillarMarginY;
        for (int x = 0; x < dataList.size(); x++) {
            //柱子Y轴上坐标：view高度 - Y轴距离view下边的距离 - Y轴刻度高度*(x+1) - 柱子高度一半
            float yTop = getHeight() - yBottomSpace - spaceVertical * (x + 1) - pillarHeight / 2;
            //柱子Y轴下坐标：view高度 - Y轴距离view下边的距离 - Y轴刻度高度*(x+1) + 柱子高度一半
            float yBottom = getHeight() - yBottomSpace - spaceVertical * (x + 1) + pillarHeight / 2;
            float xRight = xLeftSpace + yDividerWidth / 2 + pillarMarginY + fullWidth * (dataList.get(x) / FULL_AMOUNT);
            //设置渐变背景


            LinearGradient lg = new LinearGradient(xLeftSpace + xLeftSpace, yTop, xRight, yBottom, Color.parseColor("#03A9F4"), Color.parseColor("#03A9F4"), Shader.TileMode.MIRROR);
            //Shader就是着色器
            mPaint.setShader(lg);
            //绘制圆角矩形
            canvas.drawRoundRect(
                    new RectF(
                            xLeftSpace + yDividerWidth / 2,
                            yTop,
                            xRight,
                            yBottom
                    ),
                    15,
                    15,
                    mPaint
            );
            //最后将画笔去除掉Shader
            mPaint.setShader(null);

            initPaintColor(dataFontColor);
            mPaint.setTextAlign(Paint.Align.LEFT);
            String number = dataList.get(x) + "%"+" "+dataList1.get(x);
            Rect numRect = new Rect();
            mPaint.getTextBounds(number, 0, number.length(), numRect);
            canvas.drawText(number, xRight + xDividerHeight, getHeight() - yBottomSpace - spaceVertical * (x + 1) + numRect.height() / 2, mPaint);
        }

        //按着Y轴刻度右边绘制一个矩形，遮挡圆角矩形左边的圆角
        mPaint.setColor(Color.parseColor("#fff9ef"));

    }

    private void initPaintColor(int color) {
        mPaint.setColor(color);
    }

    public void setData(List<Float> data,List<String> drinkName,List<String> drinkData) {
        dataList.clear();
        dataList.addAll(data);
        drinkNameList.clear();
        drinkNameList.addAll(drinkName);
        dataList1.clear();
        dataList1.addAll(drinkData);
        invalidate();
    }


}