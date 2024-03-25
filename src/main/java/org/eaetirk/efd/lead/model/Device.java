package org.eaetirk.efd.lead.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@MappedSuperclass
public class Device {

    @Id
    @GeneratedValue
    Long id;
    String model;
    String brand;
    String creation_year;
    String style;
    String location;

    public Device(String model, String brand, String creation_year, String style, String location) {

        this.model = model;
        this.brand = brand;
        this.creation_year = creation_year;
        this.style = style;
        this.location =location;
    }
}
