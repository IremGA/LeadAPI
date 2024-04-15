package org.eaetirk.efd.lead.util;

import jakarta.enterprise.context.ApplicationScoped;
import org.eaetirk.efd.lead.constant.LeadAPIConstant;
import org.eaetirk.efd.lead.exception.LeadAPIException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class DateTimeMap {

    public Instant convertZonedDateTimeToInstant(Instant instantDate, String zoneIdStr){
        ZoneId zoneId = ZoneId.of(zoneIdStr);
        ZonedDateTime zonedDateTime = instantDate.atZone(zoneId);
        // Convert back to Instant
        return zonedDateTime.toInstant();
    }

    public Instant getInstantWithCurrentZoneId(Instant instantDate){
        if(instantDate != null){
            ZoneId defaultZoneId = ZoneId.systemDefault();
            ZonedDateTime zonedDateTime = instantDate.atZone(defaultZoneId);
            // Convert back to Instant
            return zonedDateTime.toInstant();
        }
        return null;

    }

    public String convertInstantToDateTimeFormat(Instant instant){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LeadAPIConstant.DateTimeFormat);
        return formatter.format(instant);
    }

    public Instant convertDateTimeStrToInstant(String dateTimeString){
         try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LeadAPIConstant.DateTimeFormat);
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
             return localDateTime.toInstant(java.time.ZoneOffset.UTC);
        } catch (java.time.format.DateTimeParseException e) {
            throw new LeadAPIException("Invalid date-time format: " + dateTimeString);
        }
    }

}
