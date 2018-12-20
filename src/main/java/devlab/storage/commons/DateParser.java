package devlab.storage.commons;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateParser {

    public static java.sql.Date fromStringToSqlDate(String date) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = format.parse(date);
        return new java.sql.Date(parsedDate.getTime());

    }

    public static String fromUtilDateToString(Date from, String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern);
       // from = Calendar.getInstance().getTime();
        return formatter.format(from);
    }
}
