package org.eaetirk.efd.lead.model;

import lombok.Getter;

@Getter
public enum OfferStatus {

    ACTIVE("Act"),
    ACCEPTED_BY_LEAD("Acpt"),
    REJECTED_BY_LEAD("Rjct"),
    WAITING("Wait"),
    INACTIVE("Inact");


    private final String abbreviation;

    OfferStatus(String abbreviation) {
        this.abbreviation = abbreviation;
    }

}
