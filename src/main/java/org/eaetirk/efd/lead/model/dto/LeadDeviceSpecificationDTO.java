package org.eaetirk.efd.lead.model.dto;

import org.eaetirk.efd.lead.model.LeadDeviceSpecificationType;
import org.eaetirk.efd.lead.model.SpecificationPriority;

import java.time.Instant;

public record LeadDeviceSpecificationDTO (
        Long id,
        LeadDeviceSpecificationType leadDeviceSpecificationType,
        String comment,
        SpecificationPriority specificationPriority,
        Instant updatedAt){}
