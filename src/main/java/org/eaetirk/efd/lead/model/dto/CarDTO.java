package org.eaetirk.efd.lead.model.dto;

import java.math.BigInteger;

public record CarDTO (
        Long id,
        String model,
        String brand,
        String creation_year,
        String style,
        String location,
        BigInteger kilometer
) {




}
