package com.xlf.xsrt.work.widget.calendar;

public class CalendarBean {

    /**
     * 年
     */
    private int year;

    /**
     * 月1-12
     */
    private int month;

    /**
     * 日1-31
     */
    private int day;

    /**
     * 是否是本月,这里对应的是月视图的本月，而非当前月份，请注意
     */
    private boolean isCurrentMonth;

    /**
     * 是否是今天
     */
    private boolean isCurrentDay;
    /**
     * 是否标记  true表示今天有课 false没有课
     */
    private boolean isScheme;

    public boolean isScheme() {
        return isScheme;
    }

    public void setScheme(boolean scheme) {
        isScheme = scheme;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    public void setCurrentMonth(boolean currentMonth) {
        isCurrentMonth = currentMonth;
    }

    public boolean isCurrentDay() {
        return isCurrentDay;
    }

    public void setCurrentDay(boolean currentDay) {
        isCurrentDay = currentDay;
    }

    @Override
    public String toString() {
        return "CalendarBean{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", isCurrentDay=" + isCurrentDay +
                "isCurrentMonth" + isCurrentMonth +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof CalendarBean) {
            if (((CalendarBean) o).getYear() == year && ((CalendarBean) o).getMonth() == month && ((CalendarBean) o).getDay() == day)
                return true;
        }
        return super.equals(o);
    }
}
