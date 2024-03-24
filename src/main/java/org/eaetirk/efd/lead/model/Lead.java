package org.eaetirk.efd.lead.model;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Lead contains lead info along with suggested device offers and desired device specifications by the Lead")
@Getter
@Entity
public class Lead {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Schema(required = true)
    @JsonbProperty("first_name")
    @Column(name="first_name", length = 50, nullable = false)
    private String firstName;

    @Setter
    @Schema(required = true)
    @JsonbProperty("last_name")
    @Column(name="last_name", length = 50, nullable = false)
    private String lastName;


    @Setter
    @JsonbProperty("occupation")
    private String occupation;

    @Setter
    @Schema(required = true)
    @JsonbProperty("e_mail")
    @Column(name="e_mail", nullable = false)
    private String email;

    @Setter
    @JsonbProperty("paymentPlan")
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private PaymentPlan paymentPlan;

    @JsonbProperty("createdAt")
    @Schema(implementation = String.class, format = "date")
    private final Instant createdAt=Instant.now();

    @Setter
    @JsonbProperty("respondBefore")
    @Schema(implementation = String.class, format = "date")
    private Instant respondBefore;

    @Setter
    @JsonbProperty("leadStatus")
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private LeadStatus leadStatus;

    @Setter
    @JsonbProperty("comment")
    private String comment;

    @JsonbProperty("leadOfferList")
    @OneToMany(mappedBy = "lead", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<LeadOffer> leadOfferList;

    @JsonbProperty("leadDeviceSpecificationList")
    @OneToMany(mappedBy = "lead", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<LeadDeviceSpecification> leadDeviceSpecificationList;

    public Lead(String firstName, String lastName, String occupation, String email, PaymentPlan paymentPlan, Instant respondBefore, LeadStatus leadStatus, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.occupation = occupation;
        this.email = email;
        this.paymentPlan = paymentPlan;
        this.respondBefore = respondBefore;
        this.leadStatus = leadStatus;
        this.comment = comment;
    }

    public void setLeadOfferList(List<LeadOffer> leadOfferList) {
        if(leadOfferList == null){
            leadOfferList =new ArrayList<>();
        }
        this.leadOfferList = leadOfferList;
        leadOfferList.forEach(leadOffer -> {
            leadOffer.setLead(this);
        });
    }

    public void setLeadDeviceSpecificationList(List<LeadDeviceSpecification> leadDeviceSpecificationList) {
        if(leadDeviceSpecificationList == null){
            leadDeviceSpecificationList = new ArrayList<>();
        }
        this.leadDeviceSpecificationList =leadDeviceSpecificationList;
        leadDeviceSpecificationList.forEach(leadDeviceSpecification -> {
            leadDeviceSpecification.setLead(this);
        });
    }

    public void addLeadDeviceSpecification(LeadDeviceSpecification leadDeviceSpecification){
        if(leadDeviceSpecificationList == null){
            leadDeviceSpecificationList = new ArrayList<>();
        }
        leadDeviceSpecificationList.add(leadDeviceSpecification);
        leadDeviceSpecification.setLead(this);
    }

    public void addLeadOffer(LeadOffer leadOffer){
        if(leadOfferList == null){
            leadOfferList = new ArrayList<>();
        }
        leadOfferList.add(leadOffer);
        leadOffer.setLead(this);
    }
    public Lead() {
    }
}
