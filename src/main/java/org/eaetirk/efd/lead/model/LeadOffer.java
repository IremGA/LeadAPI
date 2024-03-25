package org.eaetirk.efd.lead.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LeadOffer {


    @JoinColumn(name = "lead_id_fk")
    @ManyToOne(cascade = CascadeType.ALL)
    @Setter
    @JsonbTransient
    private Lead lead;

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @Schema(implementation = String.class, format = "date")
    private final Instant createdAt = Instant.now();

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;
    private BigDecimal priceAmount;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public LeadOffer(OfferStatus offerStatus, BigDecimal priceAmount, Currency currency) {
        this.offerStatus = offerStatus;
        this.priceAmount = priceAmount;
        this.currency = currency;
    }


}
