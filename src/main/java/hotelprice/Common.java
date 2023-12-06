package hotelprice;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class containing common methods for date conversion.
 */
public class Common {
    /**
     * Converts a given Date object to a string representation
     *
     * @param dateObj
     * @return A string of the date in format "dd/MM/yyyy".
     */
    public static String convertDateToSimpleFormat(Date dateObj) {
        SimpleDateFormat formatterObj = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = formatterObj.format(dateObj);
        return dateStr;
    }
}
