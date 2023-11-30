package hotelprice;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class containing common methods for date conversion.
 */
public class Common {
    /**
     * Converts a given Date object to a string representation in the format
     * "dd/MM/yyyy".
     *
     * @param startDate The Date object to be converted.
     * @return A string representation of the date in "dd/MM/yyyy" format.
     */
    public static String convertDate(Date startDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(startDate);
        return date;
    }
}
