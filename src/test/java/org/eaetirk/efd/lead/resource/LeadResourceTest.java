package org.eaetirk.efd.lead.resource;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eaetirk.efd.lead.constants.LeadAPITestConstants;
import org.eaetirk.efd.lead.model.*;
import org.eaetirk.efd.lead.repository.LeadRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LeadResourceTest {


    @Test
    @Order(1)
    @TestSecurity(authorizationEnabled = false)
    public void testCreateLead() {

        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .body(LeadAPITestConstants.REQUEST_CREATE_JSON)
                .when()
                .post("/efd/api/v1/lead")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .body("comment", is("new About to be Created"))
                .body("first_name", is("Irem"))
                .body("leadDeviceSpecificationList.leadDeviceSpecificationType", notNullValue())
                .body("leadOfferList.car.brand", notNullValue()).body("leadOfferList.car.model", hasItem("BMW_X5"))
                .body("leadDeviceSpecificationList.comment", hasItem("Leather"))
                .body("leadOfferList.car.brand", hasItem("BMW"))
                .body("leadStatus",  notNullValue());

    }

    @Test
    @Order(2)
    public void testCreateLeadUnauthorized() {

        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .body(LeadAPITestConstants.REQUEST_CREATE_JSON)
                .when()
                .post("/efd/api/v1/lead")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());

    }

    @Test
    @Order(3)
    @TestSecurity(authorizationEnabled = false)
    public void testGetLead() throws Exception {

        Instant now = Instant.now();
        Instant threeDaysLater = now.plus(3, java.time.temporal.ChronoUnit.DAYS);


        LeadRepository mock = Mockito.mock(LeadRepository.class);
        Mockito.when(mock.findByIdOptional(Mockito.anyLong()))
                .thenReturn(Optional.of(new Lead("Irem",
                        "Aktas", "SW_Engineer",
                        "iremgamzeli@gmail.com", PaymentPlan.BANK_TRANSFER, threeDaysLater,
                        LeadStatus.ACTIVE, "new Lead Created", Instant.now())));

        QuarkusMock.installMockForType(mock, LeadRepository.class);

        given().when().get("/efd/api/v1/lead/1").then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("comment", is("new Lead Created"))
                .body("first_name", is("Irem"))
                .body("e_mail", is("iremgamzeli@gmail.com"))
                .body("occupation", is("SW_Engineer"))
                .body("leadStatus",  is(LeadStatus.ACTIVE.toString()));


    }

    @Test
    @Order(4)
    @TestSecurity(authorizationEnabled = false)
    public void testUpdateLead() throws Exception {

        Instant now = Instant.now();
        Instant threeDaysLater = now.plus(3, java.time.temporal.ChronoUnit.DAYS);


        LeadRepository mock = Mockito.mock(LeadRepository.class);
        Mockito.when(mock.findById(Mockito.anyLong()))
                .thenReturn(createLead(threeDaysLater));

        QuarkusMock.installMockForType(mock, LeadRepository.class);


        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .body(LeadAPITestConstants.REQUEST_UPDATE_LEAD_SHORT)
                .when()
                .put("/efd/api/v1/lead/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());

    }

    @Test
    @Order(5)
    @TestSecurity(authorizationEnabled = false)
    public void testFilterWithName() throws Exception {

        Instant now = Instant.now();
        Instant threeDaysLater = now.plus(3, java.time.temporal.ChronoUnit.DAYS);


        LeadRepository mock = Mockito.mock(LeadRepository.class);
        Mockito.when(mock.findLeadByFirstName(Mockito.anyString()))
                .thenReturn(List.of(createLead(threeDaysLater)));

        QuarkusMock.installMockForType(mock, LeadRepository.class);

        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .get("/efd/api/v1/lead?first_name=Irem")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("first_name", hasItem("Irem"))
                .body("last_name", hasItem("Aktas"))
                .body("leadOfferList.car.model", notNullValue())
                .body("leadDeviceSpecificationList.comment", notNullValue())
                .body("leadOfferList.car.brand", notNullValue())
                .body("leadStatus",  notNullValue());
    }
    @Test
    @Order(6)
    @TestSecurity(authorizationEnabled = false)
    public void testFilterWithLastName() throws Exception {

        Instant now = Instant.now();
        Instant threeDaysLater = now.plus(3, java.time.temporal.ChronoUnit.DAYS);


        LeadRepository mock = Mockito.mock(LeadRepository.class);
        Mockito.when(mock.findLeadByLastName(Mockito.anyString()))
                .thenReturn(List.of(createLead(threeDaysLater)));

        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .get("/efd/api/v1/lead?last_name=Aktas")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("first_name", hasItem("Irem"))
                .body("last_name", hasItem("Aktas"))
                .body("leadOfferList.car.model", notNullValue())
                .body("leadDeviceSpecificationList.comment", notNullValue())
                .body("leadOfferList.car.brand", notNullValue())
                .body("leadStatus",  notNullValue());
    }

    @Test
    @Order(7)
    @TestSecurity(authorizationEnabled = false)
    public void testFilterWithLastNameAndFirstName() throws Exception {

        Instant now = Instant.now();
        Instant threeDaysLater = now.plus(3, java.time.temporal.ChronoUnit.DAYS);


        LeadRepository mock = Mockito.mock(LeadRepository.class);
        Mockito.when(mock.findLeadByFirstNameAndLastName(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(List.of(createLead(threeDaysLater)));

        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .get("/efd/api/v1/lead?first_name=Irem&last_name=Aktas")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("first_name", hasItem("Irem"))
                .body("last_name", hasItem("Aktas"))
                .body("leadOfferList.car.model", notNullValue())
                .body("leadDeviceSpecificationList.comment", notNullValue())
                .body("leadOfferList.car.brand", notNullValue())
                .body("leadStatus",  notNullValue());
    }
    private static Lead createLead(Instant threeDaysLater) {
        Car car = new Car("BMW_X5_1235643f34", "BMW X5 AUTO", "2018", "Sport" ,"Jankomir", new BigInteger("1234"));
        car.setId(Long.getLong("1"));
        LeadOffer leadOffer = new LeadOffer(OfferStatus.ACTIVE, new BigDecimal(39000), Currency.EUR);
        leadOffer.setId(Long.getLong("1"));
        leadOffer.setCar(car);
        LeadDeviceSpecification leadDeviceSpecification = new LeadDeviceSpecification(null, Long.getLong("1"), LeadDeviceSpecificationType.TRANSMISSION, "Manuel", SpecificationPriority.MUST_HAVE, Instant.now());
        Lead lead = new Lead("Irem", "Aktas", "SW_Engineer", "iremgamzeli@gmail.com", PaymentPlan.BANK_TRANSFER,threeDaysLater, LeadStatus.ACTIVE,"new Lead Created", Instant.now());
        List<LeadOffer> leadOfferList = new ArrayList<>(List.of(leadOffer));
        lead.setLeadOfferList(leadOfferList);
        List<LeadDeviceSpecification> leadDeviceSpecificationList = new ArrayList<>(List.of(leadDeviceSpecification));
        lead.setLeadDeviceSpecificationList(leadDeviceSpecificationList);
        return lead;
    }
}
