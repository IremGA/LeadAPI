package org.eaetirk.efd.lead.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Instant;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LeadDeviceSpecification {

    @JoinColumn(name = "lead_id_fk")
    @ManyToOne
    private Lead lead;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "ld_spec_type",length = 20)
    @Enumerated(EnumType.STRING)
    private LeadDeviceSpecificationType leadDeviceSpecificationType;

    @Column(length = 500)
    private String comment;

    @Column(name = "ld_spec_priority",length = 20)
    @Enumerated(EnumType.STRING)
    private SpecificationPriority specificationPriority;

    @Schema(implementation = String.class, format = "date")
    private Instant updatedAt = Instant.now();
}
