package org.eaetirk.efd.lead.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eaetirk.efd.lead.model.Lead;
import org.eaetirk.efd.lead.repository.LeadRepository;
import org.eaetirk.efd.lead.util.MockDataGenerator;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.stream.IntStream;

@Tag(name = "Mock Lead Generator API")
@Path("/mock-data")
public class MockDataGeneratorResource {
    @Inject
    LeadRepository leadRepository;

    @Operation(
            summary = "Inserts 10 Mock Leads to DB" ,
            description = "This API persists 10 Mock Lead records to be used for testing purpose"
    )
    @GET
    @Path("/generate")
    @Transactional
    public Response generateMockData() {
        MockDataGenerator dataGenerator = new MockDataGenerator();
        IntStream.range(0, 10).forEach(i -> {
            Lead lead = dataGenerator.generateRandomLead();
            leadRepository.persist(lead);
        });
        List<Lead> leads = leadRepository.listAll();
        return Response.ok("Mock Lead Data Generated. Number of leads in the repository : " + leads.size()).build();
    }
}