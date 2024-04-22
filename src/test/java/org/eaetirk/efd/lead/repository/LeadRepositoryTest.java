package org.eaetirk.efd.lead.repository;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eaetirk.efd.lead.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class LeadRepositoryTest {

    @Inject
    LeadRepository leadRepository;

    @Test
    @TestTransaction
    public void shouldCreateAndFindAndCountPersistedOnceLeadWithEmptyRelations(){

        Instant now = Instant.now();
        Instant threeDaysLater = now.plus(3, java.time.temporal.ChronoUnit.DAYS);

        long count = leadRepository.count();
        int listAllSize = leadRepository.listAll().size();

        assertEquals(count, listAllSize);
        Lead lead = new Lead("Irem", "Aktas", "SW_Engineer", "iremgamzeli@gmail.com", PaymentPlan.BANK_TRANSFER,threeDaysLater, LeadStatus.ACTIVE,"new Lead Created", Instant.now());

        leadRepository.persist(lead);

        assertEquals(count+1, leadRepository.count());
        assertNotNull(lead.getId());

        Lead retrievedLead = leadRepository.findById(lead.getId());
        assertEquals("Irem", retrievedLead.getFirstName());

        leadRepository.deleteById(retrievedLead.getId());
        assertEquals(count, leadRepository.count());

    }

    @Test
    @TestTransaction
    public void shouldCreateAndFindLead()  {

        Instant now = Instant.now();
        Instant threeDaysLater = now.plus(3, java.time.temporal.ChronoUnit.DAYS);


        Lead lead = createLead(threeDaysLater);

        leadRepository.persist(lead);

        assertNotNull(lead.getId());

        Lead retrievedLead = leadRepository.findById(lead.getId());
        assertEquals("Irem", retrievedLead.getFirstName());
        assertEquals("BMW_X5_1235643f34", retrievedLead.getLeadOfferList().get(0).getCar().getModel());
        assertEquals(LeadDeviceSpecificationType.TRANSMISSION, retrievedLead.getLeadDeviceSpecificationList().get(0).getLeadDeviceSpecificationType());

    }

    @Test
    @TestTransaction
    public void shouldFindLeadByFirstName() {

        Instant now = Instant.now();
        Instant threeDaysLater = now.plus(3, java.time.temporal.ChronoUnit.DAYS);

        Lead lead = createLead(threeDaysLater);

        leadRepository.persist(lead);

        Lead leadOther =createLead(threeDaysLater);
        leadOther.setFirstName("Erdal");
        leadRepository.persist(leadOther);

        Lead leadOther1 =createLead(threeDaysLater);
        leadOther1.setFirstName("Serdal");
        leadOther1.setLastName("Mert");
        leadRepository.persist(leadOther1);

        List<Lead> leadIremList = leadRepository.findLeadByFirstName("irem");
        List<Lead> leadErdalListContains = leadRepository.findLeadContainFirstName("erdal");
        List<Lead> leadAktasListContains = leadRepository.findLeadByLastName("aKtas");
        List<Lead> leadIremAktasList = leadRepository.findLeadByFirstNameAndLastName("irem","aktas");
        List<Lead> leadErtList = leadRepository.findLeadContainLastName("ert");
        List<Lead> leadIrAkList = leadRepository.findLeadContainFirstORLastName("ir","ak");


        assertEquals(3,leadRepository.count());
        assertEquals(2, leadErdalListContains.size());
        assertEquals(2, leadAktasListContains.size());
        assertEquals(1, leadIremAktasList.size());
        assertEquals(1, leadErtList.size());
        assertEquals(2, leadIrAkList.size());
        assertEquals("Irem", leadIremList.get(0).getFirstName());

    }
    private static Lead createLead(Instant threeDaysLater) {
        Car car = new Car("BMW_X5_1235643f34", "BMW X5 AUTO", "2018", "Sport" ,"Jankomir", new BigInteger("1234"));
        LeadOffer leadOffer = new LeadOffer(OfferStatus.ACTIVE, new BigDecimal(39000), Currency.EUR);
        leadOffer.setCar(car);
        LeadDeviceSpecification leadDeviceSpecification = new LeadDeviceSpecification(null, null, LeadDeviceSpecificationType.TRANSMISSION, "Manuel", SpecificationPriority.MUST_HAVE, Instant.now());
        Lead lead = new Lead("Irem", "Aktas", "SW_Engineer", "iremgamzeli@gmail.com", PaymentPlan.BANK_TRANSFER,threeDaysLater, LeadStatus.ACTIVE,"new Lead Created", Instant.now());
        List<LeadOffer> leadOfferList = new ArrayList<>(List.of(leadOffer));
        lead.setLeadOfferList(leadOfferList);
        List<LeadDeviceSpecification> leadDeviceSpecificationList = new ArrayList<>(List.of(leadDeviceSpecification));
        lead.setLeadDeviceSpecificationList(leadDeviceSpecificationList);
        return lead;
    }
}
