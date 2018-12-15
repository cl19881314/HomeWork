package com.xlf.xsrt.work.widget.calendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class WCanlendarViewPager extends NoScrollViewPager {
    /**
     * 总共显示的月个数
     */
    private int mMonthCount;

    private WCanlendarConfig mConfig;
    private int mPreViewHeight;
    private int mNextViewHeight;


    private WCanlendarConfig.CalendarSelectListener mPageSelectLister;
    private WCanlendarConfig.CalendarItemClickListener mItemListener;

    public WCanlendarViewPager(Context context) {
        this(context, null);
    }

    public WCanlendarViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    private int mCurrentViewHeight;

    public void setup(WCanlendarConfig config) {
        this.mConfig = config;
        //初始化当前显示页的高度
        updateMonthViewHeight(mConfig.getmCurrentDate().getYear(), mConfig.getmCurrentDate().getMonth());
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = mCurrentViewHeight;
        setLayoutParams(layoutParams);
        //初始化viewpager
        mMonthCount = 12 * (mConfig.getsMaxYear() - mConfig.getsMinYear())
                - mConfig.getsMinYearMonth() + 1 +
                mConfig.getsMaxYearMonth();
        setAdapter(new CanlendarAdapter());
        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //动态改变高度时，平滑过渡
                int height;
                if (position < getCurrentItem()) {//右滑-1
                    height = (int) ((mPreViewHeight)
                            * (1 - positionOffset) +
                            mCurrentViewHeight
                                    * positionOffset);
                } else {//左滑+！
                    height = (int) ((mCurrentViewHeight)
                            * (1 - positionOffset) +
                            (mNextViewHeight)
                                    * positionOffset);
                }
                post(new Runnable() {
                    @Override
                    public void run() {
                        ViewGroup.LayoutParams params = getLayoutParams();
                        params.height = height;
                        setLayoutParams(params);
                    }
                });
            }

            @Override
            public void onPageSelected(int position) {
                int year = (position + mConfig.getsMinYearMonth() - 1) / 12 + mConfig.getsMinYear();
                int month = (position + mConfig.getsMinYearMonth() - 1) % 12 + 1;
                //动态改变高度
                updateMonthViewHeight(year, month);
                if (mPageSelectLister != null) {
                    mPageSelectLister.onCalendarSelectListener(year, month);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, true);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (Math.abs(getCurrentItem() - item) > 1) {
            super.setCurrentItem(item, false);
        } else {
            super.setCurrentItem(item, smoothScroll);
        }
    }

    private void updateMonthViewHeight(int year, int month) {
        mCurrentViewHeight = WCanlendarUtil.getMonthViewHeight(year, month, mConfig.getmCanlendarItemHeight(), mConfig.getsWeekStart());
        if (month == 1) {
            mPreViewHeight = WCanlendarUtil.getMonthViewHeight(year - 1, 12, mConfig.getmCanlendarItemHeight(), mConfig.getsWeekStart());
            mNextViewHeight = WCanlendarUtil.getMonthViewHeight(year, 2, mConfig.getmCanlendarItemHeight(), mConfig.getsWeekStart());
        } else {
            mPreViewHeight = WCanlendarUtil.getMonthViewHeight(year, month - 1, mConfig.getmCanlendarItemHeight(), mConfig.getsWeekStart());
            if (month == 12) {
                mNextViewHeight = WCanlendarUtil.getMonthViewHeight(year + 1, 1, mConfig.getmCanlendarItemHeight(), mConfig.getsWeekStart());
            } else {
                mNextViewHeight = WCanlendarUtil.getMonthViewHeight(year, month + 1, mConfig.getmCanlendarItemHeight(), mConfig.getsWeekStart());
            }
        }
    }

    /**
     * 重绘
     */
    public void invalidateXX() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).invalidate();
        }
    }

    /**
     * 更新当前日期
     */
    public void updateCurrentDay(CalendarBean calendar) {
        updateCurrentDay(calendar, true);
    }

    public void updateCurrentDay(CalendarBean calendar, boolean update) {
        if (mConfig.getmCurrentDate().equals(calendar)) return;
        mConfig.setmCurrentDate(calendar);
        if (update) invalidateXX();

    }


    /**
     * 更新标记日期
     */
    void updateScheme() {
        for (int i = 0; i < getChildCount(); i++) {
            WMonthView view = (WMonthView) getChildAt(i);
            view.updateSchemes();
        }
    }

    class CanlendarAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mMonthCount;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.d("chufei", "instantiateItem");
            int year = (position + mConfig.getsMinYearMonth() - 1) / 12 + mConfig.getsMinYear();
            int month = (position + mConfig.getsMinYearMonth() - 1) % 12 + 1;
            WMonthView wMonthView = new WMonthView(getContext());
            wMonthView.setup(mConfig);
            wMonthView.initMonthAndDate(year, month);
            wMonthView.setOnItemClickListener(mItemListener);
            container.addView(wMonthView);
            return wMonthView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            WMonthView view = (WMonthView) object;
            if (view == null) {
                return;
            }
            container.removeView(view);
        }
    }

    public void setOnCanlendarPagerSelectedListener(WCanlendarConfig.CalendarSelectListener pagerSelectedListener) {
        this.mPageSelectLister = pagerSelectedListener;
    }

    public void setOnCanlendarItemListener(WCanlendarConfig.CalendarItemClickListener listener) {
        this.mItemListener = listener;
    }
}
