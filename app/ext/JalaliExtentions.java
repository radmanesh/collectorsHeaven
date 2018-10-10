package ext;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import play.Logger;
import play.templates.JavaExtensions;
import utils.JalaliCalendar;

/**
 * @author PokerMania
 *
 */
public class JalaliExtentions extends JavaExtensions {
    public static String jalali(Date date) {
        return jalali(date, "hms");
    }

    public static String jalali(Date date, String option) {
        String res = "N/A";
        boolean showHour = false, showMinute = false, showSecond = false;
        if (option.indexOf("h") >= 0)
            showHour = true;
        if (option.indexOf("m") >= 0)
            showMinute = true;
        if (option.indexOf("s") >= 0)
            showSecond = true;

        if (date != null)
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                int sec = cal.get(Calendar.SECOND);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                res = new JalaliCalendar(new GregorianCalendar(year, month, day)).toString();
                String timePart = "";// (showHour ? hour + "" : "") + (showMinute ? ":" + min : "") + (showSecond ? ":"
                                     // + sec : "");
                if (showHour) {
                    if (hour < 10)
                        timePart += "0";
                    timePart += hour;
                }
                if (showMinute) {
                    timePart += ":";
                    if (min < 10)
                        timePart += "0";
                    timePart += min;
                }
                if (showSecond) {
                    timePart += ":";
                    if (sec < 10)
                        timePart += "0";
                    timePart += sec;
                }
                if (timePart != "")
                    res = res + " " + timePart;
            } catch (Exception e) {
                Logger.error(e, "Error converting Date: %s to jalali", date.toString());
            }
        return res;
    }
}
