package com.xlf.xsrt.work.widget.calendar;

import android.content.Context;
import android.util.AttributeSet;

import java.util.Date;
import java.util.List;

public class WCanlendarConfig {
    /**
     * 周起始：周日
     */
    static final int WEEK_START_WITH_SUN = 1;

    /**
     * 周起始：周一
     */
    static final int WEEK_START_WITH_MON = 2;

    /**
     * 周起始：周六
     */
    static final int WEEK_START_WITH_SAT = 7;

    private int sWeekStart;
    /**
     * 日历最小显示的年份
     */
    private int sMinYear;
    /**
     * 日历最小显示的年份中的月份
     */
    private int sMinYearMonth;
    /**
     * 日历最大显示的年份
     */
    private int sMaxYear;
    /**
     * 日历最大显示的年份中的月份
     */
    private int sMaxYearMonth;
    /**
     * 设置今天的日期
     */
    private CalendarBean mCurrentDate;
    /**
     * 日历卡单列高度
     */
    private int mCanlendarItemHeight;
    /**
     * 日历卡单天的padding
     */
    private int mCalendarPadding;

    private int mCurrentMonthViewItem;
    private Context mContext;

    /**
     * 标记的日期,数量巨大，请使用这个
     */
    List<CalendarBean> mSchemeDates;

    /**
     * 全局的选中item
     *
     * @return
     */
    private CalendarBean mSelectDay;

    public CalendarBean getmSelectDay() {
        return mSelectDay;
    }

    public void setmSelectDay(CalendarBean mSelectDay) {
        this.mSelectDay = mSelectDay;
    }

    public int getmCanlendarItemHeight() {
        return (int) WCanlendarUtil.dipToPx(mContext, mCanlendarItemHeight);
    }

    public int getmCalendarPadding() {
        return (int) WCanlendarUtil.dipToPx(mContext, mCalendarPadding);
    }


    public int getmCurrentMonthViewItem() {
        return mCurrentMonthViewItem;
    }

    public WCanlendarConfig(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        sWeekStart = WEEK_START_WITH_SUN;//默认该日历为星期天开头
        sMinYear = 2008;
        sMinYearMonth = 2;
        sMaxYear = 2200;
        sMaxYearMonth = 12;
        mCanlendarItemHeight = 52;
        mCalendarPadding = 15;//两边的padding，应该与星期bar的padding保持一致
        mCurrentDate = new CalendarBean();
        Date date = new Date();
        mCurrentDate.setYear(WCanlendarUtil.getDate("yyyy", date));
        mCurrentDate.setMonth(WCanlendarUtil.getDate("MM", date));
        mCurrentDate.setDay(WCanlendarUtil.getDate("dd", date));
        mCurrentDate.setCurrentDay(true);
        setmSelectDay(mCurrentDate);
        mCurrentMonthViewItem = 12 * (mCurrentDate.getYear() - this.sMinYear) + mCurrentDate.getMonth() - this.sMinYearMonth;
    }

    public int getsWeekStart() {
        return sWeekStart;
    }

    public void setsWeekStart(int sWeekStart) {
        this.sWeekStart = sWeekStart;
    }

    public int getsMinYear() {
        return sMinYear;
    }


    public int getsMinYearMonth() {
        return sMinYearMonth;
    }


    public int getsMaxYear() {
        return sMaxYear;
    }


    public int getsMaxYearMonth() {
        return sMaxYearMonth;
    }


    public CalendarBean getmCurrentDate() {
        return mCurrentDate;
    }

    public void setmCurrentDate(CalendarBean mCurrentDate) {
        this.mCurrentDate = mCurrentDate;
        this.mSelectDay = mCurrentDate;
    }


    public interface CalendarSelectListener {
        void onCalendarSelectListener(int year, int month);
    }

    public interface CalendarItemClickListener {
        void onCalendarItemClickListener(CalendarBean calendar, int position);
    }
}
