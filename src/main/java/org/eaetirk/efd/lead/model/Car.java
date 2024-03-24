package org.eaetirk.efd.lead.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Car extends Device{

    @Id
    @GeneratedValue
    Long id;

    BigInteger kilometer;

    public Car(String model, String brand, String year, String style, String location,BigInteger kilometer) {
        super(model, brand, year, style,location);
        this.kilometer = kilometer;
    }



}
