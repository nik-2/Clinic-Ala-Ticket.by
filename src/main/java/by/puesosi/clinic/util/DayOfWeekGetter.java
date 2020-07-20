package by.puesosi.clinic.util;

import java.time.LocalDate;

public class DayOfWeekGetter {
    public static int getDayOfWeek(LocalDate date){
        int day = -1;
        switch (date.getDayOfWeek()){
            case MONDAY:
                day = 0;
                break;
            case TUESDAY:
                day = 1;
                break;
            case WEDNESDAY:
                day = 2;
                break;
            case THURSDAY:
                day = 3;
                break;
            case FRIDAY:
                day = 4;
                break;
            case SATURDAY:
                day = 5;
                break;
            case SUNDAY:
                day = 6;
                break;
        }
        return day;
    }
}
