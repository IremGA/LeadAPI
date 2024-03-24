package org.eaetirk.efd.lead.constant;

public class LeadAPIConstant {

    public static final String UPDATE_LEAD_OPERATION = "Update Lead";
    public static final String CREATE_LEAD_OPERATION = "Create Lead";
    public static final String GET_LEAD_OPERATION = "Get Lead";
    public static final String ERROR_WHILE_UPDATE_LEAD = "Error while updating Lead";
    public static final String ERROR_LEAD_ID_MISSING = "Lead ID is missing";
    public static final String ERROR_LEAD_ID_MISSING_REASON = "Lead ID is not provided in the request";
    public static final String ERROR_NO_DEVICE_SPEC_FOUND = "No Device Specification Found";
    public static final String ERROR_NO_DEVICE_SPEC_FOUND_REASON ="There is no existing Lead Device Specification Found with requested Device Specification ID";
    public static final String ERROR_NO_DEVICE_SPEC_ID_FOUND = "No Device Specification ID Found";
    public static final String ERROR_NO_DEVICE_SPEC_ID_FOUND_REASON = "Lead Device Specification ID must be defined in the update Request";

    public static final String ERROR_NO_LEAD_OFFER_ID_FOUND ="No Lead Offer ID Found";
    public static final String ERROR_NO_LEAD_OFFER_ID_FOUND_REASON = "Lead Offer ID must be defined In the update Request";

    public static final String ERROR_NO_LEAD_OFFER_FOUND ="No Lead Offer Found";
    public static final String ERROR_NO_LEAD_OFFER_FOUND_REASON ="There is no existing Lead Offer Found with requested offer ID";

    public static final String QUERY_LIST_BY_FIRSTNAME_ORDER_BY_LAST_NAME = "LOWER(firstName) = ?1 ORDER BY lastName";
    public static final String QUERY_LIST_BY_LASTNAME_ORDER_BY_FIRST_NAME = "LOWER(lastName) = ?1 ORDER BY firstName";
    public static final String QUERY_LIST_BY_LASTNAME_AND_FIRST_NAME ="LOWER(firstName) = ?1 AND LOWER(lastName) = ?2";
    public static final String QUERY_LIST_CONTAINS_CHAR_IN_FIRSTNAME_ORDER_BY_FIRST_NAME ="LOWER(firstName) like ?1 ORDER BY firstName ";
    public static final String QUERY_LIST_CONTAINS_CHAR_IN_LASTNAME_ORDER_BY_LAST_NAME ="LOWER(lastName) like ?1 ORDER BY lastName ";
    public static final String QUERY_LIST_CONTAINS_CHAR_IN_LASTNAME_OR_FIRSTNAME_ORDER_BY_LAST_NAME ="LOWER(firstName) like ?1 OR LOWER(lastName) like ?2 ORDER BY lastName ";


    public static final String CREATE_FALLBACK_OPERATION = "CREATE_FALLBACK_OPERATION";
    public static final String UPDATE_FALLBACK_OPERATION = "UPDATE_FALLBACK_OPERATION";
}
