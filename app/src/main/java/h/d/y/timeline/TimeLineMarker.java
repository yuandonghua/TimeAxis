package h.d.y.timeline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;


/**
 * @description:时光轴
 * @author:袁东华 created at 2016/7/22 0022 下午 1:35
 */
public class TimeLineMarker extends View {

    //线的宽度
    private int mLineSize;
    //标记的大小
    private int mMarkerSize;
    //开始的线
    private Drawable mBeginLine;
    //结束的线
    private Drawable mEndLine;
    //标记的图片
    private Drawable mMarkerDrawable;
    //文本颜色
    private int textColor;
    //文本大小
    private int textSize;
    //文本内容
    private String text;
    private Paint paint;

    public TimeLineMarker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    /**
     * @description:初始化自定义属性
     * @author:袁东华 created at 2016/7/22 0022 下午 2:27
     */
    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TimeLineMarker);
        mMarkerSize = typedArray.getDimensionPixelSize(R.styleable.TimeLineMarker_markerSize, 26);
        mLineSize = typedArray.getDimensionPixelSize(R.styleable.TimeLineMarker_lineSize, 16);
        mBeginLine = typedArray.getDrawable(R.styleable.TimeLineMarker_beginLine);
        mEndLine = typedArray.getDrawable(R.styleable.TimeLineMarker_endLine);
        mMarkerDrawable = typedArray.getDrawable(R.styleable.TimeLineMarker_marker);
        textColor = typedArray.getColor(R.styleable.TimeLineMarker_textColor, Color.WHITE);
        textSize = typedArray.getDimensionPixelSize(R.styleable.TimeLineMarker_textSize, 14);
        text = typedArray.getString(R.styleable.TimeLineMarker_text);
        paint = new Paint();


        //需要回收
        typedArray.recycle();
    }

    /**
     * @description:测量本View的宽高及子View的宽高
     * @author:袁东华 created at 2016/7/22 0022 下午 2:36
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽度
        int w = mMarkerSize + getPaddingLeft() + getPaddingRight();
        //获取高度
        int h = mMarkerSize + getPaddingTop() + getPaddingBottom();

        //MeasureSpec.AT_MOST=match_parent
        //MeasureSpec.EXACTLY=26dp具体的值
        //MeasureSpec.UNSPECIFIED=wrap_content

        //受父容器的影响,计算真实的宽高
        int widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
        int heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);
        //设置最终的测量结果
        setMeasuredDimension(widthSize, heightSize);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //当View显示的时候回调
        //定位几个Drawable的坐标,绘制
        initDrawable();


    }

    int x;
    int y;
    int contentWidth;
    int contentHeight;

    private void initDrawable() {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int width = getWidth();
        int height = getHeight();
        contentWidth = width - paddingLeft - paddingRight;
        contentHeight = height - paddingTop - paddingBottom;
        x = width >> 1;
        y = height >> 1;
        int ms;
        if (mMarkerDrawable != null) {
            //画的marker大小,可能和设置的大小不一样,与容器的大小有关
            int markerSize = Math.min(mMarkerSize, Math.min(contentWidth, contentHeight));

            ms = markerSize >> 1;
            mMarkerDrawable.setBounds(x - ms, y - ms, x + ms, y + ms);
        }

        int lineLeft = 0;
        //marker的中心点的x减去线条宽度的一半,就是线条左边的位置
        lineLeft = x - (mLineSize >> 1);
        if (mBeginLine != null) {
            mBeginLine.setBounds(lineLeft, 0, lineLeft + mLineSize, y);
        } else {
        }
        if (mEndLine != null) {
            mEndLine.setBounds(lineLeft, y, lineLeft + mLineSize, height);
        } else {
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (mBeginLine != null) {
            mBeginLine.draw(canvas);
        }
        if (mEndLine != null) {

            mEndLine.draw(canvas);
        }
        if (mMarkerDrawable != null) {

            mMarkerDrawable.draw(canvas);
        }
        if (text != null && !"".equals(text) && paint != null) {
            //  设置Rect的颜色透明
            paint.setColor(Color.TRANSPARENT);
            //  设置字体大小
            paint.setTextSize(textSize);
            paint.setStrokeWidth(3);
            //  消除字体锯齿
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            //画的marker大小,可能和设置的大小不一样,与容器的大小有关
            int markerSize = Math.min(mMarkerSize, Math.min(contentWidth, contentHeight));
            //设置个具体的Rect,盛放Text,如果new Rect()这样的Rect,会出现居中有偏差的现象
            Rect TextRect = new Rect(x - markerSize, y - markerSize, x + markerSize, y + markerSize);
            canvas.drawRect(TextRect, paint);
            paint.setColor(textColor);
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
//            baseline = ((height - fontMetrics.bottom + fontMetrics.top) >> 1) - fontMetrics.top;
            int baseline = (TextRect.bottom + TextRect.top - fontMetrics.bottom - fontMetrics.top) >> 1;
            int textX = TextRect.centerX();
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(text, textX, baseline, paint);
        }

    }

    public void setLineSize(int lineSize) {
        this.mLineSize = lineSize;
    }

    public void setMarkerSize(int markerSize) {
        this.mMarkerSize = markerSize;
    }

    public void setBeginLine(Drawable beginLine) {
        this.mBeginLine = beginLine;
        initDrawable();
        invalidate();
    }

    public void setEndLine(Drawable endLine) {
        this.mEndLine = endLine;
        initDrawable();
        invalidate();
    }

    public void setMarkerDrawable(Drawable markerDrawable) {
        this.mMarkerDrawable = markerDrawable;
        initDrawable();
        invalidate();
    }

    public void setText(String text) {
        this.text = text;
        initDrawable();
        invalidate();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        initDrawable();
        invalidate();
    }
}
