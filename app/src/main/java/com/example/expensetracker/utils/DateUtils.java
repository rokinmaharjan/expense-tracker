package com.example.expensetracker.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

    public static int getMonthFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.get(Calendar.MONTH);
    }

    public static int getYearFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.get(Calendar.YEAR);
    }

    public static String getMonthNameFromDate(Date date) {
        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

       return monthName[cal.get(Calendar.MONTH)];
    }

    public static String getMonthNameFromMonthInteger(int month) {
        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

        return monthName[month];
    }

    public static int getMonthNumberFromMonthName(String month) {
        List<String> monthName = Arrays.asList("January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December");

        return monthName.indexOf(month);
    }

    public static void main(String[] args) {
        System.err.println(getMonthNumberFromMonthName("December"));
    }
}
