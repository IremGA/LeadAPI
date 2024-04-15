package org.eaetirk.efd.lead.model.dto;

import jakarta.json.bind.annotation.JsonbProperty;

import org.eaetirk.efd.lead.model.LeadStatus;
import org.eaetirk.efd.lead.model.PaymentPlan;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@Schema(description = "Lead contains lead info along with suggested device offers and desired device specifications by the Lead")

public record  LeadDTO (
            Long id,
            @JsonbProperty("first_name")
            String firstName,
           @JsonbProperty("last_name")
           String lastName,
           @JsonbProperty("occupation")
           String occupation,
           @JsonbProperty("e_mail")
           String email,
           @JsonbProperty("paymentPlan")
           PaymentPlan paymentPlan,
            @JsonbProperty("createdAt")
            Instant createdAt,
            @JsonbProperty("respondBefore")
            Instant respondBefore,

            @JsonbProperty("leadStatus")
            LeadStatus leadStatus,

            @JsonbProperty("comment")
            String comment,

            @JsonbProperty("leadOfferList")
            List<LeadOfferDTO> leadOfferList,

            @JsonbProperty("leadDeviceSpecificationList")
            List<LeadDeviceSpecificationDTO> leadDeviceSpecificationList


        ) {

}
