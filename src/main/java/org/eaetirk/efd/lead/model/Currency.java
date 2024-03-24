package org.eaetirk.efd.lead.model;

import lombok.Getter;

@Getter
public enum Currency {

    USD("USD"),
    EUR("EUR"),
    GBP("GBP");

    private final String abbreviation;

    Currency(String abbreviation) {
        this.abbreviation = abbreviation;
    }

}
