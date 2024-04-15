package org.eaetirk.efd.lead.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.eaetirk.efd.lead.constant.LeadAPIConstant;
import org.eaetirk.efd.lead.exception.LeadAPIException;
import org.eaetirk.efd.lead.model.Lead;
import org.eaetirk.efd.lead.model.LeadDeviceSpecification;
import org.eaetirk.efd.lead.model.LeadOffer;
import org.eaetirk.efd.lead.model.dto.LeadDTO;
import org.eaetirk.efd.lead.repository.LeadRepository;
import org.eaetirk.efd.lead.resource.LeadResource;
import org.eaetirk.efd.lead.util.DTOMapper;
import org.eaetirk.efd.lead.util.EntityMapper;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class DefaultLeadService implements LeadService{

    @Inject
    EntityMapper mapper;
    @Inject
    LeadRepository leadRepository;

    @Inject
    DTOMapper dtoMapper;

    @Inject
    EntityManager entityManager;

    private static final Logger LOG = Logger.getLogger(LeadResource.class);
    @Override
    public List<LeadDTO> getLeads() throws LeadAPIException{
        return leadRepository.listAll(Sort.by("lastName")).stream()
                .map(leader -> dtoMapper.mapLeadDTO(leader)).collect(Collectors.toList());
    }

    @Override
    public LeadDTO findLeadById(Long id) throws NotFoundException, LeadAPIException{
        if(id == null ){
            throw new LeadAPIException(String.valueOf(Response.Status.BAD_REQUEST), LeadAPIConstant.ERROR_LEAD_ID_MISSING, LeadAPIConstant.ERROR_LEAD_ID_MISSING_REASON, LeadAPIConstant.GET_LEAD_OPERATION,Response.Status.BAD_REQUEST);
        }
        Lead lead = leadRepository.findByIdOptional(id).orElseThrow(NotFoundException::new);

        return dtoMapper.mapLeadDTO(lead);
    }

    @Override
    public List<LeadDTO> searchLeadByFirstNameLastName(String firstName, String lastName) {
        return switch (getSearchCriteria(firstName, lastName)) {
            case BOTH -> leadRepository.findLeadByFirstNameAndLastName(firstName, lastName).stream()
                    .map(leader ->dtoMapper.mapLeadDTO(leader)).collect(Collectors.toList());
            case FIRST_NAME_ONLY -> leadRepository.findLeadByFirstName(firstName).stream()
                    .map(leader->dtoMapper.mapLeadDTO(leader)).collect(Collectors.toList());
            case LAST_NAME_ONLY -> leadRepository.findLeadByLastName(lastName).stream()
                    .map(leader->dtoMapper.mapLeadDTO(leader)).collect(Collectors.toList());
            default -> getLeads();
        };
    }

    @Override
    public List<LeadDTO> searchLeadContainsFirstNameLastName(String firstName, String lastName) {
        return switch (getSearchCriteria(firstName, lastName)) {
            case BOTH -> leadRepository.findLeadContainFirstORLastName(firstName, lastName).stream()
                    .map(lead -> dtoMapper.mapLeadDTO(lead)).collect(Collectors.toList());
            case FIRST_NAME_ONLY -> leadRepository.findLeadContainFirstName(firstName).stream()
                    .map(lead -> dtoMapper.mapLeadDTO(lead)).collect(Collectors.toList());
            case LAST_NAME_ONLY -> leadRepository.findLeadContainLastName(lastName).stream()
                    .map(lead -> dtoMapper.mapLeadDTO(lead)).collect(Collectors.toList());
            default -> null;
        };
    }

    @Override
    public LeadDTO createLead(LeadDTO lead) throws LeadAPIException{
        Lead p_lead = dtoMapper.mapToLead(lead);
        leadRepository.persist(p_lead);
        return dtoMapper.mapLeadDTO(p_lead);
    }

    @Override
    public void deleteLead(Long id) throws LeadAPIException{
        leadRepository.deleteById(id);
    }


    @Override
    @Transactional
    public void updateLead(LeadDTO lead, Long id) throws LeadAPIException {
        try {
            if (id == null) {
                throw new LeadAPIException(String.valueOf(Response.Status.BAD_REQUEST), LeadAPIConstant.ERROR_LEAD_ID_MISSING, LeadAPIConstant.ERROR_LEAD_ID_MISSING_REASON, LeadAPIConstant.UPDATE_LEAD_OPERATION, Response.Status.BAD_REQUEST);
            }
            Lead persistedLead = leadRepository.findById(id);
            //update lead
            mapper.applyPatch(persistedLead, dtoMapper.mapToLead(lead));
            entityManager.merge(persistedLead);
            //update lead Offer
            updateLeadOffer(dtoMapper.mapToLead(lead), persistedLead);
            //update lead device specification
            updateLeadDeviceSpecification(dtoMapper.mapToLead(lead), persistedLead);
        } catch (LeadAPIException leadAPIException) {
            LOG.error("Error occurred ", leadAPIException.getMessage(), leadAPIException);
            try {
                entityManager.getTransaction().rollback();
            } catch (Exception exception) {
                LOG.error("Exception Thrown in Rollback Transaction", exception.getMessage(), exception);
                throw leadAPIException;
            }
        } catch (Exception e) {
            //OptimisticLock exception may be thrown
            LOG.error("Error occurred ", e.getMessage(), e);
            try {
                entityManager.getTransaction().rollback();
            } catch (Exception exception) {
                LOG.error("Exception Thrown in Rollback Transaction", exception.getMessage(), exception);
                throw new LeadAPIException(Response.Status.INTERNAL_SERVER_ERROR.toString(), LeadAPIConstant.ERROR_WHILE_UPDATE_LEAD, e.getMessage(), LeadAPIConstant.UPDATE_LEAD_OPERATION, Response.Status.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private void updateLeadDeviceSpecification(Lead lead, Lead persistedLead) {
        if(lead.getLeadDeviceSpecificationList() != null && !lead.getLeadDeviceSpecificationList().equals(persistedLead.getLeadDeviceSpecificationList()))
            for (LeadDeviceSpecification leadDeviceSpecification : lead.getLeadDeviceSpecificationList()) {
                if(leadDeviceSpecification.getId() == null ){
                    throw new LeadAPIException(Response.Status.BAD_REQUEST.toString(),LeadAPIConstant.ERROR_NO_DEVICE_SPEC_ID_FOUND,LeadAPIConstant.ERROR_NO_DEVICE_SPEC_ID_FOUND_REASON, LeadAPIConstant.UPDATE_LEAD_OPERATION, Response.Status.BAD_REQUEST);
                }
                Optional<LeadDeviceSpecification> leadDeviceSpecificationOptional = persistedLead.getLeadDeviceSpecificationList().stream().filter(p_spec -> Objects.equals(p_spec.getId(), leadDeviceSpecification.getId())).findFirst();
                if (leadDeviceSpecificationOptional.isPresent()) {
                    LeadDeviceSpecification foundObject = leadDeviceSpecificationOptional.get();
                    mapper.applyPatch(foundObject, leadDeviceSpecification);
                    entityManager.merge(foundObject);
                }else{
                    throw new LeadAPIException(Response.Status.BAD_REQUEST.toString(), LeadAPIConstant.ERROR_NO_DEVICE_SPEC_FOUND,LeadAPIConstant.ERROR_NO_DEVICE_SPEC_FOUND_REASON, LeadAPIConstant.UPDATE_LEAD_OPERATION, Response.Status.BAD_REQUEST);
                }
            }
    }

    private void updateLeadOffer(Lead lead, Lead persistedLead) {
        if(lead.getLeadOfferList() != null && !lead.getLeadOfferList().equals(persistedLead.getLeadOfferList()))
            for (LeadOffer offer : lead.getLeadOfferList()) {
                if(offer.getId() == null ){
                    throw new LeadAPIException(Response.Status.BAD_REQUEST.toString(), LeadAPIConstant.ERROR_NO_LEAD_OFFER_ID_FOUND,LeadAPIConstant.ERROR_NO_LEAD_OFFER_ID_FOUND_REASON, LeadAPIConstant.UPDATE_LEAD_OPERATION, Response.Status.BAD_REQUEST);
                }
                Optional<LeadOffer> leadOffer = persistedLead.getLeadOfferList().stream().filter(p_offer -> Objects.equals(p_offer.getId(), offer.getId())).findFirst();
                if (leadOffer.isPresent()) {
                    LeadOffer foundObject = leadOffer.get();
                    mapper.applyPatch(foundObject, offer);
                    entityManager.merge(foundObject);
                }else{
                    throw new LeadAPIException(Response.Status.BAD_REQUEST.toString(),LeadAPIConstant.ERROR_NO_LEAD_OFFER_FOUND, LeadAPIConstant.ERROR_NO_LEAD_OFFER_FOUND_REASON, LeadAPIConstant.UPDATE_LEAD_OPERATION, Response.Status.BAD_REQUEST);
                }
            }
    }

    private SearchCriteria getSearchCriteria(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            return SearchCriteria.BOTH;
        } else if (firstName != null) {
            return SearchCriteria.FIRST_NAME_ONLY;
        } else if (lastName != null) {
            return SearchCriteria.LAST_NAME_ONLY;
        } else {
            return SearchCriteria.NONE;
        }
    }

    private enum SearchCriteria {
        BOTH,
        FIRST_NAME_ONLY,
        LAST_NAME_ONLY,
        NONE
    }

}

