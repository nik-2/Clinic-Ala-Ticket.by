package by.puesosi.clinic.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class DifferenceBetweenServiceTimeAndNowGetter {
    public static int getDifferenceBetweenStartServiceTimeAndNow(String serviceTime){
        LocalDate localDate = LocalDate.now();
        int hour = Integer.parseInt(serviceTime.substring(0,2));
        int min = Integer.parseInt(serviceTime.substring(3,5));
        int hourNow = localDate.atTime(LocalTime.now()).getHour();
        int minNow = localDate.atTime(LocalTime.now()).getMinute();
        return (hourNow - hour) * 60 + minNow - min;
    }

    public static int getDifferenceBetweenEndServiceTimeAndNow(String serviceTime){
        LocalDate localDate = LocalDate.now();
        int hour = Integer.parseInt(serviceTime.substring(6,8));
        int min = Integer.parseInt(serviceTime.substring(9,11));
        int hourNow = localDate.atTime(LocalTime.now()).getHour();
        int minNow = localDate.atTime(LocalTime.now()).getMinute();
        return (hourNow - hour) * 60 + minNow - min;
    }
}
