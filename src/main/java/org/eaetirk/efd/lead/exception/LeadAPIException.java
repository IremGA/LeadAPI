package org.eaetirk.efd.lead.exception;

import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class LeadAPIException extends RuntimeException {

    private String code;
    private String reason;
    private String operation;
    private Response.Status httpStatus;

    public LeadAPIException(String message) {
        super(message);
    }

    public LeadAPIException(String message, String operation) {
        super(message);
        this.operation = operation;
    }

    public LeadAPIException(String code, String message, String reason, String operation, Response.Status httpStatus ) {
        super(message);
        this.code = code;
        this.reason=reason;
        this.operation = operation;
        this.httpStatus = httpStatus;
    }

}
