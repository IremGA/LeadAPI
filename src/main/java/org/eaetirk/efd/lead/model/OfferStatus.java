package org.eaetirk.efd.lead.model;

public enum OfferStatus {

    ACTIVE("Act"),
    ACCEPTED_BY_LEAD("Acpt"),
    REJECTED_BY_LEAD("Rjct"),
    WAITING("Wait"),
    INACTIVE("Inact");


    private final String abbreviation;

    private OfferStatus(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
