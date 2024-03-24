package org.eaetirk.efd.lead.exception;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Data;

@Data
public class ErrorResponse {
    @JsonbProperty("Lead_API_Error_Message")
    private String message;
    @JsonbProperty("Lead_API_Error_Reason")
    private String reason;
    @JsonbProperty("Lead_API_Operation")
    private String operation;
    @JsonbProperty("Lead_API_HttpStatusCode")
    private String code;

}
