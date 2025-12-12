package com.ragnar.main.Util.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    public static Date ParseDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date publicationDate;
        try {
            publicationDate = sdf.parse(date);
            return publicationDate;
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format", e);
        }
    }
}
