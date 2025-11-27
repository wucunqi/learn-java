package com.wcq;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class OSTest {
    @Test
    public void test(){
        WeekFields weekFields = WeekFields.ISO;

        LocalDate startDate = LocalDate.of(2025, 12, 29);
        LocalDate endDate = LocalDate.of(2026, 1, 2);

        int isoWeek = startDate.get(weekFields.weekOfWeekBasedYear());
        int isoYear = startDate.get(weekFields.weekBasedYear());

        System.out.println(isoWeek);
        System.out.println(isoYear);

        // System.out.println(startDate.get(weekFields.weekOfYear()));
        // System.out.println(endDate.get(weekFields.weekOfYear()));
        //
        // System.out.println(startDate.get(ChronoField.YEAR));
        // System.out.println(endDate.get(ChronoField.YEAR));
    }

    @Test
    public void test2(){
        LocalDate startDate = LocalDate.of(2025, 10, 27);
        LocalDate endDate = LocalDate.of(2025, 11, 6);
        WeekFields weekFields = WeekFields.ISO;
        int weekOfYear = startDate.get(weekFields.weekOfWeekBasedYear());
        System.out.println("开始日期所在周: " + weekOfYear);
        weekOfYear = endDate.get(weekFields.weekOfWeekBasedYear());
        System.out.println("结束日期所在周: " + weekOfYear);
    }
}
