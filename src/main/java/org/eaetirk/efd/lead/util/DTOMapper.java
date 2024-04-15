package org.eaetirk.efd.lead.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eaetirk.efd.lead.constant.LeadAPIConstant;
import org.eaetirk.efd.lead.model.Lead;
import org.eaetirk.efd.lead.model.LeadDeviceSpecification;
import org.eaetirk.efd.lead.model.LeadOffer;
import org.eaetirk.efd.lead.model.dto.LeadDTO;
import org.eaetirk.efd.lead.model.dto.LeadDeviceSpecificationDTO;
import org.eaetirk.efd.lead.model.dto.LeadOfferDTO;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DTOMapper {

    @Inject
    DateTimeMap dateTimeMap;

    public LeadDTO mapLeadDTO(Lead lead){
        List<LeadOfferDTO> leadOfferList = getLeadOfferDTOS(lead);
        List<LeadDeviceSpecificationDTO> leadDeviceSpecificationDTOList= getLeadDeviceSpecificationDTOs(lead);

        return new LeadDTO(lead.getId(),
                lead.getFirstName(),lead.getLastName(), lead.getOccupation(),
                lead.getEmail(),lead.getPaymentPlan(),lead.getCreatedAt(),
                lead.getRespondBefore(),lead.getLeadStatus(),
                lead.getComment(),leadOfferList,
                leadDeviceSpecificationDTOList);
    }

    private static List<LeadDeviceSpecificationDTO> getLeadDeviceSpecificationDTOs(Lead lead){
        List<LeadDeviceSpecificationDTO> leadDeviceSpecificationDTOList= new ArrayList<>();
        if(null != lead.getLeadDeviceSpecificationList()){
            for(LeadDeviceSpecification leadDeviceSpecification
                    : lead.getLeadDeviceSpecificationList() ){
                LeadDeviceSpecificationDTO leadDeviceSpecificationDTO
                        = new LeadDeviceSpecificationDTO(leadDeviceSpecification.getId(), leadDeviceSpecification.getLeadDeviceSpecificationType(),leadDeviceSpecification.getComment(),leadDeviceSpecification.getSpecificationPriority(),leadDeviceSpecification.getUpdatedAt());
                leadDeviceSpecificationDTOList.add(leadDeviceSpecificationDTO);
            }
        }
        return leadDeviceSpecificationDTOList;
    }

    @NotNull
    private static List<LeadOfferDTO> getLeadOfferDTOS(Lead lead) {
        List<LeadOfferDTO> leadOfferList = new ArrayList<>();
        if (lead.getLeadOfferList() != null){
            for (LeadOffer leadOffer:
                    lead.getLeadOfferList()) {
                LeadOfferDTO leadOfferDTO = new LeadOfferDTO(leadOffer.getId(),leadOffer.getCar(),leadOffer.getCreatedAt(),leadOffer.getOfferStatus(), leadOffer.getPriceAmount(), leadOffer.getCurrency());
                leadOfferList.add(leadOfferDTO);
            }
        }
        return leadOfferList;
    }

    public Lead mapToLead(LeadDTO leadDTO){
        Instant respondBeforeInstant = dateTimeMap.getInstantWithCurrentZoneId(leadDTO.respondBefore());
        Instant createdDateInstant = dateTimeMap.convertZonedDateTimeToInstant(Instant.now(), LeadAPIConstant.ZONE_ID_ZAGREB);
        Lead lead =  new Lead(leadDTO.firstName(),leadDTO.lastName(),leadDTO.occupation(),leadDTO.email(), leadDTO.paymentPlan(), respondBeforeInstant, leadDTO.leadStatus(),leadDTO.comment(),createdDateInstant);

        if(leadDTO.leadOfferList() != null){
            List<LeadOffer> leadOfferList = new ArrayList<>();
            for (LeadOfferDTO lodto:
                    leadDTO.leadOfferList()) {
                LeadOffer leadOffer = new LeadOffer();
                leadOffer.setId(lodto.id());
                leadOffer.setOfferStatus(lodto.offerStatus());
                leadOffer.setCurrency(lodto.currency());
                leadOffer.setCar(lodto.car());
                leadOffer.setPriceAmount(lodto.priceAmount());
                leadOfferList.add(leadOffer);
            }
            lead.setLeadOfferList(leadOfferList);
        }

        if(leadDTO.leadDeviceSpecificationList() != null){
            List<LeadDeviceSpecification> leadDeviceSpecificationList = new ArrayList<>();
            for (LeadDeviceSpecificationDTO ldsDto
                    : leadDTO.leadDeviceSpecificationList()) {
                LeadDeviceSpecification leadDeviceSpecification = new LeadDeviceSpecification();
                leadDeviceSpecification.setComment(ldsDto.comment());
                leadDeviceSpecification.setId(ldsDto.id());
                leadDeviceSpecification.setSpecificationPriority(ldsDto.specificationPriority());
                leadDeviceSpecification.setLeadDeviceSpecificationType(ldsDto.leadDeviceSpecificationType());
                leadDeviceSpecification.setUpdatedAt(dateTimeMap.getInstantWithCurrentZoneId(ldsDto.updatedAt()));
                leadDeviceSpecificationList.add(leadDeviceSpecification);
            }
            lead.setLeadDeviceSpecificationList(leadDeviceSpecificationList);

        }
        return lead;
    }
}
