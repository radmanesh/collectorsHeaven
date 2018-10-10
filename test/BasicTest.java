/*
 *
 */
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.text.NumberFormatter;

import org.junit.Test;

import play.test.UnitTest;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.CalendarUtils;
import sun.util.resources.CalendarData;

public class BasicTest extends UnitTest {

    @Test
    public void aVeryImportantThingToTest() {
        assertEquals(2, 1 + 1);
    }

    static public void main(String[] args) {


        Locale list[] = NumberFormat.getAvailableLocales();
        for (Locale aLocale : list) {
            NumberFormat nf = NumberFormat.getNumberInstance(aLocale);
            String out = aLocale.getDisplayName() + " - " + aLocale.getDisplayCountry() + " - " + aLocale.getDisplayScript();
            System.out.println(aLocale.toString() + "  >>   " + nf.format(1245.3));
        }
    }

}
