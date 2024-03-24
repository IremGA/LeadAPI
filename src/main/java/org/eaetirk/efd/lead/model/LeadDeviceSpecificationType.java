package org.eaetirk.efd.lead.model;

import lombok.Getter;

@Getter
public enum LeadDeviceSpecificationType {

    TRANSMISSION("Transmission"),
    TRIM("Trim"),
    TIRE("Tire");

    private final String abbreviation;

    LeadDeviceSpecificationType(String abbreviation) {
        this.abbreviation = abbreviation;
    }

}
