package org.eaetirk.efd.lead.model;


import java.math.BigDecimal;
import java.math.RoundingMode;


public class Money {
    private BigDecimal amount;
    private Currency currency;


    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
    public Money add(Money money) {
        if (!this.currency.equals(money.getCurrency())) {
            throw new IllegalArgumentException("Currencies don't match.");
        }
        BigDecimal result = this.amount.add(money.getAmount());
        return new Money(result, this.currency);
    }

    public Money subtract(Money money) {
        if (!this.currency.equals(money.getCurrency())) {
            throw new IllegalArgumentException("Currencies don't match.");
        }
        BigDecimal result = this.amount.subtract(money.getAmount());
        return new Money(result, this.currency);
    }

    public Money multiply(BigDecimal multiplier) {
        BigDecimal result = this.amount.multiply(multiplier);
        return new Money(result, this.currency);
    }

    public Money divide(BigDecimal divisor) {
        BigDecimal result = this.amount.divide(divisor, RoundingMode.HALF_EVEN);
        return new Money(result, this.currency);
    }

    @Override
    public String toString() {
        return amount.setScale(2, RoundingMode.HALF_EVEN) + " " + currency;
    }

}
