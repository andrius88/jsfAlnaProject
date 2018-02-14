package lt.asw.jsfCRUD.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStringConverter {

    public static String dateToString(Date dateIn){

        SimpleDateFormat smplDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateIns = "";

        dateIns = smplDateFormat.format(dateIn);
        return dateIns;
    }

    public static Date stringToDate(String strDateIn){

        String year="2000", month="01", day="01";
        Date dateIns = new Date(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        year = strDateIn.substring(0,4);
        month = strDateIn.substring(5,7);
        day = strDateIn.substring(8,10);

        try {
            dateIns = dateFormat.parse(year + "-" + month + "-" + day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateIns;
    }
}
