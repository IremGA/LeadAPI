package org.eaetirk.efd.lead.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Lead {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Schema(required = true)
    @Column(name="first_name", length = 50, nullable = false)
    private String firstName;

    @Setter
    @Schema(required = true)
    @Column(name="last_name", length = 50, nullable = false)
    private String lastName;


    @Setter
    private String occupation;

    @Setter
    @Schema(required = true)
    @Column(name="e_mail", nullable = false)
    private String email;

    @Setter
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private PaymentPlan paymentPlan;

    @Schema(implementation = String.class, format = "date")
    private Instant createdAt;

    @Setter
    @Schema(implementation = String.class, format = "date")
    private Instant respondBefore;

    @Setter
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private LeadStatus leadStatus;

    @Setter
    private String comment;

    @OneToMany(mappedBy = "lead", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<LeadOffer> leadOfferList;

    @OneToMany(mappedBy = "lead", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<LeadDeviceSpecification> leadDeviceSpecificationList;

    public Lead(String firstName, String lastName, String occupation, String email, PaymentPlan paymentPlan, Instant respondBefore, LeadStatus leadStatus, String comment, Instant createdAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.occupation = occupation;
        this.email = email;
        this.paymentPlan = paymentPlan;
        this.respondBefore = respondBefore;
        this.leadStatus = leadStatus;
        this.comment = comment;
        this.createdAt = createdAt;
    }
    public void setLeadOfferList(List<LeadOffer> leadOfferList) {
        if(leadOfferList == null){
            leadOfferList =new ArrayList<>();
        }
        this.leadOfferList = leadOfferList;
        leadOfferList.forEach(leadOffer -> leadOffer.setLead(this));
    }

    public void setLeadDeviceSpecificationList(List<LeadDeviceSpecification> leadDeviceSpecificationList) {
        if(leadDeviceSpecificationList == null){
            leadDeviceSpecificationList = new ArrayList<>();
        }
        this.leadDeviceSpecificationList =leadDeviceSpecificationList;
        leadDeviceSpecificationList.forEach(leadDeviceSpecification -> leadDeviceSpecification.setLead(this));
    }

}
