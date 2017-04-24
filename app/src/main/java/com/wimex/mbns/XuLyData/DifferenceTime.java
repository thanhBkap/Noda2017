package com.wimex.mbns.XuLyData;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 3/8/2017.
 */

public class DifferenceTime {
    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        //return (TimeUnit.MILLISECONDS.convert(diff,TimeUnit.MILLISECONDS)/1000-60*TimeUnit.MINUTES.convert(diff,TimeUnit.MILLISECONDS));
        //return TimeUnit.MINUTES.convert(diff,TimeUnit.MILLISECONDS)-60*TimeUnit.HOURS.convert(diff,TimeUnit.MILLISECONDS);
        //return TimeUnit.HOURS.convert(diff,TimeUnit.MILLISECONDS);
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
    public static long getDifferenceHours(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.HOURS.convert(diff,TimeUnit.MILLISECONDS);
    }
    public static long getDifferenceMinutes(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.MINUTES.convert(diff,TimeUnit.MILLISECONDS)-60*TimeUnit.HOURS.convert(diff,TimeUnit.MILLISECONDS);
    }
    public static long getDifferenceSeconds(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return (TimeUnit.MILLISECONDS.convert(diff,TimeUnit.MILLISECONDS)/1000-60*TimeUnit.MINUTES.convert(diff,TimeUnit.MILLISECONDS));
    }
    public static long getDifferenceMilliSeconds(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return diff;
    }
}
