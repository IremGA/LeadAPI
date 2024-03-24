package org.eaetirk.efd.lead.model;

public enum LeadStatus {

    ACTIVE("Act"),
    OFFER_READY("OfrRdy"),
    OFFER_SENT_TO_LEAD("OfrSnt"),
    FAILED("Failed"),
    SUSPENDED("Susp"),
    INACTIVE("Inact");


    private final String abbreviation;

    private LeadStatus(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
