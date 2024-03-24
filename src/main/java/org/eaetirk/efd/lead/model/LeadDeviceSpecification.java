package org.eaetirk.efd.lead.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
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
    @JsonbTransient
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