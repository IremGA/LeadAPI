package org.eaetirk.efd.lead.service;

import org.eaetirk.efd.lead.model.Lead;

import java.util.List;

public interface LeadService {

    List<Lead> getLeads();
    Lead findLeadById(Long id);
    List<Lead> searchLeadByFirstNameLastName(String firstName, String lastName);
    List<Lead> searchLeadContainsFirstNameLastName(String firstName, String lastName);
    void createLead(Lead lead);
    void updateLead(Lead lead, Long id);
    void deleteLead(Long id);

}
