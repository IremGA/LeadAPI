package org.eaetirk.efd.lead.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.eaetirk.efd.lead.model.LeadDeviceSpecification;

@ApplicationScoped
public class LeadDeviceSpecificationRepository implements PanacheRepository<LeadDeviceSpecification> {

}
