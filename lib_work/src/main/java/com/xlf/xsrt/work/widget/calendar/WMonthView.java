package com.xlf.xsrt.work.widget.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class WMonthView extends View implements View.OnClickListener {
    private Context mContext;
    private int mYear;
    private int mMonth;
    private int mLineCount;
    private int mItemHeight;
    private int mItemWidth;

    private Paint mCurMonthTextPaint;
    private Paint mCurMonthTextPaintSelected;
    private Paint mCurDayTextPaint;
    private Paint mSelectTPaint;
    private Paint mSchemePaint;
    private Paint mSelectCurrentDay;
    private Paint mCurCircleDayPaint;
    /**
     * 日历项
     */
    List<CalendarBean> mItems;
    /**
     * 下个月的偏移量
     */
    private int mNextDiff;
    /**
     * 当前点击项
     */
//    int mCurrentItem = -1;
    /**
     * 点击的位置
     */
    private float mX, mY;

    private WCanlendarConfig mConfig;

    private float mTextBaseline;

    private WCanlendarConfig.CalendarItemClickListener mItemListener;


    public WMonthView(Context context) {
        this(context, null);
    }

    public WMonthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WMonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mCurMonthTextPaint = new Paint();
        mCurMonthTextPaint.setTextSize(24);
        mCurMonthTextPaint.setAntiAlias(true);
        mCurMonthTextPaint.setColor(Color.BLACK);
        mCurMonthTextPaint.setTextAlign(Paint.Align.CENTER);
        mCurMonthTextPaint.setTextSize(WCanlendarUtil.dipToPx(context, 15));//设置字体大小

        mCurMonthTextPaintSelected = new Paint();
        mCurMonthTextPaintSelected.setTextSize(24);
        mCurMonthTextPaintSelected.setAntiAlias(true);
        mCurMonthTextPaintSelected.setColor(Color.WHITE);
        mCurMonthTextPaintSelected.setTextAlign(Paint.Align.CENTER);
        mCurMonthTextPaintSelected.setTextSize(WCanlendarUtil.dipToPx(context, 15));//设置字体大小

        mCurDayTextPaint = new Paint();
        mCurDayTextPaint.setTextSize(24);
        mCurDayTextPaint.setAntiAlias(true);
        mCurDayTextPaint.setColor(Color.parseColor("#00724C"));
        mCurDayTextPaint.setTextAlign(Paint.Align.CENTER);
//        mCurDayTextPaint.setFakeBoldText(true);
        mCurDayTextPaint.setTextSize(WCanlendarUtil.dipToPx(context, 15));//设置字体大小

        mSelectTPaint = new Paint();
        mSelectTPaint.setAntiAlias(true);
        mSelectTPaint.setColor(Color.parseColor("#F59937"));
        mSelectTPaint.setStyle(Paint.Style.FILL);

        mSelectCurrentDay = new Paint();
        mSelectCurrentDay.setAntiAlias(true);
        mSelectCurrentDay.setColor(Color.parseColor("#00724C"));
        mSelectCurrentDay.setStyle(Paint.Style.FILL);

        mSchemePaint = new Paint();
        mSchemePaint.setAntiAlias(true);
        mSchemePaint.setStyle(Paint.Style.FILL);


        mCurCircleDayPaint = new Paint();
        mCurCircleDayPaint.setAntiAlias(true);
        mCurCircleDayPaint.setColor(Color.parseColor("#00724C"));
        mCurCircleDayPaint.setStyle(Paint.Style.STROKE);
        mCurCircleDayPaint.setStrokeWidth(WCanlendarUtil.dipToPx(context, 1));

        setOnClickListener(this);
    }

    /**
     * 初始化配置
     *
     * @param config
     */
    public void setup(WCanlendarConfig config) {
        this.mConfig = config;
        this.mItemHeight = config.getmCanlendarItemHeight();
        Paint.FontMetrics metrics = mCurMonthTextPaint.getFontMetrics();
        mTextBaseline = mItemHeight / 2 - metrics.descent + (metrics.bottom - metrics.top) / 2;
    }

    /**
     * 初始化日历页数据
     *
     * @param year
     * @param month
     */
    public void initMonthAndDate(int year, int month) {
        this.mYear = year;
        this.mMonth = month;
        initCanlendar();

    }

    private void initCanlendar() {
        mNextDiff = WCanlendarUtil.getMonthEndDiff(mYear, mMonth, mConfig.getsWeekStart());
        int monthDayCount = WCanlendarUtil.getMonthDaysCount(mYear, mMonth);
        int preDiff = WCanlendarUtil.getMonthViewStartDiff(mYear, mMonth, mConfig.getsWeekStart());
        mLineCount = (preDiff + monthDayCount + mNextDiff) / 7;
        mItems = WCanlendarUtil.initCalendarForMonthView(mYear, mMonth, mConfig.getmCurrentDate(), mConfig.getsWeekStart());
        addSchemesFromMap();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mLineCount != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mItemHeight * mLineCount, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mLineCount == 0) return;
        mItemWidth = (getWidth() - 2 * mConfig.getmCalendarPadding()) / 7;
        int d = 0;
        for (int i = 0; i < mLineCount; i++) {//行
            for (int j = 0; j < 7; j++) {//列
                CalendarBean calendar = mItems.get(d);
                if (d > mItems.size() - mNextDiff) {
                    return;//月末预置下月日期不绘制
                }
                if (!calendar.isCurrentMonth()) {
                    ++d;
                    continue;//月首上月日期不绘制
                }
                draw(canvas, calendar, i, j, d);
                ++d;
            }
        }

    }

    /**
     * 开始绘制
     *
     * @param canvas   canvas
     * @param calendar 对应日历
     * @param i        行
     * @param j        列
     * @param d        第几个
     */
    private void draw(Canvas canvas, CalendarBean calendar, int i, int j, int d) {
        int x = j * mItemWidth + mConfig.getmCalendarPadding();
        int y = i * mItemHeight;
        boolean hasScheme = calendar.isScheme();
        boolean isSelected = calendar.equals(mConfig.getmSelectDay());//当前点击项
        if (hasScheme) {
            onDrawScheme(canvas, calendar, x, y);
        }
        if (isSelected) {
            onDrawSelected(canvas, calendar, x, y);
        }
        onDrawText(canvas, calendar, x, y, isSelected);
    }

    private void onDrawSelected(Canvas canvas, CalendarBean calendar, int x, int y) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        if (calendar.isCurrentDay()) {
            canvas.drawCircle(cx, cy, WCanlendarUtil.dipToPx(mContext, 15), mSelectCurrentDay);
        } else {
            canvas.drawCircle(cx, cy, WCanlendarUtil.dipToPx(mContext, 15), mSelectTPaint);
        }
    }

    private void onDrawScheme(Canvas canvas, CalendarBean calendar, int x, int y) {
        int cx = x + mItemWidth / 2;
        int cy = (int) (y + mItemHeight - WCanlendarUtil.dipToPx(mContext, 2));
        if (calendar.getTimeState() == 0) {//有未提交
            mSchemePaint.setColor(Color.parseColor("#F53756"));
        } else {//全都都已提交
            mSchemePaint.setColor(Color.parseColor("#C5C5C5"));
        }
        canvas.drawCircle(cx, cy, WCanlendarUtil.dipToPx(mContext, 2), mSchemePaint);
    }

    private void onDrawText(Canvas canvas, CalendarBean calendar, int x, int y, Boolean isSelected) {
        float baselineY = mTextBaseline + y;
        int cx = x + mItemWidth / 2;
        canvas.drawText(calendar.isCurrentDay() ? "今" : String.valueOf(calendar.getDay()), cx, baselineY,
                isSelected ? mCurMonthTextPaintSelected : calendar.isCurrentDay() ? mCurDayTextPaint : mCurMonthTextPaint);
        if (calendar.isCurrentDay()) {
            int cy = y + mItemHeight / 2;
            canvas.drawCircle(cx, cy, WCanlendarUtil.dipToPx(mContext, 13), mCurCircleDayPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1)
            return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = event.getX();
                mY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                mX = event.getX();
                mY = event.getY();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        CalendarBean calendar = getIndex();
        if (calendar.isScheme() || calendar.isCurrentDay()) {
            mConfig.setmSelectDay(calendar);
            if (mItemListener != null) {
                mItemListener.onCalendarItemClickListener(calendar, mItems.indexOf(calendar));
            }
            invalidate();
        }//未标记或者不是今天不可点击


    }

    public void setOnItemClickListener(WCanlendarConfig.CalendarItemClickListener listener) {
        this.mItemListener = listener;
    }

    private CalendarBean getIndex() {
        int indexX = (int) mX / mItemWidth;
        if (indexX >= 7) {
            indexX = 6;
        }
        int indexY = (int) mY / mItemHeight;
        int position = indexY * 7 + indexX;// 选择项
        if (position >= 0 && position < mItems.size())
            return mItems.get(position);
        return null;
    }

    public void updateSchemes() {
        addSchemesFromMap();
        invalidate();
    }

    /**
     * 添加日期对应的课程
     */
    public void addSchemesFromMap() {
        if (mConfig.mSchemeDates == null || mConfig.mSchemeDates.size() == 0) {
            return;
        }
        for (CalendarBean schems : mConfig.mSchemeDates) {
            for (CalendarBean a : mItems) {
                if (a.equals(schems)) {
                    a.setScheme(true);
                    a.setTimeState(schems.getTimeState());
                }
            }
        }

    }
}
