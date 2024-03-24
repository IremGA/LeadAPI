package org.eaetirk.efd.lead.model;

import lombok.Getter;

@Getter
public enum SpecificationPriority {

    MUST_HAVE("MH"),
    NICE_TO_HAVE("NTH");


    private final String abbreviation;

    SpecificationPriority(String abbreviation) {
        this.abbreviation = abbreviation;
    }

}
