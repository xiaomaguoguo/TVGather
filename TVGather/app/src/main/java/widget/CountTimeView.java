package widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.bftv.knothing.firsttv.R;

/**
 * Created by KNothing on 2017/4/23.
 */

public class CountTimeView extends View implements View.OnClickListener  {

    private final static String TAG = "CountTimeProgressView";

    private Context mContext;

    private Path mPath;
    private Path mDst;

    //背景色画笔
    private Paint mBorderBottomPaint;
    //绘制画笔
    private Paint mBorderDrawPaint;
    //标记的小球
    private Paint mMarkBallPaint;
    //背景色
    private Paint mBgPaint;
    private Paint mTextPaint;

    private PathMeasure mPathMeasure;
    private ValueAnimator mAnimator;

    private float[] mPos;
    private float[] mTan;
    private float mCurrentValue;
    private float mLength;


    //MarkBall parameter
    private boolean mMarkBallFlag = true;
    private float mMarkBallWidth = 3f;
    private int mMarkBallColor = Color.RED;

    private float mBorderWidth = 3f;
    private int mBorderDrawColor = Color.parseColor("#99928A");
    private int mBorderBottomColor = Color.parseColor("#AE3124");
    private int mBackgroundColor = Color.WHITE;
    private boolean mShowIconFlat = false;

    //center text
    private String mTitleCenter = "跳过";
    private float mTextSize = 16f;
    private int mTextColor = Color.parseColor("#FFFFFF");

    private int mCountTime = 0;


    private int mCanvasCenterHeight;
    private int mCanvasCenterWidth;


    private int mStartAngle = 0;
    private boolean mIsClockwise = true;
    //view radius
    private float radius = 0;

    //动画取消标记
    private boolean onAnimationCancelMark = false;

    private String displayText;
    private OnEndListener mOnEndListener;
    private int mTextStyle = TextStyle.JUMP;

    private Bitmap alarmBitmap ;


    public CountTimeView(Context context) {
        this(context, null);
    }

    public CountTimeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;

        // Load the styled attributes and set their properties
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CountTimeView, defStyleAttr, 0);
        mTextSize = attributes.getDimension(R.styleable.CountTimeView_titleCenterSize, sp2px(mTextSize));
        mTextColor = attributes.getColor(R.styleable.CountTimeView_titleCenterColor, mTextColor);
        mTitleCenter = attributes.getString(R.styleable.CountTimeView_titleCenter);

        mBorderWidth = attributes.getDimension(R.styleable.CountTimeView_borderWidth, dp2px(mBorderWidth));
        mBorderDrawColor = attributes.getColor(R.styleable.CountTimeView_borderDrawColor, mBorderDrawColor);
        mBorderBottomColor = attributes.getColor(R.styleable.CountTimeView_borderBottomColor, mBorderBottomColor);

        mMarkBallWidth = attributes.getDimension(R.styleable.CountTimeView_markBallWidth, dp2px(mMarkBallWidth));
        mMarkBallColor = attributes.getColor(R.styleable.CountTimeView_markBallColor, mMarkBallColor);
        mMarkBallFlag = attributes.getBoolean(R.styleable.CountTimeView_markBallFlag, mMarkBallFlag);
        mShowIconFlat = attributes.getBoolean(R.styleable.CountTimeView_markIconFlat, mShowIconFlat);

        mBackgroundColor = attributes.getColor(R.styleable.CountTimeView_background_Color, mBackgroundColor);


        //起始位置角度
        mStartAngle = (int) (attributes.getInteger(R.styleable.CountTimeView_startAngle, mStartAngle) + 270) % 360;
        mIsClockwise = attributes.getBoolean(R.styleable.CountTimeView_clockwise, mIsClockwise);

        mTextStyle = attributes.getInteger(R.styleable.CountTimeView_textStyle, TextStyle.JUMP);
        mCountTime = attributes.getInteger(R.styleable.CountTimeView_countTime, 0);

        mBorderBottomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderBottomPaint.setStyle(Paint.Style.STROKE);
        mBorderBottomPaint.setStrokeWidth(mBorderWidth);
        mBorderBottomPaint.setColor(mBorderDrawColor);


        mBorderDrawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderDrawPaint.setStyle(Paint.Style.STROKE);
        mBorderDrawPaint.setStrokeWidth(mBorderWidth);
        mBorderDrawPaint.setColor(mBorderBottomColor);

        mMarkBallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMarkBallPaint.setStyle(Paint.Style.FILL);
        mMarkBallPaint.setColor(mMarkBallColor);

        mBgPaint = new Paint();
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(mBackgroundColor);

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);


        mPath = new Path();
        mPathMeasure = new PathMeasure();

        mDst = new Path();
        mPos = new float[2];
        mTan = new float[2];

        alarmBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.alarm_icon);

        initAnimation();
        setOnClickListener(this);
    }

    private void initAnimation() {
        if (mAnimator != null) {
            mAnimator.setDuration(mCountTime);
            return;
        }
        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setDuration(mCountTime);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentValue = (float) animation.getAnimatedValue();
                invalidate();
            }

        });
        mAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                onAnimationCancelMark = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (mOnEndListener != null && !onAnimationCancelMark) {
                    Log.e("CountTimeProgressView", "AnimationOver");
                    mOnEndListener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                onAnimationCancelMark = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(TAG,"onDetachedFromWindow");
        if (isRunning()) {
            cancelCountTimeAnimation();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(mStartAngle);

        //绘制背景色
        canvas.drawCircle(0, 0, radius, mBgPaint);
        canvas.drawPath(mPath, mBorderBottomPaint);

        mDst.reset();
        mDst.lineTo(0, 0);

        //draw sport path。
        float stop = mLength * mCurrentValue;
        mPathMeasure.getSegment(0, stop, mDst, true);

        canvas.drawPath(mDst, mBorderDrawPaint);

        mPathMeasure.getPosTan(mCurrentValue * mLength, mPos, mTan);

        if (mMarkBallFlag) {
            canvas.drawCircle(mPos[0], mPos[1], mMarkBallWidth, mMarkBallPaint);
        }

        switch (mTextStyle) {
            case TextStyle.SECOND:
//                displayText = (int) (mCountTime * (1 - mCurrentValue) / 1000) + "s";
                if (mTitleCenter.contains("%")) {
                    displayText = String.format(mTitleCenter, (int) (mCountTime * (1 - mCurrentValue) / 1000));
                } else {
                    displayText = (int) (mCountTime * (1 - mCurrentValue) / 1000) + "s";
                }

                break;
            case TextStyle.CLOCK:
                displayText = methodTime2((long) (mCountTime * (1 - mCurrentValue)));
                break;
            case TextStyle.JUMP:
                if (!TextUtils.isEmpty(mTitleCenter)) {
                    displayText = mTitleCenter;
                }
                break;
            case TextStyle.NONE:
            default:
                displayText = "";
                break;
        }

        if (!TextUtils.isEmpty(displayText)) {
            float middle = mTextPaint.measureText(displayText);
            canvas.rotate(-mStartAngle);
            canvas.drawText(displayText, 0 - (middle / 2), 0 - ((mTextPaint.descent() + mTextPaint.ascent()) / 2), mTextPaint);

            if(alarmBitmap != null && mShowIconFlat){ // 绘制文本下方的icon
                canvas.drawBitmap(alarmBitmap,0 - alarmBitmap.getWidth()/2,alarmBitmap.getHeight()/2 + dp2px(12), mTextPaint);
            }
        }

        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCanvasCenterWidth = w / 2;
        mCanvasCenterHeight = h / 2;
        calcRadius();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int imageSize = (width < height) ? width : height;
        setMeasuredDimension(imageSize, imageSize);

    }


    @Override
    public void onClick(View view) {
        if (mOnEndListener != null) {
            mOnEndListener.onClick(getOverageTime());
        }
    }

    public void calcRadius() {
        if (mMarkBallFlag) {
            radius = mCanvasCenterWidth - Math.max(mBorderWidth, mMarkBallWidth);
        } else {
            radius = mCanvasCenterWidth - mBorderWidth;
        }
        mPath.reset();
        if (!mIsClockwise) {
            mPath.addCircle(0, 0, radius, Path.Direction.CCW);
        } else {
            mPath.addCircle(0, 0, radius, Path.Direction.CW);
        }


        mPathMeasure.setPath(mPath, false);
        mLength = mPathMeasure.getLength();
    }

    private int sp2px(float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    // a integer to xx:xx:xx
    public static String methodTime2(long time){
        long hour = time / 3600;
        long minute = time / 60 % 60;
        long second = time % 60;
        StringBuilder sb = new StringBuilder();
        sb.append(unitFormat(hour));
        sb.append(":");
        sb.append(unitFormat(minute));
        sb.append(":");
        sb.append(unitFormat(second));
        return sb.toString();
    }

    public static String unitFormat(long i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Long.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * start countTime
     */
    public void startCountTimeAnimation() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator.start();
        }
    }

    /**
     * cancel countTime
     */
    public void cancelCountTimeAnimation() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }

    public boolean isRunning() {
        if (mAnimator != null) {
            return mAnimator.isRunning();
        }
        return false;
    }

    /**
     * @return Get the overage time
     */
    public long getOverageTime() {
        return (long) (mCountTime * (1 - mCurrentValue));
    }

    public void setBorderWidth(int borderWidth) {
        this.mBorderWidth = borderWidth;
        mBorderBottomPaint.setStrokeWidth(borderWidth);
        mBorderDrawPaint.setStrokeWidth(borderWidth);
        calcRadius();
        invalidate();
    }

    public void setBorderBottomColor(int borderBottomColor) {
        this.mBorderBottomColor = borderBottomColor;
        mBorderBottomPaint.setColor(borderBottomColor);
        invalidate();
    }

    public void setCenterIconShow(boolean isShowIcon){
        this.mShowIconFlat = isShowIcon;
        invalidate();
    }

    public void setBorderDrawColor(int borderDrawColor) {
        this.mBorderDrawColor = borderDrawColor;
        mBorderDrawPaint.setColor(borderDrawColor);
        invalidate();
    }

    public void setBackgroundColor(int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
        mBgPaint.setColor(backgroundColor);
        invalidate();
    }

    public void setStartAngle(int startAngle) {
        startAngle = (startAngle + 270) % 360;
        this.mStartAngle = startAngle;
        invalidate();
    }
    public void setClockwise(boolean clockwise){
        this.mIsClockwise = clockwise;
        calcRadius();
        invalidate();
    }

    public void setMarkBallFlag(boolean markBallFlag) {
        this.mMarkBallFlag = markBallFlag;
        calcRadius();
        invalidate();
    }

    public void setMarkBallWidth(float markBallWidth) {
        this.mMarkBallWidth = markBallWidth;
        calcRadius();
        invalidate();
    }

    public void setMarkBallColor(int markBallColor) {
        this.mMarkBallColor = markBallColor;
        mMarkBallPaint.setColor(markBallColor);
        invalidate();
    }

    public void setCountTime(int countTime) {
        this.mCountTime = countTime;
        initAnimation();
    }

    public void addOnEndListener(OnEndListener onEndListener) {
        this.mOnEndListener = onEndListener;
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
        mTextPaint.setTextSize(mTextSize);
        invalidate();
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
        mTextPaint.setColor(mTextColor);
        invalidate();
    }

    /**
     * 选择显示的类型
     * TextStyle.JUMP,固定文字（例如"跳过"）
     * TextStyle.SECOND,倒计时（5s）
     * TextStyle.CLOCK，倒计时（时钟00:00:02）
     *
     * @param textStyle 选择显示的类型
     */
    public void setTextStyle(int textStyle) {
        this.mTextStyle = textStyle;
    }

    public void setTitleCenter(String titleCenter) {
        this.mTitleCenter = titleCenter;
    }


    public interface OnEndListener {
        public void onAnimationEnd();

        public void onClick(long overageTime);
    }


    public class TextStyle {
        /**
         * 固定文字（例如"跳过"）
         */
        public static final int JUMP = 0;
        /**
         * 倒计时（5s）
         */
        public static final int SECOND = 1;
        /**
         * 倒计时（时钟00:00:02）
         */
        public static final int CLOCK = 2;
        /**
         * 不显示
         */
        public static final int NONE = 3;
    }

}
