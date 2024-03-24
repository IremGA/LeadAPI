package org.eaetirk.efd.lead.util;

import java.time.DayOfWeek;
import java.time.Instant;

public class LeadUtil {

    public Instant returnThreeWorkingDaysAfter(Instant moment){

        // Add 3 days to the instant
        Instant threeDaysLater = moment.plus(3, java.time.temporal.ChronoUnit.DAYS);

        // Adjust to next working day if it's a weekend
        if (threeDaysLater.atZone(java.time.ZoneId.systemDefault()).getDayOfWeek() == DayOfWeek.SATURDAY) {
            threeDaysLater = threeDaysLater.plus(2, java.time.temporal.ChronoUnit.DAYS); // skip Saturday and Sunday
        } else if (threeDaysLater.atZone(java.time.ZoneId.systemDefault()).getDayOfWeek() == DayOfWeek.SUNDAY) {
            threeDaysLater = threeDaysLater.plus(1, java.time.temporal.ChronoUnit.DAYS); // skip Sunday
        }

        return threeDaysLater;
    }

}
