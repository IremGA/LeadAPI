package org.eaetirk.efd.lead.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
