package com.example.healthemanager1.ui.dashboard.SleepManager.widget;

import android.annotation.SuppressLint;
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

import com.example.healthemanager1.R;
import com.example.healthemanager1.util.DensityUtils;

/**
 * Created by YH on 2017/2/12.
 */

public class CircleProgressView extends View {

    /*
     <!-- 弧线宽度 -->
        <attr name="arcWidth" format="dimension" />
        <!-- 刻度个数 -->
        <attr name="scaleCount" format="integer" />
        <!-- 渐变起始颜色 -->
        <attr name="startColor" format="color" />
        <!-- 渐变终止颜色 -->
        <attr name="endColor" format="color" />
        <!-- 标签说明文本 -->
        <attr name="labelText" format="string" />
        <!-- 文本颜色 -->
        <attr name="textColor" format="color" />
        <!-- 百分比文本字体大小 -->
        <attr name="progressTextSize" format="dimension" />
        <!-- 标签说明字体大小 -->
        <attr name="labelTextSize" format="dimension" />
     */
    private float mArcWidth;//弧线宽度
    private int mScaleCount;//刻度的个数
    private int mStartColor;//开始颜色
    private int mEndColor;//结束颜色
    /* 渐变颜色数组 */
    private int[] mColorArray;



    private String mLabeText;//说明文本
    private int mTextColor;//说明文本颜色
    private float mProgressTextSize;//进度百分比字体大小
    private float mLabeTextSize;//说明文本字体大小

    /* 背景弧线画笔 */
    private Paint mArcBackPaint;
    /* 百分比值弧线画笔 */
    private Paint mArcForePaint;
    /* 刻度线画笔 */
    private Paint mLinePaint;
    /* 标签说明文本画笔 */
    private Paint mLabelTextPaint;
    /* 百分比文本画笔 */
    private Paint mProgressTextPaint;
    /* 弧线外切矩形 */
    private RectF mArcRectF;
    /* 测量文本宽高的矩形 */
    private Rect mTextRect;

    /* 百分比 */
    private float mProgress;
    /* 百分比对应角度 */
    private float mSweepAngle;

    private float mSweepAngle1;

    private Paint mPaintText;

    /**
     * 外部设置百分比方法
     *
     * @param progress
     */
//    public void setProgress(float xProgress,float progress) {
////        (long) (Math.abs(xProgress - progress) * 20)
//        ValueAnimator anim = ValueAnimator.ofFloat(xProgress, progress);//这是起点到终点
//        anim.setDuration(5000);//这是时间
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mProgress = (float) animation.getAnimatedValue();
//                mSweepAngle1 = xProgress *360/100 -90;
//                mSweepAngle = 360-mSweepAngle1+progress*360/100;
//                mProgress = (float) (Math.round(mProgress * 10)) / 10;//四舍五入保留到小数点后两位
//                invalidate();
//            }
//        });
//        anim.start();
//    }

    public void setProgress(float xProgress,float progress,float yProgress) {

                mSweepAngle1 = xProgress *360/100 -90;
                mSweepAngle = 270-mSweepAngle1+progress*360/100;
                mProgress = yProgress;
                invalidate();

    }




    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ResourceAsColor")
    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView, defStyleAttr, 0);
        mArcWidth = a.getDimensionPixelSize(R.styleable.CircleProgressView_arcWidth, DensityUtils.dp2px(context, 5));
        mScaleCount = a.getInt(R.styleable.CircleProgressView_scaleCount, 4);
        mStartColor = R.color.white;
        mEndColor = R.color.white;
        mColorArray = new int[]{mStartColor, mEndColor};

        mLabeText = a.getString(R.styleable.CircleProgressView_labelText);
        mTextColor = a.getColor(R.styleable.CircleProgressView_textColor, 0xff4f5f6f);
        mProgressTextSize = a.getDimensionPixelSize(R.styleable.CircleProgressView_progressTextSize, DensityUtils.sp2px(context, 20));
        mLabeTextSize = a.getDimensionPixelSize(R.styleable.CircleProgressView_labelTextSize, DensityUtils.sp2px(context, 18));

        a.recycle();
        mArcBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿

        mArcBackPaint.setStyle(Paint.Style.STROKE);//设置画笔是空心的
        mArcBackPaint.setStrokeWidth(mArcWidth*3);//设置空心画笔画出来的边框宽度
        mArcBackPaint.setColor(Color.LTGRAY);//设置画笔颜色

        mArcForePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcForePaint.setStyle(Paint.Style.STROKE);
        mArcForePaint.setStrokeWidth(mArcWidth*3);
//        mArcForePaint.setColor(Color.LTGRAY);//设置画笔颜色

        mArcRectF = new RectF();

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStrokeWidth(DensityUtils.dp2px(context, 0));

        mProgressTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressTextPaint.setStyle(Paint.Style.FILL);//设置画笔是实心的
        mProgressTextPaint.setColor(mTextColor);
        mProgressTextPaint.setTextSize(mProgressTextSize);

        mLabelTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLabelTextPaint.setStyle(Paint.Style.FILL);
        mLabelTextPaint.setColor(mTextColor);
        mLabelTextPaint.setTextSize(mLabeTextSize);

        mPaintText = new Paint();
        mPaintText.setColor(Color.BLUE);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(25);
        mPaintText.setAntiAlias(true);
        mTextRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measuredDimension(widthMeasureSpec), measuredDimension(heightMeasureSpec));
    }

    private int measuredDimension(int measureSpec) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {//明确指定宽高
            result = size;
        } else {
            result = 300;
            if (mode == MeasureSpec.AT_MOST) {//一般为wrap_content;
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mArcRectF.set(mArcWidth*3 / 2, mArcWidth*3 / 2, getWidth() - mArcWidth*3/2, getHeight() - mArcWidth*3 / 2);
        //画背景弧线
        canvas.drawArc(mArcRectF, -90, 360, false, mArcBackPaint);
        //设置渐变渲染
        LinearGradient linearGradient = new LinearGradient(getWidth() / 2, 0, getWidth(), getHeight()/2, mColorArray, null, Shader.TileMode.CLAMP);
        mArcForePaint.setShader(linearGradient);
        //画百分比值弧线
        canvas.drawArc(mArcRectF, mSweepAngle1, mSweepAngle, false, mArcForePaint);//开始角度，旋转角度，0度在3点钟位置
        //画刻度线
        for (int i = 0; i < mScaleCount; i++) {
            canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, mArcWidth, mLinePaint);
            //旋转画布
            canvas.rotate(360 / mScaleCount, getWidth() / 2, getHeight() / 2);
        }
//        画百分比文本
        String progressText = String.valueOf(mProgress);
        mProgressTextPaint.getTextBounds(progressText, 0, progressText.length(), mTextRect);
        float progressTextWidth = mTextRect.width();
        float progressTextHeight = mTextRect.height();
        canvas.drawText(progressText, getWidth() / 2 - progressTextWidth / 2,
                getHeight() / 2 + progressTextHeight / 2, mProgressTextPaint);
        //画标签说明文本
        mLabelTextPaint.getTextBounds(mLabeText, 0, mLabeText.length(), mTextRect);
        canvas.drawText(mLabeText, getWidth() / 2 - mTextRect.width() / 2,
                getHeight() / 2 - progressTextHeight / 2 - mTextRect.height(), mLabelTextPaint);


        for (int i = 1; i <=4; i++) {
            String[] x={"6点","12点","18点","0点"};
            canvas.save();// 保存当前画布
            canvas.rotate(90*i, getWidth() /2, getWidth() /2);
            canvas.drawText(x[i-1], getWidth()/2 , mArcWidth*9/2 , mPaintText);
            canvas.restore();//
        }


    }
}
