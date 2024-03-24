package org.eaetirk.efd.lead.model;

public enum SpecificationPriority {

    MUST_HAVE("MH"),
    NICE_TO_HAVE("NTH");


    private final String abbreviation;

    private SpecificationPriority(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
