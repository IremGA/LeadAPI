package org.eaetirk.efd.lead.util;

import com.github.javafaker.Faker;
import org.eaetirk.efd.lead.model.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

public class MockDataGenerator {
        private Faker faker;

        public MockDataGenerator() {
            faker = new Faker();
        }

        public Lead generateRandomLead() {

            Instant now = Instant.now();
            Instant threeDaysLater = now.plus(3, java.time.temporal.ChronoUnit.DAYS);
            String model = "BMW_X"+faker.number().numberBetween(1,7);
            String km = String.valueOf(faker.number().numberBetween(5000,50000));
            String price = String.valueOf(faker.number().numberBetween(36000,50000));
            Car car = new Car(model, "BMW", "2018", "SUV" ,faker.address().city(), new BigInteger(km));
            LeadOffer leadOffer = new LeadOffer(OfferStatus.ACTIVE, new BigDecimal(price), Currency.EUR);
            leadOffer.setCar(car);
            LeadDeviceSpecification leadDeviceSpecification = new LeadDeviceSpecification(null, null, LeadDeviceSpecificationType.TRANSMISSION, "Manuel", SpecificationPriority.MUST_HAVE, Instant.now());
            Lead lead = new Lead(faker.name().firstName(), faker.name().lastName(), faker.name().title(), faker.internet().emailAddress(), PaymentPlan.BANK_TRANSFER, threeDaysLater,LeadStatus.ACTIVE,"new Lead Created");
            lead.addLeadOffer(leadOffer);
            lead.addLeadDeviceSpecification(leadDeviceSpecification);
            return lead;
        }
    }

