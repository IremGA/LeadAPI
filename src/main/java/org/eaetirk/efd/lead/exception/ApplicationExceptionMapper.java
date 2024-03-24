package org.eaetirk.efd.lead.exception;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class ApplicationExceptionMapper implements ExceptionMapper<LeadAPIException> {

    @Override
    public Response toResponse(LeadAPIException e) {
        ErrorResponse errorResponse =mapAPIExceptionToErrorResponse(e);
        if(e.getHttpStatus() != null){
            return Response.status(e.getHttpStatus())
                    .entity(errorResponse)
                    .build();
        }else{
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();
        }
    }

    private ErrorResponse mapAPIExceptionToErrorResponse(LeadAPIException e) {

        ErrorResponse errorResponse = new ErrorResponse();

        if(e.getMessage() != null){
            errorResponse.setMessage(e.getMessage());
        }else{
            errorResponse.setMessage("Unknown Error Happened");
        }
        if(e.getReason() != null){
            errorResponse.setReason(e.getReason());
        }else{
            errorResponse.setReason("Unknown Error Happened in Lead API");
        }
        if(e.getOperation() != null){
            errorResponse.setOperation(e.getOperation());
        }else{
            errorResponse.setReason("Unknown Operation");
        }
        errorResponse.setCode(e.getCode());

        return errorResponse;
    }
}
