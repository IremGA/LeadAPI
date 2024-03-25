package org.eaetirk.efd.lead.util;

import jakarta.enterprise.context.ApplicationScoped;
import org.eaetirk.efd.lead.exception.LeadAPIException;
import org.eaetirk.efd.lead.model.*;

@ApplicationScoped
public class EntityMapper {

    public <T> void applyPatch(T existingEntity, T patchedEntity) {
        if (existingEntity.getClass() != patchedEntity.getClass()) {
            throw new LeadAPIException("Entities must be same");
        }

        switch (existingEntity.getClass().getSimpleName()) {
            case "Lead":
                applyLeadPatch((Lead) existingEntity, (Lead) patchedEntity);
                break;
            case "LeadOffer":
                applyLeadOfferPatch((LeadOffer) existingEntity, (LeadOffer) patchedEntity);
                break;
            case "LeadDeviceSpecification":
                applyLeadDeviceSpecificationPatch((LeadDeviceSpecification) existingEntity, (LeadDeviceSpecification) patchedEntity);
                break;
            default:
                throw new IllegalArgumentException("Unsupported entity class: " + existingEntity.getClass().getSimpleName());
        }
    }

    private void applyLeadDeviceSpecificationPatch(LeadDeviceSpecification existingEntity, LeadDeviceSpecification patchedEntity) {
        if(patchedEntity.getComment() != null && !patchedEntity.getComment().equals(existingEntity.getComment())){
            existingEntity.setComment(patchedEntity.getComment());
        }
        if(patchedEntity.getSpecificationPriority() != null && !patchedEntity.getSpecificationPriority().equals(existingEntity.getSpecificationPriority())){
            existingEntity.setSpecificationPriority(patchedEntity.getSpecificationPriority());
        }
        if(patchedEntity.getLeadDeviceSpecificationType() != null && !patchedEntity.getLeadDeviceSpecificationType().equals(existingEntity.getLeadDeviceSpecificationType())){
            existingEntity.setLeadDeviceSpecificationType(patchedEntity.getLeadDeviceSpecificationType());
        }
        if(patchedEntity.getUpdatedAt() != null && !patchedEntity.getUpdatedAt().equals(existingEntity.getUpdatedAt())){
            existingEntity.setUpdatedAt(patchedEntity.getUpdatedAt());
        }
    }

    private void applyLeadOfferPatch(LeadOffer existingEntity, LeadOffer patchedEntity) {

        if(patchedEntity.getPriceAmount()!= null && !patchedEntity.getPriceAmount().equals(existingEntity.getPriceAmount())){
            existingEntity.setPriceAmount(patchedEntity.getPriceAmount());

        }
        if(patchedEntity.getCar()!= null && !patchedEntity.getCar().equals(existingEntity.getCar())){
            mapPatchedCarToExistingCar(patchedEntity, existingEntity);
        }
        if(patchedEntity.getOfferStatus()!= null && !patchedEntity.getOfferStatus().equals(existingEntity.getOfferStatus())){
            existingEntity.setOfferStatus(patchedEntity.getOfferStatus());
        }
        if(patchedEntity.getCurrency()!= null && !patchedEntity.getCurrency().equals(existingEntity.getCurrency())){
            existingEntity.setCurrency(patchedEntity.getCurrency());
        }
    }


    private void mapPatchedCarToExistingCar(LeadOffer patchedEntity, LeadOffer existingEntity) {
        Car patchedEntityCar = patchedEntity.getCar();
        Car existingEntityCar = existingEntity.getCar();
        if(patchedEntityCar.getModel()!= null && !patchedEntityCar.getModel().equals(existingEntityCar.getModel())){
            existingEntity.getCar().setModel(patchedEntityCar.getModel());
        }
        if(patchedEntityCar.getKilometer()!= null && !patchedEntityCar.getKilometer().equals(existingEntityCar.getKilometer())){
            existingEntity.getCar().setKilometer(patchedEntityCar.getKilometer());
        }
        if(patchedEntityCar.getLocation()!= null && !patchedEntityCar.getLocation().equals(existingEntityCar.getLocation())){
            existingEntity.getCar().setLocation(patchedEntityCar.getLocation());
        }
        if(patchedEntityCar.getBrand()!= null && !patchedEntityCar.getBrand().equals(existingEntityCar.getBrand())){
            existingEntity.getCar().setBrand(patchedEntityCar.getBrand());
        }
        if(patchedEntityCar.getCreation_year()!= null && !patchedEntityCar.getCreation_year().equals(existingEntityCar.getCreation_year())){
            existingEntity.getCar().setCreation_year(patchedEntityCar.getCreation_year());
        }
        if(patchedEntityCar.getStyle()!= null && !patchedEntityCar.getStyle().equals(existingEntityCar.getStyle())){
            existingEntity.getCar().setStyle(patchedEntityCar.getStyle());
        }
    }

    // Method to apply changes for the Lead entity
    private void applyLeadPatch(Lead existingLead, Lead patchedLead) {

        if (patchedLead.getFirstName() != null && !patchedLead.getFirstName().equals(existingLead.getFirstName())) {
            existingLead.setFirstName(patchedLead.getFirstName());
        }
        if (patchedLead.getLastName() != null && !patchedLead.getLastName().equals(existingLead.getLastName())) {
            existingLead.setLastName(patchedLead.getLastName());
        }
        if (patchedLead.getEmail() != null && !patchedLead.getEmail().equals(existingLead.getEmail())) {
            existingLead.setEmail(patchedLead.getEmail());
        }
        if(patchedLead.getOccupation() != null && !patchedLead.getOccupation().equals(existingLead.getOccupation())){
           existingLead.setOccupation(patchedLead.getOccupation());
        }
        if(patchedLead.getComment() != null && !patchedLead.getComment().equals(existingLead.getComment())){
            existingLead.setComment(patchedLead.getComment());
        }
        if(patchedLead.getLeadStatus() != null && !patchedLead.getLeadStatus().equals(existingLead.getLeadStatus())){
            existingLead.setLeadStatus(patchedLead.getLeadStatus());
        }
        if(patchedLead.getPaymentPlan() != null && !patchedLead.getPaymentPlan().equals(existingLead.getPaymentPlan())){
            existingLead.setPaymentPlan(patchedLead.getPaymentPlan());
        }
        if(patchedLead.getRespondBefore() != null && !patchedLead.getRespondBefore().equals(existingLead.getRespondBefore())){
            existingLead.setRespondBefore(patchedLead.getRespondBefore());
        }
    }

}
