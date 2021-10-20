package com.airdata.air.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CurrentDate {
    private CurrentDate(){}

    public static String currentDate(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
