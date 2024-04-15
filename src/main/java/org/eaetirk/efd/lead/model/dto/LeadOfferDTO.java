package org.eaetirk.efd.lead.model.dto;

import org.eaetirk.efd.lead.model.Car;
import org.eaetirk.efd.lead.model.Currency;
import org.eaetirk.efd.lead.model.OfferStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record LeadOfferDTO (
        Long id,
        Car car,
        Instant createdAt,
        OfferStatus offerStatus,
        BigDecimal priceAmount,
        Currency currency

) {

}
