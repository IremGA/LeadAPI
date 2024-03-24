package org.eaetirk.efd.lead.model;

public enum PaymentPlan {

    BANK_TRANSFER("BT"),
    CASH("C"),
    BUY_NOW_PAY_LATER_COMPANY("BNPL_C"),
    BUY_NOW_PAY_LATER_BANK("BNPL_B");


    private final String abbreviation;

    private PaymentPlan(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
