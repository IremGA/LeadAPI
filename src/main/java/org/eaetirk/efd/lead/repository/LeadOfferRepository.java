package org.eaetirk.efd.lead.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.eaetirk.efd.lead.model.LeadOffer;

@ApplicationScoped
public class LeadOfferRepository implements PanacheRepository<LeadOffer> {
}
