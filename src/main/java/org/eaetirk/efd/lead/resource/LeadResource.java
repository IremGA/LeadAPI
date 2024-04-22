package org.eaetirk.efd.lead.resource;

import io.quarkus.oidc.OIDCException;
import io.quarkus.security.Authenticated;
import io.quarkus.security.UnauthorizedException;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.eaetirk.efd.lead.constant.LeadAPIConstant;
import org.eaetirk.efd.lead.exception.ApplicationExceptionMapper;
import org.eaetirk.efd.lead.exception.LeadAPIException;
import org.eaetirk.efd.lead.model.dto.LeadDTO;
import org.eaetirk.efd.lead.service.LeadService;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.List;

import org.hibernate.PropertyValueException;
import org.jboss.logging.Logger;

@Path("/lead")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional(Transactional.TxType.SUPPORTS)
@Tag(name = "Lead API Rest Endpoints")
@Authenticated
public class LeadResource {

    @Inject
    LeadService leadService;
    private static final Logger LOG = Logger.getLogger(LeadResource.class);
    @Inject
    ApplicationExceptionMapper applicationExceptionMapper;

    @GET
    @Path("{id}")
    @Operation(
            summary = "Gets a Lead by ID ",
            description = "This API returns a Lead with specified ID in the request path. " +
                    "When there is no data found with that ID, returns NotFound as a response."
    )
    @Retry(maxRetries = 4, delay = 5000)
    public Response findLeadById(@PathParam("id") Long id){
        LOG.info("GET Lead Operation is Called");
        try{
            LeadDTO lead = leadService.findLeadById(id);
            return Response.ok(lead).build();
        }catch(LeadAPIException apiException){
            LOG.error("Error occurred: {} ", apiException.getMessage(), apiException);
            return applicationExceptionMapper.toResponse(apiException);
        }catch(NotFoundException notFoundException){
            LOG.error("Lead not found: {} ", notFoundException.getMessage(), notFoundException);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Lead Not Found with message : " + notFoundException.getMessage())
                    .build();
        }
    }


    @GET
    @Operation(
            summary = "Retrieves Leads in the repository if there is no query param specified.",
            description = "This API retrieves leads specified exact query params as first_name,  last_name or both (by ignoring case). " +
                    "When there is no data found with specified query params, returns all leads in repository."
    )
    public List<LeadDTO> listLeads(@QueryParam("first_name") String firstName,
                                @QueryParam("last_name") String lastName){
        LOG.info("LIST Leads Operation is Called");
        return  leadService.searchLeadByFirstNameLastName(firstName,lastName );
    }

    @GET
    @Path("/contains")
    @Operation(
            summary = "Retrieves Leads containing specified first_name or last_name ",
            description = "This API retrieves leads containing specified query params first_name,  last_name or both " +
                    "When there is no data found with specified query params, returns null list."
    )
    public List<LeadDTO> listLeadsContains(@QueryParam("first_name") String firstName,
                                @QueryParam("last_name") String lastName){
        LOG.info("LIST Leads Contains Operation is Called");
        return  leadService.searchLeadContainsFirstNameLastName(firstName,lastName );
    }


    @Transactional(Transactional.TxType.REQUIRED)
    @POST
    @Operation(
            summary = "Creates a Lead"
    )
    @APIResponse(responseCode = "201", description = "Lead created successfully")
    @Retry(retryOn = {UnauthorizedException.class, NotFoundException.class, OIDCException.class},maxRetries = 4, delay = 5000)
    @Fallback(fallbackMethod = "createLeadFallBack")
    public Response createLead(LeadDTO lead, @Context UriInfo uriInfo){
        try{
            LOG.info("Create Lead Operation is Called");
            LeadDTO leadDTO = leadService.createLead(lead);
            LeadDTO persistedLead = leadService.findLeadById(leadDTO.id());
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(Long.toString(leadDTO.id()));
            return Response.created(uriBuilder.build()).entity(persistedLead).build();
        }catch (PropertyValueException valueException){
            LeadAPIException leadAPIException = new LeadAPIException(Response.Status.BAD_REQUEST.toString(), "Invalid Request Value","Invalid Request Json",LeadAPIConstant.CREATE_LEAD_OPERATION, Response.Status.BAD_REQUEST);
            LOG.error("Error occurred ", LeadAPIConstant.CREATE_LEAD_OPERATION,valueException);
            return applicationExceptionMapper.toResponse(leadAPIException);
        }
        catch (Exception e){
            LeadAPIException leadAPIException = new LeadAPIException(e.getMessage(), LeadAPIConstant.CREATE_LEAD_OPERATION);
            LOG.error("Error occurred ", LeadAPIConstant.CREATE_LEAD_OPERATION,e);
            return applicationExceptionMapper.toResponse(leadAPIException);
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    @PUT
    @Operation(
            summary = "Updates a Lead"
    )
    @APIResponse(responseCode = "200", description = "Lead updated successfully")
    @Retry(maxRetries = 4, delay = 5000)
    @Fallback(fallbackMethod = "updateLeadFallBack")
    @Path("/{id}")
    public Response updateLead(@PathParam("id") Long id,LeadDTO lead){

        try{
            LOG.info("Update Lead Called ");
            leadService.updateLead(lead, id);
            return Response.ok().build();
        }catch (LeadAPIException leadAPIException){
            LOG.info("Error occurred ", leadAPIException.getMessage(), leadAPIException);
            return applicationExceptionMapper.toResponse(leadAPIException);
        }catch (NotFoundException notFoundException){
            LOG.error("Error occurred  ", notFoundException.getMessage(), notFoundException);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Lead Not Found with message : " + notFoundException.getMessage())
                    .build();
        }catch(Exception e){
            LOG.error("Error occurred ", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }

    }


    @Transactional
    @DELETE
    @Path("{id}")
    @Operation(
            summary = "Deletes a Lead by ID"
    )
    public void deleteLead(@PathParam("id") Long id){
        LOG.info("Delete Lead Called ");
        leadService.deleteLead(id);
    }


    //FallBack Methods
    public Response createLeadFallBack(LeadDTO lead, UriInfo uriInfo) {
        try {
            LOG.info("Create Lead FallBack Operation is Called");
            saveFailedRequest(lead, "createLeadRequestFallBack");
            LeadAPIException leadAPIException = new LeadAPIException("CreateLead Operation Failed, Request is saved to be run later", LeadAPIConstant.CREATE_FALLBACK_OPERATION );
            return applicationExceptionMapper.toResponse(leadAPIException);
        } catch (Exception e) {
            LeadAPIException leadAPIException = new LeadAPIException(e.getMessage(), LeadAPIConstant.CREATE_FALLBACK_OPERATION );
            LOG.error("Error occurred ", LeadAPIConstant.CREATE_FALLBACK_OPERATION, e);
            return applicationExceptionMapper.toResponse(leadAPIException);
        }
    }

    public Response updateLeadFallBack(Long id,LeadDTO lead){

        try{
            LOG.info("Update Lead FallBack Operation is Called");
            String fileName = "updateLeadRequestFallBackLeadID_"+id+"_";
            saveFailedRequest(lead, fileName);
            LeadAPIException leadAPIException = new LeadAPIException("UpdateLead Operation Failed, Request is saved to be run later", LeadAPIConstant.UPDATE_FALLBACK_OPERATION );
            return applicationExceptionMapper.toResponse(leadAPIException);
        }catch(Exception e){
            LOG.error("Error occurred ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }

    }

    private void saveFailedRequest(LeadDTO lead, String fileName) {

        try {
            String leadJSON = JsonbBuilder.create().toJson(lead);
            PrintWriter out = new PrintWriter(fileName + Instant.now().toEpochMilli()+".json");
            out.println(leadJSON);
        } catch (FileNotFoundException e) {
            LOG.error("Error occurred while saving Request File", e);
            throw new RuntimeException(e);
        }
    }
}


