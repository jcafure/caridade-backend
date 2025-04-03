package dev.caridadems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDTO implements Serializable {

    private Integer id;
    private String cep;
    private String street;
    private String number;
    private String complement;
    private String typeAddress;
    private CityDTO city;
}
