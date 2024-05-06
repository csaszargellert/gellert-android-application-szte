package com.app.nailappointment.utils;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DateTimeHelper {

    private DateTimeHelper() {}

    public static String formatDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormatter.format(calendar.getTime());
    }

    public static String formatTime(int startHour, int endHour) {
       return formatHour(startHour) + " - " + formatHour(endHour);
    }

    private static String formatHour(int hour) {
        LocalTime hourTime = LocalTime.of(hour, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return hourTime.format(formatter);
    }
}
