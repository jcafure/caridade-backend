package dev.caridadems.model;

import dev.caridadems.domain.TypeAddress;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address extends BaseEntity{

    private String cep;
    private String street;
    private String number;
    private String complement;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "charityGroup_id")
    private CharityGroup charityGroup;

    @Enumerated(EnumType.ORDINAL)
    private TypeAddress typeAddress;
}
