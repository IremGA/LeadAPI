package org.eaetirk.efd.lead.service;

import org.eaetirk.efd.lead.model.dto.LeadDTO;

import java.util.List;

public interface LeadService {

    List<LeadDTO> getLeads();
    LeadDTO findLeadById(Long id);
    List<LeadDTO> searchLeadByFirstNameLastName(String firstName, String lastName);
    List<LeadDTO> searchLeadContainsFirstNameLastName(String firstName, String lastName);
    LeadDTO createLead(LeadDTO lead);
    void updateLead(LeadDTO lead, Long id);
    void deleteLead(Long id);

}
