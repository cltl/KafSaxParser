package eu.kyotoproject.util;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Piek Vossen
 * Date: 17-dec-2008
 * Time: 14:38:37
 * To change this template use File | Settings | File Templates.
 * This file is part of KafSaxParser.

    KafSaxParser is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    KafSaxParser is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with KafSaxParser.  If not, see <http://www.gnu.org/licenses/>.
 */
public class DateUtil {


    /**
     * Pass in the time in milliseconds and get a formatted string
     * @param date
     * @return
     */
    static public String getDateString (long date) {
        SimpleDateFormat formatter
            = new SimpleDateFormat ("yyyy.MM.dd G 'at' HH:mm:ss a zzz");
        Date currentTime_1 = new Date(date);
        String dateString = formatter.format(currentTime_1);
        return dateString;
    }

    /**
     * Get current system time as formatted string
     * @return
     */
    static public String getDateString () {
        SimpleDateFormat formatter
            = new SimpleDateFormat ("yyyy.MM.dd G 'at' HH:mm:ss a zzz");
        Date currentTime_1 = new Date(System.currentTimeMillis());
        String dateString = formatter.format(currentTime_1);
        return dateString;
    }

    static public Calendar getCaledarObject (String dateString) {
        Calendar calendarObj = Calendar.getInstance();
        SimpleDateFormat formatter
            = new SimpleDateFormat ("yyyy.MM.dd G 'at' HH:mm:ss a zzz");
        try {
            formatter.parse(dateString);
            calendarObj = formatter.getCalendar();
            
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return calendarObj;
    }

}
