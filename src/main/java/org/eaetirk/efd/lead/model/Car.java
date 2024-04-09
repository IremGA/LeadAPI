package org.eaetirk.efd.lead.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Car extends Vehicle {

    BigInteger kilometer;

    public Car(String model, String brand, String year, String style, String location,BigInteger kilometer) {
        super(model, brand, year, style,location);
        this.kilometer = kilometer;
    }



}
