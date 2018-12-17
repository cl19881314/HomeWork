package com.xlf.xsrt.work.widget.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;


import com.xlf.xsrt.work.R;

import java.util.List;


public class WCanlendarView extends FrameLayout {
    private WCanlendarConfig mConfig;
    private View mRootView;
    private ImageButton mPreMonth;
    private ImageButton mNextMonth;
    private TextView mTitle;
    private WCanlendarViewPager mCanlendarViewPager;

    public WCanlendarView(@NonNull Context context) {
        this(context, null);
    }

    public WCanlendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WCanlendarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mConfig = new WCanlendarConfig(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRootView = View.inflate(getContext(), R.layout.xsrt_layout_canlendar_weekbar, null);
        mCanlendarViewPager = mRootView.findViewById(R.id.canlendar_month);
        mCanlendarViewPager.setup(mConfig);
        mCanlendarViewPager.setCurrentItem(mConfig.getmCurrentMonthViewItem());
        this.addView(mRootView);
    }

    public int getCurrentYear() {
        return mConfig.getmCurrentDate().getYear();
    }

    public int getCurrentMonth() {
        return mConfig.getmCurrentDate().getMonth();
    }

    public int getCurrentDay() {
        return mConfig.getmCurrentDate().getDay();
    }

    /**
     * 滚动到下一个月
     *
     * @return
     */
    public void moveToNextMonth() {
        mCanlendarViewPager.setCurrentItem(mCanlendarViewPager.getCurrentItem() + 1, false);
    }

    public void moveToNextMonth(boolean smoothScroll) {
        mCanlendarViewPager.setCurrentItem(mCanlendarViewPager.getCurrentItem() + 1, smoothScroll);
    }

    /**
     * 滚动到上一个月
     *
     * @return
     */

    public void moveToPreMonth() {
        mCanlendarViewPager.setCurrentItem(mCanlendarViewPager.getCurrentItem() - 1, false);
    }

    public void moveToPreMonth(boolean smoothScroll) {
        mCanlendarViewPager.setCurrentItem(mCanlendarViewPager.getCurrentItem() - 1, smoothScroll);
    }

    /**
     * 滑动事件
     *
     * @param listener
     */
    public void setOnCanlendarPageSelectListener(WCanlendarConfig.CalendarSelectListener listener) {
        mCanlendarViewPager.setOnCanlendarPagerSelectedListener(listener);
    }

    public void setOnCanlendarItemListener(WCanlendarConfig.CalendarItemClickListener listener) {
        mCanlendarViewPager.setOnCanlendarItemListener(listener);
    }

    /**
     * 重新绘制
     */

    public void updateCurrentDay(CalendarBean calendar, boolean update) {
        mCanlendarViewPager.updateCurrentDay(calendar, update);
    }

    public void setSchemeDate(List<CalendarBean> data) {
        mConfig.mSchemeDates = data;
        mCanlendarViewPager.updateScheme();
    }

    /**
     * 滚动到制定日期
     *
     * @param year
     * @param month
     */
    public void moveToDate(int year, int month) {
        int page = 12 * (year - mConfig.getsMinYear()) + month - mConfig.getsMinYearMonth();
        if (mCanlendarViewPager.getCurrentItem() == page) return;
        mCanlendarViewPager.setCurrentItem(page, false);
    }
}
