package org.eaetirk.efd.lead.model;

public enum LeadDeviceSpecificationType {

    TRANSMISSION("Transmission"),
    TRIM("Trim"),
    TIRE("Tire");

    private final String abbreviation;

    private LeadDeviceSpecificationType(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
