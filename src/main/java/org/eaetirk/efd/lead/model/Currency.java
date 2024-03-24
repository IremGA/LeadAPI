package org.eaetirk.efd.lead.model;

public enum Currency {

    USD("USD"),
    EUR("EUR"),
    GBP("GBP");

    private final String abbreviation;

    private Currency(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
